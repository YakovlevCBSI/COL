package com.cbsi.col.pageobject.customers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;

import com.cbsi.col.pageobject.documents.DocumentsBasePage;
import com.cbsi.col.pageobject.documents.DocumentsPage;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.DocStatus;
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
		documentsPage = documentsPage.switchToTab(DocumentTabs.QUOTES);
		
		Long quoteNumber = null;
		
		List<LinkedHashMap<String, String>> quoteList = documentsPage.getTableAsMaps();
		for(LinkedHashMap<String, String> m: quoteList){
			if (m.get("status").contains(DocStatus.Open.toString())){
				quoteNumber = Long.parseLong(m.get("doc#"));
				break;
			}
		}
		
		logger.info("deleting " + quoteNumber);
		documentsPage.deleteQuoteByDocNumber(quoteNumber);
	}
	
	@Test
	public void deleteProposalFromAccountview(){
		currentAccount = ((ProposalPage) currentAccount.clickCreateProposal().setContact()).clickSave().goToAccountsPage().goToRecentAccountsTab().clickViewCustomer(companyNameCommon);
		DocumentsPage documentsPage = currentAccount.getDocumentsPage().switchToTab(DocumentTabs.PROPOSALS);
			
		documentsPage.deleteDocumentByCompanyName("Qa");
	}
	
	@Test
	public void contactDropdownSorted(){
		//check if contact size is more than two.
		while(currentAccount.getContacts().size() <= 2){
			CreateAccountPage contactPage = currentAccount.clickAddAContact();
			contactPage.setContactInfo_FirstName("qa");
			contactPage.setContactInfo_LastName(contactPage.getRamdomLetter() + "abc");
			contactPage.clickSave();
			currentAccount = contactPage.goToAccountsPage().goToRecentAccountsTab().clickViewCustomer(companyNameCommon);
		}
		
		DocumentsPage documentsPage = currentAccount.getDocumentsPage();
		List<String> contactList = documentsPage.clickContactList().getContactList();
		Collections.sort(contactList);
		
		List<String> contactListActual = documentsPage.getContactList();
		
		assertEquals(contactList, contactListActual);		
	}
	
	@Test
	public void ModifiedyByDropdownSorted(){
		 DocumentsPage documentsPage = currentAccount.getDocumentsPage();
		 List<String> modifiedBys = documentsPage.clickModifiedBy().getModifiedByList();
		 Collections.sort(modifiedBys);
		
		 List<String> modifiedBysActual = documentsPage.getModifiedByList();
		
		assertEquals(modifiedBys, modifiedBysActual);
	}
	
//	@Test
//	public void purchaseOrderLinkRedirectsPage(){
//
//	}

}
