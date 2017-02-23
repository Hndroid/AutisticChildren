package com.orangelink.UART_UDP.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;


public class BaseFragment extends Fragment {

	protected BaseActivity context;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = (BaseActivity) getActivity();
	}
	
}
