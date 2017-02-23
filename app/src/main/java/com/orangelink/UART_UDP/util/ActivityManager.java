package com.orangelink.UART_UDP.util;

import java.util.ArrayList;

import com.orangelink.UART_UDP.base.BaseActivity;

import android.app.Activity;

public class ActivityManager {

	private ArrayList<BaseActivity> activityList = new ArrayList<BaseActivity>();
	private static ActivityManager instance;

	private ActivityManager() {
	}

	public static ActivityManager getInstance() {
		if (instance == null) {
			instance = new ActivityManager();
		}
		return instance;

	}

	public void addActivity(BaseActivity activity) {
		activityList.add(activity);
	}

	public void exit() {
		for (Activity activity : activityList) {
			activity.finish();
		}
		System.exit(0);
	}

	public void remove(Class<? extends BaseActivity> c) {
		for (Activity activity : activityList) {
			if (activity.getClass() == c) {
				activity.finish();
			}
		}
	}

	public void finishAll() {
		for (BaseActivity activity : activityList) {
			activity.finish();
		}
		activityList.clear();
	}

	public boolean isActivityExit(Class<? extends BaseActivity> c) {
		for (BaseActivity activity : activityList) {
			if (activity.getClass() == c) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<BaseActivity> getActivityList() {
		return activityList;
	}
}
