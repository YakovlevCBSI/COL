package com.cbsi.tests.FcatDB;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser.Feature;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.cbsi.tests.FCatMongoObject.MongoItem;
import com.cbsi.tests.FCatMongoObject.Product;
import com.cbsi.tests.util.GlobalVar;
import com.cbsi.tests.util.PropertyUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class MongoConnector {

	private static String host =  PropertyUtil.get("mongo.server");
	private static String port = PropertyUtil.get("mongo.port");	
	private static String dbname = PropertyUtil.get("mongo.db");
	private static String username = PropertyUtil.get("mongo.username");
	private static String pw = PropertyUtil.get("mongo.password");
	
	private static MongoClient mongoClient;
	private static MongoDatabase db;
	private static DBCursor cursor;

	public static MongoCollection<DBObject> getDBObject(String collectionName){
		connectToMongo();

		BasicDBObject searchQuery = new BasicDBObject();	
		MongoCollection<DBObject> dbObject = db.getCollection(collectionName, DBObject.class);

		return dbObject;
	}

	public static void connectToMongo(){
		
		System.out.println(host);
		System.out.println(username);
		System.out.println(pw);
		System.out.println(dbname);
		
		MongoCredential credential = MongoCredential.createScramSha1Credential(username, dbname, pw.toCharArray());
		List<MongoCredential> credentials =new ArrayList<MongoCredential>();
		credentials.add(credential);
		mongoClient = new MongoClient(new ServerAddress(host, Integer.parseInt(port)), Arrays.asList(credential));
		
		System.out.println("success init connection");

		db =  mongoClient.getDatabase(dbname).withReadPreference(ReadPreference.primary());

		return;
	}

	public static void quit(){
		mongoClient.close();
		
		System.out.println("connection quit");
	}
	
//	public static void main(String[] args) {
//	
//	MongoConnector mongo = new MongoConnector();
//
//	MongoCursor<DBObject> cursor = mongo.getDBObject("item").find().sort(new BasicDBObject(MongoItem.MODID,1)).limit(1).iterator();
//
//	mongo.queryFor("item",new String[]{"partyId:10", "catId:5605"}, 1);
//	List<Product> products = mongo.turnQueryIntoProductList();
//
//	for(Product product: products){
//		System.out.println(product.toString());
//	}
//	
//	long h = Long.parseLong(-9 + "");
//	System.out.println(h);
//	
//	mongo.quit();
//}

}
