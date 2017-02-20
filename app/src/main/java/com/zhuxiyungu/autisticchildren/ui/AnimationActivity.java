package com.zhuxiyungu.autisticchildren.ui;

import android.content.Context;
import android.content.Intent;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置为全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.animation_activity_layout);
        context = this;
        //设置屏幕为横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ButterKnife.bind(this);


        showFlash();
        //倒计时ui

        countDown();
    }

    //跳转注册页面
    // TODO: 或者实现跳转儿童模式
    public void countDown() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(context, RegisterActivity.class);
                startActivity(intent);
                finish();
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
}
