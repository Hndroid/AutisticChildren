package com.zhuxiyungu.autisticchildren.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.zhuxiyungu.autisticchildren.R;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by null on 17-2-20.
 */

public class NoTalkingActivity extends AutoLayoutActivity {
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置为全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //设置屏幕为横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.no_talking_activity_layout);
        context = this;
        countDown();
    }

    public void countDown() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(context, ChildModelActivity.class));
                finish();
            }
        }, 5000);
    }
}
