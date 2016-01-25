package com.cbsi.col.pageobject.home;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cbsi.col.pageobject.customers.AccountsPage;
import com.cbsi.col.pageobject.customers.RecentAccountsTab;
import com.cbsi.col.pageobject.documents.DocumentsPage;
import com.cbsi.col.pageobject.documents.DocumentsPage.DocumentTabs;
import com.cbsi.col.pageobject.documents.QuotePage;
import com.cbsi.col.pageobject.home.SearchPopup.QueryColumn;
import com.cbsi.col.pageobject.home.SearchPopup.QueryOption;
import com.cbsi.col.test.foundation.ColBaseTest;
import com.cbsi.col.test.util.StringUtil;
import com.cbsi.col.test.util.TableUtil;

public class SearchPopupTest extends ColBaseTest{
	
	public RecentAccountsTab recentCustomersPage;
	
	public SearchPopupTest(String url, String browser) {
		super(url, browser);
		// TODO Auto-generated constructor stub
	}
	
	@Test
	public void searchAccountAllContains(){
		String keyword = "qa";
		AccountsPage accountsPage = homePage.searchFor(QueryOption.Customers, true, QueryColumn.All, keyword, AccountsPage.class);
		List<LinkedHashMap<String, String>> maps = accountsPage.getTableAsMaps();
		assertTrue(TableUtil.tableMapHasWordContains(maps, keyword));
	}
	
	@Test
	public void searchAccountAllStartsWith(){
		String keyword = "qa";
		AccountsPage accountsPage = homePage.searchFor(QueryOption.Customers, false, QueryColumn.All, keyword, AccountsPage.class);
		List<LinkedHashMap<String, String>> maps = accountsPage.getTableAsMaps();
		assertTrue(TableUtil.tableMapHasWordStartsWith(maps, keyword));
	}
	
	@Test
	public void searchAccountEmailContains(){
//		String keyword = "a\\_";
		String keyword = "qa";

		AccountsPage accountsPage = homePage.searchFor(QueryOption.Customers, true, QueryColumn.Email, keyword, AccountsPage.class);
		List<LinkedHashMap<String, String>> maps = accountsPage.getTableAsMaps();
		assertTrue(TableUtil.tableMapHasWord(maps, "email",keyword, false));
	}
	
	@Test
	public void searchAccountEmailStartsWith(){
		String keyword = "qa";
		AccountsPage accountsPage = homePage.searchFor(QueryOption.Customers, false, QueryColumn.Email, keyword, AccountsPage.class);
		List<LinkedHashMap<String, String>> maps = accountsPage.getTableAsMaps();
		assertTrue(TableUtil.tableMapHasWord(maps, "email",keyword, true));
	}
	
	@Test
	public void searchAccountCompanyContains(){
//		String keyword = "a\\_";
		String keyword = "qa";

		AccountsPage accountsPage = homePage.searchFor(QueryOption.Customers, true, QueryColumn.Company, keyword, AccountsPage.class);
		List<LinkedHashMap<String, String>> maps = accountsPage.getTableAsMaps();
		assertTrue(TableUtil.tableMapHasWord(maps,"companylocationcode", keyword, false));
	}
	
	@Test
	public void searchAccountCompanyStartsWith(){
		String keyword = "qa";
		AccountsPage accountsPage = homePage.searchFor(QueryOption.Customers, false, QueryColumn.Company, keyword, AccountsPage.class);
		List<LinkedHashMap<String, String>> maps = accountsPage.getTableAsMaps();
		assertTrue(TableUtil.tableMapHasWord(maps,"companylocationcode", keyword, true));
	}
	
	@Test
	public void searchAccountCityStartsWith(){
		String keyword = "Irvin";
		AccountsPage accountsPage = homePage.searchFor(QueryOption.Customers, false, QueryColumn.City, keyword, AccountsPage.class);
		List<LinkedHashMap<String, String>> maps = accountsPage.getTableAsMaps();
		assertTrue(TableUtil.tableMapHasWord(maps,"city", keyword, true));
	}
	
	@Test
	public void searchAccountCityContainsWith(){
		String keyword = "rvin";
		AccountsPage accountsPage = homePage.searchFor(QueryOption.Customers, true, QueryColumn.City, keyword, AccountsPage.class);
		List<LinkedHashMap<String, String>> maps = accountsPage.getTableAsMaps();
		assertTrue(TableUtil.tableMapHasWord(maps,"city", keyword, false));
	}
	
	@Test
	public void filterContainsMatchesPartialString(){
		String description = companyName;
		String keyword = "qa " + description;
		
		QuotePage quotePage = goToFirstOpenQuote();
		quotePage = (QuotePage) quotePage.setDescription(description);
		quotePage = (QuotePage) quotePage.clickSave();
		homePage = quotePage.goToHomePage();
		
		DocumentsPage documentsPage = homePage.searchFor(QueryOption.QuotesAndOrders, true, QueryColumn.All, keyword, DocumentsPage.class);
		List<LinkedHashMap<String, String>> maps = documentsPage.getTableAsMaps();
		assertTrue(maps.size() >=1);

	}
}
