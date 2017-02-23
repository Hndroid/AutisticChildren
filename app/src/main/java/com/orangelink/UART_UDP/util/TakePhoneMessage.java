package com.orangelink.UART_UDP.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class TakePhoneMessage {

	public static void takePhone(Context context, String num) {
		Uri uri = Uri.parse("tel:" + num);
		Intent intent = new Intent(Intent.ACTION_DIAL, uri);
		context.startActivity(intent);
	}

	public static void sendMessage(Context context, String num, String content) {
		Uri uri = Uri.parse("smsto:" + num);
		Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
		intent.putExtra("sms_body", content);
		context.startActivity(intent);
	}

}
