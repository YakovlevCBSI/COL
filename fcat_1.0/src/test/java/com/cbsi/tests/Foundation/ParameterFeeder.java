package com.cbsi.tests.Foundation;

import java.io.IOException;
import java.util.Collection;

import com.cbsi.tests.util.GlobalVar;
import com.cbsi.tests.util.ReadFile;


public class ParameterFeeder {
	private String browser="";
	private String URL="";
	public ParameterFeeder(){
		
		try {
			ReadFile.setGlobalVars();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Object[][] configureTestParams(String whichURLArray){
		String[] URLs = null;
		
		if(whichURLArray.equals("all")){
			URLs= getAllURL();
		}
		else if(whichURLArray.equals("embed")){
			URLs = getEmbedURL();
		}
		else if(whichURLArray.equals("form")){
			URLs = getFormURL();
		}
		
		int dataLength = 2;
		int numBrowser = getBrowsers().length;
		
		int numURL = URLs.length;
		
		//- number of browser stack used browsers * number of devserver.
		int totalNumOfTest = numBrowser * numURL;  //- (RemovePhxForIEFromParam(URLs, getBrowsers()));
		
		Object[][] objects= new Object[totalNumOfTest][dataLength];

		int count =0;
		for(int i=0; i< objects.length; i++){

				objects[i][0] = URLs[i/numBrowser];
				
				objects[i][1] =getBrowsers()[i%numBrowser];
				
		}
		
		return objects;
		
	}
	
	public int RemovePhxForIEFromParam(String[] URL, String[] browsers){
		
		int URLToRemoveInBrowserStack=0;
		for(String url: URL){
			if(url.contains(GlobalVar.devServer)){
				URLToRemoveInBrowserStack++;
			}
		}
		
		int BrowserToRemoveInBrowserStack=0;
		for(String browser: browsers){
			if(browser.contains("internet explorer")){
				BrowserToRemoveInBrowserStack++;
			}
		}
		
		return URLToRemoveInBrowserStack * BrowserToRemoveInBrowserStack;
	}

	public String[] getAllURL(){
		String[] URLs = {
				GlobalVar.stageServer,
				GlobalVar.stageServer + GlobalVar.embedPath,
				GlobalVar.BFPServer, // BFP only embed.
				GlobalVar.devServer,
				GlobalVar.devServer + GlobalVar.embedPath
				
		};
		
		return URLs;
	}
	
	public String[] getEmbedURL(){
		String[] URLs = {
			//	GlobalVar.stageServer,
				GlobalVar.stageServer + GlobalVar.embedPath,
				GlobalVar.BFPServer, // BFP only embed.
			//	GlobalVar.devServer,
				GlobalVar.devServer + GlobalVar.embedPath
				
		};
		
		return URLs;
	}
	
	public String[] getFormURL(){
		String[] URLs = {
				GlobalVar.stageServer,
				//GlobalVar.stageServer + GlobalVar.embedPath,
				//GlobalVar.BFPServer, // BFP only embed.
				GlobalVar.devServer,
				//GlobalVar.devServer + GlobalVar.embedPath
				
		};
		
		return URLs;
	}
	public String[] getBrowsers(){
		String[] browsers = {
				"chrome 39",
				"firefox 34",
				"internet explorer 11",
				//safari
		};
		
		return browsers;
		
	}
	
	public static void main(String[] args){
		Object[][] objects =  new ParameterFeeder().configureTestParams("all");
		for(int i= 0; i< objects.length; i++){
			for (int j=0; j<objects[i].length; j++){
				System.out.println(objects[i][j]);
			}
		}
	}
	
}
