package com.orangelink.UART_UDP.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.orangelink.UART_UDP.dialog.PhotoDialog;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Environment;
import android.text.TextUtils;

public class ImageUtils {

	/**
	 * 旋转图片为正方向
	 * 
	 * @param angle
	 * @param bitmap
	 * @return Bitmap
	 */
	public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}

	
	
	public static Bitmap getSimplifyBitmap(String path) {
		int side = 960;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		int dWidth = options.outWidth / side;
		int dHeight = options.outHeight / side;
		if (dWidth < dHeight) {
			options.inSampleSize = dHeight;
		} else {
			options.inSampleSize = dWidth;
		}
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inJustDecodeBounds = false;
		Bitmap bitmap = BitmapFactory.decodeFile(path, options);
		
		return bitmap;
	}

	public static String bitmap2File(String path, String name) {
		File file = null;
		Bitmap bitmap = getSimplifyBitmap(path);
		int degree = readPictureDegree(path);
		bitmap = ImageUtils.rotaingImageView(degree, bitmap);
		if(null == bitmap) {
			return null;
		}
		try {
			File pathFile = new File(PhotoDialog.mFilePath);
			file = new File(PhotoDialog.mFilePath, name);
			if(file.exists()) {
				file.delete();
			}
			if(!pathFile.exists()) {
				pathFile.mkdirs();
			}
			file.createNewFile();
			FileOutputStream fileOut = new FileOutputStream(file);
			int size = 100;
			if(bitmap.getHeight() > 1000 || bitmap.getWidth() > 1000) {
				size = 80;
			}
			bitmap.compress(Bitmap.CompressFormat.JPEG, size, fileOut);
//			ByteArrayOutputStream os = new ByteArrayOutputStream();
//			bitmap.compress(CompressFormat.PNG, 100, os);
//			byte[] bytes = os.toByteArray();
//			fileOut.write(bytes);
//			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file.getAbsolutePath();
	}

	public static Bitmap drawableToBitmap(Drawable d) {
		int w = d.getIntrinsicWidth();
		int h = d.getIntrinsicWidth();
		Config config = d.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
		Bitmap bitmap = Bitmap.createBitmap(w, h, config);
		Canvas c = new Canvas(bitmap);
		d.setBounds(0, 0, w, h);
		d.draw(c);
		return bitmap;
	}

	/**
	 * 下载图片操作
	 * 
	 * @param context
	 * @param imageUrl
	 *            －－ 图片url地址
	 */
	/*
	 * public static void downloadImage(Context context, Bitmap bitmap, String
	 * imageUrl) { String path = getCameraPath(imageUrl); File file = new
	 * File(path); if (!file.exists()) { if (bitmap != null) { try {
	 * bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new
	 * FileOutputStream(file)); } catch (FileNotFoundException e) {
	 * e.printStackTrace(); Toast.makeText(context,
	 * R.string.uc_image_download_failure, Toast.LENGTH_SHORT).show(); return; }
	 * // 发送数据装载广播让保存的图片显示到相册 context.sendBroadcast(new
	 * Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" +
	 * Environment.getExternalStorageDirectory()))); } else {
	 * Toast.makeText(context, R.string.uc_image_not_exist,
	 * Toast.LENGTH_SHORT).show(); return; } } Toast.makeText(context,
	 * R.string.uc_image_download_success, Toast.LENGTH_SHORT).show(); }
	 */

	// public static void downloadImage(final Context context, Bitmap bitmap,
	// String imageUrl) {
	//
	// if(!android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
	// {
	// Toast.makeText(context, "无SDcard", Toast.LENGTH_SHORT).show();
	// return;
	// }
	//
	// String path = getCameraPath(imageUrl);
	// final File file = new File(path);
	//
	// if(!file.exists()) {
	// DisplayImageOptions options = new DisplayImageOptions.Builder()
	// .cacheInMemory()
	// .cacheOnDisc()
	// .build();
	// ImageLoader.getInstance().loadImage(imageUrl, options, new
	// SimpleImageLoadingListener(){
	// @Override
	// public void onLoadingComplete(String imageUri, View view, Bitmap
	// loadedImage) {
	// if(loadedImage != null) {
	// try {
	// loadedImage.compress(Bitmap.CompressFormat.JPEG, 100, new
	// FileOutputStream(file));
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// Toast.makeText(context, R.string.uc_image_download_failure,
	// Toast.LENGTH_SHORT).show();
	// return;
	// }
	// // 发送数据装载广播让保存的图片显示到相册
	// context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
	// Uri.parse("file://" + Environment.getExternalStorageDirectory())));
	// Toast.makeText(context, R.string.uc_image_download_success,
	// Toast.LENGTH_SHORT).show();
	// } else {
	// Toast.makeText(context, R.string.uc_image_download_failure,
	// Toast.LENGTH_SHORT).show();
	// }
	// }
	//
	// @Override
	// public void onLoadingFailed(String imageUri, View view, FailReason
	// failReason) {
	// Toast.makeText(context, R.string.uc_image_download_failure,
	// Toast.LENGTH_SHORT).show();
	// }
	// });
	//
	// } else {
	// Toast.makeText(context, R.string.uc_image_download_success,
	// Toast.LENGTH_SHORT).show();
	// }
	//
	//
	// }

	/**
	 * 获取有效显示路径
	 * 
	 * @param photo
	 * @return
	 */
	/*
	 * public static String getValidlyPath(CampusStylePhotoObject photo) { if
	 * (!TextUtils.isEmpty(photo.image1Path)) { return photo.image1Path; } else
	 * if (!TextUtils.isEmpty(photo.image2Path)) { return photo.image2Path; }
	 * else if (!TextUtils.isEmpty(photo.image3Path)) { return photo.image3Path;
	 * } else if (!TextUtils.isEmpty(photo.image4Path)) { return
	 * photo.image4Path; } else if (!TextUtils.isEmpty(photo.image5Path)) {
	 * return photo.image5Path; } return ""; }
	 */
	/**
	 * 图片规格 -100*
	 */
	public static final int TYPE_IMAGE_100XX = 1;
	/**
	 * 图片规格 -300*
	 */
	public static final int TYPE_IMAGE_300XX = 2;
	/**
	 * 图片规格 -720*
	 */
	public static final int TYPE_IMAGE_720XX = 3;
	/**
	 * 图片规格-原图
	 */
	public static final int TYPE_IMAGE_ORIGINAL = 4;

	/**
	 * 组装Image路径
	 * 
	 * @param imageUrl
	 * @param type
	 * @return
	 */
	public static String componentImageUrl(String imageUrl, int type) {

		if (TextUtils.isEmpty(imageUrl)) {
			return imageUrl;
		}

		int lastPositionSp = imageUrl.lastIndexOf("/") + 1;
		String name = imageUrl.substring(lastPositionSp);
		imageUrl = imageUrl.substring(0, lastPositionSp);
		switch (type) {
		case TYPE_IMAGE_100XX:
			imageUrl += "100xx" + File.separator;
			break;
		case TYPE_IMAGE_300XX:
			imageUrl += "300xx" + File.separator;
			break;
		case TYPE_IMAGE_720XX:
			imageUrl += "720xx" + File.separator;
			break;
		case TYPE_IMAGE_ORIGINAL:
			imageUrl += "original" + File.separator;
			break;
		}
		imageUrl += name;
		return imageUrl;
	}

	/**
	 * 读取图片属性：旋转的角度
	 * 
	 * @param path
	 *            图片绝对路径
	 * @return degree旋转的角度
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	public static String getDirectPath() {
		return Environment.getExternalStorageDirectory().getPath() + "/wificity/images/";
	}

	public static String getFileName(String imageUrl) {
		return String.valueOf(imageUrl.hashCode()) + ".png";
	}

	public static String getFilePath(String imageUrl) {
		return getDirectPath() + getFileName(imageUrl);
	}

	public static String getCameraPath(String imageUrl) {
		StringBuffer sb = new StringBuffer();
		sb.append(Environment.getExternalStorageDirectory().getPath());
		sb.append("/DCIM/Camera/");
		File file = new File(sb.toString());
		if (!file.exists()) {
			file.mkdirs();
		}
		sb.append(getFileName(imageUrl));
		return sb.toString();
	}

	/**
	 * 设置图片圆角
	 * 
	 * @param bitmap
	 *            数据源
	 * @param innerCorner
	 *            圆角显示的位置 8个圆角半径值 分别对就左上、右上、右下、左下四个点<br/>
	 *            float inner[] = new float[] {20, 20, //左上<br/>
	 *            0, 0,//右上<br/>
	 *            20,20,//右下<br/>
	 *            0,0 //左下<br/>
	 *            };<br/>
	 * @return
	 */
	public static Bitmap drawCorner(Bitmap bitmap, float[] innerCorner) {
		if (bitmap == null) {
			return null;
		}
		Bitmap output = null;
		try {
			output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		} catch (OutOfMemoryError e) {
			if (output != null && !output.isRecycled()) {
				output.recycle();
				output = null;
			}
			return null;
		}
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		Path path = new Path();
		path.addRoundRect(rectF, innerCorner, Direction.CW);
		canvas.drawPath(path, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}
}