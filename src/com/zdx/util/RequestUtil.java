package com.zdx.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


public class RequestUtil {
	
	

	public static String getRequestType(String requestStr) {
		
		String str[] = requestStr.split(" ");
		return str[0].toUpperCase();
	}
	public static String getRequestFileName(String requestStr) throws UnsupportedEncodingException {
		String str[] = requestStr.split(" ");
		requestStr=URLDecoder.decode(str[1], "utf-8");
		return requestStr;
	}

	
	public static void main(String[] args) {
		
	}
}
