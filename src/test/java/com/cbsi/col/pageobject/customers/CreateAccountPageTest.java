package com.cbsi.col.pageobject.customers;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.customers.AccountsPage;
import com.cbsi.col.pageobject.customers.AccountsPage.AccountType;
import com.cbsi.col.pageobject.customers.CreateAccountPage;
import com.cbsi.col.pageobject.customers.RecentAccountsTab;
import com.cbsi.col.pageobject.home.HomePage;
import com.cbsi.col.pageobject.home.LoginPage;
import com.cbsi.col.pageobject.home.ColBasePage.Time;
import com.cbsi.col.pageobject.home.SearchPopup.QueryColumn;
import com.cbsi.col.pageobject.home.SearchPopup.QueryOption;
import com.cbsi.col.test.foundation.ColBaseTest;

//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreateAccountPageTest extends ColBaseTest{

	public static boolean isLoggedIn=false;

	public CreateAccountPageTest(String url, String browser){
		super(url, browser);
	}

	@Before
	public void startUp(){
		super.startUp();
		navigateToCustomersPage();
		
	}
	
	@After
	public void cleanUp(){
		takeScreenshot();
		super.cleanUp();
		super.startUp();
		homePage.goToAccountsPage().goToRecentAccountsTab().deleteCompany(companyName);
//		AccountsPage accountPage = homePage.goToAccountsPage().goToAllAcountsTab().setFilterByDate(Time.TODAY);
//		AccountsPage accountPage = homePage.searchFor(QueryOption.Customers, true, QueryColumn.All, companyName, AccountsPage.class);
//		accountPage.deleteCompany(companyName);
	}

	@Test
	public void createCustomer(){
		RecentAccountsTab recentAccountsTab = createAccount(AccountType.CUSTOMER);
		assertTrue(recentAccountsTab.hasCompany(companyName));
	}
	
	@Ignore("Lead messed up data")
	@Test
	public void createLead(){
		RecentAccountsTab recentAccountsTab = createAccount(AccountType.LEAD);
		assertTrue(recentAccountsTab.hasCompany(companyName));
	}
	
	@Test
	public void createProspect(){
		RecentAccountsTab recentAccountsTab = createAccount(AccountType.PROSPECT);
		assertTrue(recentAccountsTab.hasCompany(companyName));
	}

	@Test
	public void createPartner(){
		RecentAccountsTab recentAccountsTab = createAccount(AccountType.PARTNER);
		assertTrue(recentAccountsTab.hasCompany(companyName));
	}
	
	@Test
	public void createVendor(){
		RecentAccountsTab recentAccountsTab = createAccount(AccountType.VENDOR);
		assertTrue(recentAccountsTab.hasCompany(companyName));
	}
	
	@Test
	public void Generic(){
		RecentAccountsTab recentAccountsTab = createAccount(AccountType.GENERIC);
		assertTrue(recentAccountsTab.hasCompany(companyName));
	}
}
