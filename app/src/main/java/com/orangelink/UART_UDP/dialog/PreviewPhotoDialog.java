package com.orangelink.UART_UDP.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.orangelink.UART_UDP.net.SimpleImageLoader;
import com.orangelink.UART_UDP.widget.photoview.PhotoView;
import com.zhuxiyungu.autisticchildren.R;

import java.util.List;

public class PreviewPhotoDialog extends Dialog {
	private List<String> pathList;
	private Context context;
	private int position;

	public PreviewPhotoDialog(Context context, List<String> pathList, int position) {
		super(context, R.style.dialog_full);
		this.context = context;
		this.pathList = pathList;
		this.position = position;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.previewphoto);
		findViewById(R.id.actionbar_back).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		ViewPager viewPager = (ViewPager) findViewById(R.id.previewphoto_photo);
		viewPager.setAdapter(new SamplePagerAdapter());
		viewPager.setCurrentItem(position);
	}
	
	public class SamplePagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			if (pathList != null)
				return pathList.size();
			return 0;
		}

		@Override
		public View instantiateItem(ViewGroup container, int position) {
			PhotoView photoView = new PhotoView(container.getContext());
			SimpleImageLoader.display(context, photoView, pathList.get(position));
			container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			return photoView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	}

}