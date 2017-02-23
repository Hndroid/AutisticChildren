package com.orangelink.UART_UDP.base;

import java.io.File;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.BitmapAjaxCallback;
import com.androidquery.util.AQUtility;
import com.kwapp.net.fastdevelop.imagecache.FDImageLoader;
import com.orangelink.UART_UDP.common.AppCrashHandler;
import com.orangelink.UART_UDP.util.LogUtil;

public class BaseApplication extends Application {

	private LogUtil log = new LogUtil(getClass().getSimpleName());
	public static Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		AppCrashHandler crashHandler = AppCrashHandler.getInstance();
		crashHandler.init(getApplicationContext());

		AjaxCallback.setNetworkLimit(24);
		AjaxCallback.setTimeout(15000);
		BitmapAjaxCallback.setCacheLimit(100);
		context = this;

		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			String path = Environment.getExternalStorageDirectory() + "/readilytake/aquerycache/";
			File f = new File(path);
			AQUtility.setCacheDir(f);
		}
	}


	FDImageLoader fdImageLoader;

	public FDImageLoader getFdImageLoader() {
		return fdImageLoader;
	}


	@Override
	public void onLowMemory() {
		super.onLowMemory();
		BitmapAjaxCallback.clearCache();
	}

}
