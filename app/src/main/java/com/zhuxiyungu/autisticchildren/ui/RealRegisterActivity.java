package com.zhuxiyungu.autisticchildren.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.zhuxiyungu.autisticchildren.R;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by null on 17-2-21.
 */

public class RealRegisterActivity extends AutoLayoutActivity {
    @BindView(R.id.input_userName)
    MaterialEditText inputUserName;
    @BindView(R.id.input_password)
    MaterialEditText inputPassword;
    @BindView(R.id.skip)
    Button skip;
    @BindView(R.id.register)
    Button register;
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置为全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.real_register_activity_layout);
        context = this;
        //设置屏幕为横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ButterKnife.bind(this);


        sharedPreferences = context.getSharedPreferences("userinfoi", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        //注册按钮实现事件
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("username", inputUserName.getText().toString().trim());
                editor.putString("password", inputPassword.getText().toString().trim());
                editor.commit();
                Intent intent = new Intent(context, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChildModelActivity.class);
                startActivity(intent);
            }
        });
    }
}
