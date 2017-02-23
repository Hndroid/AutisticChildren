package com.orangelink.UART_UDP.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
/**
 * Class <code>Utility</code>
 * 
 * @Description 应用功能工具类
 * 
 * @author Jacob So
 * 
 * @version 1.0, 2013
 */
public class Utility 
{
	
	/**
	 * 获取系统版本
	 * @return float 版本号 如4.3
	 */
	public static float getSystemVersion(){
		String string = android.os.Build.VERSION.RELEASE;
		string = string.substring(0, 3).trim();
		float i = Float.valueOf(string);
		return i;
		
	}
	
    /** 
     * 判断是否有sdcard 
     * @return b
     */  
    public static boolean hasSDCard(){  
        boolean b = false;  
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){  
            b = true;  
        }  
        return b;  
    }  
      
    /** 
     * 得到sdcard路径 
     * @return 
     */  
    public static String getExtPath(){  
        String path = "";  
        if(hasSDCard()){  
            path = Environment.getExternalStorageDirectory().getPath();  
        }  
        return path;  
    }  
    
    /** 
     * 得到/data/data/yanbin.imagedownload目录 
     * @param mActivity activity对象
     * @return 
     */  
    public static String getPackagePath(Activity mActivity){  
        return mActivity.getFilesDir().toString();  
    }  
  
    /** 
     * 根据url得到图片名 
     * @param url 地址
     * @return 
     */  
    public static String getImageName(String url) {  
    	String x="";
    	String y="";
    	String z="";
        String imageName = "";  
        try {  
        if(url != null){  
           x = url.substring(url.lastIndexOf("/") + 1);  
            y=url.substring(0,url.lastIndexOf("/"));
            z=y.substring(y.lastIndexOf("/")+1,y.length());
            imageName=z+x;
            
        }  
        } catch (Exception e) {  
            e.printStackTrace();  
            imageName = null;  
        }
        return imageName;  
    }  
/**
 * 社区取名专用
 * @param url
 * @return
 */
	
    public static String getImageNameshequ(String url) {  
    	String x="";
    
        String imageName = "";  
        try {  
        if(url != null){  
           x = url.substring((url.lastIndexOf(":") + 1),(url.lastIndexOf(".") -1));  
          
           imageName=x;
        }  
        } catch (Exception e) {  
            e.printStackTrace();  
            imageName = null;  
        }
        return imageName;  
    }  
/*	
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}
		
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}
*/
	
    /** 
     * 判断有无网络 
     * 
     * @param   context
     * @return  true
     * @exception e
     */  
	public static boolean isConnect(Context context) 
	{
		try 
		{ 
			ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); 
			if (connectivity != null) 
			{ 
				NetworkInfo info = connectivity.getActiveNetworkInfo(); 
				if (info != null&& info.isConnected()) 
				{ 
					if (info.getState() == NetworkInfo.State.CONNECTED) 
					{ 
						return true; 
					} 
				} 
			} 
		} 
		catch (Exception e) 
		{ 
			Log.v("Utility-isConnect",e.toString()); 
    	} 
        return false; 
    }
	
    /** 
     * 判断有无开启GPS 
     * 
     * @param   context
     * @return  true
     */  	
	public static boolean openGPSSettings(Context context) 
	{
        LocationManager alm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) 
        {
            return true;
        }
        else
        {
            return false;
        }
	}
    /** 
     * 获取位置信息 
     * 
     * @param   context 上下文
     * @param	locationListener 位置监听者
     * @return  location 位置对象
     */  	
	public static Location getLocation(Context context,LocationListener locationListener)
    {
        LocationManager locationManager;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); 
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
        String provider = locationManager.getBestProvider(criteria, true); 
        Location location = locationManager.getLastKnownLocation(provider); 
        locationManager.requestLocationUpdates(provider, 60000, 10, locationListener);
        return location;
    }
    /** 
     * 开启StrictMode,用于html解析
     * 
     * @param   
     * @return  
     */  
    public static void struct() 
    {  
        StrictMode
        .setThreadPolicy(new StrictMode.ThreadPolicy.Builder()  
        .detectDiskReads().detectDiskWrites().detectNetwork() // or  
        // .detectAll()  
        // for  
        // all  
        // detectable  
        // problems  
        .penaltyLog().build());  
        StrictMode
        .setVmPolicy(new StrictMode.VmPolicy.Builder()  
        .detectLeakedSqlLiteObjects() // 探测SQLite数据库操作  
        .penaltyLog() // 打印logcat  
        .penaltyDeath().build());  
    } 
    
    

    /** 
     * 身份证 电话格式验证 
     * 
     * @param   phone 电话
     * @param	id  身份证
     * @return  true
     */ 
	public static boolean regInfo(String phone,String id)
	{
		String pRegex = "[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$";
		String iRegex = "((11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65|71|81|82|91)\\d{4})((((19|20)(([02468][048])|([13579][26]))0229))|((20[0-9][0-9])|(19[0-9][0-9]))((((0[1-9])|(1[0-2]))((0[1-9])|(1\\d)|(2[0-8])))|((((0[1,3-9])|(1[0-2]))(29|30))|(((0[13578])|(1[02]))31))))((\\d{3}(x|X))|(\\d{4}))";  
		if(Pattern.matches(pRegex,phone)&&Pattern.matches(iRegex,id))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * 呼出软键盘
	 * @param v 响应者view
	 */
	public static void callSoftKeyboard(final View v)
	{
			Timer timer = new Timer();
			timer.schedule(new TimerTask()
			{
				public void run() 
				{
					InputMethodManager inputManager =
							(InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
					inputManager.showSoftInput(v, 0);
		         }
		     },  

		         500);
	}
	
	 /**
	  * 生成照片名字
	  * @param 
	  * @return  dateFormat.format(date) + ".jpg" 生成名字 
	  */	
    @SuppressLint("SimpleDateFormat")
	public static String getPhotoFileName() {  
        Date date = new Date(System.currentTimeMillis());  
        SimpleDateFormat dateFormat = new SimpleDateFormat(  
                "'IMG'_yyyyMMddHHmmss");  
        return dateFormat.format(date) + ".jpg";  
    }  
	
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
     * 把bitmap转换成String
     * 
     * @param filePath 路径
     * @return  Base64.encodeToString(b, Base64.DEFAULT) 字符串处理图片
     */ 
	public static String bitmapToString(String filePath) {

	        Bitmap bm = getSmallBitmap(filePath);
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
	        byte[] b = baos.toByteArray();
	        return Base64.encodeToString(b, Base64.DEFAULT);
	    }
	
    /** 
     * 根据路径获得图片并压缩，返回bitmap用于显示
     * 
     * @param filePath 路径
     * @return  BitmapFactory.decodeFile(filePath, options) 压缩图片
     */ 
	public static Bitmap getSmallBitmap(String filePath) {
	        final BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inJustDecodeBounds = true;
	        BitmapFactory.decodeFile(filePath, options);

	        // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, 480, 800);

	        // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;

	    return BitmapFactory.decodeFile(filePath, options);
	    }
	
    /** 
     * 计算图片的缩放值
     * 
     * @param options bitmap设置参数
     * @param reqWidth 缩放宽
     * @param reqHeight 缩放高
     * @return  inSampleSize
     */ 
	public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;

	    if (height > reqHeight || width > reqWidth) {
	             final int heightRatio = Math.round((float) height/ (float) reqHeight);
	             final int widthRatio = Math.round((float) width / (float) reqWidth);
	             inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
	    }
	        return inSampleSize;
	}
	

	/**
	* 删除文件夹
	* @param folderPath 文件夹路径
	* @return 
	* 
	*/
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); //删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); //删除空文件夹
			
		}
		catch (Exception e) {
			//  System.out.println("删除文件夹操作出错");
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件夹里面的所有文件
	 * @param path String 文件夹路径 如 c:/fqf
	 */
	public static boolean delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return false;
		}
		if (!file.isDirectory()) {
			return false;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			}
			else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path+"/"+ tempList[i]);//先删除文件夹里面的文件
				delFolder(path+"/"+ tempList[i]);//再删除空文件夹
			}
		}
		return true;
	}
	
	public static void Toast(Context context, String msg) {
		Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.BOTTOM, 0, 70);
		toast.show();
	}
	
	public static void closeApp(Context context){
		Intent intent = new Intent("finish");
		context.sendBroadcast(intent);
	}
}
