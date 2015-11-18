package com.cbsi.tests.FCatMongoObject;

import java.util.Map;

import org.bson.types.ObjectId;

import com.mongodb.DBObject;

public class MongoItem {

	public static final String MASTERID = "masterId";
	public static final String ID = "id";
	public static final String MFPN = "mfPn";
	public static final String MF = "mf";
	public static final String ITEMIDS = "itemIds";
	public static final String DEL="del";
	public static final String MAP="map";
	
	public static final String CATALOGID = "catId";
	public static final String PARTYID = "partyId";
	
	public static final String MODID = "modId";
	
	private String masterId;
	private Map<String, String> itemIds;
	private long catalogId;
	private long partyId;
	private ObjectId modifiedId;
	private int map;
	private Boolean del;

	public MongoItem(DBObject dbObject) {
		// TODO Auto-generated constructor stub
		constructFromDBOject(dbObject);
	}
	
	private void constructFromDBOject(DBObject dbObject) {
		// TODO Auto-generated method stub
		setMasterId((String) dbObject.get(MASTERID));
		setItemIds((Map<String, String>)dbObject.get(ITEMIDS));
		setCatalogId((Long)dbObject.get(CATALOGID));
		setPartyId((Long)dbObject.get(PARTYID));
		setModifiedId((ObjectId)dbObject.get(MODID));
		setMap((Integer)dbObject.get(MAP));
		setDel((Boolean)dbObject.get(DEL));
	}

	public String getMasterId() {
		return masterId;
	}

	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}

	public Map<String, String> getItemIds() {
		return itemIds;
	}

	public void setItemIds(Map<String, String> itemIds) {
		this.itemIds = itemIds;
	}

	public long getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(long catalogId) {
		this.catalogId = catalogId;
	}

	public long getPartyId() {
		return partyId;
	}

	public void setPartyId(long partyId) {
		this.partyId = partyId;
	}

	public ObjectId getModifiedId() {
		return modifiedId;
	}

	public void setModifiedId(ObjectId modifiedId) {
		this.modifiedId = modifiedId;
	}
	
	public int getMap() {
		return map;
	}

	public void setMap(int map) {
		this.map = map;
	}

	public boolean getDel() {
		return del;
	}

	public void setDel(Boolean del) {
		if(del==null) this.del = null;
		else this.del = true;
	}

}
