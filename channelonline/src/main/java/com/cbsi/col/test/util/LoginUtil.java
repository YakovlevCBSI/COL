package com.cbsi.col.test.util;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cbsi.col.test.util.LoginProperty;

public class LoginUtil {
	public final Logger logger = LoggerFactory.getLogger(LoginUtil.class);
	
	public static String path = "src/main/resources/Users";
	private String userName;
	
	public String checkOutUser(){
		File folder = new File(path);
		File[] users = folder.listFiles();
		
		if(users.length >=1){
			userName = users[0].getName();
			logger.debug("check out user: " + userName);
			users[0].delete();
			
			if(userName.equals("user1")) 
				return LoginProperty.testUser1;
			else if (userName.equals("user2")) 
				return LoginProperty.testUser2;
			else 
				return LoginProperty.testUser3;
		}
		
		return null;
	}
	
	public void checkInUser(){
		File user = new File(path + "/" + userName);
		try {
			user.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.debug("problem checking in user");
			e.printStackTrace();
		}
		
		logger.debug("check in user " + userName);
	}
	
	public static void main(String[] args){
		LoginUtil login = new LoginUtil();
		login.checkOutUser();
		login.checkInUser();
	}
}
