package com.orangelink.UART_UDP.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;

import com.kwapp.net.fastdevelop.listener.FDNetworkExceptionListener;
import com.kwapp.net.fastdevelop.utils.FDJsonUtils;
import com.kwapp.net.fastdevelop.utils.FDNetUtil;
import com.orangelink.UART_UDP.base.BaseApplication;
import com.orangelink.UART_UDP.common.Constants;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 访问网络管理工具
 * 
 * @author yi
 * 
 */
public class NetManagement implements Callback {

	static NetManagement netManagement;
	static Context context;
	Handler mHandler;
	Dialog dialog;
	String dialogText;// 是否弹框
	HashMap<Context, JsonThread> JsonThreadMap;
	HashMap<Context, PostThread> PostThreadMap;

	public static final int LOAD_START = 2001;
	public static final int LOAD_SUCCESS = 2002;
	public static final int LOAD_FAIL = 2003;
	boolean isToast=false;
	private ExecutorService executorService;

	private NetManagement() {
		super();
		mHandler = new Handler(this);
		PostThreadMap=new HashMap<Context, NetManagement.PostThread>();
		JsonThreadMap=new HashMap<Context, NetManagement.JsonThread>();
		executorService = Executors.newFixedThreadPool(10);//只有10个线�?
	}

	/**
	 * 单例模式
	 * 
	 * @param context
	 * @return
	 */
	public static NetManagement getNetManagement(Context context) {
		if (netManagement == null) {
			netManagement = new NetManagement();
		}
		NetManagement.context = context;
		return netManagement;
	}

	/**
	 * 
	 */
	public void getJson(Handler handler, String[] keys, String[] values,
			String url, String dialogText) {
		
		String phpsessid = null;
		if(NetManagement.context != null){
			if(NetManagement.context instanceof Activity) {
				Activity act = (Activity)NetManagement.context;
				BaseApplication application = (BaseApplication) act.getApplication();
//				phpsessid = application.getPHPSESSID();
			}
		}
		
		if(phpsessid != null && !phpsessid.isEmpty()) {
			String cookie = "PHPSESSID="+phpsessid;
			getJsonPostCookie(handler, keys, values, url, cookie, dialogText);
		} else {
			for(int i=0;i<keys.length;i++)
			{
				System.out.println(keys[i]+"::::"+values[i]);
			}
			isToast=false;
			this.dialogText = dialogText;
			JsonThread jsonThread = new JsonThread(keys, values, url, handler);
			executorService.submit(jsonThread);
			JsonThreadMap.put(context, jsonThread);
		}
	}
	
	/**
	 * 取
	 */
	public void getJson(Handler handler, 
			String url, String dialogText) {
		
		String phpsessid = null;
		if(NetManagement.context != null){
			if(NetManagement.context instanceof Activity) {
				Activity act = (Activity)NetManagement.context;
				BaseApplication application = (BaseApplication) act.getApplication();
//				phpsessid = application.getPHPSESSID();
			}
		}
		
		if(phpsessid != null && !phpsessid.isEmpty()) {
			String cookie = "PHPSESSID="+phpsessid;
			getJsonPostCookie(handler, null, null, url, cookie, dialogText);
		} else {
			isToast=false;
			this.dialogText = dialogText;
			JsonThread jsonThread = new JsonThread(null, null, url, handler);
			executorService.submit(jsonThread);
			JsonThreadMap.put(context, jsonThread);
		}
	}
	/**
	 * 
	 */
	public void getJsonPostCookie(Handler handler, String[] keys, String[] values,
			String url,String cookie, String dialogText) {
		isToast=false;
		this.dialogText = dialogText;
		JsonThread jsonThread = new JsonThread(keys, values, url,cookie, handler);
		executorService.submit(jsonThread);
		JsonThreadMap.put(context, jsonThread);
	}
	/**
	 * 
	 */
	public void getJsonAndCookie(Handler handler, String[] keys, String[] values,String url, Object dialogText) {
		isToast=false;
		
		
			
			if (dialogText instanceof String) {
				dialogText=dialogText.toString();
			} else if (dialogText instanceof Integer) {
				dialogText=((Integer) dialogText);
			}
		
		
	
		JsonThread jsonThread = new JsonThread(keys, values, url,true, handler);
		executorService.submit(jsonThread);
		JsonThreadMap.put(context, jsonThread);
	}

	public void getString(Handler handler, String[] keys, String[] values,String url, String dialogText) {
		this.dialogText = dialogText;
		for(int i=0;i<keys.length;i++)
		{
			System.out.println(keys[i]+"::::"+values[i]);
		}
		isToast=false;
		PostThread postThread = new PostThread(keys, values, url, handler);
		executorService.submit(postThread);
		PostThreadMap.put(context, postThread);
	}
	
	
	
	public void setIsToast(boolean isToast)
	{		
		this.isToast=isToast;
	}
	/**
	 * 把正在运行的线程关闭
	 */
	public void clear()
	{
		if(JsonThreadMap.get(context)!=null)
		{
			JsonThread jsonThread=JsonThreadMap.get(context);
			if(jsonThread.isAlive())
			{
				jsonThread.fdJsonUtil.closeConnection();
			}
		}
		if(PostThreadMap.get(context)!=null)
		{
			PostThread postThread=PostThreadMap.get(context);
			if(postThread.isAlive())
			{
				postThread.fdNetUtil.closeConnection();
			}
		}
	}
	/**
	 * 解析JSon的线�?
	 * @author yi
	 *
	 */
	class JsonThread extends Thread implements FDNetworkExceptionListener {
		String[] keys = {};
		String[] values = {};
		String url;
		boolean obtainCookie = false;
		String cookie;
		Handler handler;
		FDJsonUtils fdJsonUtil;
		StringBuilder sessionId;
		public JsonThread(String[] keys, String[] values, String url,
				Handler handler) {
			super();
			this.keys = keys;
			this.values = values;
			this.url = url;
			this.handler = handler;
			
		}
		public JsonThread(String[] keys, String[] values, String url,
				boolean obtainCookie,Handler handler) {
			super();
			this.keys = keys;
			this.values = values;
			this.url = url;
			this.handler = handler;
			this.obtainCookie = obtainCookie;
		}
		public JsonThread(String[] keys, String[] values, String url,String cookie,
				Handler handler) {
			super();
			this.keys = keys;
			this.values = values;
			this.url = url;
			this.handler = handler;
			this.cookie = cookie;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			if(dialogText!=null)
			{
				mHandler.sendEmptyMessage(0);
			}
			//Load
			handler.sendEmptyMessage(LOAD_START);
			fdJsonUtil = new FDJsonUtils();
			Object object = null;
			if(obtainCookie){
				object = fdJsonUtil.parseJsonGetCookie(context, keys, values, url,
						Constants.CONNECTION_OUT_TIME, this);
			}else if(cookie != null && !cookie.isEmpty()){
					object = fdJsonUtil.parseJsonPostCookie(context, keys, values, url,Constants.CONNECTION_OUT_TIME,cookie, this);
			}else {
				object = fdJsonUtil.parseJson(context, keys, values, url,
					Constants.CONNECTION_OUT_TIME, this);
			}
			if(object==null){
				handler.sendEmptyMessage(LOAD_FAIL);
				mHandler.sendEmptyMessage(4);
			}else{				
				Message msg = new Message();
				msg.what = LOAD_SUCCESS;
				msg.obj = object;
				// 把返回的数据发�?出去
				handler.sendMessage(msg);
				mHandler.sendEmptyMessage(1);
			}
		}

		@Override
		public void connectionTimeOut() {
			// TODO Auto-generated method stub
			mHandler.sendEmptyMessage(2);
		}

		@Override
		public void networkException() {
			// TODO Auto-generated method stub
			mHandler.sendEmptyMessage(3);
		}

		@Override
		public void resultIsNull() {
			// TODO Auto-generated method stub
			mHandler.sendEmptyMessage(4);
		}

		@Override
		public void networkDisable() {
			// TODO Auto-generated method stub
			mHandler.sendEmptyMessage(5);
		}

	};
	/**
	 * Post的线�?
	 * @author yi
	 *
	 */
	class PostThread extends Thread implements FDNetworkExceptionListener {
		String[] keys = {};
		String[] values = {};
		String url;
		Handler handler;
		FDNetUtil fdNetUtil;
		public PostThread(String[] keys, String[] values, String url,
				Handler handler) {
			super();
			this.keys = keys;
			this.values = values;
			this.url = url;
			this.handler = handler;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			if(dialogText!=null)
			{
				mHandler.sendEmptyMessage(0);
			}
			// �?��Load
			handler.sendEmptyMessage(LOAD_START);
			fdNetUtil = new FDNetUtil();
			Object object = fdNetUtil.postDataForString(context, keys, values, url,
					Constants.CONNECTION_OUT_TIME, this);
			if(object==null){
				handler.sendEmptyMessage(LOAD_FAIL);
				mHandler.sendEmptyMessage(4);
			}else{				
				Message msg = new Message();
				msg.what = LOAD_SUCCESS;
				msg.obj = object;
				System.out.println("object:::"+object);
				// 把返回的数据发�?出去
				handler.sendMessage(msg);
				mHandler.sendEmptyMessage(1);
			}
		}

		@Override
		public void connectionTimeOut() {
			// TODO Auto-generated method stub
			mHandler.sendEmptyMessage(2);
		}

		@Override
		public void networkException() {
			// TODO Auto-generated method stub
			mHandler.sendEmptyMessage(3);
		}

		@Override
		public void resultIsNull() {
			// TODO Auto-generated method stub
			mHandler.sendEmptyMessage(4);
		}

		@Override
		public void networkDisable() {
			// TODO Auto-generated method stub
			mHandler.sendEmptyMessage(5);
		}

	};

	
	@Override
	
	  public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case 0:
			if(context!=null)
//			{				
//				dialog=FDDialogUtil.create(context, dialogText, null, null, null, 2);
//				dialog.setOnKeyListener(new DialogInterface.OnKeyListener()
//				{
//					
//					public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//						// TODO Auto-generated method stub
//						// Cancel task.
//						if (keyCode == KeyEvent.KEYCODE_BACK&&keyCode == KeyEvent.ACTION_DOWN) {
//							//点击返回�?
//							clear();
//						}
//						return false;
//					}
//				});
//			}
			break;
		case 1:
			if(context!=null&&dialog!=null)
			{
				dialog.dismiss();
			}
			break;
		case 2:
			if(context!=null&&dialog!=null)
			{
				dialog.dismiss();
			}
			if(!isToast)
			{				
				isToast=true;
//				FDToastUtil.show(context, R.string.app_name1075);
			}
			break;
		case 3:
			if(context!=null&&dialog!=null)
			{
				dialog.dismiss();
			}
			if(!isToast)
			{							
				isToast=true;
//				FDToastUtil.show(context,R.string.app_name1076);
			}
			break;
		case 4:
			if(context!=null&&dialog!=null)
			{
				dialog.dismiss();
			}
			if(!isToast)
			{							
				isToast=true;
				//FDToastUtil.show(context, R.string.app_name1077);
			}
			break;
		case 5:
			if(context!=null&&dialog!=null)
			{
				dialog.dismiss();
			}
//			FDToastUtil.show(context, R.string.app_name1078);
			break;

		default:
			break;
		}
		return false;
	}

}

