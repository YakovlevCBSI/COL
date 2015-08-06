package com.cbsi.col.test.pageobject.customers;

import org.junit.Before;
import org.junit.Test;

import com.cbsi.col.pageobject.customers.CurrentAccountTab;
import com.cbsi.col.pageobject.customers.RecentAccountsTab;
import com.cbsi.col.pageobject.documents.DocumentsPage;
import com.cbsi.col.test.foundation.ColBaseTest;

public class CreateProposalTest extends ColBaseTest{
	public RecentAccountsTab recentCustomersPage;
	public DocumentsPage documentPage;
	
	public CreateProposalTest(String url, String browser) {
		super(url, browser);
		// TODO Auto-generated constructor stub
	}
	
	@Before
	public void startUp(){
		super.startUp();
		navigateToCustomersPage();
		recentCustomersPage = createAccount();
	}
	
	//currently error creating a proposal.
	@Test
	public void createProposal(){
		CurrentAccountTab currentAccountPage= recentCustomersPage.clickViewCustomer(companyName);
//		currentAccountPage.click
	}

}
