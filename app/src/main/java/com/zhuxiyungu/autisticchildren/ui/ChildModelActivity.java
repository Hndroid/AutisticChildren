package com.zhuxiyungu.autisticchildren.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.zhuxiyungu.autisticchildren.R;
import com.zhuxiyungu.autisticchildren.bean.Question;
import com.zhuxiyungu.autisticchildren.bean.QuestionBean;
import com.zhuxiyungu.autisticchildren.util.JsonParserUtil;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by null on 17-2-17.
 */

public class ChildModelActivity extends AutoLayoutActivity implements Serializable{

    private static final String TAG = "ChildModelActivity";
    @BindView(R.id.show_question_textview)
    TextView showQuestionTextview;
    private Context context;
    private SpeechSynthesizer mTts;
    private SpeechRecognizer mIat;
    ArrayList<Question> arrayList = new ArrayList<Question>();

    private int i = 0;
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    private SharedPreferences mSharedPreferences;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    //识别的语音结果
    private String result = null;
    private SharedPreferences sharedPreferences;


    //语音合成监听器（朗读规则）
    private SynthesizerListener synthesizerListenerForRule = new SynthesizerListener() {
        @Override
        public void onSpeakBegin() {

        }

        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {

        }

        @Override
        public void onSpeakPaused() {

        }

        @Override
        public void onSpeakResumed() {

        }

        @Override
        public void onSpeakProgress(int i, int i1, int i2) {

        }

        @Override
        public void onCompleted(SpeechError speechError) {
            //调用函数开始合成语音
            startSynthesis();
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置为全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //设置屏幕为横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.child_mode_activity_layout);
        mSharedPreferences = getSharedPreferences("com.iflytek.setting", Activity.MODE_PRIVATE);
        ButterKnife.bind(this);
        context = this;

        //初始化即创建语音配置对象
        SpeechUtility.createUtility(context, SpeechConstant.APPID + "=58afec48");

        //1.通过Context对象创建一个SharedPreference对象
        sharedPreferences = context.getSharedPreferences("answer_times", Context.MODE_PRIVATE);
        //2.通过sharedPreferences对象获取一个Editor对象
        editor = sharedPreferences.edit();
        //3.往Editor中添加数据
        //4.提交Editor对象



        //[1]创建SpeechRecognizer对象,第二个参数:本地识别时传InitListener
        mIat = SpeechRecognizer.createRecognizer(context, null);

        //[2]语音识别参数设置
        setSpeechParameters();

        //1.创建 SpeechSynthesizer 对象, 第二个参数:本地合成时传 InitListener
        mTts = SpeechSynthesizer.createSynthesizer(context, null);
        //2.合成参数设置
        setSynthesisParameters();

    }

    //在界面获取焦点的时候调用下面的方法
    @Override
    protected void onResume() {
        super.onResume();
        //游戏规则;
        if (i == 0) {
            gameRule();
        } else {
            i = sharedPreferences.getInt("FLAG", 0);
            startSynthesis();
        }
    }

    //[2]语音识别参数设置
    public void setSpeechParameters() {
        //设置听写参数,详见《MSC Reference Manual》SpeechConstant类
        mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");
        // 设置听写引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");
        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString("iat_vadbos_preference", "5000"));

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString("iat_vadeos_preference", "1500"));
    }

    ///听写监听器
    private RecognizerListener mRecoListener = new RecognizerListener() {
        //听写结果回调接口(返回Json格式结果,用户可参见附录13.1);
        //一般情况下会通过onResults接口多次返回结果,完整的识别内容是多次结果的累加;
        //关于解析Json的代码可参见Demo中JsonParser类;
        //isLast等于true时会话结束。
        public void onResult(RecognizerResult results, boolean isLast) {
            result = parsingJnson(results);
            Log.d(TAG, "onResult: " + result);

        }

        //会话发生错误回调接口
        public void onError(SpeechError error) {
            //打印错误码描述
            Log.d(TAG, "error:" + error.getPlainDescription(true));
        }

        //开始录音
        public void onBeginOfSpeech() {
        }

        //volume音量值0~30,data音频数据
        public void onVolumeChanged(int volume, byte[] data) {
        }

        //结束录音
        public void onEndOfSpeech() {
            if (result != null) {
                //语音不为空的情况，即可以接收到儿童的回答
                /*Log.d(TAG, "onResult: " + result.contains(arrayList.get(i).answer));*/
                //接收到语音处理的问题
                jumpPage();
            } else {
                //语音为空的情况，即接收不到儿童的回答
                Intent intent = new Intent(context, NoTalkingActivity.class);
                ++i;
                if (i < arrayList.size()){
                    editor.putInt("FLAG", i);
                    editor.commit();
                }else {
                    // TODO: 17-2-20 这里实现超过问题数的逻辑
                    editor.clear();
                    editor.putInt("FLAG", 0);
                    editor.commit();
                }
                startActivity(intent);
            }
        }

        //扩展用接口
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
        }
    };

    //2.合成语音参数设置
    public void setSynthesisParameters() {
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan"); //设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "30");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "100");//设置音量,范围 0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
    }

    //3.语音开始合成问题
    public void askingQuestion(Question question) {
        showQuestionTextview.setText(question.question_context.toString().trim());
        mTts.startSpeaking(question.question_context.trim(), synthesizerListener);
    }

    //合成语音的监听器
    private SynthesizerListener synthesizerListener = new SynthesizerListener() {


        @Override
        public void onSpeakBegin() {

        }

        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {

        }

        @Override
        public void onSpeakPaused() {

        }

        @Override
        public void onSpeakResumed() {

        }

        @Override
        public void onSpeakProgress(int i, int i1, int i2) {

        }

        @Override
        public void onCompleted(SpeechError speechError) {
            //问题语音合成完成以后，就开始等待儿童语音输入
            waitToSpeek();
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };

    /*
    * 以下是问答逻辑的具体实现
    *
    *
    * */

    //[1]跳转到该页面的时候首先介绍游戏规则
    public void gameRule() {
        Log.d(TAG, "gameRule: ++++++++++++++++++++++++++++++++++++++++++++++++++");
        mTts.startSpeaking("小朋友，你好吗？我是超级无敌可爱的小叮当，回答我的问题，答对了就奖励一颗糖果，想不想吃？", synthesizerListenerForRule);
    }

    //[2]开始合成问题的语音
    public void startSynthesis() {
        arrayList = QuestionBean.bean();
        askingQuestion(arrayList.get(i));
    }

    //[3]等待答案的儿童答案语音的录入
    public void waitToSpeek() {
        mIat.startListening(mRecoListener);

    }


    //解析JNSON
    private String parsingJnson(RecognizerResult results) {
        String text = JsonParserUtil.parseIatResult(results.getResultString());

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }

        return resultBuffer.toString().trim();
    }

    //接收到语音后，判断跳转
    public void jumpPage() {
        Log.d("dingdang", result);
        Log.d("dingdang", arrayList.get(i).answer);

        if (result.contains(arrayList.get(i).answer)) {
            //接收儿童正确的语音后执行的逻辑
            Intent intent = new Intent(context, RightActivity.class);
            ++i;
            Bundle bundle = new Bundle();
            if (i < arrayList.size()){
                editor.putInt("FLAG", i);
                editor.commit();
            }else {
                // TODO: 17-2-20 这里实现超过问题数的逻辑
                editor.clear();
                editor.putInt("FLAG", 0);
                editor.commit();
            }
            startActivity(intent);
        } else {
            //接收儿童错误的逻辑
            Intent intent = new Intent(context, WrongActivity.class);
            ++i;
            if (i < arrayList.size()){
                editor.putInt("FLAG", i);
                editor.commit();
            }else {
                // TODO: 17-2-20 这里实现超过问题数的逻辑
                editor.clear();
                editor.putInt("FLAG", 0);
                editor.commit();
            }
            startActivity(intent);
        }
    }

    //当用户按返回键的时候注销资源
    @Override
    public void onBackPressed() {
        mTts.stopSpeaking();
        mTts.destroy();
        mIat.cancel();
        mIat.destroy();
        super.onBackPressed();
    }
}
