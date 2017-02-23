package com.orangelink.UART_UDP.common;


import android.content.Context;

import com.kwapp.net.fastdevelop.utils.FDSharedPreferencesUtil;

public abstract class CommonData extends Context {

	public static final boolean DEBUG = false;
	public static final String NETWORK_ERROR = "网络故障，请检查您的网络";

	public static String email;
	public static String psw;
	public static String member_id;
	public static String wifi;

	public static String psw(Context context){
		if(psw.length()<2){
			psw= FDSharedPreferencesUtil.get(context, Constants.SP_NAME_APP, "psw");
		}
		return psw;
	}

	public static String member_id(Context context){
		if(member_id.length()<2){
			member_id=FDSharedPreferencesUtil.get(context, Constants.SP_NAME_APP, "member_id");
		}
		return member_id;
	}

	public static String email(Context context){
		if(email.length()<2){
			email= FDSharedPreferencesUtil.get(context, Constants.SP_NAME_APP, "email");
		}
		return email;
	}


	public static void Save(Context context, String key, String value){

		FDSharedPreferencesUtil.save(context, Constants.SP_NAME_APP, key, value);

	}

	public static String Get(Context context,String value){

		return	FDSharedPreferencesUtil.get(context, Constants.SP_NAME_APP, value);

	}







//	public static List<SimpleBean> getSexList() {
//		// 性别
//		List<SimpleBean> list = new ArrayList<SimpleBean>();
//		list.add(new SimpleBean("男", "男"));
//		list.add(new SimpleBean("女", "女"));
//		return list;
//	}
}