package com.orangelink.UART_UDP.util;

import java.util.Observable;

public class DUserInfoObservable extends Observable {
	private static DUserInfoObservable observable = new DUserInfoObservable();

	private DUserInfoObservable() {
	}

	public static DUserInfoObservable getInstance() {
		return observable;
	}
	
	public void setChanged() {
        super.setChanged();
    }
}
