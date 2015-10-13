package com.cbsi.col.pageobject.customers;

import org.junit.Before;
import org.junit.Test;

import com.cbsi.col.pageobject.documents.DocumentsPage;
import com.cbsi.col.pageobject.documents.DocumentsPage.DocumentTabs;
import com.cbsi.col.pageobject.documents.ProposalPage;
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
		if(!recentAccountsTab.hasCompany(companyNameCommon)) { // if account does not exist, create one.
			homePage = recentAccountsTab.goToHomePage();
			navigateToCustomersPage();
			recentAccountsTab = createAccount();
		}
		currentAccount = recentAccountsTab.clickViewCustomer(companyNameCommon);
	}
	
	@Test
	public void deleteQuoteFromAccountView(){
		DocumentsPage documentsPage = currentAccount.getDocumentsPage();
		documentsPage = documentsPage.switchToTab(DocumentTabs.QUOTES).deleteDocumentByCompanyName(companyNameCommon);
	}
	
	@Test
	public void deleteProposalFromAccountview(){
//		currentAccount.
		DocumentsPage documentsPage = currentAccount.getDocumentsPage();
		documentsPage = documentsPage.switchToTab(DocumentTabs.PROPOSALS);
		
		if(!documentsPage.hasProposal(1)){ //if proposal does not exist, create one.
			currentAccount = currentAccount.exitDocumentsPage();
			currentAccount = ((ProposalPage) currentAccount.clickCreateProposal().setContact()).clickSave().goToAccountsPage().goToRecentAccountsTab().clickViewCustomer(companyNameCommon);
			documentsPage = currentAccount.getDocumentsPage().switchToTab(DocumentTabs.PROPOSALS);
		}
				
		documentsPage.deleteDocumentByCompanyName("Qa");
	}
	
	@Test
	public void purchaseOrderLinkRedirectsPage(){
	
	}

}
