package com.cbsi.col.test.pageobject.customers;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.cbsi.col.pageobject.ProductsPage;
import com.cbsi.col.pageobject.ProductsPage.Action;
import com.cbsi.col.pageobject.customers.CurrentAccountTab;
import com.cbsi.col.pageobject.customers.RecentAccountsTab;
import com.cbsi.col.pageobject.documents.DocumentsPage;
import com.cbsi.col.pageobject.documents.SalesOrderPage;
import com.cbsi.col.pageobject.documents.QuotePage;
import com.cbsi.col.test.foundation.ColBaseTest;

public class ConvertOrderTest extends ColBaseTest{
	public RecentAccountsTab recentCustomersPage;
	public DocumentsPage documentPage;
	
	public int quoteNumber;
	
	public ConvertOrderTest(String url, String browser) {
		super(url, browser);
		// TODO Auto-generated constructor stub
	}
	
	@Before
	public void startUp(){
		super.startUp();
		navigateToCustomersPage();
		recentCustomersPage = createAccount();
		createQuote();
	}
	
	@Test
	public void convertToOrder(){
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
