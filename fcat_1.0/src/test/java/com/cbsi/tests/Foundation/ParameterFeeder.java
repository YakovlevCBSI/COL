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
	
	public Object[][] configureTestParams(){
		
		int dataLength = 2;
		int numBrowser = getBrowsers().length;
		int numURL = getURL().length;
		int totalNumOfTest = numBrowser * numURL ;
		
		Object[][] objects= new Object[totalNumOfTest][dataLength];

		int count =0;
		for(int i=0; i< objects.length; i++){

				objects[i][0] = getURL()[i/numBrowser];
				
				if(i%numBrowser == 0){
					objects[i][1] = getBrowsers()[0];
				}
				else{
					objects[i][1] = getBrowsers()[1];
				}
				
		}
		
		return objects;
		
	}

	public String[] getURL(){
		String[] URLs = {
				GlobalVar.stageServer,
				GlobalVar.stageServer + GlobalVar.embedPath,
				GlobalVar.BFPServer, // BFP only embed.
				GlobalVar.devServer,
				GlobalVar.devServer + GlobalVar.embedPath
				
		};
		
		return URLs;
	}
	
	public String[] getBrowsers(){
		String[] browsers = {
				"chrome 39",
				"firefox 34",
				//"internet explorer",
				//safari
		};
		
		return browsers;
		
	}
	
	public static void main(String[] args){
		Object[][] objects =  new ParameterFeeder().configureTestParams();
		for(int i= 0; i< objects.length; i++){
			for (int j=0; j<objects[i].length; j++){
				System.out.println(objects[i][j]);
			}
		}
	}
	
}
