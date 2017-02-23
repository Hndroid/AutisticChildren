package com.orangelink.UART_UDP.net;


import org.json.JSONObject;

import com.orangelink.UART_UDP.base.BaseActivity;
import com.orangelink.UART_UDP.common.CommonData;

import android.widget.Toast;


public abstract class HttpListener {
	
	private BaseActivity context;
	
	public HttpListener() {
	}
	
	public HttpListener(BaseActivity context) {
		this.context = context;
	}
	
	/**
	 * 开始请求
	 * @throws Exception
	 */
	public void onStart() throws Exception {};
	
	/**
	 * 请求数据
	 * @param jsonObject
	 * @throws Exception
	 */
	public void onResult(JSONObject jsonObject) throws Exception {
		if (context != null) {
			context.dismissProgress();
		}
	};
	
	/**
	 * 请求失败
	 * @param code
	 * @param message
	 * @throws Exception
	 */
	public void onFail(String code, String message) throws Exception {
		if (context != null) {
			if ("-10001".equals(code)) {
				Toast.makeText(context, CommonData.NETWORK_ERROR, Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			}
		}
	};
	
	/**
	 * 请求成功
	 * @param jsonObject
	 * @throws Exception
	 */
	public abstract void onComplete(JSONObject jsonObject) throws Exception;
}
