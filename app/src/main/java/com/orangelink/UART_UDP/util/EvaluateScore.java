package com.orangelink.UART_UDP.util;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.widget.Toast;

public class EvaluateScore {

	/**
	 * 去手机上存在的app market中评分
	 * 
	 * @param context
	 */
	public static void GoToEvaluateScore(Context context) {
		Uri uri = Uri.parse("market://details?id="
				+ PkgUtil.getPackageName(context));
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		List<ResolveInfo> localList = context.getPackageManager()
				.queryIntentActivities(intent,
						PackageManager.GET_INTENT_FILTERS);
		if ((localList != null) && (localList.size() > 0)) {
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		} else {
			Toast.makeText(context, "您没有安装应用市场", Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 去手机上存在的能分享的app进行分享
	 * 
	 * @param context
	 */
	public static void share(Context context) {
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.setType("text/*");
		sendIntent.putExtra(Intent.EXTRA_TEXT, "silver good app!");
		context.startActivity(sendIntent);
	}

}
