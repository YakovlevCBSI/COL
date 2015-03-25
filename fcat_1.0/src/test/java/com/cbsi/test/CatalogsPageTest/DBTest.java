package com.cbsi.test.CatalogsPageTest;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.tests.FCatSqlObject.Catalog;
import com.cbsi.tests.FcatDB.MySQLConnector;
import com.cbsi.tests.Foundation.StageBaseTest;
import com.cbsi.tests.PageObjects.CatalogsPage;
import com.cbsi.tests.PageObjects.PartyPopupPage;
import com.cbsi.tests.util.GlobalVar;


public class DBTest extends StageBaseTest{

	public DBTest(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}

	private static String query_CatalogNamesForm = 
			"select * from fcat.catalog " + 
			"where not active = 0 and catalog.party = 1 " + 
			"and catalog_type=10 and content_type=11 " + 
			"order by catalog_name";
	
	private static String query_CatalogNamesEmbed = 
			"select * from fcat.catalog " + 
			"where not active = 0 and fcat.catalog.party = 5 " + 
			"order by catalog_name";
	/**
	private static String query_searchPartyLimit30 = "SELECT (party_name) FROM fcat.catalog " + 
													"right join fcat.party " + 
													"on fcat.catalog.party = fcat.party.id " + 
													"order by party_name limit 30";
													*/
	private static String query_searchPartyLimit30 = 
			"SELECT (party_name) FROM fcat.party " + 
			"order by party_name limit 30";
	
	private static  String query_searchPartyWithActiveCatalogs = 
			"select * from catalog as c "+
			"inner join party as p " +
			"on p.id = c.party " +
			"where not c.active=0 and p.party_name= \'BFP\' " +
			"and catalog_type=10 and content_type=11 " + 
			"order by c.catalog_name ";
																		
	
	private static List<Catalog> catalogsFromForm;
	private static List<Catalog> catalogsFromEmbed;
	private static List<String> partiesLimit30;
	private static List<Catalog> activeCatalogs;
	private static List<String> partiesWithActiveCatalogs;
	
	@Rule 
	public Timeout globalTimeout = new Timeout(480000);
	
	@BeforeClass
	public static void readFromDB(){
		
		MySQLConnector mysql = new MySQLConnector();
		mysql.connectToFcatDB();
		System.out.println(query_CatalogNamesForm);
		//-------- matchCatalogsObjects_name_itemCount_modifiedBy_onDefaultPage----------//
		catalogsFromForm = mysql.runQuery(query_CatalogNamesForm, Catalog.class, false);
		catalogsFromEmbed = mysql.runQuery(query_CatalogNamesEmbed, Catalog.class, false);
		System.out.println(catalogsFromForm);
		
		//------- allPartiesDisplayInPartySelectorDialog ----------//
		partiesLimit30 = mysql.runQuery(query_searchPartyLimit30);
		
		//---- partyChooserSearchMatchDB ----------//
		activeCatalogs = mysql.runQuery(query_searchPartyWithActiveCatalogs, Catalog.class, false);
		partiesWithActiveCatalogs = mysql.runQuery(query_searchPartyWithActiveCatalogs);
		
		
		mysql.quit();
		
	}

	
	///This should only run for fcat cloud fomr based plus anythign db related.
	@Test
	public void matchCatalogsDBObjectsToCatalogTableObject(){
		
		
		List<Catalog> catalogsNameFromSQL = (getURL().contains(GlobalVar.embedPath) ? catalogsFromEmbed:catalogsFromForm);

	
		//CatalogsPage catalogsPage= PageFactory.initElements(driver, CatalogsPage.class);
		List<Catalog> catalogNameFromWeb = turnCatalogsTableIntoObject();
		
		for(Catalog c: catalogsNameFromSQL){
			System.out.println("sql: "+ c.getCatalog_name() + "/ " + c.getModifiedBy() + " / " + c.getCreated() + " / " + c.getItemCount() + " / " + c.getParty());
		}
		
		for(Catalog c: catalogNameFromWeb){
			System.out.println("web:" + c.getCatalog_name() + "/ " + c.getModifiedBy() + " / " + c.getCreated() + " / " + c.getItemCount() + " / " + c.getParty());
		}
		
		assertTrue(twoListsAreEqual(catalogsNameFromSQL, catalogNameFromWeb));
		
	}

	@Test
	public void first30PartiesDisplayInPartySelectorDialog(){
		//get parties from first three pages, then match with db.
		if(getURL().contains(GlobalVar.embedPath)) return;
		
		CatalogsPage catalogsPage = PageFactory.initElements(driver, CatalogsPage.class);
		PartyPopupPage partyPopup = catalogsPage.clickSearchParty();

		List<String> searchElementsPage1 = partyPopup.searchResultToText();
		List<String> searchElementsPage2 = partyPopup.clickNext().searchResultToText();
		List<String> searchElementsPage3 = partyPopup.clickNext().searchResultToText();
		
		List<String> searchElementsAll = addAllLists(searchElementsPage1, searchElementsPage2, searchElementsPage3);
		/**
		System.out.println(searchElementsAll.size());
		for(String s: searchElementsAll){
			System.out.println(s);
		}
		*/
		
		assertTrue(twoListsAreEqual(partiesLimit30, searchElementsAll));
		
	
		
	}
	/**
	@Test
	public void tokenContainsParyRef(){
		
	}
	*/
	/**
	@Test
	public void PartyChooserSearchBFPThenMatchDB(){
		if(getURL().contains(GlobalVar.embedPath)) return;
		
		Map<String, Object> map = toStringObjectMap(partiesWithActiveCatalogs, activeCatalogs);
		Map<String, List<Object>> sortedMap = sortPartyToCatalogsObjectMapByParty(map);
		
		for(Map.Entry<String, List<Object>> entry : sortedMap.entrySet()){
			//if(count >3) break;
			
			String searchText = entry.getKey();
			System.out.println("test is : " + searchText);
			CatalogsPage catalogsPage = PageFactory.initElements(driver, CatalogsPage.class);
			PartyPopupPage partyPopup = catalogsPage.clickSearchParty();
			CatalogsPage postcatalogPage = partyPopup.searchParty(searchText).pickFromResult();
			//click searchText result;
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<Catalog> catalogNameFromWeb = turnCatalogsTableIntoObject();
			assertTrue(twoListsAreEqual((List<Catalog>)(Object)entry.getValue(), catalogNameFromWeb));		
		}
		
	}
	*/
	@Test
	public void PartyChooserBFPMatchesDB(){
		if(getURL().contains(GlobalVar.embedPath)) return;
		
		CatalogsPage catalogsPage = PageFactory.initElements(driver, CatalogsPage.class);
		PartyPopupPage partyPopup = catalogsPage.clickSearchParty();
		CatalogsPage postcatalogPage = partyPopup.searchParty("bfp").pickFromResult();

		List<Catalog> catalogNameFromWeb = turnCatalogsTableIntoObject();

		assertTrue(twoListsAreEqual(activeCatalogs, catalogNameFromWeb));
		           
	}
	
	/**
	@Test
	public void selectPartyMatchesCorrectOnDB(){
		
	}
	*/
	
	/**
	@Test
	public void DisplayNumberOfProductsMatchDB(){
		List<Catalog> catalogsNameFromSQL = (getURL().contains(GlobalVar.embedPath) ? catalogsFromEmbed:catalogsFromForm);
		List<String> catalogsNameFromSQLGetName = new ArrayList<String>();
		for(Catalog c: catalogsNameFromSQL){
			//catalogsNameFromSQLGetName.add(                  c.get);
		}
		
	}
	*/
	/**
	@Test
	public void DisplayCatalogNameMatchDB(){
		
	}
	*/
	public boolean hasDuplicate(List<String> someStringList){
		Set<String> addset = new HashSet<String>();
		Set<String> returnSet = new HashSet<String>();
		boolean containsDuplicate = false;
		for(String s: someStringList){
			if(!addset.add(s)){
				returnSet.add(s);
				System.out.println("duplicate list: " + s);
			}
		}
		
		if(returnSet.size() >=1){
			return false;
		}
		
		return true;
		
	}
	
	public List<Catalog> turnCatalogsTableIntoObject(){

		List<WebElement> elements = driver.findElements(By.cssSelector("tbody#catalog-list-table-body tr"));
		List<Catalog> catalogsFromTable = new ArrayList<Catalog>();
		
		//System.out.println(elements.size());
		
		//int count=0;
		for(WebElement e: elements){
			if(e.getAttribute("data-id") != null){
				String name = e.findElement(By.xpath("td[@class='name-column']/a")).getText();
				String itemCount = e.findElement(By.xpath("td[@class='number-column']/span")).getText();
				String modifiedBy = e.findElement(By.xpath("td[@class='user-name-column']")).getText();
				
				Catalog catalog = new Catalog();
				catalog.setCatalog_name(name.equals("")?null:name);
				catalog.setItemCount(new BigDecimal(itemCount).equals("")?null:new BigDecimal(itemCount));
				catalog.setModifiedBy(modifiedBy.equals("")?null:modifiedBy);
				
				catalogsFromTable.add(catalog);
			}
	
		}
		/**
		for(Catalog c: catalogsFromTable){
			System.out.println(c.getCatalog_name());
		}
		*/
		return catalogsFromTable;
	}
	
	public <T> List<T> addAllLists(List<T>...list){

		List<T> searchElementsAll = new ArrayList<T>();
		for(int i=0; i< list.length; i++){
			searchElementsAll.addAll(list[i]);
		}
		
		return searchElementsAll;
	}
	
	public <T> Map<String, Object> toStringObjectMap(List<String> name, List<T> SQLObjects){
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("name size: " + name.size() + " / SQLObjectsSize: " + SQLObjects.size() );
		if(name.size() == SQLObjects.size()){
			for(int i=0; i<name.size(); i++){
				map.put(i + "###" + name.get(i), SQLObjects.get(i));
				//System.out.println(i + " / " + name.get(i) + " / "  + ((Catalog)SQLObjects.get(i)).getCatalog_name());
			
				//System.out.println(name.get(i) + " / " + ((Catalog)SQLObjects.get(i)).getCatalog_name());
				/**
				if(i > 320) {
					System.out.println(name.get(i) + " / " + ((Catalog)SQLObjects.get(i)).getCatalog_name());
				}
				*/
			}
		}
		else{
			System.out.println("Two list size have to be equal for toStringObjectMapToWork");
		}
		
		System.out.println("ToStrinbObject map size: " + map.size());
		return map;
		
	}
	
	public Map<String, List<Object>> sortPartyToCatalogsObjectMapByParty(Map<String, Object> map){
		Map<String, List<Object>> newMap = new HashMap<String, List<Object>>();
		List<Object> CatalogsList = null;
		String temp = "";
		
		System.out.println("paramter map: " + map.size());  /// good upto here.... showing 131
		
		for(Map.Entry<String, Object> entry :map.entrySet()){
			
			String cleanKey = entry.getKey().split("###")[1];
			if(!newMap.containsKey(cleanKey)){
				
					//System.out.println("found afterfirstiteration ... " + entry.getKey() + "/ " + ((Catalog)entry.getValue()).getCatalog_name());
					
					CatalogsList = new ArrayList<Object>();
					CatalogsList.add(entry.getValue());
					newMap.put(cleanKey, CatalogsList);
				
				//System.out.println(temp);
			}
			else{

				List<Object> exitingCatalogList = newMap.get(cleanKey);
				exitingCatalogList.add(entry.getValue());
				//CatalogsList.add(entry.getValue());
				
				//System.out.println("else add; " + ((Catalog)entry.getValue()).getCatalog_name());
			}
			//temp = entry.getKey();
			
		}
		if(CatalogsList == null){
			System.out.println("CatalogsList failed toinitilize.....");

		}
		
		System.out.println("Size: " + newMap.size() );
		return newMap;
		
	}
	
}