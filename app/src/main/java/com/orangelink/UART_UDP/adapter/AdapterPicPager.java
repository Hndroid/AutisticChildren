package com.orangelink.UART_UDP.adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.orangelink.UART_UDP.common.Constants;
import com.orangelink.UART_UDP.util.ListImageDownloader;
import com.orangelink.UART_UDP.util.ListOnImageDownload;

public class AdapterPicPager extends PagerAdapter {
	private ListImageDownloader mDownloader;
	private int mPosition=0;
	private Activity context;
	
	private List<ImageView> imageViews;
	private List<String> imagePath = null;
	public AdapterPicPager(List<ImageView> pic){
		this.imageViews = pic;
	}
	public AdapterPicPager(Activity context,List<ImageView> pic,List<String> path){
		this.context = context;
		this.imageViews = pic;
		this.imagePath = path;

	}

	@Override
	public int getCount() {
		return imageViews.size();
	}

	@Override
	public Object instantiateItem(View container, final int position) {
		
		   if (mDownloader == null) 
	        {  
	            mDownloader = new ListImageDownloader();  
	        } 
	        if (mDownloader != null) {  
	        	System.out.println("异步下载图片  ");
//	        	public void imageDownloadmy(String url,ImageView mImageView,String path,Activity mActivity,ListOnImageDownload download){  

	        	mDownloader.imageDownloadmy(imagePath.get(position),imageViews.get(position),Constants.imageSubDirInSDCard_downLoad,
	        			context, new ListOnImageDownload() {
	        		

							@Override
							public void onDownloadSucc(Bitmap bitmap,ImageView imageView) {
								// TODO Auto-generated method stub
								imageViews.get(position).setBackgroundDrawable(new BitmapDrawable(bitmap));//(new BitmapDrawable(bitmap));
							}  
	        		
	        	});  
	        } 
		
		
		
		((ViewPager) container).addView(imageViews.get(position));
	//	View view = imageViews.get(position);
//		if(imagePath != null){
//			view.setOnClickListener(new OnClickListener(){
//
//				@Override
//				public void onClick(View v) {
//				}
//				
//			});
//		}

		return imageViews.get(position);
	}

	@Override
	public void destroyItem(ViewGroup view, int position, Object arg2) {
		view.removeView(imageViews.get(position));
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

}