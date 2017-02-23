package com.orangelink.UART_UDP.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;

import com.zhuxiyungu.autisticchildren.R;

import java.io.File;


public class PhotoDialog extends Dialog implements OnClickListener {

	protected Activity mContext;
	protected Fragment fragment;
	public static String mFilePath = Environment.getExternalStorageDirectory()
			.getPath() + "/liverdoctor/images/";// 头像存储路径
	public static String mFileName = "img";// 头像名称
	private String fileName;// 照片名称

	public PhotoDialog(Activity context) {
		super(context, R.style.MyDialog);
		this.mContext = context;
	}

	public PhotoDialog(Activity context, Fragment fragment) {
		super(context, R.style.MyDialog);
		this.mContext = context;
		this.fragment = fragment;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photodialog);
		File dir = new File(mFilePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		WindowManager windowManager = mContext.getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.width = display.getWidth()
				- mContext.getResources().getDimensionPixelSize(
						R.dimen.dialog_margin) * 2; // 设置宽度
		getWindow().setAttributes(lp);
		getWindow().setGravity(Gravity.BOTTOM);
		setCanceledOnTouchOutside(true);
		findViewById(R.id.photodialog_bycamera_btn).setOnClickListener(this);
		findViewById(R.id.photodialog_byalbum_btn).setOnClickListener(this);
		findViewById(R.id.photodialog_cancel_btn).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.photodialog_bycamera_btn:
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
					mFilePath, (fileName != null ? fileName : mFileName)
							+ ".jpg")));
			if (fragment == null) {
				mContext.startActivityForResult(intent, 1);
			} else {
				fragment.startActivityForResult(intent, 1);
			}
			dismiss();
			break;
		case R.id.photodialog_byalbum_btn:
			Intent i = new Intent(Intent.ACTION_PICK, null);
			i.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					"image/*");
			if (fragment == null) {
				mContext.startActivityForResult(i, 2);
			} else {
				fragment.startActivityForResult(i, 2);
			}
			dismiss();
			break;
		case R.id.photodialog_cancel_btn:
			dismiss();
			break;
		default:
			break;
		}
	}

}
