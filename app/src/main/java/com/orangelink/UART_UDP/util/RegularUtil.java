package com.orangelink.UART_UDP.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularUtil {
	
	public static boolean isEmail(String email) {
		boolean tag = true;
		final String pattern1 = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		final Pattern pattern = Pattern.compile(pattern1);
		final Matcher mat = pattern.matcher(email);
		if (!mat.find()) {
			tag = false;
		}
		return tag;
	}
	
	public static boolean isPhoneNumber(String phone) {
//		String regex = "^13[0-9]\\d{8}$|^15[0-9]\\d{8}$|^18[0256789]\\d{8}$|^147\\d{8}$";
		String regex = "^1([0-9]{10})$";
		Pattern p = Pattern.compile(regex);
		Matcher matcher = p.matcher(phone);
		if (matcher.find()) {
			return true;
		}
		return false;
	}
	
	@Deprecated
	public static boolean isCMCCPhoneNumber(String phone) {
		String regex ="^((13[4-9])|(15[0-2, 7-9])|(18[2-4,7-8])|(147))\\d{8}$";
		Pattern p = Pattern.compile(regex);
		Matcher matcher = p.matcher(phone);
		if (matcher.find()) {
			return true;
		}
		return false;
	}
	
	@Deprecated
	public static String matchesPhoneNumber(String phone_number) { 
		String cm = "^((13[4-9])|(147)|(15[0-2,7-9])|(18[2-4,7-8]))\\d{8}$"; 
		String cu = "^((13[0-2])|(145)|(15[5-6])|(186))\\d{8}$"; 
		String ct = "^((133)|(153)|(18[0,9]))\\d{8}$"; 
		if (phone_number.matches(cm)) { 
			return "（移动）";
		} else if (phone_number.matches(cu)) { 
			return "（联通）";
		} else if (phone_number.matches(ct)) { 
			return "（电信）";
		} else { 
			return "";
		} 
	}
}
