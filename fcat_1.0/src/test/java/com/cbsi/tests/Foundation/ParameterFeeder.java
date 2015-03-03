package com.cbsi.tests.Foundation;

import java.io.IOException;
import java.util.Collection;

import com.cbsi.tests.util.GlobalVar;
import com.cbsi.tests.util.ReadFile;


public class ParameterFeeder {
	private String browser="";
	private String URL="";
	public static boolean isDevTest = System.getProperty("environment")!=null?true:false;
	//private boolean isDevTest = true;

	public ParameterFeeder(){
		
		try {
			ReadFile.setGlobalVars();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//if(System.getProperty("environment") != null);
		//isDevTest = System.getProperty("environment").equals("true")?false:true;
		
		System.out.println("DEVTEST: ");
		try{
			System.out.println("here it be: ");System.getProperty("environment");
		}catch(NullPointerException e){
			System.out.println("passing null ex..");
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
		String[] URLFinal;
		if(!isDevTest){
			String[] URLs = {
					GlobalVar.stageServer,
					GlobalVar.stageServer + GlobalVar.embedPath,
					GlobalVar.BFPServer, // BFP only embed.
					
					
			};
			URLFinal = URLs;
		}
		else{
			String[] URLsDev = {
				GlobalVar.devServer,
				GlobalVar.devServer + GlobalVar.embedPath
						};
			URLFinal = URLsDev;

		}
		return URLFinal;
	}
	
	public String[] getEmbedURL(){
		String[] URLFinal;
		if(!isDevTest){
			String[] URLs = {
				//	GlobalVar.stageServer,
					GlobalVar.stageServer + GlobalVar.embedPath,
					GlobalVar.BFPServer, // BFP only embed.
				//	GlobalVar.devServer,
			//-------->	GlobalVar.devServer + GlobalVar.embedPath
					
			};
			URLFinal = URLs;
		}
		else{
			String[] URLsDev = {
					GlobalVar.devServer + GlobalVar.embedPath
			};
			
			URLFinal = URLsDev;
		}
		
		return URLFinal;
	}
	
	public String[] getFormURL(){
		String[] URLFinal;
		if(!isDevTest){
			String[] URLs = {
					GlobalVar.stageServer,
					//GlobalVar.stageServer + GlobalVar.embedPath,
					//GlobalVar.BFPServer, // BFP only embed.
				//--------->GlobalVar.devServer,
					//GlobalVar.devServer + GlobalVar.embedPath
			};
			URLFinal = URLs;	
		}else{
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
