package com.orangelink.UART_UDP.util;

import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class IMMManageUtil {
	/**
	 * 根据view显示输入法
	 * 
	 * @param ctx
	 * @param view
	 */
	public static void showIMM(Context ctx, View view) {
		InputMethodManager imm = (InputMethodManager) ctx
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(view, InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * 隐藏输入法
	 * 
	 * @param ctx
	 * @param token
	 *            getCurrentFocus().getWindowToken()
	 */
	public static void hideIMM(Context ctx, IBinder token) {
		InputMethodManager imm = (InputMethodManager) ctx
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(token,
				InputMethodManager.RESULT_UNCHANGED_SHOWN);
	}
}
