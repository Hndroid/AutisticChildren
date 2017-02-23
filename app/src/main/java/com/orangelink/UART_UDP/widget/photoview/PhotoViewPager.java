package com.orangelink.UART_UDP.widget.photoview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class PhotoViewPager extends ViewPager {

	public PhotoViewPager(Context context) {
		super(context);
	}
	
	public PhotoViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	// --------------------------------------------------------//
	// getX()抛IllegalArgumentException导致某些系统死机问题
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		try {
			return super.onInterceptTouchEvent(ev);
		} catch (IllegalArgumentException e) {
			Log.v("lintest", "onInterceptTouchEvent---IllegalArgumentException");
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		try {
			return super.onTouchEvent(ev);
		} catch (IllegalArgumentException e) {
			Log.v("lintest", "onTouchEvent---IllegalArgumentException");
			e.printStackTrace();
			return false;
		}
	}
}
