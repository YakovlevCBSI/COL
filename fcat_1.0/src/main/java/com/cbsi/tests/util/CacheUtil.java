package com.cbsi.tests.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.cbsi.tests.FCatMongoObject.MongoItem;

public class CacheUtil {
	static String server = "http://ccs-dev1.cloudapp.net:8080"; //stage

	private String mf;
	private String mfPn;
	private String upcEan;
	
	private HttpURLConnection con;
	private String result;
	
	private Map<String, String> mapResult;
	
	public Map<String, String> getCacheByupcEan(String upcEan){
		return getCache("", "", upcEan);
	}
	
	public Map<String, String> getCacheByMfMfpn(String mf, String mfPn){
		return getCache(mf, mfPn, "");
	}
	
	public  Map<String, String> getCache(String mf, String mfPn, String upcEan){
		try{
			 URL address = new URL(getUrl(mf, mfPn, upcEan));
			 con = (HttpURLConnection) address.openConnection();
			 con.setRequestMethod("GET");
		}catch(Exception e){
			System.out.println("FAILED REQUEST");
			e.printStackTrace();
		}
		
		try {
			getResponse();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		responseToMapResult();
		
		System.out.println("response: " + result);
		return mapResult;
	}
	

	public void getResponse() throws IOException{
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		
		result = response.toString();
		
//		System.out.println("getReponse: " + result);
		in.close();
	}
	
	public void responseToMapResult(){
		mapResult = new HashMap<String, String>();
		
		String[] properties = StringUtil.cleanJsonChars(result).split(",");
		
		for(String property: properties){
			String key = property.split(":")[0];
			String value = property.split(":")[1];

			mapResult.put(key, value);
		}
	}
	
	public static String getUrl(String mf, String mfPn, String upcEan){
		return StringUtil.cleanUrl(server + "/fcat-fastmap/querypn?mfr="+mf+"&pn="+mfPn+"&upc=" + upcEan);
	}
	
	public static void main(String[] args){
		new CacheUtil().getCache("","","0000000001267");
	}
}
