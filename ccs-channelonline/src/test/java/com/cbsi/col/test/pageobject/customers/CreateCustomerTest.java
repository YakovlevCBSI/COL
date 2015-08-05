package com.cbsi.col.test.pageobject.customers;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.HomePage;
import com.cbsi.col.pageobject.LoginPage;
import com.cbsi.col.pageobject.customers.AccountsPage;
import com.cbsi.col.pageobject.customers.CreateAccountPage;
import com.cbsi.col.pageobject.customers.RecentAccountsTab;
import com.cbsi.col.test.foundation.ColBaseTest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreateCustomerTest extends ColBaseTest{

	public static boolean isLoggedIn=false;

	public CreateCustomerTest(String url, String browser){
		super(url, browser);
	}

	@Before
	public void startUp(){
		super.startUp();
		navigateToCustomersPage();
		
	}

	@Test
	public void test1_createCustomer(){		
		System.out.println(companyName);
		RecentAccountsTab recentCustomersPage = createAccount();
		assertTrue(recentCustomersPage.hasCompany(companyName));
	}
	
	@Test
	public void test2_searchCustomer() throws InterruptedException{
		System.out.println(companyName);
		AccountsPage customersSeachPage = customersPage.searchCustomer(companyName, true);
		assertTrue(customersSeachPage.hasCompany(companyName));
	}
	
	@Test
	public void test3_deleteCustomer(){
		RecentAccountsTab recentCustomersPage= customersPage.goToRecentCustomersTab();
		recentCustomersPage.deleteCompany(companyName);
	}
	

}
