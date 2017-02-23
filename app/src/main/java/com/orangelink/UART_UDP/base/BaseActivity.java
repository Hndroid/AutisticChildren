package com.orangelink.UART_UDP.base;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.androidquery.util.AQUtility;
import com.orangelink.UART_UDP.util.ActivityManager;
import com.orangelink.UART_UDP.util.LogUtil;
import com.zhuxiyungu.autisticchildren.R;

public class BaseActivity extends FragmentActivity {

	public Handler handler = new Handler();
	protected BaseActivity context;
	private boolean isDestroy = true;
	public LogUtil log = new LogUtil(getClass().getSimpleName());
	private View mLoadingBar = null;
	protected ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.setContentView(R.layout.load_activity);
		context = this;
		isDestroy = false;
		ActivityManager.getInstance().addActivity(this);
	}


	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
//		outState.putSerializable("loginUser", CommonData.loginUser);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
//		CommonData.loginUser = (RepLoginBean) savedInstanceState.getSerializable("loginUser");
	}

	@Override
	protected void onDestroy() {
		dismissProgress();
		isDestroy = true;
		super.onDestroy();
		ActivityManager.getInstance().getActivityList().remove(this);
		if (isTaskRoot()) {
			long triggerSize = 20000000; // 大于20M时候开始清除
			long targetSize = 5000000; // 直到少于5M
			AQUtility.cleanCacheAsync(this, triggerSize, targetSize);
		}
	}

	public void showProgress() {
		if (isDestroy) {
			return;
		}
		if (progressDialog == null) {
			progressDialog = ProgressDialog.show(this, "", getResources().getString(R.string.wait_later));
			progressDialog.setCanceledOnTouchOutside(false);
		} else {
			progressDialog.show();
		}
	}

	public void dismissProgress() {
		if (isDestroy) {
			return;
		}
		if (handler != null) {
			handler.post(new Runnable() {
				@Override
				public void run() {
					if (null != progressDialog) {
						try {
							progressDialog.dismiss();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			});
		}

	}

}
