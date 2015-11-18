package com.cbsi.tests.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class StringUtil {
	public static String cleanJsonChars(String text){
		return text.replaceAll("[{ } \"]", "");
	}
	
	public static String cleanUrl(String text){
		return text.replaceAll(" ", "%20");
	}
	
	public static void main(String[] args){
		System.out.println(cleanUrl("http://ccs-dev1.cloudapp.net:8080/fcat-fastmap/querypn?mfr=Lightspeed Systems&pn=SYS-LB-1224&upc="));
	}
}
