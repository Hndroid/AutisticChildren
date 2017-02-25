package com.zhuxiyungu.autisticchildren.mvp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhuxiyungu.autisticchildren.R;
import com.zhuxiyungu.autisticchildren.ui.ChildModelActivity;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by null on 17-2-23.
 * <p>
 * 家长模式的主Activity
 */

public class ParentModeActivity extends AutoLayoutActivity implements View.OnClickListener {

    @BindView(R.id.image_head)
    ImageView imageHead;   //头像
    @BindView(R.id.individual)
    TextView individual;    //个人信息
    @BindView(R.id.count)
    TextView count;         //套题
    @BindView(R.id.star_answer)
    TextView starAnswer;    //开始答题
    @BindView(R.id.add_device)
    TextView addDevice;     //添加设备
    @BindView(R.id.choose_voice)
    TextView chooseVoice;   //选择语音
    @BindView(R.id.check_updata)
    TextView checkUpdata;       //检查更新
    @BindView(R.id.feed_back)
    TextView feedBack;         //意见反馈
    @BindView(R.id.about)
    TextView about;     //关于
    @BindView(R.id.logout)
    Button logout;          //注销
    @BindView(R.id.swipe_refresh_lauout)
    SwipeRefreshLayout swipeRefreshLauout;      //下拉更新

    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_mode_activity_layout);
        ButterKnife.bind(this);
        context = this;

        //获取头像
        Glide.with(this).load(R.mipmap.zhuce)
                .bitmapTransform(new CropCircleTransformation(context)).into(imageHead);

        starAnswer.setOnClickListener(this);
        imageHead.setOnClickListener(this);
        individual.setOnClickListener(this);
        addDevice.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_head://头像
                break;
            case R.id.individual://个人信息
                break;
            case R.id.count://选择套题
                break;
            case R.id.star_answer://选择答题
                Intent intent = new Intent(context, ChildModelActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.add_device://添加设备
                getFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.container, new AddDeviceFragment())
                        .commit();
                break;
            case R.id.choose_voice://选择语音
                break;
            case R.id.check_updata://检查更新
                break;
            case R.id.feed_back://意见反馈
                break;
            case R.id.about://关于
                break;
            case R.id.logout://注销帐号
                break;
        }
    }
}
