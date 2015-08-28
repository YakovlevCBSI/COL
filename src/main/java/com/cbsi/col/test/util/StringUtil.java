package com.cbsi.col.test.util;

public class StringUtil {
	public static String cleanTableKey(String text){
		return text.replaceAll("[/ \\s+ -]", "").toLowerCase();
	}
	
	public static String cleanRegexChars(String text){
		return text.replaceAll("[\\\\]", "").toLowerCase();
	}
	
	public static String cleanCurrency(String text){
		return text.replaceAll("[,]", "");
	}
	public static void main(String[] args){
		String keyword = "a\\_";
		
		System.out.println(cleanRegexChars(keyword));
	}
}
