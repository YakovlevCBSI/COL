package com.cbsi.col.pageobject.customers;

import org.junit.Before;
import org.junit.Test;

import com.cbsi.col.test.foundation.ColBaseTest;

public class cleanUpCompanies extends ColBaseTest{
	public cleanUpCompanies(String url, String browser) {
		super(url, browser);
		// TODO Auto-generated constructor stub
	}

	@Before
	public void startUp(){
		super.startUp();
		navigateToCustomersPage();
		
	}
	
	@Test
	public void cleanUpCompanies() throws InterruptedException{
		customersPage = customersPage.goToAllAcountsTab();
		while(true){
			customersPage.deleteCompany("Qa");
//			Thread.sleep(300);
		}
	}
}
