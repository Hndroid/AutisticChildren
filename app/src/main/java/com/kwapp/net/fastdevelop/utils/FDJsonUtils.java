package com.kwapp.net.fastdevelop.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.kwapp.net.fastdevelop.listener.FDNetworkExceptionListener;
import com.kwapp.net.fastdevelop.listener.FDOnJsonListener;

public class FDJsonUtils {
	private static final String DEBUG_TAG = "FDJsonUtil";
	JSONObject rootJsonObject;
	JSONArray rootJsonArray;
	FDNetUtil fdNetUtil;
	FDOnJsonListener fdOnJsonListener;

	public Object parseJson(Context context, String[] keys, String[] values,
			String url, Integer connectionTime,
			FDNetworkExceptionListener fdNetworkExceptionListener) {
		String json = getJsonString(context, keys, values, url, connectionTime,
				fdNetworkExceptionListener);
		System.out.println("json:::" + json);
		switch (getRootType(json)) {
		case 1:
			return parseJSONObject(this.rootJsonObject);
		case 2:
			return parseJSONArray(this.rootJsonArray);
		case 3:
			return null;
		}
		return null;
	}
	
	public Object parseJsonPostCookie(Context context, String[] keys, String[] values,
			String url, Integer connectionTime, String cookie,
			FDNetworkExceptionListener fdNetworkExceptionListener) {
		
		String json = null;
		
		if ((keys == null) || (values == null)){
			json =  getStringPostCookie(context, url, connectionTime,cookie,
					fdNetworkExceptionListener);
		} else {
			json = postDataAndCookieForString(context, keys, values, url, connectionTime,cookie,
					fdNetworkExceptionListener);
		}
		
		System.out.println("json:::" + json);
		switch (getRootType(json)) {
		case 1:
			return parseJSONObject(this.rootJsonObject);
		case 2:
			return parseJSONArray(this.rootJsonArray);
		case 3:
			return null;
		}
		return null;
	}
	
	public Object parseJsonGetCookie(Context context, String[] keys, String[] values,
			String url, Integer connectionTime,
			FDNetworkExceptionListener fdNetworkExceptionListener) {
		
		String json = null;
		
		if ((keys == null) || (values == null)){
			json =  getStringGetCookie(context, url, connectionTime,
					fdNetworkExceptionListener);
		} else {
			json =  postDataForStringAndGetCookie(context, keys, values, url, connectionTime,
					fdNetworkExceptionListener);
		}
		
		System.out.println("json:::" + json);
		switch (getRootType(json)) {
		case 1:
			return parseJSONObject(this.rootJsonObject);
		case 2:
			return parseJSONArray(this.rootJsonArray);
		case 3:
			return null;
		}
		return null;
	}

	public Object parseJson(String json) {
		System.out.println("json:::" + json);
		switch (getRootType(json)) {
		case 1:
			return parseJSONObject(this.rootJsonObject);
		case 2:
			return parseJSONArray(this.rootJsonArray);
		case 3:
			return null;
		}
		return null;
	}

	private String getJsonString(Context context, String[] keys,
			String[] values, String url, Integer connectionTime,
			FDNetworkExceptionListener fdNetworkExceptionListener) {
		String json = "FDJsonUtil";
		this.fdNetUtil = new FDNetUtil();
		String result = null;
		if ((keys == null) || (values == null))
			result = this.fdNetUtil.getString(context, url, connectionTime,
					fdNetworkExceptionListener);
		else {
			result = this.fdNetUtil.postDataForString(context, keys, values,
					url, connectionTime, fdNetworkExceptionListener);
		}
		if (result != null) {
			json = result;
		}
		return json;
	}
	

	public static boolean isConn(Context context) {
		
		boolean bisConnFlag = false;
		ConnectivityManager conManager = (ConnectivityManager) context
				.getSystemService("connectivity");
		NetworkInfo network = conManager.getActiveNetworkInfo();
		if (network != null) {
			bisConnFlag = network.isAvailable();
		}
		return bisConnFlag;
		
		/*
		ConnectivityManager connectivity = (ConnectivityManager) context 
				.getSystemService(Context.CONNECTIVITY_SERVICE); 
				if (connectivity == null) { 
				} else { 
				NetworkInfo[] info = connectivity.getAllNetworkInfo(); 
				if (info != null) { 
				for (int i = 0; i < info.length; i++) { 
				if (info[i].getState() == NetworkInfo.State.CONNECTED) { 
				return true; 
				} 
				} 
				} 
				} 
				return false; 
				*/
		

		
	}

	 public String getStringGetCookie(Context context, String url, Integer connectionTime, FDNetworkExceptionListener fdNetworkExceptionListener)
	  {
	    if (!isConn(context)) {
	      if (fdNetworkExceptionListener != null) {
	        fdNetworkExceptionListener.networkDisable();
	      }
	      return null;
	    }
	    String result = null;
	    boolean flag = true;
	    if ((url == null) || ("".equals(url.trim()))) {
	      Log.e("FastDevelop", "param url is null or empty string");
	      return result;
	    }
	    try {
	      URL u = new URL(url);
	      HttpURLConnection conn = (HttpURLConnection)u.openConnection();
	      conn.setUseCaches(false);
	      conn.setInstanceFollowRedirects(true);  
	      conn.setDoInput(true);// 允许输入
          conn.setDoOutput(true);// 允许输出
          conn.setRequestProperty("connection", "keepalive");
       //   conn. setRequestProperty("Content-Type", "text/plain; charset=utf-8");
         conn.connect();
	      if (connectionTime != null) {
	        conn.setConnectTimeout(30000);
	      }
	      if (conn.getResponseCode() == 200) {
	        InputStream in = conn.getInputStream();
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        byte[] buffer = new byte[1024];
	        int len = -1;
	        while ((len = in.read(buffer)) > 0) {
	          out.write(buffer, 0, len);
	        }
	        result = out.toString("utf-8");
	        
	        
	      //Get the cookie
			String cookie = conn.getHeaderField("set-cookie");
			
			if(cookie!=null && cookie.length()>0){
				StringBuffer sb = new StringBuffer(result);
				cookie = cookie.substring(0, cookie.indexOf(";"));
				String PHPSESSID = cookie.substring(10, cookie.length());
				sb.insert(1, "\"PHPSESSID\":\"" +PHPSESSID + "\",");
				result = sb.toString();
				System.out.println("------------获取cookie-----------------"+cookie);           
			}
	        
	        out.close();
	        in.close();
	        conn.disconnect();
	      }
	    } catch (ConnectException connectException) {
	      if (fdNetworkExceptionListener != null) {
	        fdNetworkExceptionListener.networkException();
	      }
	      connectException.printStackTrace();
	      flag = false;
	    } catch (SocketTimeoutException socketTimeoutException) {
	      if (fdNetworkExceptionListener != null) {
	        fdNetworkExceptionListener.connectionTimeOut();
	      }
	      socketTimeoutException.printStackTrace();
	      flag = false;
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    if ((flag) && (result == null) && (fdNetworkExceptionListener != null)) {
	      fdNetworkExceptionListener.resultIsNull();
	    }
	    return result;
	  }
	 
	 public String getStringPostCookie(Context context, String url, Integer connectionTime,String cookie, FDNetworkExceptionListener fdNetworkExceptionListener)
	 {
		 if (!isConn(context)) {
			 if (fdNetworkExceptionListener != null) {
				 fdNetworkExceptionListener.networkDisable();
			 }
			 return null;
		 }
		 String result = null;
		 boolean flag = true;
		 if ((url == null) || ("".equals(url.trim()))) {
			 Log.e("FastDevelop", "param url is null or empty string");
			 return result;
		 }
		 try {
			 URL u = new URL(url);
			 HttpURLConnection conn = (HttpURLConnection)u.openConnection();
			 System.out.println("------------发送cookie---------:"+cookie);
			 conn.setRequestProperty("Cookie", cookie);
			 conn.setRequestMethod("POST");
			 conn.setRequestProperty("connection", "keepalive");
			 conn.setUseCaches(false);
			 // conn. setRequestProperty("Content-Type", "text/plain; charset=utf-8");
			 conn.setInstanceFollowRedirects(true);  
			 conn.setDoInput(true);// 允许输入
	         conn.setDoOutput(true);// 允许输出
	         conn.connect();
			 if (connectionTime != null) {
				 conn.setConnectTimeout(30000);
//				 conn.setConnectTimeout(connectionTime.intValue());
			 }
			 if (conn.getResponseCode() == 200) {
				 InputStream in = conn.getInputStream();
				 ByteArrayOutputStream out = new ByteArrayOutputStream();
				 byte[] buffer = new byte[1024];
				 int len = -1;
				 while ((len = in.read(buffer)) > 0) {
					 out.write(buffer, 0, len);
				 }
				 result = out.toString("utf-8");
				 
				 out.close();
				 in.close();
				 conn.disconnect();
			 }
		 } catch (ConnectException connectException) {
			 if (fdNetworkExceptionListener != null) {
				 fdNetworkExceptionListener.networkException();
			 }
			 connectException.printStackTrace();
			 flag = false;
		 } catch (SocketTimeoutException socketTimeoutException) {
			 if (fdNetworkExceptionListener != null) {
				 fdNetworkExceptionListener.connectionTimeOut();
			 }
			 socketTimeoutException.printStackTrace();
			 flag = false;
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		 if ((flag) && (result == null) && (fdNetworkExceptionListener != null)) {
			 fdNetworkExceptionListener.resultIsNull();
		 }
		 return result;
	 }
	
	public String postDataAndCookieForString(Context context, String[] keys,
			String[] values, String url, Integer connectionTime,String cookie,
			FDNetworkExceptionListener fdNetworkExceptionListener) {
		if (!isConn(context)) {
			if (fdNetworkExceptionListener != null) {
				fdNetworkExceptionListener.networkDisable();
			}
			return null;
		}
		String result = null;
		boolean flag = true;
		if ((url == null) || ("".equals(url.trim()))) {
			Log.e("FastDevelop", "param url is null or empty string");
			return result;
		}
		if ((keys == null) || (values == null)) {
			Log.e("FastDevelop", "param keys(or values) is null");
			return result;
		}

		if (keys.length != values.length) {
			Log.e("FastDevelop",
					"length of param keys is not equal to length of param values");
			return result;
		}
		try {
			URL mURL = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) mURL.openConnection();
			System.out.println("------------发送cookie---------:"+cookie);
			conn.setRequestProperty("Cookie", cookie);
			conn.setRequestMethod("POST");
			if (connectionTime != null) {
				conn.setConnectTimeout(30*1000);
			}
			conn.setUseCaches(false);
			conn.setInstanceFollowRedirects(true);
			 conn.setRequestProperty("connection", "keepalive");
			conn.setDoInput(true);// 允许输入
			//  conn. setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            conn.setDoOutput(true);// 允许输出
            conn.connect();
			StringBuffer params = new StringBuffer();
			int length = keys.length;
			for (int i = 0; i < length; ++i) {
				params.append(keys[i]).append("=").append(values[i]);
				if (i < length - 1) {
					params.append("&");
				}
			}
			byte[] bypes = params.toString().getBytes();
			//OutputStream output=conn.getOutputStream();
			//conn.getOutputStream().write(bypes, 0, bypes.length);
			OutputStream outputStream=conn.getOutputStream();
			outputStream.write(bypes, 0, bypes.length);
			outputStream.flush();
			//conn.getOutputStream();
			
			//conn.getOutputStream().flush();
			if (conn.getResponseCode() == 200) {
				InputStream is = conn.getInputStream();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = -1;
				while ((len = is.read(buffer)) > 0) {
					out.write(buffer, 0, len);
				}
				result = out.toString("utf-8");
				
				out.close();
				is.close();
				conn.disconnect();
				
			}
		} catch (ConnectException connectException) {
			if (fdNetworkExceptionListener != null) {
				fdNetworkExceptionListener.networkException();
				connectException.printStackTrace();
				flag = false;
			}
		} catch (SocketTimeoutException socketTimeoutException) {
			if (fdNetworkExceptionListener != null) {
				fdNetworkExceptionListener.connectionTimeOut();
				socketTimeoutException.printStackTrace();
				flag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if ((flag) && (result == null) && (fdNetworkExceptionListener != null)) {
			fdNetworkExceptionListener.resultIsNull();
		}

		return result;
	}
	
	public String postDataForStringAndGetCookie(Context context, String[] keys,String[] values, String url, Integer connectionTime,
			FDNetworkExceptionListener fdNetworkExceptionListener) {
		if (!isConn(context)) {
			if (fdNetworkExceptionListener != null) {
				fdNetworkExceptionListener.networkDisable();
			}
			return null;
		}
		String result = null;
		boolean flag = true;
		if ((url == null) || ("".equals(url.trim()))) {
			Log.e("FastDevelop", "param url is null or empty string");
			return result;
		}
		if ((keys == null) || (values == null)) {
			Log.e("FastDevelop", "param keys(or values) is null");
			return result;
		}
		
		if (keys.length != values.length) {
			Log.e("FastDevelop",
					"length of param keys is not equal to length of param values");
			return result;
		}
		try {
			URL mURL = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) mURL.openConnection();
			//System.out.println("------------发送cookie---------PHPSESSID=d5quakj7o3ugut9e2hp6efoqa2--------");
			//conn.setRequestProperty("Cookie", "PHPSESSID=q5quakj7o3ugut9e2hp6efoqa2;");
			conn.setRequestMethod("POST");
			if (connectionTime != null) {
				conn.setConnectTimeout(30000);
			}
		
		  // conn. setRequestProperty("Content-Type", "text/plain; charset=utf-8");
			
			conn.setUseCaches(false);
			conn.setInstanceFollowRedirects(true);  
			conn.setDoInput(true);// 允许输入
            conn.setDoOutput(true);// 允许输出
            conn.setRequestProperty("connection", "keepalive");
        //  conn.connect();
			StringBuffer params = new StringBuffer();
			int length = keys.length;
			for (int i = 0; i < length; ++i) {
				params.append(keys[i]).append("=").append(values[i]);
				if (i < length - 1) {
					params.append("&");
				}
			}
			byte[] bypes = params.toString().getBytes();
			OutputStream outputStream=conn.getOutputStream();
			outputStream.write(bypes, 0, bypes.length);
			outputStream.flush();
			if (conn.getResponseCode() == 200) {
				InputStream is = conn.getInputStream();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = -1;
				while ((len = is.read(buffer)) > 0) {
					out.write(buffer, 0, len);
				}
				result = out.toString("utf-8");
				
				//Get the cookie
				String cookie = conn.getHeaderField("set-cookie");
				
				if(cookie!=null && cookie.length()>0){
					StringBuffer sb = new StringBuffer(result);
					cookie = cookie.substring(0, cookie.indexOf(";"));
					String PHPSESSID = cookie.substring(10, cookie.length());
					sb.insert(1, "\"PHPSESSID\":\"" +PHPSESSID + "\",");
					result = sb.toString();
					System.out.println("------------获取cookie-----------------"+cookie);           
				}
				
				out.close();
				is.close();
				conn.disconnect();
			}
		} catch (ConnectException connectException) {
			if (fdNetworkExceptionListener != null) {
				fdNetworkExceptionListener.networkException();
				connectException.printStackTrace();
				flag = false;
			}
		} catch (SocketTimeoutException socketTimeoutException) {
			if (fdNetworkExceptionListener != null) {
				fdNetworkExceptionListener.connectionTimeOut();
				socketTimeoutException.printStackTrace();
				flag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if ((flag) && (result == null) && (fdNetworkExceptionListener != null)) {
			fdNetworkExceptionListener.resultIsNull();
		}
		
		return result;
	}

	private int getRootType(String json) {
		int flag = 1;
		try {
			this.rootJsonObject = new JSONObject(json);
			flag = 1;
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("FDJsonUtil", "非JSONObject");
			try {
				this.rootJsonArray = new JSONArray(json);
				flag = 2;
			} catch (Exception e1) {
				e1.printStackTrace();
				Log.e("FDJsonUtil", "非JSON格式的字符串");
				if (this.fdOnJsonListener != null) {
					this.fdOnJsonListener.onJsonStringError(json);
				}
				flag = 3;
			}
		}
		return flag;
	}

	private ArrayList<Object> parseJSONArray(JSONArray jsonArray) {
		ArrayList list = new ArrayList();
		int length = jsonArray.length();
		for (int i = 0; i < length; ++i) {
			try {
				Object value = jsonArray.get(i);
				if (value instanceof JSONArray)
					list.add(parseJSONArray((JSONArray) value));
				else if (value instanceof JSONObject)
					list.add(parseJSONObject((JSONObject) value));
				else if (value != null)
					list.add(value);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	private HashMap<String, Object> parseJSONObject(JSONObject jsonObject) {
		HashMap map = new HashMap();
		Iterator iterator = jsonObject.keys();

		while (iterator.hasNext()) {
			Object next = iterator.next();
			try {
				String key = next.toString();

				Object value = jsonObject.get(key);
				if (value instanceof JSONObject)
					map.put(key, parseJSONObject((JSONObject) value));
				else if (value instanceof JSONArray) {
					map.put(key, parseJSONArray((JSONArray) value));
				} else if ((value == null) || (value.equals("null"))
						|| (value.equals("null"))) {
					map.put(key, "");
				} else
					map.put(key, value);

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return map;
	}

	public void closeConnection() {
		if (this.fdNetUtil != null)
			this.fdNetUtil.closeConnection();
	}

	public void setFDOnJsonListener(FDOnJsonListener fdOnJsonListener) {
		this.fdOnJsonListener = fdOnJsonListener;
	}
}