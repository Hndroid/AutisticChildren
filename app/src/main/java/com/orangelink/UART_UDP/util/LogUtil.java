package com.orangelink.UART_UDP.util;

import com.orangelink.UART_UDP.common.CommonData;

import android.util.Log;

public class LogUtil {
	
	private String tag;
	
	public LogUtil(String tag) {
		this.tag = tag;
	}

	public void i(String msg) {
		if (CommonData.DEBUG) {
			Log.i(tag, msg);
		}
	}

	public void v(String msg) {
		if (CommonData.DEBUG) {
			Log.v(tag, msg);
		}
	}

	public void d(String msg) {
		if (CommonData.DEBUG) {
			Log.d(tag, msg);
		}
	}

	public void w(String msg) {
		if (CommonData.DEBUG) {
			Log.w(tag, msg);
		}
	}

	public void e(String msg) {
		if (CommonData.DEBUG) {
			Log.e(tag, msg);
		}
	}

	public void e(String msg, Throwable tr) {
		if (CommonData.DEBUG) {
			Log.e(tag, msg, tr);
		}
	}
}
