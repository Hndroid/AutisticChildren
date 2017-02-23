package com.orangelink.UART_UDP.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {
	SharedPreferences sp;
	SharedPreferences.Editor editor;
	Context context;

	public static final String SPREFERENCES_NAME = "sp_info";

	public SharedPreferencesHelper(Context c, String name) {
		context = c;
		sp = context.getSharedPreferences(name, Context.MODE_WORLD_READABLE
				| Context.MODE_WORLD_WRITEABLE);
		editor = sp.edit();
	}

	public SharedPreferencesHelper(Context c) {
		context = c;
		sp = context.getSharedPreferences(SPREFERENCES_NAME,
				Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE);
		editor = sp.edit();
	}

	public void putValue(String key, String value) {
		editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public String getValue(String key) {
		return sp.getString(key, null);
	}

	public void putBoolean(String key, boolean value) {
		editor = sp.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public boolean getBoolean(String key) {
		return sp.getBoolean(key, false);
	}
	
	public boolean getBoolean(String key, boolean value) {
		return sp.getBoolean(key, value);
	}

}
