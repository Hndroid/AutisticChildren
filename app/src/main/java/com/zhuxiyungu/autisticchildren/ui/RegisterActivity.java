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
import android.widget.CheckBox;
import android.widget.ImageView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.zhuxiyungu.autisticchildren.R;
import com.zhuxiyungu.autisticchildren.mvp.view.ParentModeActivity;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *  这是一个登录界面的activity
 * Created by null on 17-2-17.
 */

public class RegisterActivity extends AutoLayoutActivity {
    @BindView(R.id.bg_imageview)
    ImageView bgImageview;
    @BindView(R.id.skip)
    Button skip;
    @BindView(R.id.input_userName)
    MaterialEditText inputUserName;
    @BindView(R.id.register)
    Button register;
    @BindView(R.id.logup)
    Button logup;
    @BindView(R.id.remenber_password)
    CheckBox remenberPassword;
    @BindView(R.id.input_password)
    MaterialEditText inputPassword;
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

        setContentView(R.layout.login_activity_layout);
        ButterKnife.bind(this);
        //设置屏幕为横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        context = this;

        sharedPreferences = context.getSharedPreferences("userinfoi", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        inputUserName.setText(sharedPreferences.getString("username", "").toString().trim());
        inputPassword.setText(sharedPreferences.getString("password", "").toString().trim());


        //登录按钮
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (remenberPassword.isChecked() == true){
                    editor.putBoolean("isCheck", true);
                    editor.commit();
                }
                //这里直接进入到儿童模式
                /*Intent intent = new Intent(context, ChildModelActivity.class);
                startActivity(intent);
                finish();*/

                // TODO: 17-2-21 这里写的是登录到家长的界面
                //这里进入家长模式
                Intent intent = new Intent(context, ParentModeActivity.class);
                startActivity(intent);
                finish();

            }
        });


        //点击注册实现事件
        //[1]跳转到注册的界面
        logup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RealRegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    //方法实现逻辑注册逻辑
    //[1]判断用户是否选择保存密码
    //[1.1]如果用户选择保存密码，则写进xml文件中保存下来
    //[1.2]如果用户不选择保存，则不写进
    public void realizingLogicInRegister() {
        if (remenberPassword.isChecked()) {
            editor.putString(inputUserName.getText().toString(), "");
            editor.putString(inputPassword.getText().toString(), "");
            editor.commit();
        }
    }

}
