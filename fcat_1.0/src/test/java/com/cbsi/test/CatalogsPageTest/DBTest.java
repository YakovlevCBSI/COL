package com.cbsi.test.CatalogsPageTest;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.tests.FCatSqlObject.Catalog;
import com.cbsi.tests.FcatMySQL.MySQLConnector;
import com.cbsi.tests.Foundation.StageBaseTest;
import com.cbsi.tests.PageObjects.CatalogsPage;
import com.cbsi.tests.util.GlobalVar;

public class DBTest extends StageBaseTest{

	public DBTest(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}

	private static String query_CatalogNamesForm = "select * from fcat.catalog " + 
										"where not active = 0 and fcat.catalog.party = 1 " + 
										"order by catalog_name";
	
	private static String query_CatalogNamesEmbed = "select * from fcat.catalog " + 
			"where not active = 0 and fcat.catalog.party = 5 " + 
			"order by catalog_name";
	
	private static List<com.cbsi.tests.FCatSqlObject.Catalog> catalogsFromForm;
	private static List<com.cbsi.tests.FCatSqlObject.Catalog> catalogsFromEmbed;

	
	@BeforeClass
	public static void readFromDB(){
		catalogsFromForm = new MySQLConnector().connectToFcatDB().runQuery(query_CatalogNamesForm, com.cbsi.tests.FCatSqlObject.Catalog.class);
		catalogsFromEmbed = new MySQLConnector().connectToFcatDB().runQuery(query_CatalogNamesEmbed, com.cbsi.tests.FCatSqlObject.Catalog.class);

	}

	
	///This should only run for fcat cloud fomr based plus anythign db related.
	@Test
	public void matchCatalogsObjects_name_itemCount_modifiedBy_onDefaultPage(){
		
		
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
	/**
	@Test
	public void DisplayNumberOfProductsMatchDB(){
		List<Catalog> catalogsNameFromSQL = (getURL().contains(GlobalVar.embedPath) ? catalogsFromEmbed:catalogsFromForm);
		List<String> catalogsNameFromSQLGetName = new ArrayList<String>();
		for(Catalog c: catalogsNameFromSQL){
			//catalogsNameFromSQLGetName.add(c.get);
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
	
	public <T> boolean twoListsAreEqual(List<T> list1, List<T> list2){
		System.out.println("is it equal " + list1.equals(list2));
		if(!list1.equals(list2)){ 
			Collection subtractedList1 = CollectionUtils.subtract(list1, list2);
			Collection subtractedList2 = CollectionUtils.subtract(list2, list1);
			
			if(subtractedList1.size() >= 1){
				System.out.println();
				System.out.println("list1: ");
				for(Object c: subtractedList1){
					Catalog convertC= (Catalog)c;
					System.out.println(convertC.getCatalog_name() + "/ " + convertC.getModifiedBy() + "/ " + convertC.getParty());
				}
			}
			
			if(subtractedList2.size() >= 1){
				System.out.println();
				System.out.println("list2: ");
				for(Object c: subtractedList2){
					Catalog convertC= (Catalog)c;
					System.out.println(convertC.getCatalog_name() + "/ " + convertC.getModifiedBy() + "/ " + convertC.getParty());
				}
			}
			return false;
		}
		
		return true;
	}
	
	public List<Catalog> turnCatalogsTableIntoObject(){
		CatalogsPage catalogsPage= PageFactory.initElements(driver, CatalogsPage.class);

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
	
}