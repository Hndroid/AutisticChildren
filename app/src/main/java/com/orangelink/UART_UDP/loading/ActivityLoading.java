package com.orangelink.UART_UDP.loading;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.orangelink.UART_UDP.base.BaseActivity;
import com.orangelink.UART_UDP.device.EsptouchActivity;
import com.zhuxiyungu.autisticchildren.R;

public class ActivityLoading extends BaseActivity {
	private static final long SPLASH_DELAY_MILLIS = 3000;//定义停留时间
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);

		PackageInfo pi = null;
		try {
			pi = getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
			String curVersion = pi.versionName;
			((TextView)findViewById(R.id.myapp)).setText("UART TEST" + curVersion);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}


		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
					Intent intent = new Intent(context, EsptouchActivity.class);
					startActivity(intent);
					finish();
//				}

			}
		}, SPLASH_DELAY_MILLIS);//设置停留时间
	}













}
