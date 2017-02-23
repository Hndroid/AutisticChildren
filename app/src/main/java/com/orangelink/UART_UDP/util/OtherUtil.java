package com.orangelink.UART_UDP.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kwapp.net.fastdevelop.utils.FDBitmapUtil;
import com.kwapp.net.fastdevelop.utils.FDUnitUtil;
import com.orangelink.UART_UDP.common.Constants;
import com.zhuxiyungu.autisticchildren.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OtherUtil {
	 /**
	  * 将给定图片维持宽高比缩放后，截取正中间的正方形部分。
	  * @param imgPath      原图路径
	  * @param edgeLength  希望得到的正方形部分的边长
	  * @return  result 缩放截取正中部分后的位图。失败返回null
	  */
	public static Bitmap centerSquareScaleBitmap(String imgPath, int edgeLength)
	{
		//Logs.v(imgPath);
		Options options = new BitmapFactory.Options(); 
		options.inJustDecodeBounds = false; 
		options.inSampleSize = 10; 	  
		Bitmap bitmap = BitmapFactory.decodeFile(imgPath, options); 
		
		
		if(null == bitmap || edgeLength <= 0)
		{
			return  null;
		}
		
		Bitmap result = bitmap;
		int widthOrg = bitmap.getWidth();
		int heightOrg = bitmap.getHeight();
		
		if(widthOrg > edgeLength && heightOrg > edgeLength)
		{
			//压缩到一个最小长度是edgeLength的bitmap
			int longerEdge = (int)(edgeLength * Math.max(widthOrg, heightOrg) / Math.min(widthOrg, heightOrg));
			int scaledWidth = widthOrg > heightOrg ? longerEdge : edgeLength;
			int scaledHeight = widthOrg > heightOrg ? edgeLength : longerEdge;
			Bitmap scaledBitmap;
			
			try{
				scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
			} 
			catch(Exception e){
				return null;
			}
       
			//从图中截取正中间的正方形部分。
			int xTopLeft = (scaledWidth - edgeLength) / 2;
			int yTopLeft = (scaledHeight - edgeLength) / 2;
			
			try{
				result = Bitmap.createBitmap(scaledBitmap, xTopLeft, yTopLeft, edgeLength, edgeLength);
				scaledBitmap.recycle();
			}
			catch(Exception e){
 	    return null;
			}         
		}
	      
	 return result;
	}
	
	  /**
     * 将图片截取为圆角图片
     * @param bitmap 原图片
     * @param ratio 截取比例，如果是8，则圆角半径是宽高的1/8，如果是2，则是圆形图片
     * @return 圆角矩形图片
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float ratio) {
            
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                            bitmap.getHeight(), Config.ARGB_8888);
            Canvas canvas = new Canvas(output);

            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(rect);

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            canvas.drawRoundRect(rectF, bitmap.getWidth()/ratio, 
                            bitmap.getHeight()/ratio, paint);

            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
            return output;
    }
	
	public static String getDateTime(String format) {  
        Date date = new Date(System.currentTimeMillis());  
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);  
        return dateFormat.format(date);  
    }  
	
	public static LinearLayout setLoadingLayout(Context context,ProgressBar progressBar,LinearLayout loadingLayout) {// 布局的设置
		LinearLayout layout = new LinearLayout(context);
		layout.setMinimumHeight(80);
		layout.setOrientation(LinearLayout.HORIZONTAL);
		layout.setGravity(Gravity.CENTER);
		layout.setFocusable(false);
		layout.setClickable(false);
		TextView textView = new TextView(context);
		progressBar = new ProgressBar(context);
		progressBar.setVisibility(View.GONE);
		progressBar.setLayoutParams(new LinearLayout.LayoutParams(40, 40));
		textView.setText(R.string.Load_more);

		textView.setGravity(Gravity.CENTER);
		layout.addView(progressBar);
		layout.addView(textView);
		loadingLayout = new LinearLayout(context);
		loadingLayout.setBackgroundResource(R.color.gray_bg);
		loadingLayout.addView(layout);

		loadingLayout.setGravity(Gravity.CENTER);
		loadingLayout.setFocusable(false);
		loadingLayout.setClickable(false);
		return loadingLayout;
	}
	
	
	public static int setListViewHeightBasedOnChildren2(Context context,
			ListView listView) {

		ListAdapter listAdapter = listView.getAdapter();

		if (listAdapter == null) {

			return 0;

		}

		int totalHeight = 0;

		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView); 
	        listItem.measure(0, 0); 
			totalHeight +=  listItem.getMeasuredHeight();
			listItem.setFocusable(false);
			listItem.setFocusableInTouchMode(false);

		}
		LinearLayout.LayoutParams params = (LayoutParams) listView
				.getLayoutParams();
		params.height = totalHeight
				+ (FDUnitUtil.dp2px(context, 1) * (listAdapter.getCount() - 1));
		params.width = LayoutParams.FILL_PARENT;
		listView.setLayoutParams(params);
		return params.height;
	}
	
	public static void setListViewHeightBasedOnChildren3(Context context,
			ListView listView) {

		ListAdapter listAdapter = listView.getAdapter();

		if (listAdapter == null) {

			return;

		}

		int totalHeight = 0;

		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView); 
	        listItem.measure(0, 0); 
			totalHeight +=  listItem.getMeasuredHeight();
			listItem.setFocusable(false);
			listItem.setFocusableInTouchMode(false);
		}


		RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) listView
				.getLayoutParams();
		params.height = totalHeight
				+ (FDUnitUtil.dp2px(context, 1) * (listAdapter.getCount() - 1));
		params.width = LayoutParams.FILL_PARENT;
		listView.setFocusable(false);
		listView.setLayoutParams(params);
	}
	
	/** 
	 * 图片转灰度 
	 *  
	 * @param bmSrc 
	 * @return 
	 */  
	public static Drawable bitmap2Gray(Drawable bmSrc0)  
	{  
	    Bitmap bmSrc=FDBitmapUtil.drawable2bitmap(bmSrc0);
		Bitmap bitmap = Bitmap.createBitmap(bmSrc.getWidth(), bmSrc.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		
		 // 建立Paint 物件  
        Paint vPaint = new Paint();  
        vPaint .setAlpha( 150 );   // Bitmap透明度(0 ~ 255)  
        
        canvas.drawBitmap ( bmSrc , 0, 0, vPaint );  //有透明  
	  
	    return FDBitmapUtil.bitmap2drawable(bitmap);  
	}  
	public static StateListDrawable newSelector(Context context, Drawable bmSrc0) {
		
		StateListDrawable bg = new StateListDrawable();
		
		Drawable normal = bmSrc0;
		Drawable focused = OtherUtil.bitmap2Gray(bmSrc0);
		
		bg.addState(new int[] { 16842910, 16842919 }, focused);
		bg.addState(new int[] { 16842908, 16842910 }, focused);
		bg.addState(new int[] { 16842910 }, normal);
		bg.addState(new int[] { 16842908 }, focused);
		bg.addState(new int[] {}, normal);

		return bg;
	}
	private static final int imageUpperLimitPix=300;
	
	// decode这个图片并且按比例缩放以减少内存消耗，虚拟机对每张图片的缓存大小也是有限制的
	public static String decodeFile(String imagePath) {
		File file=new File(imagePath);
		try {
			// decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(file), null, o);

			// Find the correct scale value. It should be the power of 2.
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < imageUpperLimitPix && height_tmp / 2 < imageUpperLimitPix)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}
			// decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			Bitmap newBitmap= BitmapFactory.decodeStream(new FileInputStream(file), null, o2);
			return storeImageToFile(newBitmap);
		} catch (FileNotFoundException e) {
		}
		return null;
	}
	private static String storeImageToFile(Bitmap bitmap){
        if(bitmap ==null){
            return null;
        }
    	File file1 = new File(Environment.getExternalStorageDirectory(),
				Constants.imageSubDirInSDCard_upLoad);

		if (!file1.exists()) { // 创建目录
			file1.mkdirs();
		}
		
		String name = System.currentTimeMillis() + ".jpg";
		File file = new File(file1, name);

        RandomAccessFile accessFile =null;        
      
        
        ByteArrayOutputStream steam =new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, steam);
        byte[] buffer = steam.toByteArray();

        try {
            accessFile =new RandomAccessFile(file, "rw");
            accessFile.write(buffer);
        } catch (Exception e) {
            return null;
        }
        
        try {
            steam.close();
            accessFile.close();
        } catch (IOException e) {
            //Note: do nothing.
        }
        
        return file.getAbsolutePath();
    }
	
	
	public  static String uploadHeadeadImageToFile(Bitmap bitmap){//默认图片的写入SD卡
        if(bitmap ==null){
            return null;
        }
    	File file1 = new File(Environment.getExternalStorageDirectory(),
				Constants.imageSubDirInSDCard_upLoad);

		if (!file1.exists()) { // 创建目录
			file1.mkdirs();
		}
		
		String name = "default_head"+ ".jpg";
		File file = new File(file1, name);

        RandomAccessFile accessFile =null;        
      
        
        ByteArrayOutputStream steam =new ByteArrayOutputStream();
        
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, steam);
        byte[] buffer = steam.toByteArray();

        try {
            accessFile =new RandomAccessFile(file, "rw");
            accessFile.write(buffer);
        } catch (Exception e) {
            return null;
        }
        
        try {
            steam.close();
            accessFile.close();
        } catch (IOException e) {
            //Note: do nothing.
        }
        
        return file.getAbsolutePath();
    }
	
	
	
}


