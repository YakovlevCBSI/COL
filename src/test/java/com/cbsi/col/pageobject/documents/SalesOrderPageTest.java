package com.cbsi.col.pageobject.documents;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.customers.AccountsPage.AccountType;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.DocumentState;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.EditProductPage;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.LineActions;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.PriceCalculator;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.SendPage;
import com.cbsi.col.pageobject.documents.DocumentsPage.DocumentTabs;
import com.cbsi.col.pageobject.documents.DocumentsPage.Status;
import com.cbsi.col.test.foundation.DocumentsBasePageTest;

public class SalesOrderPageTest extends DocumentsBasePageTest{

	public SalesOrderPageTest(String url, String browser) {
		super(url, browser);
		// TODO Auto-generated constructor stub
	}

	@Test
	public void convertToSalesOrder(){
		super.convertToSalesOrder();
		
		SalesOrderPage orderPage = documentPage.goToOrder(orderNumber);
		assertEquals(DocumentState.Submitted.toString(), orderPage.getDocumentState());

		
		orderPage = orderPage.clickCompleteThisOrder();
		
		assertEquals(DocumentState.Complete.toString(), orderPage.getDocumentState());
	}
	
	
	@Test
	public void createBundleInSalesOrder() throws InterruptedException{
		super.convertToSalesOrder();

		SalesOrderPage orderPage = documentPage.goToOrder(orderNumber);
		orderPage.selectProductFromTable(1);
		orderPage = (SalesOrderPage) orderPage.selectFromLineActions(LineActions.Convert_to_Bundle);
		orderPage.setBundleHeader("test1");
		orderPage.setBundleDesc("test description");
		orderPage = (SalesOrderPage) orderPage.clickSaveLineItem();
		
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
		super.convertToSalesOrder();
		
		SalesOrderPage orderPage= documentPage.goToOrder(orderNumber);
		QuotePage quotePage = orderPage.clickCopyToNewQuote();
		long newQuoteNumber = quotePage.getQuoteNumber();
		
		quotePage.clickSave();
		
		DocumentsPage documentPage = quotePage.goToDocumentsPage().switchToTab(DocumentTabs.QUOTES);
		assertTrue(documentPage.hasQuote(newQuoteNumber));
	}
	
	@Test//#5405
	public void BillToShipToShowsInProspectSalesOrder(){
		convertToSalesOrder(true, AccountType.PROSPECT);
		SalesOrderPage salesOrderPage = documentPage.goToOrder(orderNumber);

		System.out.println("sales: " + salesOrderPage.getBillTo());
		System.out.println("bills: " + salesOrderPage.getShipTo());

		assertTrue(salesOrderPage.getBillTo().contains(companyNameCommon));
		assertTrue(salesOrderPage.getShipTo().contains(companyNameCommon));
	}
	
	@Test
	public void addSubTotalAndBundleSubTotal(){
		super.convertToSalesOrder();		
		
		SalesOrderPage orderPage= documentPage.goToOrder(orderNumber);
		PriceCalculator priceCalculator = addSubtotalBundleWorkFlow(orderPage).getPriceCalculator();
		assertTrue(3200.00 == priceCalculator.getSubtotal() || 2509.50 == priceCalculator.getSubtotal());

	}
	
	@Test
	public void salesOrderNoteShowsInPreview(){
		super.convertToSalesOrder();
	
		SalesOrderPage orderPage= documentPage.goToOrder(orderNumber);
		orderPage.selectProductFromTable(1);
		EditProductPage editProductPage = orderPage.editProductFromTable(1);
		editProductPage.addItemNote("qa_ball_game").clickSaveThenExit();
		
		orderPage = PageFactory.initElements(driver, SalesOrderPage.class);
		
		assertEquals("qa_ball_game", orderPage.getItemNoteFromTable(1));
		
		SendPage sendPage = orderPage.clickSend();
		assertEquals("qa_ball_game", sendPage.getLineItemNote());	
	}
	
	@Test
	public void createDocDropdownIsNotDisplayedWhenOrderIsPending(){
		
	}
	
	@Test
	public void showSerialShippingIsDisplayed(){
		documentPage = customersPage.goToDocumentsPage().switchToTab(DocumentTabs.QUOTES).filterByStatus(Status.QUOTES_OPEN);
		
		super.convertToSalesOrderOnly();
		
		SalesOrderPage orderPage= documentPage.goToOrder(orderNumber);
		orderPage = (SalesOrderPage) orderPage.clickShowSerialShipping();
		
		assertTrue(orderPage.isShipTrackTableDisplayed());
	}
	
	@Test
	public void lockedOrderIsUneditable(){
		documentPage = customersPage.goToDocumentsPage().switchToTab(DocumentTabs.ORDERS).filterByStatus(Status.ORDERS_SUBMITTED);
		
		Long submittedOrder = Long.parseLong(documentPage.getTableAsMaps().get(0).get("doc#"));

		SalesOrderPage orderPage = documentPage.goToOrder(submittedOrder);
		orderPage = orderPage.clickCompleteThisOrder();
		
		assertEquals(DocumentState.Complete.toString(), orderPage.getDocumentState());
		
		assertFalse(orderPage.isReorderLinesDisplayed());
		assertFalse(orderPage.isAddImportUpdateDropdownDisplayed());
		assertFalse(orderPage.isLiveCost());
		
		assertFalse(orderPage.isShipToEnabled());
		assertFalse(orderPage.isBillToEnabled());
	}
}
