package com.cbsi.fcat.util;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FtpUtil {

	public static final String ftpServer =GlobalVar.ftpURL;
	public static final int port = 21;
	public static String username = GlobalVar.ftpUserName;
	public static String password = GlobalVar.ftpPassword;
	
	public static FTPClient ftpClient;
	public void  login(){
		
		ftpClient= new FTPClient();
		try {
			ftpClient.connect(new URL(GlobalVar.ftpURL).getHost(), port);
	
		int ftpReplyCode = ftpClient.getReplyCode();
		
		if(!FTPReply.isPositiveCompletion(ftpReplyCode)){
			System.err.println("failed connectign to ftp due to " + ftpReplyCode);
		}
		System.out.println(username +" / " + password);
		boolean success = ftpClient.login(username, password);
		
			if(!success){
				System.err.println("failed at logging in");
			}
			else{
				System.out.println("log in successful!");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<String> listFilesInDir(String path){
		FTPFile[] files  = null;
		
		try {
			ftpClient.changeWorkingDirectory(path);
			files = ftpClient.listFiles();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<String> fileNames = new ArrayList<String>();
		for(FTPFile file: files){
			fileNames.add(file.getName());
		}
		
		return fileNames;
	}
	
	public Boolean renameFileNameInCurDir(String oldFile, String newFile){
		try {
			return ftpClient.rename(oldFile, newFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void quit(){
		try{
			ftpClient.quit();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException{

	}
}
