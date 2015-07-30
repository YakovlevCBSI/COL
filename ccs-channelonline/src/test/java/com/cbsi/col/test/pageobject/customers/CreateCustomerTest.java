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
import com.cbsi.col.test.foundation.ColBaseTest;
import com.cbsi.col.test.pageobject.customers.CreateNewCustomerPage;
import com.cbsi.col.test.pageobject.customers.CustomersPage;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreateCustomerTest extends ColBaseTest{

	private CustomersPage customersPage;
	public static final String companyName = "QaCustomer_ " +System.currentTimeMillis();
	public static final String address = "444 Oceancrest Dr";
	public static final String city = "Irvine";
	public static final String zip = "90019";
	public static boolean isLoggedIn=false;

	public CreateCustomerTest(String url, String browser){
		super(url, browser);
	}
	@Before
	public void startUp(){
		HomePage homePage = null;
			super.startUp();
			LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
			homePage = loginPage.loginToHomePage();

		customersPage = homePage.goToCustomersPage();
	}

	@Test
	public void test1_createCustomer(){
		System.out.println(companyName);
		CreateNewCustomerPage createNewCustomerPage = customersPage.clickCreateNewCustomer("customer");
		createNewCustomerPage.setCompanyName(companyName);
		createNewCustomerPage.setAddress(address);
		createNewCustomerPage.setCity(city);
		createNewCustomerPage.setZip(zip);
		createNewCustomerPage.clickFinish();
		
		RecentCustomersPage recentCustomersPage = createNewCustomerPage.goToHomePage().goToCustomersPage().goToRecentCustomersPage();
		assertTrue(recentCustomersPage.hasCompany(companyName));
	}
	
	@Test
	public void test2_searchCustomer() throws InterruptedException{
		CustomersPage customersSeachPage = customersPage.searchCustomer(companyName, true);
		assertTrue(customersSeachPage.hasCompany(companyName));
	}
	
	@Test
	public void test3_deleteCustomer(){
		System.out.println(companyName);

		customersPage.deleteCompany(companyName);
	}
	

}
