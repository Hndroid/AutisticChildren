package com.orangelink.UART_UDP.util;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Context;



/**
 * @author DisplayMetrics
 *
 */

public class DisplayMetricsUtil {

	

	private static android.util.DisplayMetrics dm;
	private static android.util.DisplayMetrics display(Context context){

		dm=new android.util.DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		return dm;
	}
	//取屏幕宽度
	public static int getWidth(Context context)
	{
		if(dm==null)
		{
			dm=display(context);
		}
		return dm.widthPixels;
	}
	//取屏幕高度
	public static int getHeight(Context context)
	{
		if(dm==null)
		{
			dm=display(context);
		}
		return dm.heightPixels;
	}
	//取标题栏高度
	public static int getTitleBarHeight(Context context)
	{
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, sbar = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			sbar = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return sbar;
	}
	
}
