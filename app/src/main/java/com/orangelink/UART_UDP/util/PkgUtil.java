package com.orangelink.UART_UDP.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

public class PkgUtil {

	/**
	 * 获取软件版本名称
	 * 
	 * @param context
	 * @return
	 */
	public static String getAppVersionName(Context context) {
		PackageInfo info;
		try {
			info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取package名称
	 * 
	 * @param context
	 * @return
	 */
	public static String getPackageName(Context context) {
		PackageInfo info;
		try {
			info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return info.packageName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取软件版本code
	 * 
	 * @param context
	 * @return
	 */
	public static int getVersionCode(Context context) {
		PackageInfo info;
		try {
			info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return info.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
