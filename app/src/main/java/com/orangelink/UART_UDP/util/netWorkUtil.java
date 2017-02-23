package com.orangelink.UART_UDP.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

import com.orangelink.UART_UDP.bean.Info;

public class netWorkUtil {


    /**
     * get请求服务器
     *
     * @param path     路径
     * @param params   数据
     * @param encoding "UTF-8"
     * @return
     * @throws Exception
     */
    public static int PZsendGETRequest(String path,
                                               Map<String, String> params, String encoding) throws Exception {
        StringBuilder url = new StringBuilder(path);
        url.append("?");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            url.append(entry.getKey()).append("=");
            url.append(URLEncoder.encode(entry.getValue(), encoding));// 编码
            url.append('&');
        }
        url.deleteCharAt(url.length() - 1);
        HttpURLConnection connection = (HttpURLConnection) new URL(url.toString()).openConnection();
        connection.setConnectTimeout(1000);
        connection.setReadTimeout(2000);
        connection.setRequestMethod("GET");
        Log.d("ww", "url:" + url.toString());
//        Log.d("ww", "ResponseCode:" + connection.getResponseCode());
//        int ret=connection.getResponseCode();
//        if (connection.getResponseCode() == 200) {
//            InputStream in = connection.getInputStream();
//            Log.d("ww", "get请求返回的数据：：" + in);
//            return in;
//        }
        return connection.getResponseCode();
    }


    public static InputStream sendGETRequest(String path,
                                             Map<String, String> params, String encoding) throws Exception {
        StringBuilder url = new StringBuilder(path);
        url.append("?");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            url.append(entry.getKey()).append("=");
            url.append(URLEncoder.encode(entry.getValue(), encoding));// 编码
            url.append('&');
        }
        url.deleteCharAt(url.length() - 1);
        HttpURLConnection connection = (HttpURLConnection) new URL(
                url.toString()).openConnection();
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(30000);
        connection.setRequestMethod("GET");
        Log.d("ww", "url:" + url.toString());
        Log.d("ww", "ResponseCode:" + connection.getResponseCode());
        if (connection.getResponseCode() == 200) {
            InputStream in = connection.getInputStream();
            Log.d("ww", "get请求返回的数据：：" + in);
            return in;
        }
        return null;
    }

    public static InputStream sendGETRequest2(String path,
                                              String encoding) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL(path).openConnection();
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(30000);
        connection.setRequestMethod("GET");
        Log.d("ww", "ResponseCode:" + connection.getResponseCode());
        if (connection.getResponseCode() == 200) {
            InputStream in = connection.getInputStream();
            Log.d("ww", "get请求返回的数据：：" + in);
            return in;
        }
        return null;
    }


    public static boolean sendPostRequest(String path, Map<String, String> params, String enc) throws Exception {
        // title=dsfdsf&timelength=23&method=save
        StringBuilder sb = new StringBuilder();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(entry.getKey()).append('=')
                        .append(URLEncoder.encode(entry.getValue(), enc)).append('&');
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        byte[] entitydata = sb.toString().getBytes();//得到实体的二进制数据
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(5 * 1000);
        conn.setDoOutput(true);//如果通过post提交数据，必须设置允许对外输出数据
        //Content-Type: application/x-www-form-urlencoded
        //Content-Length: 38
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(entitydata.length));
        OutputStream outStream = conn.getOutputStream();
        outStream.write(entitydata);
        outStream.flush();
        outStream.close();
        if (conn.getResponseCode() == 200) {
            return true;
        }
        return false;
    }


    /**
     * pull解析xml
     *
     * @param is
     * @return
     */
    public static List<Info> xmlPullParsing(InputStream is) {
        List<Info> list = null;
        Info info = null;
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(is, "UTF-8");
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        list = new ArrayList<Info>();
                        break;
                    case XmlPullParser.START_TAG:
                        if ("response".equals(parser.getName())) {
                            info = new Info();
                        } else if ("retcode".equals(parser.getName())) {
                            info.setRetcode(parser.nextText());
                        } else if (("retdesc").equals(parser.getName())) {
                            info.setRetdesc(parser.nextText());
                        } else if ("sn".equals(parser.getName())) {
                            info.setSn(parser.nextText());
                        } else if ("mac".equals(parser.getName())) {
                            info.setMac(parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("response")) {
                            list.add(info);
                            Log.d("ww", "pull解析后得到的数据：：" + list);
                            info = null;
                        }
                        break;
                }
                event = parser.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    public static Map<String, Object> xmlPullParsing2(InputStream is) {
        Map<String, Object> map = null;
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(is, "UTF-8");
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if ("response".equals(parser.getName())) {
                            map = new HashMap<String, Object>();
                        } else if ("retcode".equals(parser.getName())) {
                            map.put("retcode", parser.nextText());
                        } else if (("retdesc").equals(parser.getName())) {
                            map.put("retdesc", parser.nextText());
                        } else if ("host".equals(parser.getName())) {
                            map.put("host", parser.nextText());
                        } else if ("port".equals(parser.getName())) {
                            map.put("port", parser.nextText());
                        } else if ("cgidir".equals(parser.getName())) {
                            map.put("cgidir", parser.nextText());
                        } else if ("cgiformat".equals(parser.getName())) {
                            map.put("cgiformat", parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("response")) {
                            Log.d("ww", "pull解析后得到的数据：：" + map.toString());
//						list.add(info);
//						DEBug.print("pull解析后得到的数据：：" + list);
//						Log.d("ww", "pull解析后得到的数据：：" + list);
//						info = null;
                        }
                        break;
                }
                event = parser.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map;
    }
}
