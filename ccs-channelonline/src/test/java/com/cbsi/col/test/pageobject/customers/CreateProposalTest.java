package com.cbsi.col.test.pageobject.customers;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cbsi.col.pageobject.customers.CurrentAccountTab;
import com.cbsi.col.pageobject.customers.RecentAccountsTab;
import com.cbsi.col.pageobject.documents.DocumentsPage;
import com.cbsi.col.pageobject.documents.ProposalPage;
import com.cbsi.col.pageobject.documents.DocumentsPage.DocumentTabs;
import com.cbsi.col.pageobject.documents.DocumentsPage.Time;
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
	
	@After
	public void cleanUp(){
		super.cleanUp();
		super.startUp();
		RecentAccountsTab recentCusotmersPage = homePage.goToAccountsPage().goToRecentAccountsTab();
		recentCusotmersPage.deleteCompany(companyName);		
	}
	
	//currently error creating a proposal.
	@Test
	public void createProposalFull(){
		int docNumber;
		CurrentAccountTab currentAccountPage= recentCustomersPage.clickViewCustomer(companyName);
		ProposalPage proposalPage = currentAccountPage.clickCreateProposal();
		docNumber = proposalPage.getQuoteNumber();
		
		documentPage = proposalPage.clickSave().goToHomePage().goToDocumentsPage().switchToTab(DocumentTabs.PROPOSALS).filterByDate(Time.TODAY);
		assertTrue(documentPage.hasProposal(docNumber));		
	}
	
	@Test
	public void createProposalQuick(){
		int docNumber;
		CurrentAccountTab currentAccountPage= recentCustomersPage.clickViewCustomer(companyName);
		ProposalPage proposalPage = currentAccountPage.clickCreateProposal(false);
		docNumber = proposalPage.getQuoteNumber();
		
		documentPage = proposalPage.clickSave().goToHomePage().goToDocumentsPage().switchToTab(DocumentTabs.PROPOSALS).filterByDate(Time.TODAY);
		assertTrue(documentPage.hasProposal(docNumber));		
	}

}
