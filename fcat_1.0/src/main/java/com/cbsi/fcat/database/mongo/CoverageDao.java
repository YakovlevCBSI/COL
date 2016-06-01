package com.cbsi.fcat.database.mongo;

import java.util.ArrayList;
import java.util.List;

import com.cbsi.fcat.database.util.MongoConnector;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.ReadPreference;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

public class CoverageDao {
	
	public static final String COVERAGE = "coverage";
	
	public static List<DBObject> getCoverageItemByCtype(String ctype){
		return getCoverageItem(ctype, 5000);
	}
	
	public static List<DBObject> getCoverageItem(String ctype, int limit){
//		List<MongoItem> coverageItems = new ArrayList<MongoItem>();
		List<DBObject> coverageItems = new ArrayList<DBObject>();

		BasicDBObject query = new BasicDBObject(MongoItem.CTYPE, ctype);
		
		MongoCursor<DBObject> cursor = getCoverageCollection().find(query).limit(limit).iterator();
		while(cursor.hasNext()){
			coverageItems.add(cursor.next());
		}
		
		MongoConnector.quit();
		
		return coverageItems;
	}
	
	public static List<DBObject> getCoverageItem(String mfId, String mfPn, String ctype, int limit){
		List<DBObject> coverageItems = new ArrayList<DBObject>();
		
		BasicDBObject query = new BasicDBObject(MongoItem.MFID, mfId)
								.append(MongoItem.MFPN, mfPn)
								.append(MongoItem.CTYPE, ctype);
		
		MongoCursor<DBObject> cursor = getCoverageCollection().find(query).limit(limit).iterator();
		while(cursor.hasNext()){
			coverageItems.add(cursor.next());
		}
		
		return coverageItems;
	}
	
	public static Long getCoverageItemCount(String ctype){
		BasicDBObject query = new BasicDBObject(MongoItem.CTYPE, ctype);
		
		return getCoverageCollection().count(query);
	}
	
	public static MongoCollection<DBObject> getCoverageCollection(){
		return MongoConnector.getDBObject(COVERAGE);
	}
	
	public static void main(String[] args){
		System.out.println(CoverageDao.getCoverageItemCount("110-28"));
	}
}
