package com.orangelink.UART_UDP.net;

import java.util.Map;


import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.orangelink.UART_UDP.common.CommonData;
import com.orangelink.UART_UDP.util.LogUtil;

public class HttpTool {
	
	private static LogUtil log = new LogUtil("HttpTool");

	public static void doPost(Context context, String url, Map<String, ?> params, final HttpListener listener) {
		final AQuery aq = new AQuery(context);
		try {
			listener.onStart();
		} catch (Exception e) {
			e.printStackTrace();
		}
		aq.ajax(url, params, JSONObject.class, new AjaxCallback<JSONObject>(){
			@Override
			public void callback(String url, JSONObject jsonObject, AjaxStatus status) {
				try {
					listener.onResult(jsonObject);
				} catch (Exception e) {
					e.printStackTrace();
				}

				Log.v("orangelink_net", url);
				Log.v("orangelink_net", params.toString());


				if (status.getCode() == 200 && jsonObject != null) {
					try {
						Log.v("orangelink_net", "200");
						Log.v("orangelink_net", jsonObject.toString());
						listener.onComplete(jsonObject);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					Log.v("orangelink_net", "不是200:"+status.getCode());
					try {
						listener.onFail("-10000", CommonData.NETWORK_ERROR);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
	
}
