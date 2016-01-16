package com.cbsi.col.pageobject.customers;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.cbsi.col.test.foundation.ColBaseTest;

public class AccountsPageTest extends ColBaseTest{

	public AccountsPageTest(String url, String browser) {
		super(url, browser);
		// TODO Auto-generated constructor stub
	}

	@Test
	public void currentUserIsSelectedAfterSwitchingTab(){
		AllAccountsTab allAccountsTab = homePage.goToAccountsPage().goToAllAcountsTab();
		int found = allAccountsTab.getFound();
		 		
		allAccountsTab = allAccountsTab.goToRecentAccountsTab().goToAllAcountsTab();
		assertTrue(found == allAccountsTab.getFound());
	}
}
