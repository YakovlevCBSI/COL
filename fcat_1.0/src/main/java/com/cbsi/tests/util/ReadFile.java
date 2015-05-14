package com.cbsi.tests.util;

import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class ReadFile {
	
	public static String[]  getVariables(File file) throws IOException{
		
			FileInputStream inputStream = null;
			String lines = "";
			try{
				 inputStream = new FileInputStream(file);
			
				 lines = IOUtils.toString(inputStream);
			}finally{
				inputStream.close();
			}
			
			//System.out.println(lines);
			
		return StringUtils.substringsBetween(lines, "\"", "\"");
	}
	
	public static void setGlobalVars() throws IOException{
		String userHome = System.getProperty("user.home");
		File file = new File(userHome+ "/fcatCredit.txt");
		String[] tempVar = getVariables(file);
		/**
		System.out.println(tempVar.length);
		for (String s: tempVar){
			System.out.println(s);
		}
		*/
		GlobalVar.BFPId = (tempVar[0]);
		GlobalVar.BFPPw = (tempVar[1]);
		GlobalVar.LocalId = (tempVar[2]);
		GlobalVar.LocalPw = (tempVar[3]);
		GlobalVar.stageServer = (tempVar[4]);
		GlobalVar.devServer = (tempVar[5]);
		GlobalVar.BFPServer = tempVar[6];
		GlobalVar.prodServer = tempVar[7];
		GlobalVar.BSId= tempVar[8];
		GlobalVar.BSAccessKey= tempVar[9];
		GlobalVar.dbURL=tempVar[10];
		GlobalVar.dbUserName=tempVar[11];
		GlobalVar.dbPassword=tempVar[12];
		GlobalVar.ftpURL=tempVar[13];
		GlobalVar.ftpUserName=tempVar[14];
		GlobalVar.ftpPassword=tempVar[15];
		
		GlobalVar.MongoHost=tempVar[16];
		GlobalVar.MongoUsername=tempVar[17];
		System.out.println(GlobalVar.MongoUsername);
		GlobalVar.MongoPassword= tempVar[18];
	}
	
	//For debug.
	public static void main(String[] args) throws IOException{
		/**
		System.out.println(System.getProperty("os.name"));
		System.out.println(System.getProperty("user.dir"));
		System.out.println(System.getProperty("user.home"));
		*///System.out.println(GlobalVar.stageServer);
		ReadFile.setGlobalVars();
		System.out.println(GlobalVar.BSId + "/ " + GlobalVar.BSAccessKey);
		
		
		System.out.println(GlobalVar.dbURL + "\n" + GlobalVar.dbUserName + "\n" + GlobalVar.dbPassword);
		System.out.println(GlobalVar.prodServer);
	}
	
	
}
