package com.cbsi.col.pageobject.customers;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.cbsi.col.pageobject.documents.DocumentsBasePage;
import com.cbsi.col.pageobject.documents.DocumentsPage;
import com.cbsi.col.pageobject.documents.DocumentsPage.DocumentTabs;
import com.cbsi.col.pageobject.documents.ProposalPage;
import com.cbsi.col.test.foundation.ColBaseTest;
import com.cbsi.col.test.foundation.DocumentsBasePageTest;

public class CurrentAccountTabTest extends DocumentsBasePageTest{

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
		DocumentsPage documentsPage = currentAccount.getDocumentsPage().switchToTab(DocumentTabs.QUOTES);;
		if(!documentsPage.hasQuote(1)){
			homePage = currentAccount.exitDocumentsPage().goToHomePage();
			navigateToCustomersPage();
			createQuote();
			documentsPage = documentsPage.goToAccountsPage().goToCurrentAccountTab().getDocumentsPage();
		}
		documentsPage = documentsPage.switchToTab(DocumentTabs.QUOTES).deleteDocumentByCompanyName("Qa");
	}
	
	@Test
	public void deleteProposalFromAccountview(){
		currentAccount = ((ProposalPage) currentAccount.clickCreateProposal().setContact()).clickSave().goToAccountsPage().goToRecentAccountsTab().clickViewCustomer(companyNameCommon);
		DocumentsPage documentsPage = currentAccount.getDocumentsPage().switchToTab(DocumentTabs.PROPOSALS);
			
		documentsPage.deleteDocumentByCompanyName("Qa");
	}
	
	@Test
	public void purchaseOrderLinkRedirectsPage(){

	}

}
