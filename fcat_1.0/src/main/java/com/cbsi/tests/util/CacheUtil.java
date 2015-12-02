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
	private String mf;
	private String mfPn;
	private String upcEan;
	
	private HttpURLConnection con;
	private String result;
	
	private Map<String, String> mapResult;
	
	private static Env env;
	public CacheUtil(Env env){
		this.env = env;
	}
	
	public Map<String, String> getCacheByupcEan(String upcEan){
		return getCache("", "", upcEan);
	}
	
	public Map<String, String> getCacheByMfMfpn(String mf, String mfPn){
		return getCache(mf, mfPn, "");
	}
	
	public  Map<String, String> getCache(String mf, String mfPn, String upcEan){
		try{
			 URL address = new URL(getUrl(mf, mfPn, upcEan));
			 System.out.println(address.toString());
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
	
	public String getUrl(String mf, String mfPn, String upcEan){
		
		return env.getServer() + "/fcat-fastmap/querypn?"
				+ "mfr=" + StringUtil.cleanUrl(mf)
				+"&pn="+ StringUtil.cleanUrl(mfPn) 
				+ "&upc=" + StringUtil.cleanUrl(upcEan);
	}
	
	public static void main(String[] args){
		new CacheUtil(Env.STAGE).getCache("","","0000000001267");
	}
	
	public enum Env{
		STAGE("http://ccs-dev1.cloudapp.net:8080"),
		PROD("http://ccs-fcat-us1.cloudapp.net:6700");
		
		private String server;
		
		Env(String server){
			this.server = server;
		}
		
		public String getServer(){
			return server;
		}
	}
	
	
}
