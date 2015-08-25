package com.cbsi.col.pageobject.documents;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import com.cbsi.col.pageobject.customers.AccountsPage;
import com.cbsi.col.pageobject.customers.CurrentAccountTab;
import com.cbsi.col.pageobject.customers.RecentAccountsTab;
import com.cbsi.col.pageobject.documents.DocumentsPage;
import com.cbsi.col.pageobject.documents.QuotePage;
import com.cbsi.col.pageobject.documents.SalesOrderPage;
import com.cbsi.col.pageobject.documents.DocumentsPage.DocumentTabs;
import com.cbsi.col.pageobject.home.ProductsPage;
import com.cbsi.col.pageobject.home.ProductsPage.Action;
import com.cbsi.col.test.foundation.ColBaseTest;

public class QuotePageTest extends ColBaseTest{

	public RecentAccountsTab recentCustomersPage;
	public DocumentsPage documentPage;
	
	private int quoteNumber;
	
	public QuotePageTest(String url, String browser) {
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
		navigateToCustomersPage();
		customersPage.goToRecentAccountsTab().deleteCompany(companyName);
	}
	
	@Test
	public void createQuoteTest(){
		createQuote();
	}
	
	@Test
	public void copyToNewQuote(){
		createQuote();
		QuotePage quotePage = documentPage.goToQuote(quoteNumber);
		int oldQuote = quotePage.getQuoteNumber();
		
		QuotePage quotePageNew = quotePage.clickCopyToNewQuote();
		int newQuote = quotePageNew.getQuoteNumber();
		
		assertFalse(oldQuote + " : " + newQuote, oldQuote == newQuote);
		
		
	}
	
	@Test
	public void convertToOrder(){
		createQuote();
		QuotePage quotePage = documentPage.goToQuote(quoteNumber);
		
		SalesOrderPage orderPageAdress = quotePage.clickConvertToOrder();
		orderPageAdress.setFirstName("Quality");
		orderPageAdress.setLastName("Assurance");
		orderPageAdress.setEmail("shefali.ayachit@cbsi.com");
		orderPageAdress.setAddress(address);
		orderPageAdress.setCity(city);
		orderPageAdress.setZip(zip);
		
		SalesOrderPage salesOrderPagePayment = orderPageAdress.clickSave();
		
	}

	public void createQuote(){
		CurrentAccountTab currentAccountPage=  recentCustomersPage.clickViewCustomer(companyName);
		
		QuotePage quotePage = currentAccountPage.clickCreateQuote();
		quoteNumber = quotePage.getQuoteNumber();
		
		ProductsPage productPage = quotePage.searchProduct("Lenovo");
		productPage.checkCompareBoxes(1,2,3);
		
		QuotePage quotePageNew = productPage.selectAction(Action.AddToQuote);
		quotePageNew.clickSave();
		
		documentPage = quotePageNew.goToDocumentsPage();		
		
		assertTrue("didnt find quote #" + quoteNumber, documentPage.hasQuote(quoteNumber));
	}
	
//	@Test
//	public void cleanUpCompanies(){
//		recentCustomersPage = homePage.goToAccountsPage().goToRecentCustomersTab();
//		AccountsPage customersPage1 = null;
//		while(true){
//			customersPage1 = recentCustomersPage.deleteCompany("Qa");
//			recentCustomersPage= customersPage1.goToAccountsPage().goToRecentAccountsTab();
//			
//		}
//	}
	
//	@Test
//	public void cleanUpDocuments(){
//		DocumentsPage docPage  = homePage.goToDocumentsPage();
//		while(true){
//			try{
//			docPage = docPage.deleteQuotesByCompnayName("qacustomer_");
//			}catch(NullPointerException e){
//				driver.findElement(By.linkText("Modified By")).click();
//
//			}
//			
//		}
//	}
}
