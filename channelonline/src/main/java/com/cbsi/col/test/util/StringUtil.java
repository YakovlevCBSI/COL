package com.cbsi.col.test.util;

import org.apache.commons.lang3.text.WordUtils;

public class StringUtil {
	public static String cleanTableKey(String text){
		return text.replaceAll("[/ \\s+ -]", "").toLowerCase();
	}
	
	public static String cleanRegexChars(String text){
		return text.replaceAll("[\\\\]", "").toLowerCase();
	}
	
	public static String cleanCurrency(String text){
		return text.replaceAll("[, $]", "");
	}
	
	public static String cleanElementName(String text){
		return text.replaceAll("[_]", " ");
	}
	
	public static String camelCase(String text){
		return WordUtils.capitalizeFully(text, new char[]{'_'}).replaceAll("_", "");
	}
	
	public static String cleanPageTitle(String text){
		int startIndex =  text.indexOf("(");
		int endIndex = text.indexOf(")");
		
		return text.substring(startIndex+1, endIndex);
	}
	
	public static void main(String[] args){
//		System.out.println(getUserName());
		
		String state = "sales order   (Approved)";
		System.out.println(cleanPageTitle(state));
	}
	
	public static String cleanUserName(String name){
		if(name.contains("cbsiqa")){
			return "user, test";
		}
		
		String userName =  name.split("@")[0];
		System.out.println(userName);
		
		
		String[] names = userName.split("\\.");
		return names[1] + "," + names[0];
	}

}
