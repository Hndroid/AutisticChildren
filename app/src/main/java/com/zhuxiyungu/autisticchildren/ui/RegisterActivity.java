package com.zhuxiyungu.autisticchildren.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.zhuxiyungu.autisticchildren.R;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by null on 17-2-17.
 */

public class RegisterActivity extends AutoLayoutActivity {
    @BindView(R.id.bg_imageview)
    ImageView bgImageview;
    @BindView(R.id.skip)
    Button skip;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置为全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.login_activity_layout);
        ButterKnife.bind(this);
        //设置屏幕为横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        context = this;

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChildModelActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
