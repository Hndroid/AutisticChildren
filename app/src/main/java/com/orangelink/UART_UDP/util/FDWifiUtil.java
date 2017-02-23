package com.orangelink.UART_UDP.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.util.Log;

/**
 * Wifi工具类
 * 
 * @author yi
 * 
 */
public class FDWifiUtil {
	private static FDWifiUtil fdWifiUtil;
	// 定义WifiManager对象
	public static WifiManager mWifiManager;
	// 定义WifiInfo对象
	private static WifiInfo mWifiInfo;
	// 扫描出的网络连接列表
	private List<ScanResult> mWifiList;
	// 网络连接列表
	private List<WifiConfiguration> mWifiConfiguration;
	// 定义一个WifiLock
	WifiLock mWifiLock;

	// 0表示WIFI_STATE_DISABLING，wifi正在关闭。
	// 1表示WIFI_STATE_DISABLED，wifi不可用。
	// 2表示WIFI_STATE_ENABLING，wifi正在打开
	// 3表示WIFI_STATE_ENABLED, wifi已经打开
	// 4表示WIFI_STATE_UNKNOWN,wifi未知状态
	private FDWifiUtil() {

	}

	public static FDWifiUtil getFDWifiUtil(Context context) {
		if (fdWifiUtil == null) {
			fdWifiUtil = new FDWifiUtil();
		}
		// 取得WifiManager对象
		mWifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		// 取得WifiInfo对象
		mWifiInfo = mWifiManager.getConnectionInfo();
		return fdWifiUtil;
	}

	/**
	 * 连接指定网络
	 */
	public void connect() {
		mWifiInfo = mWifiManager.getConnectionInfo();

	}

	/**
	 * 检查当前wifi状态
	 */
	public int checkState() {

		return mWifiManager.getWifiState();
	}

	/**
	 * 打开WIFI
	 */
	public void OpenWifi() {
		if (!mWifiManager.isWifiEnabled()) {
			mWifiManager.setWifiEnabled(true);
		}
	}

	/**
	 * 关闭WIFI
	 */
	public void CloseWifi() {
		if (mWifiManager.isWifiEnabled()) {
			mWifiManager.setWifiEnabled(false);
		}
	}

	/**
	 * 得到配置好的网络
	 * 
	 * @return
	 */
	public List<WifiConfiguration> GetConfiguration() {
		return mWifiConfiguration;
	}

	/**
	 * 指定配置好的网络进行连接
	 * 
	 * @param index
	 */
	public void ConnectConfiguration(int index) {
		// 索引大于配置好的网络索引返回
		if (index > mWifiConfiguration.size()) {
			return;
		}
		// 连接配置好的指定ID的网络
		mWifiManager.enableNetwork(mWifiConfiguration.get(index).networkId,
				true);
	}

	/**
	 * 启动扫描
	 */
	public void StartScan() {
		mWifiManager.startScan();
		// 得到扫描结果
		mWifiList = mWifiManager.getScanResults();
		// 得到配置好的网络连接
		mWifiConfiguration = mWifiManager.getConfiguredNetworks();
	}

	/**
	 * 得到网络列表
	 * 
	 * @return
	 */
	public ArrayList<String> GetWifiList() {
		// 得到扫描结果
		mWifiList = mWifiManager.getScanResults();
		if (mWifiList == null)
			return null;
		ArrayList<String> al = new ArrayList<String>();
		for (ScanResult result : mWifiList) {
			al.add(result.SSID);

		}
		return al;
	}

	/**
	 * 得到MAC地址
	 * 
	 * @return
	 */
	public String GetMacAddress() {
		return (mWifiInfo == null) ? "NULL" : mWifiInfo.getMacAddress();
	}

	/**
	 * 得到接入点的BSSID
	 * 
	 * @return
	 */
	public String GetBSSID() {
		return (mWifiInfo == null) ? "NULL" : mWifiInfo.getBSSID();
	}

	/**
	 * 得到接入点的SSID
	 * 
	 * @return
	 */
	public String GetSSID() {
		return (mWifiInfo == null) ? "NULL" : mWifiInfo.getSSID();
	}

	/**
	 * 得到IP地址
	 * 
	 * @return
	 */
	public int GetIPAddress() {
		return (mWifiInfo == null) ? 0 : mWifiInfo.getIpAddress();
	}

	/**
	 * 得到连接的ID
	 * 
	 * @return
	 */
	public int GetNetworkId() {
		return (mWifiInfo == null) ? 0 : mWifiInfo.getNetworkId();
	}

	/**
	 * 得到WifiInfo的所有信息包
	 * 
	 * @return
	 */
	public String GetWifiInfo() {
		return (mWifiInfo == null) ? "NULL" : mWifiInfo.toString();
	}
	
	// 添加一个网络并连接
	public boolean addNetwork(WifiConfiguration wcg) {
		int wcgID = mWifiManager.addNetwork(wcg);
		return mWifiManager.enableNetwork(wcgID, true);
	}

	// 添加一个网络并连接
	public boolean AddNetwork(String ssid, String key) {
		WifiConfiguration wc = new WifiConfiguration();
		wc.SSID = "\"" + ssid + "\"";
		wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
		wc.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
		wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
		wc.status = WifiConfiguration.Status.ENABLED;
		wc.networkId = mWifiManager.addNetwork(wc);
		System.out.println("wc" + wc);
		boolean bRet = mWifiManager.enableNetwork(wc.networkId, true);
		return bRet;

	}
	//分为三种情况：1没有密码2用wep加密3用wpa加密  

	public WifiConfiguration CreateWifiInfo(String SSID, String Password,
			int Type) {
		Log.d("ww", "执行 WifiConfiguration");
		WifiConfiguration config = new WifiConfiguration();
		config.allowedAuthAlgorithms.clear();
		config.allowedGroupCiphers.clear();
		config.allowedKeyManagement.clear();
		config.allowedPairwiseCiphers.clear();
		config.allowedProtocols.clear();
		config.SSID = "\"" + SSID + "\"";

		WifiConfiguration tempConfig = this.IsExsits(SSID);
		if (tempConfig != null) {
			mWifiManager.removeNetwork(tempConfig.networkId);
		}

		if (Type == 1) // WIFICIPHER_NOPASS
		{
			config.wepKeys[0] = "";
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			config.wepTxKeyIndex = 0;
		}
		if (Type == 2) // WIFICIPHER_WEP
		{
			config.hiddenSSID = true;
			config.wepKeys[0] = "\"" + Password + "\"";
			config.allowedAuthAlgorithms
					.set(WifiConfiguration.AuthAlgorithm.SHARED);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
			config.allowedGroupCiphers
					.set(WifiConfiguration.GroupCipher.WEP104);
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			config.wepTxKeyIndex = 0;
		}
		if (Type == 3) // WIFICIPHER_WPA
		{
			config.preSharedKey = "\"" + Password + "\"";
			config.hiddenSSID = true;
			config.allowedAuthAlgorithms
					.set(WifiConfiguration.AuthAlgorithm.OPEN);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
			config.allowedPairwiseCiphers
					.set(WifiConfiguration.PairwiseCipher.TKIP);
			// config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			config.allowedPairwiseCiphers
					.set(WifiConfiguration.PairwiseCipher.CCMP);
			config.status = WifiConfiguration.Status.ENABLED;
		}
		mWifiManager.enableNetwork(config.networkId, true);
		return config;
	}

	/**
	 * 判断某SSID的AP是否保存
	 * 
	 * @param SSID
	 * @return
	 */
	public static WifiConfiguration IsExsits(String SSID) {
		List<WifiConfiguration> existingConfigs = mWifiManager
				.getConfiguredNetworks();
		System.out.println("existingConfigs" + existingConfigs);
		for (WifiConfiguration existingConfig : existingConfigs) {
			if (existingConfig.SSID.equals("\"" + SSID + "\"")) {
				return existingConfig;
			}
		}
		return null;
	}

	public static String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {

		}
		return null;
	}

	// 断开指定ID的网络
	public void DisconnectWifi(int netId) {
		mWifiManager.disableNetwork(netId);
		mWifiManager.disconnect();
	}
	
	public void connectWifi(int netId) {
		mWifiManager.enableNetwork(netId, true);
		
	}

}