package com.cbsi.test.fastmapCache;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.cbsi.tests.FCatMongoObject.ItemDao;
import com.cbsi.tests.FCatMongoObject.MongoItem;
import com.cbsi.tests.util.CacheUtil;

public class checkItemsInCache {

	private static final int upcCatalogId = 5605;
	private static final int masterCatalogId=313;
	private static final int masterCatalogId2=5450;
	private static final int partyId = 10;
	@Test
	public void checkUpcEanIsLoaded(){
		List<MongoItem> mongoItems = ItemDao.getItemFirst(upcCatalogId, partyId);
		
		String upcAsc = mongoItems.get(0).getItemIds().get(MongoItem.ID);
		
		System.out.println(upcAsc);
		List<MongoItem> mongoItemsLast =  ItemDao.getItemLast(upcCatalogId, partyId);
		String upcDsc = mongoItemsLast.get(0).getItemIds().get(MongoItem.ID);

		Assert.assertNotEquals("null",new CacheUtil().getCacheByupcEan(upcAsc).get(MongoItem.MASTERID));
		Assert.assertNotEquals("null",new CacheUtil().getCacheByupcEan(upcDsc).get(MongoItem.MASTERID));

	}
	
	@Test
	public void checkMasterIsLoaded(){
		List<MongoItem> mongoItems = ItemDao.getItemFirst(masterCatalogId, partyId);
		
		String mf1 = mongoItems.get(0).getItemIds().get(MongoItem.MF);
		String mfPn1 = mongoItems.get(0).getItemIds().get(MongoItem.MFPN);
		
		System.out.println(mf1 + " : " + mfPn1);
		List<MongoItem> mongoItemsLast =  ItemDao.getItemLast(masterCatalogId, partyId);
		String mf2 = mongoItemsLast.get(0).getItemIds().get(MongoItem.MF);
		String mfPn2 = mongoItemsLast.get(0).getItemIds().get(MongoItem.MFPN);

		Assert.assertNotEquals("null", new CacheUtil().getCacheByMfMfpn(mf1, mfPn1).get(MongoItem.MASTERID));
		Assert.assertNotEquals("null", new CacheUtil().getCacheByMfMfpn(mf2, mfPn2).get(MongoItem.MASTERID));
	}
	
	@Test
	public void checkMaster2IsLoaded(){
		List<MongoItem> mongoItems = ItemDao.getItemFirst(masterCatalogId2, partyId);
		String mf1 = mongoItems.get(0).getItemIds().get(MongoItem.MF);
		String mfPn1 = mongoItems.get(0).getItemIds().get(MongoItem.MFPN);
				
		List<MongoItem> mongoItemsLast =  ItemDao.getItemLast(masterCatalogId2, partyId);
		String mf2 = mongoItemsLast.get(0).getItemIds().get(MongoItem.MF);
		String mfPn2 = mongoItemsLast.get(0).getItemIds().get(MongoItem.MFPN);

		Assert.assertNotEquals("null", new CacheUtil().getCacheByMfMfpn(mf1, mfPn1).get(MongoItem.MASTERID));
		Assert.assertNotEquals("null", new CacheUtil().getCacheByMfMfpn(mf2, mfPn2).get(MongoItem.MASTERID));
	}
}
