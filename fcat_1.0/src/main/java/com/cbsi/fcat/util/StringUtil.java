package com.cbsi.fcat.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class StringUtil {
	public static String cleanJsonChars(String text){
		return text.replaceAll("[{ } \"]", "");
	}
	
	public static String cleanUrl(String text){
//		return text.replaceAll(" ", "%20");
		try {
			return URLEncoder.encode(text, "UTF-8").replaceAll(" ", "%20");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;

	}
	
	public static void main(String[] args) throws UnsupportedEncodingException{
//		System.out.println(cleanUrl("http://ccs-dev1.cloudapp.net:8080/fcat-fastmap/querypn?mfr=Lightspeed Systems&pn=SYS-LB-1224&upc="));
		String url = "http://ccs-dev1.cloudapp.net:8080/fcat-fastmap/querypn?mfr=Hewlett%20Packard%20Enterprise&pn=HG932A5#8EB&upc=\n";
		System.out.println(url);
		System.out.println(cleanUrl(url));

	}
}
