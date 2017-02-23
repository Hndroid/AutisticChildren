package com.orangelink.UART_UDP.util;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zhuxiyungu.autisticchildren.R;


public class TitleUtil {
	public static void setTitle(final Activity activity, Object title, Object back, Object forward, boolean isRegister) {
		// 设置标题
		if (title != null) {
			TextView tv = ((TextView) (activity.findViewById(R.id.actionbar_title)));
			if (title instanceof String) {
				tv.setText(title.toString());
			} else if (title instanceof Integer) {
				tv.setText((Integer) title);
			}
		}
		// 显示返回按钮，实现监听
		if (back != null) {
			// 设置返回的文字
//			TextView mBackText = (TextView) activity.findViewById(R.id.actionbar_back_text);
			if (back instanceof String) {
//				mBackText.setText(back.toString());
			} else if (back instanceof Integer) {
//				mBackText.setText((Integer) back);
			}

			activity.findViewById(R.id.actionbar_back).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					activity.finish();

				}
			});
		} else {
			activity.findViewById(R.id.actionbar_back).setVisibility(View.GONE);
		}

		// 显示前进按钮，无监听实现
		if (forward != null) {
			Log.v("------", "set");
			TextView mForwardText = (TextView) activity.findViewById(R.id.actionbar_forward_text);
			if (forward instanceof String) {
				mForwardText.setText(forward.toString());
			} else if (forward instanceof Integer) {
				mForwardText.setText((Integer) forward);
			}

			if (isRegister) {
//				ImageView mForwardImg = (ImageView) activity.findViewById(R.id.actionbar_forward_img);
//				mForwardImg.setImageDrawable(activity.getResources().getDrawable(R.drawable.signup));
//				mForwardText.setTextColor(activity.getResources().getColor(R.color.bule_text));
			} else {
//				ImageView mForwardImg = (ImageView) activity.findViewById(R.id.actionbar_forward_img);
//				mForwardImg.setImageDrawable(activity.getResources().getDrawable(R.drawable.goahead));
				activity.findViewById(R.id.actionbar_forword).setVisibility(View.VISIBLE);
			}
		} else {
			activity.findViewById(R.id.actionbar_forword).setVisibility(View.GONE);
		}
	}
	
	public static void setTitle(final Activity activity, View view, Object title, Object back, Object forward, boolean isRegister) {
		// 设置标题
		if (title != null) {
			TextView tv = ((TextView) (view.findViewById(R.id.actionbar_title)));
			if (title instanceof String) {
				tv.setText(title.toString());
			} else if (title instanceof Integer) {
				tv.setText((Integer) title);
			}
		}
		// 显示返回按钮，实现监听
		if (back != null) {
			// 设置返回的文字
//			TextView mBackText = (TextView) view.findViewById(R.id.actionbar_back_text);
			if (back instanceof String) {
//				mBackText.setText(back.toString());
			} else if (back instanceof Integer) {
//				mBackText.setText((Integer) back);
			}
			
			view.findViewById(R.id.actionbar_back).setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					activity.finish();
					
				}
			});
		} else {
			view.findViewById(R.id.actionbar_back).setVisibility(View.GONE);
		}
		
		// 显示前进按钮，无监听实现
		if (forward != null) {
			Log.v("------", "set");
			TextView mForwardText = (TextView) view.findViewById(R.id.actionbar_forward_text);
			if (forward instanceof String) {
				mForwardText.setText(forward.toString());
			} else if (forward instanceof Integer) {
				mForwardText.setText((Integer) forward);
			}
			
			if (isRegister) {
//				ImageView mForwardImg = (ImageView) view.findViewById(R.id.actionbar_forward_img);
//				mForwardImg.setImageDrawable(activity.getResources().getDrawable(R.drawable.signup));
//				mForwardText.setTextColor(activity.getResources().getColor(R.color.bule_text));
			} else {
//				ImageView mForwardImg = (ImageView) view.findViewById(R.id.actionbar_forward_img);
//				mForwardImg.setImageDrawable(activity.getResources().getDrawable(R.drawable.goahead));
				view.findViewById(R.id.actionbar_forword).setVisibility(View.VISIBLE);
			}
		} else {
			view.findViewById(R.id.actionbar_forword).setVisibility(View.GONE);
		}
	}
}
