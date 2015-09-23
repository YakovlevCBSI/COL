package com.cbsi.col.pageobject.customers;

import org.junit.Before;
import org.junit.Test;

import com.cbsi.col.test.foundation.ColBaseTest;

public class CurrentAccountTabTest extends ColBaseTest{

	public CurrentAccountTabTest(String url, String browser) {
		super(url, browser);
		// TODO Auto-generated constructor stub
	}
	
	private CurrentAccountTab currentAccount;
	
	@Before
	public void startUp(){
		super.startUp();
		RecentAccountsTab recentAccountsTab = homePage.goToAccountsPage().goToRecentAccountsTab();
		currentAccount = recentAccountsTab.clickViewCustomer("Qa_");
	}
	
	@Test
	public void deleteQuoteFromAccountView(){
		currentAccount.deleteADocumentFromTable();
	}
	
	@Test
	public void deleteProposalFromAccountview(){
//		currentAccount.
	}
	
	@Test
	public void purchaseOrderLinkRedirectsPage(){
		
	}

}
