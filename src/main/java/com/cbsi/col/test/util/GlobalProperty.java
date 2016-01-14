package com.cbsi.col.test.util;

public class GlobalProperty {
	public static String URL = System.getProperty("url");
	public static String USERNAME = System.getProperty("username");
	public static String GRID = System.getProperty("grid");
	
	public static boolean isProd = System.getProperty("environment")==null?false:true;
	
	public static String getProperty(String propertyValue, String defaultValue){
		if(propertyValue == null){
			return defaultValue;
		}
		
		return propertyValue;
	}
}
