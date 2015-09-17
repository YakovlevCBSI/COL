package com.cbsi.col.pageobject.documents;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.cbsi.col.pageobject.customers.AccountsPage.AccountType;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.LineActions;
import com.cbsi.col.pageobject.documents.DocumentsPage.DocumentTabs;
import com.cbsi.col.test.foundation.DocumentsBasePageTest;

public class SalesOrderPageTest extends DocumentsBasePageTest{

	public SalesOrderPageTest(String url, String browser) {
		super(url, browser);
		// TODO Auto-generated constructor stub
	}

	@Test
	public void convertToSalesOrder(){
		super.convertToSalesOrder();
	}
	
	
	@Test
	public void createBundleInSalesOrder() throws InterruptedException{
		convertToSalesOrder();

		SalesOrderPage orderPage = documentPage.goToOrder(orderNumber);
		orderPage.selectProductFromTable(1);
		orderPage = (SalesOrderPage) orderPage.selectFromLineActions(LineActions.Convert_to_Bundle);
		orderPage.setBundleHeader("test1");
		orderPage.setBundleDesc("test description");
		orderPage = (SalesOrderPage) orderPage.clickSaveBundle();
		
		assertEquals("test1", orderPage.getBundleHeader());
		assertEquals("test description", orderPage.getBundleDesc());
	}
	
	@Test
	public void convertQuoteWithBundleToSalesOrder(){
		createQuote();
		QuotePage quotePage = documentPage.goToQuote(quoteNumber);

		quotePage.selectProductFromTable(1);
		quotePage = (QuotePage)quotePage.selectFromLineActions(LineActions.Convert_to_Bundle);
		quotePage.setBundleHeader("test1");
		quotePage.setBundleDesc("test description");
		quotePage.clickSave();
		
		documentPage = quotePage.goToDocumentsPage().switchToTab(DocumentTabs.QUOTES);	
		
		convertToSalesOrderOnly();
	}
	
	@Test//#5406
	public void copySalesOrderToNewQuote(){
		convertToSalesOrder();
		
		SalesOrderPage orderPage= documentPage.goToOrder(orderNumber);
		QuotePage quotePage = orderPage.clickCopyToNewQuote();
		int newQuoteNumber = quotePage.getQuoteNumber();
		
		quotePage.clickSave();
		
		DocumentsPage documentPage = quotePage.goToDocumentsPage().switchToTab(DocumentTabs.QUOTES);
		assertTrue(documentPage.hasQuote(newQuoteNumber));
	}
	
	@Test//#5405
	public void BillToShipToShowsInProspectSalesOrder(){
		convertToSalesOrder(true, AccountType.PROSPECT);
		SalesOrderPage salesOrderPage = documentPage.goToOrder(orderNumber);

		assertTrue(salesOrderPage.getBillTo().contains(address));
		assertTrue(salesOrderPage.getShipTo().contains(address));
	}
}
