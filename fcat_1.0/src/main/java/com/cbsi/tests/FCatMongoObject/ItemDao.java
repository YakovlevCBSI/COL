package com.cbsi.tests.FCatMongoObject;

import java.util.ArrayList;
import java.util.List;

import com.cbsi.tests.FcatDB.MongoConnector;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

public class ItemDao {

	
	private static final int ASC = 1;
	private static final int DESC = -1;

	public static List<MongoItem> getItemFirst(int catId, int partyId){
		return getItems(catId, partyId, MongoItem.MODID, ASC, 1, 0);
	}
	
	public static List<MongoItem> getItemLast(int catId, int partyId){
		return getItems(catId, partyId, MongoItem.MODID, DESC, 1, 0);
	}
	
	public static List<MongoItem> getItems(int catId, int partyId){
		return getItems(catId, partyId, MongoItem.MODID, DESC, 1, 0);
	}
	
	public static List<MongoItem> getItems(int catId, int partyId, String sortBy){
		return getItems(catId, partyId, sortBy, DESC, 1, 0);
	}
	
	public static List<MongoItem> getItems(int catId, int partyId,String sortBy, int ascOrDescedning, int limit, int skip){
		
		List<MongoItem> items = new ArrayList<MongoItem>();
		
		BasicDBObject query = new BasicDBObject(MongoItem.PARTYID,  partyId)
								.append(MongoItem.CATALOGID, catId);
								
		System.out.println();
		
		MongoCursor<DBObject> cursor = getItemCollection().find(query).sort(new BasicDBObject(sortBy,ascOrDescedning)).skip(skip).limit(limit).iterator();
		while(cursor.hasNext()){
			items.add(new MongoItem(cursor.next()));
			System.out.println(cursor.toString());
		}
		
		MongoConnector.quit();
		
		return items;
	}

//	public List<MongoItem> getItemMid(){
//		
//	}
	
	//overload methods depends on enum.
	
	public static MongoCollection<DBObject> getItemCollection(){
		return MongoConnector.getDBObject("item");
	}
}
