package com.cbsi.tests.Foundation;

import java.io.IOException;
import java.util.Collection;

import com.cbsi.tests.util.GlobalVar;
import com.cbsi.tests.util.ReadFile;


public class ParameterFeeder {
	public static boolean isDevTest = false;
//	public static boolean isProdTest = false;
//	private boolean isDevTest = true;
	public static boolean isProdTest = false;

	public ParameterFeeder(){
		
		try {
			ReadFile.setGlobalVars();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		isDevTest = isDevTesting();
		isProdTest = isProdTesting();
		System.out.println("<------------DEVorPROD: " + isDevTest +" / " + isProdTest +"--------->");
	}
	
	public boolean isDevTesting(){
		String system="";
		if((system = System.getProperty("environment")) != null){
			if(system.equals("dev")) return true;
		}
	
		return false;
	}
	
	public boolean isProdTesting(){
		String system="";
		if((system = System.getProperty("environment")) != null){
			if(system.equals("prod")) return true;
		}
		 return false;
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
		else if(whichURLArray.equals("stage")){
			URLs = getStageURL();
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
		//pritnParams();
		
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
		String[] URLFinal = null;
		if(!isDevTest && !isProdTest){
			String[] URLs = {
					GlobalVar.stageServer,
					GlobalVar.stageServer + GlobalVar.embedPath,
					GlobalVar.BFPServer, // BFP only embed.
					
					
			};
			URLFinal = URLs;
		}
		else if (isDevTest){
			String[] URLsDev = {
				GlobalVar.devServer,
				GlobalVar.devServer + GlobalVar.embedPath
						};
			URLFinal = URLsDev;

		}
		else{
			String[] URLsProd = {
					GlobalVar.prodServer + GlobalVar.embedPath
			};
			
			URLFinal = URLsProd;
		}
		return URLFinal;
	}
	
	public String[] getEmbedURL(){
		String[] URLFinal = null;
		if(!isDevTest && !isProdTest){
			String[] URLs = {
				//	GlobalVar.stageServer,
					GlobalVar.stageServer + GlobalVar.embedPath,
					GlobalVar.BFPServer, // BFP only embed.
				//	GlobalVar.devServer,
			//-------->	GlobalVar.devServer + GlobalVar.embedPath
					
			};
			URLFinal = URLs;
		}
		else if (isDevTest){
			String[] URLsDev = {
					GlobalVar.devServer + GlobalVar.embedPath
			};
			
			URLFinal = URLsDev;
		}
		else{
			String[] URLsProd = {
					GlobalVar.prodServer + GlobalVar.embedPath
			};
			
			URLFinal = URLsProd;
		}	
		return URLFinal;
	}
	
	public String[] getFormURL(){
		String[] URLFinal = null;
		if(!isDevTest && !isProdTest){
			String[] URLs = {
					GlobalVar.stageServer,
					//GlobalVar.stageServer + GlobalVar.embedPath,
					//GlobalVar.BFPServer, // BFP only embed.
				//--------->GlobalVar.devServer,
					//GlobalVar.devServer + GlobalVar.embedPath
			};
			URLFinal = URLs;
		}
		else if (isDevTest){
			String[] URLsDev = {
					GlobalVar.devServer
			};
			URLFinal = URLsDev;
		}
		
		return URLFinal;
	}
	
	public String[] getStageURL(){
		String[] URLs = {
				GlobalVar.stageServer,
				GlobalVar.stageServer + GlobalVar.embedPath,
				//GlobalVar.BFPServer, // BFP only embed.
				//GlobalVar.devServer,
				//GlobalVar.devServer + GlobalVar.embedPath
				
		};
		
		return URLs;
	}
	public String[] getBrowsers(){
		String[] browsers = {
				"chrome 39",
				"firefox 34",
				//"internet explorer 11",
				//safari
		};
		
		return browsers;
		
	}
	
	public static void pritnParams(){
		Object[][] objects =  new ParameterFeeder().configureTestParams("all");
		for(int i= 0; i< objects.length; i++){
			for (int j=0; j<objects[i].length; j++){
				System.out.println(objects[i][j]);
			}
		}
	}
	
}
