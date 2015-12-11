package com.cbsi.fcat.util;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cbsi.fcat.util.CacheUtil;
import com.cbsi.fcat.util.CacheUtil.Env;
import com.cbsi.tests.FCatMongoObject.ItemDao;
import com.cbsi.tests.FCatMongoObject.MongoItem;

public class checkItemsInCache {

	private static final int upcCatalogId = 5605;
	private static final int masterCatalogId=313;
	private static final int masterCatalogId2=5450;
	private static final int partyId = 10;
	
	public CacheUtil cacheUtil;
	@Before
	public void startUp(){
		 cacheUtil = new CacheUtil(Env.STAGE);
	}
	
	@Test
	public void checkUpcEanIsLoaded(){
		List<MongoItem> mongoItems = ItemDao.getItemFirst(upcCatalogId, partyId);
		String upcAsc = mongoItems.get(0).getItemIds().get(MongoItem.ID);
		
		List<MongoItem> mongoItemsLast =  ItemDao.getItemLast(upcCatalogId, partyId);
		String upcDsc = mongoItemsLast.get(0).getItemIds().get(MongoItem.ID);
		
		
		List<MongoItem> mongoItemsMid =  ItemDao.getItems(upcCatalogId, partyId, MongoItem.MODID, -1, 1, 10000);
		String upcMid = mongoItemsMid.get(0).getItemIds().get(MongoItem.ID);
		
		Assert.assertNotEquals("null",cacheUtil.getCacheByupcEan(upcAsc).get(MongoItem.MASTERID));
		Assert.assertNotEquals("null",cacheUtil.getCacheByupcEan(upcDsc).get(MongoItem.MASTERID));
		Assert.assertNotEquals("null",cacheUtil.getCacheByupcEan(upcMid).get(MongoItem.MASTERID));

	}
	
	@Test
	public void checkMasterIsLoaded(){
		List<MongoItem> mongoItems = ItemDao.getItemFirst(masterCatalogId, partyId);	
		String mf1 = mongoItems.get(0).getItemIds().get(MongoItem.MF);
		String mfPn1 = mongoItems.get(0).getItemIds().get(MongoItem.MFPN);
		
		List<MongoItem> mongoItemsLast =  ItemDao.getItemLast(masterCatalogId, partyId);
		String mf2 = mongoItemsLast.get(0).getItemIds().get(MongoItem.MF);
		String mfPn2 = mongoItemsLast.get(0).getItemIds().get(MongoItem.MFPN);
		
		List<MongoItem> mongoItemsMid =  ItemDao.getItems(masterCatalogId, partyId, MongoItem.MODID, -1, 1, 10000);
		String mf3 = mongoItemsMid.get(0).getItemIds().get(MongoItem.MF);
		String mfPn3 = mongoItemsMid.get(0).getItemIds().get(MongoItem.MFPN);

		System.out.println("mf: " + mf1 + " / " + "mfPn: " + mfPn1);
		System.out.println("mf: " + mf2 + " / " + "mfPn: " + mfPn2);
		System.out.println("mf: " + mf3 + " / " + "mfPn: " + mfPn3);


		Assert.assertNotEquals("null", cacheUtil.getCacheByMfMfpn(mf1, mfPn1).get(MongoItem.MASTERID));
		Assert.assertNotEquals("null", cacheUtil.getCacheByMfMfpn(mf2, mfPn2).get(MongoItem.MASTERID));
		Assert.assertNotEquals("null", cacheUtil.getCacheByMfMfpn(mf3, mfPn3).get(MongoItem.MASTERID));

	}
	
	@Test
	public void checkMaster2IsLoaded(){
		List<MongoItem> mongoItems = ItemDao.getItemFirst(masterCatalogId2, partyId);
		String mf1 = mongoItems.get(0).getItemIds().get(MongoItem.MF);
		String mfPn1 = mongoItems.get(0).getItemIds().get(MongoItem.MFPN);
				
		List<MongoItem> mongoItemsLast =  ItemDao.getItemLast(masterCatalogId2, partyId);
		String mf2 = mongoItemsLast.get(0).getItemIds().get(MongoItem.MF);
		String mfPn2 = mongoItemsLast.get(0).getItemIds().get(MongoItem.MFPN);
		
		System.out.println("mf: " + mf1 + " / " + "mfPn: " + mfPn1);
		System.out.println("mf: " + mf2 + " / " + "mfPn: " + mfPn2);
		
		Assert.assertNotEquals("null", cacheUtil.getCacheByMfMfpn(mf1, mfPn1).get(MongoItem.MASTERID));
		Assert.assertNotEquals("null", cacheUtil.getCacheByMfMfpn(mf2, mfPn2).get(MongoItem.MASTERID));
	}
	
	
}
