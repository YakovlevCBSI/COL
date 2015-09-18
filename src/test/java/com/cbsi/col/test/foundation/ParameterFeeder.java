package com.cbsi.col.test.foundation;

public class ParameterFeeder {

	public Object[][] configureTestParams(){
		String[] URLs = null;
		

		URLs= getColUrls();

		
//		if(includeHttps) URLs = doubleArrayUrlWithHttps(URLs); // adding https test case.
		
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
		
//	private static final String colUrl="https://stage.channelonline.com/colqa_sanity/";
	private static final String colUrl="https://usmb.channelonline.com/acme/home/";
	public String[] getColUrls(){
		String[] urls = {
				colUrl
			};
		return urls;
	}
	
	public String[] getBrowsers(){
		String[] browsers = {
				"chrome",
//				"firefox"
//				"internet explorer"
		};
		
		return browsers;
	}
		
	public static void main(String[] args){
		Object[][] testSetup = new ParameterFeeder().configureTestParams();
		for(int i=0; i< testSetup.length; i++){
			System.out.println(i);
			System.out.println(testSetup[i][0] + "| " + "browser: " + testSetup[i][1]);

		}
	}
}
