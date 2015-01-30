package com.cbsi.tests.FcatDB;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser.Feature;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.cbsi.tests.FCatMongoObject.Product;
import com.cbsi.tests.util.GlobalVar;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoConnector {
	private static String host =  GlobalVar.MongoHost;
	private static String dbname = "fcat-dev";
	private static String username = GlobalVar.MongoUsername;
	private static String pw = GlobalVar.MongoPassword;
	private static MongoClient mongoClient;
	private static DB db;
	private static DBCursor cursor;
	
	public void queryFor(String collectionName, String[] queryText, int limitNum){
		DBCollection table = db.getCollection(collectionName);
		
		BasicDBObject searchQuery = new BasicDBObject();
		
		for(String s: queryText){
			String[] datas = s.split(":");

			if(datas[0].equals("partyId") || datas[0].equals("catId")){
				searchQuery.put(datas[0], Integer.parseInt(datas[1].trim()));	
			}
			else{
				searchQuery.put(datas[0], datas[1]);	
			}
		}
		
		//order by object id here
		BasicDBObject desc = new BasicDBObject();
		desc.put("_id", -1);

		//searchQuery.put("itemIds.mfPn", "abcde");
		
		cursor = table.find(searchQuery).limit(limitNum).sort(desc);
		return;
	}
	
	public static void main(String[] args) {
		/**
		MongoConnector mongo = new MongoConnector();
		mongo.connectToMongo();
		mongo.queryFor("item",new String[]{"partyId:1", "catId:8666"}, 10);
		List<Product> products = mongo.turnQueryIntoProductList();

		for(Product product: products){
			System.out.println(product.toString());
		}
		*/
		
		long h = Long.parseLong(-9 + "");
		System.out.println(h);
	}
	
	public List<Product> turnQueryIntoProductList(){
		
		List<Product> products = new ArrayList<Product>();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

		while(cursor.hasNext()){
			DBObject obj = cursor.next();
			Product product = new Product();
			
			int map = -9;
			String mfpn ="";
			String upcEan="";
			String mf="";
			String id="";
			String locale="";
			String contentType="";
			String msrp="";
			String price="";
			String productUrl="";
			String inventory = "";
			String cnetSkuId ="";
			
			
			//product.setId(obj.get("_id").toString());
			//product.setItemIds(obj.get("itemIds").toString());
			map = Integer.parseInt(obj.get("map").toString().replace("\"", ""));
			
			String itemIds= obj.get("itemIds").toString();
			//System.out.println("object id: " + itemIds);
			String contents ="";
			try{
				contents = obj.get("contents").toString();
				//System.out.println(contents);
			}
			catch(NullPointerException e){
				System.out.println("contents not found...");
			}
			
			 try {
				JsonNode node = mapper.readValue(itemIds, JsonNode.class);
				
				mfpn = node.findValue("mfPn").toString().replace("\"", "");
				//upcEan= node.findValue("upcEan").toString().replace("\"", "");
				upcEan= findValueNotNull(node, ("upcEan"));

				
				mf = node.findValue("mf").toString().replace("\"", "");
				id = node.findValue("id").toString().replace("\"", "");

				
				if(!contents.isEmpty()){
					//System.out.println("is not empty...");
					JsonNode nodeExtra = mapper.readValue(contents, JsonNode.class);
				//	System.out.println(nodeExtra.toString());
					
					locale = nodeExtra.findValue("locale").toString().replace("\"", "");
					
					//System.out.print("local: " + nodeExtra.findValue("locale") + "\t");
					contentType = nodeExtra.findValue("contentType").toString().replace("\"", "");

				
						String content = nodeExtra.findValue("content").toString();
						JsonNode lastInnerElement = mapper.readValue(content, JsonNode.class);
						
						msrp = findValueNotNull(lastInnerElement,("MSRP"));
						price = findValueNotNull(lastInnerElement,("price"));
						productUrl = findValueNotNull(lastInnerElement,("productUrl"));
						//System.out.println("long parse: " + lastInnerElement.findValue("inventory").toString().replace("\"", ""));
						inventory = findValueNotNull(lastInnerElement,("inventory"));
						//cnetSkuId = lastInnerElement.findValue("cnetSkuId").toString().replace("\"", "");
						cnetSkuId = findValueNotNull(lastInnerElement,("cnetSkuId"));
					//System.out.println(msrp + " / "  + price + " / " + productUrl + " / " + inventory + " / " + cnetSkuId);
							
				}	
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				quit();
			}
			
			product.setMap(map);
			product.setMfpn(mfpn);
			product.setUpcEan(upcEan);
			product.setMf(mf);
			product.setId(id);
			product.setLocale(locale);
			product.setContentType(contentType);
			product.setMsrp(msrp);
			product.setPrice(price);
			product.setProductUrl(productUrl);
			product.setInventory(inventory);
			product.setCentSkuId(cnetSkuId);
			
			products.add(product);
			
		}
		return products;
	}
	
	public MongoConnector connectToMongo(){
		try {
			mongoClient = new MongoClient(host);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db = mongoClient.getDB(dbname);
		boolean auth = db.authenticate(username, pw.toCharArray());
		System.out.println("auth success: " + auth);
		
		if(!auth){
				try {
					throw new Exception("Failed to connect to mongo.....");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return this;
	}
	
	public String findValueNotNull(JsonNode element, String key){
		String value = "";
		try{
			value = element.findValue(key).toString().replace("\"", "");
		}
		catch(NullPointerException e){
			System.out.println("key: " + key + " was not found...");
			value="";
		}
		
		return value;
	}
	public static void quit(){
		mongoClient.close();
	}
}
