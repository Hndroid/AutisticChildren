package com.zhuxiyungu.autisticchildren.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.zhuxiyungu.autisticchildren.R;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;

/**
 * Created by null on 17-2-18.
 */

public class WrongActivity extends AutoLayoutActivity {

    private Context context;
    private final String WRONG = "答错了！继续努力吧！";
    private SpeechSynthesizer mTts;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置为全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.wrong_activity_layout);
        ButterKnife.bind(this);
        //设置屏幕为横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        context = this;

        mTts = SpeechSynthesizer.createSynthesizer(context, null);
        setSynthesisParameters();
        mTts.startSpeaking(WRONG, synthesizerListener);


        countDown();

    }

    //这个过程为出糖果的过程
    public void countDown() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(context, ChildModelActivity.class));
                finish();
            }
        }, 5000);
    }

    //2.合成语音参数设置
    public void setSynthesisParameters() {
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan"); //设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "30");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "100");//设置音量,范围 0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
    }

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

        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };

}
