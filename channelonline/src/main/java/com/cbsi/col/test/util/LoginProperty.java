package com.cbsi.col.test.util;

import java.util.HashMap;

public class LoginProperty {
//	public static final String testUser_manual = "shefali.ayachit@cbsinteractive.com";
//	public static final String testUser_manual = "cbsiqa0@gmail.com";

	public static final String testUser = "cbsiqa@gmail.com";

	public static final String testUser1 = "cbsiqa@gmail.com";
	public static final String testUser2 = "cbsiqa0@gmail.com";
	public static final String testUser3 = "cbsiqa1@gmail.com";
	public static final String testUser4= "cbsiqa+1@gmail.com";
	public static final String testUser_manual = "albert.park@cbsinteractive.com";
	
	public static final String testUser1_prod = "cbsiqa+1@gmail.com";
	public static final String testUser2_prod = "cbsiqa+2@gmail.com";
	public static final String testUser3_prod = "cbsiqa+3@gmail.com";
	public static final String testUser4_prod = "cbsiqa+4@gmail.com";
	public static final String testUserManual_prod = "cbsiqa+4@gmail.com";


	public static final String testPassword = "Password1";
	public static final String testPassword_prod = "M4dm4x55";


public static void main(String[] args){
	HashMap<Integer, String> map = new HashMap<>(2);
	map.put(1, "hello1");
	map.put(2, "hello2");
	map.put(3, "hello3");
	
	System.out.println(map.size());
	String expectedPrice = String.format("%.2f", 1.23232);

	System.out.println(expectedPrice);
}
	
}
