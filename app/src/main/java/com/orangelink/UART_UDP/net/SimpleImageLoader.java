package com.orangelink.UART_UDP.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.zhuxiyungu.autisticchildren.R;

import java.io.File;


public class SimpleImageLoader {

	/**
	 * 异步加载图片
	 * 
	 * @param context
	 *            andriod的Context
	 * @param imageView
	 *            被加载图片的ImageView
	 * @param url
	 *            图片的http地址
	 */
	public static void display(Context context, ImageView imageView, String url) {
		if (TextUtils.isEmpty(url)) {
			url = "";
		}
		AQuery aq = new AQuery(imageView);
		Bitmap bitmap = null;
		if (imageView.getDrawable() != null) {
			bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
		}
		if (bitmap == null) {
			bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.headimg_default);
		}
		if (url.startsWith("http://")) {
			aq.id(imageView).image(url, true, true, 0, R.drawable.headimg_default, bitmap, 0);
		} else {
			try {
				File file = new File(url);
				aq.id(imageView).image(file, 0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void display(Context context, ImageView imageView, String url, boolean cache) {
		if (TextUtils.isEmpty(url)) {
			url = "";
		}
		AQuery aq = new AQuery(imageView);
		Bitmap bitmap = null;
		if (imageView.getDrawable() != null) {
			bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
		}
		if (bitmap == null) {
			bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.headimg_default);
		}
		if (url.startsWith("http://")) {
			aq.id(imageView).image(url, cache, cache, 0, 0, null, 0);
		} else {
			try {
				File file = new File(url);
				aq.id(imageView).image(file, 0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
