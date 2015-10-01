package com.cbsi.col.pageobject.documents;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.customers.CurrentAccountTab;
import com.cbsi.col.pageobject.customers.RecentAccountsTab;
import com.cbsi.col.pageobject.customers.AccountsPage.AccountType;
import com.cbsi.col.pageobject.documents.DocumentsPage;
import com.cbsi.col.pageobject.documents.OrderOptionsPage.Payment;
import com.cbsi.col.pageobject.documents.ProposalPage;
import com.cbsi.col.pageobject.documents.DocumentsPage.DocumentTabs;
import com.cbsi.col.pageobject.home.ColBasePage.Time;
import com.cbsi.col.test.foundation.ColBaseTest;

public class ProposalPageTest extends ColBaseTest{
	public RecentAccountsTab recentCustomersPage;
	public DocumentsPage documentPage;
	
	public ProposalPageTest(String url, String browser) {
		super(url, browser);
		// TODO Auto-generated constructor stub
	}
	
	@Before
	public void startUp(){
		super.startUp();
		navigateToCustomersPage();
	}
	
	@Test
	public void createProposalFull(){
		int docNumber;
		CurrentAccountTab currentAccountPage= customersPage.goToRecentAccountsTab().clickViewCustomer("Qa_");
		ProposalPage proposalPage = currentAccountPage.clickCreateProposal();
		docNumber = proposalPage.getQuoteNumber();
		
		documentPage = proposalPage.clickSave().goToHomePage().goToDocumentsPage().switchToTab(DocumentTabs.PROPOSALS);
		assertTrue(documentPage.hasProposal(docNumber));		
	}
	
	@Test
	public void createProposalQuick(){
		int docNumber;
		CurrentAccountTab currentAccountPage= customersPage.goToRecentAccountsTab().clickViewCustomer("Qa_");
		ProposalPage proposalPage = currentAccountPage.clickCreateProposal(false);
		docNumber = proposalPage.getQuoteNumber();
		
		documentPage = proposalPage.clickSave().goToHomePage().goToDocumentsPage().switchToTab(DocumentTabs.PROPOSALS);
		assertTrue(documentPage.hasProposal(docNumber));		
	}
	
	@Test
	public void eSignInProposal(){
		CurrentAccountTab currentAccountPage= customersPage.goToRecentAccountsTab().clickViewCustomer("Qa_");
		ProposalPage proposalPage = currentAccountPage.clickCreateProposal();
		
		AddressPage addressPage  = proposalPage.clickPrePareForESign();
		addressPage.setFirstName("qa");
		addressPage.setLastName("qa");
		addressPage.clickCopyToShipping();
		
		OrderOptionsPage orderOptionPage  = addressPage.clickSave();
		ProposalPage proposlPageFinal = (ProposalPage) orderOptionPage.setPoNumberAndPaymentMethod(123, Payment.MoneyOrder).clickSave(ProposalPage.class);
	}
	
	@Test
	public void companyLinkRedirectToCurrentAccount(){
		ProposalPage proposalPage = customersPage.goToDocumentsPage().switchToTab(DocumentTabs.PROPOSALS).goToProposal(1);
		String expectedName = proposalPage.getCompanyName();
		CurrentAccountTab currentAccountTab = proposalPage.clickCompanyLink();
		assertTrue(currentAccountTab.getCompany().contains(expectedName));
	}

}
