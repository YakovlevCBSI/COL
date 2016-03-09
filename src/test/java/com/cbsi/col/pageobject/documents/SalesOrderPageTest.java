package com.cbsi.col.pageobject.documents;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.customers.AccountsPage.AccountType;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.DocStatus;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.DocumentState;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.EditProductPage;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.LineActions;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.PriceCalculator;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.SendPage;
import com.cbsi.col.pageobject.documents.DocumentsPage.DocumentTabs;
import com.cbsi.col.pageobject.documents.DocumentsPage.Status;
import com.cbsi.col.test.foundation.DocumentsBasePageTest;
import com.cbsi.col.test.util.StringUtil;

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
		
		double taxedSubTotalWithoutBundle = 0;
		double taxedSubTotal = 0;

		double bundleSubTotalActual = 0;
		double bundleSubTotalExepcted = 0;
		
		boolean isInBundle = false;
		
		super.convertToSalesOrder();		
		
		SalesOrderPage orderPage= documentPage.goToOrder(orderNumber);
		PriceCalculator priceCalculator = addSubtotalBundleWorkFlow(orderPage).getPriceCalculator();
		
		List<LinkedHashMap<String, String>> maps = orderPage.getTableAsMaps();

		for(LinkedHashMap<String, String> map: maps){
				
			if(!isInBundle && map.get("description").contains("Bundle Header")) {
				isInBundle = true;
			}
			
			else if(isInBundle && map.get("description").contains("Bundle Subtotal")){
				bundleSubTotalActual = Double.parseDouble(StringUtil.cleanCurrency(map.get("total")));
				isInBundle = false;
			}
			
			else if(isInBundle){
				bundleSubTotalExepcted += Integer.parseInt(map.get("qty")) * Double.parseDouble(map.get("price"));
				System.out.println("is in bundle condition." + bundleSubTotalExepcted);
			}
			
			else if (!map.get("qty").isEmpty() && !isInBundle){
				taxedSubTotalWithoutBundle += Integer.parseInt(map.get("qty")) * Double.parseDouble(map.get("price"));
				System.out.println("last condition: "+ taxedSubTotalWithoutBundle);
			}
		}
		
		taxedSubTotal = taxedSubTotalWithoutBundle + bundleSubTotalActual;
		
				
		System.out.println("calculated taxedsubtotal is " + taxedSubTotal);

		assertTrue(priceCalculator.getSubtotal() + " : " + taxedSubTotal, 
				priceCalculator.getSubtotal()==taxedSubTotal);
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
		SalesOrderPage orderPage = goToFirstDocument(DocumentTabs.ORDERS, DocStatus.Submitted, SalesOrderPage.class);
		orderPage = orderPage.clickCompleteThisOrder();
		
		assertEquals(DocumentState.Complete.toString(), orderPage.getDocumentState());
		
		assertFalse(orderPage.isReorderLinesDisplayed());
		assertFalse(orderPage.isAddImportUpdateDropdownDisplayed());
		assertFalse(orderPage.isLiveCost());
		
		assertFalse(orderPage.isShipToEnabled());
		assertFalse(orderPage.isBillToEnabled());
	}
	
	@Test
	public void discountOptionChangedNontaxableSubtotal(){
		SalesOrderPage orderPage = goToFirstDocument(DocumentTabs.ORDERS, DocStatus.Submitted, SalesOrderPage.class);

		PriceCalculator priceCalculator = orderPage.getPriceCalculator();
		priceCalculator.setDiscount(50, true);
		priceCalculator.setDiscountType(false);
		priceCalculator.setDiscountType(true);
		
		double subTotal = priceCalculator.getNonTaxableSubTotal();
		
		orderPage = orderPage.clickSave();
		
		System.out.println("subTotal amount: " + subTotal);
		
		assertTrue(subTotal !=0);
		assertTrue(subTotal + ":" + orderPage.getPriceCalculator().getNonTaxableSubTotal(), 
				subTotal== orderPage.getPriceCalculator().getNonTaxableSubTotal());
	}
}
