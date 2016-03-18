package com.cbsi.fcat.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {
	
//	public final static String filePath = "src/main/resources/database_prod.properties";
	public final static String filePath = "src/main/resources/database.properties";

	private static Properties prop;
	private static InputStream input;

	public static void openInputStream(){
		prop = new Properties();
		
		input = null;
		
		try{
			input = new FileInputStream(filePath);
//			input = new FileInputStream();

			prop.load(input);

		}catch(IOException ex){
			ex.printStackTrace();
		}
	}

	public static void closeInputStream(){
		if(input != null){
			try{
				input.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	public static String get(String p){
		openInputStream();
		
		String pr = prop.getProperty(p);
		
		closeInputStream();
		return pr;
	}
}
