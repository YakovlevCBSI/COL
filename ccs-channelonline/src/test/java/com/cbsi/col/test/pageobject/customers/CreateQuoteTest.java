package com.cbsi.col.test.pageobject.customers;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cbsi.col.pageobject.ProductsPage;
import com.cbsi.col.pageobject.ProductsPage.Action;
import com.cbsi.col.pageobject.documents.DocumentsPage;
import com.cbsi.col.pageobject.documents.QuotePage;
import com.cbsi.col.test.foundation.ColBaseTest;

public class CreateQuoteTest extends ColBaseTest{

	public RecentAccountsTab recentCustomersPage;
	public DocumentsPage documentPage;
	
	private int quoteNumber;
	
	public CreateQuoteTest(String url, String browser) {
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
		recentCustomersPage = documentPage.goToHomePage().goToAccountsPage().goToRecentCustomersTab();
		recentCustomersPage.deleteCompany(companyName);
		super.cleanUp();
	}
	
	@Test
	public void test1_createQuote(){
		recentCustomersPage = recentCustomersPage.goToAccountsPage().goToRecentCustomersTab();
		CurrentAccountTab currentAccountPage=  recentCustomersPage.clickViewCustomer("QaCustomer_ ");
		
		QuotePage quotePage = currentAccountPage.ClickCreateQuote();
		quoteNumber = quotePage.getQuoteNumber();
		
		ProductsPage productPage = quotePage.searchProduct("Lenovo");
		productPage.checkCompareBoxes(1,2,3);
		
		QuotePage quotePageNew = productPage.selectAction(Action.AddToQuote);
		quotePageNew.clickSave();
		
		documentPage = quotePageNew.goToDocumentsPage();		
		
		assertTrue("didnt find quote #" + quoteNumber, documentPage.hasQuote(quoteNumber));
	}
	
	

}
