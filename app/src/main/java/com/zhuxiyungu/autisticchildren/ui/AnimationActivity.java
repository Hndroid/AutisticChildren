package com.zhuxiyungu.autisticchildren.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.zhuxiyungu.autisticchildren.R;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by null on 17-2-17.
 * 开机动画的配置Activity
 */

public class AnimationActivity extends AutoLayoutActivity {
    @BindView(R.id.animation)
    ImageView animation;
    @BindView(R.id.word)
    ImageView word;
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String username;
    private String password;
    private boolean isCheck;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置为全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.animation_activity_layout);
        //设置屏幕为横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ButterKnife.bind(this);
        context = this;

        //使用sharedPreferences存储登录信息
        //1.通过Context对象创建一个SharedPreference对象
        sharedPreferences = context.getSharedPreferences("userinfoi", Context.MODE_PRIVATE);
        //2.通过sharedPreference获取存放的数据
        username = sharedPreferences.getString("username", "");
        password = sharedPreferences.getString("password", "");
        isCheck = sharedPreferences.getBoolean("isCheck", false);

        showFlash();
        //倒计时ui
        // TODO: 17-2-21 暂这里时写在
        countDown();
    }

    //跳转注册页面
    // TODO: 或者实现跳转儿童模式
    public void countDown() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                realizinglogic();
            }
        }, 5000);
    }

    //设置引导动画界面
    public void showFlash() {
        ScaleAnimation sa = new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(2000); //设置动画执行的时间
        sa.setRepeatCount(3); //设置重复的次数
        sa.setRepeatMode(Animation.REVERSE);//设置动画执行的模式
        word.startAnimation(sa);
    }

    //进入app引导界面的实现逻辑
    //[1]检测是否是第一次打开app，如果是第一次打开app的话，直接进入注册界面
    //[2]如果是第二次打开app，则判断是否是已经记住密码（默认选择不记住密码）
    //[2.1]如果用户选择记住密码，则直接进入儿童模式
    //[2.2]如果用户选择不记住密码，则进入登录界面
    public void realizinglogic(){
        if (username == "" && password == ""){
            Intent intent = new Intent(context, RealRegisterActivity.class);
            // TODO: 17-2-20 这的 RegisterActivity.class 需要注意更改
            startActivity(intent);
            finish();
        }else {
            if (isCheck == true){
                Intent intent = new Intent(context, ChildModelActivity.class);
                startActivity(intent);
                finish();
            }else {
                Intent intent = new Intent(context, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

}
