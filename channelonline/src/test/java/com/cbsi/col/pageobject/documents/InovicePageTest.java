package com.cbsi.col.pageobject.documents;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import com.cbsi.col.pageobject.documents.DocumentsBasePage.Doc;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.DocStatus;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.LineActions;
import com.cbsi.col.pageobject.documents.DocumentsPage.DocumentTabs;
import com.cbsi.col.pageobject.purchaseorders.PurchaseOrdersTab;
import com.cbsi.col.test.foundation.DocumentsBasePageTest;

public class InovicePageTest extends DocumentsBasePageTest{

	public InovicePageTest(String url, String browser) {
		super(url, browser);
		// TODO Auto-generated constructor stub
	}

	@Test
	public void convertToInvoice(){
		convertToSalesOrder();

		SalesOrderPage salesOrderPage = documentPage.goToOrder(orderNumber);
		InvoicePage invoicePage = salesOrderPage.clickConvertToInvoice();
		invoiceNumber = invoicePage.getInvoiceNumber();
		invoicePage.clickSave();
		documentPage = invoicePage.goToDocumentsPage().switchToTab(DocumentTabs.INVOICES);
		
		assertTrue(documentPage.hasInvoice(invoiceNumber));
	}
	
	@Test//#5389
	public void convertSalesOrderWithBundleToInvoice(){
		convertToSalesOrder();
		
		SalesOrderPage orderPage = documentPage.goToOrder(orderNumber);
		orderPage.selectProductFromTable(1);
		orderPage = (SalesOrderPage) orderPage.selectFromLineActions(LineActions.Convert_to_Bundle);
		orderPage.setBundleHeader("test1");
		orderPage.setBundleDesc("test description");
		orderPage = (SalesOrderPage) orderPage.clickSaveLineItem();
		orderPage.clickSave();
		
		InvoicePage invoicePage = orderPage.clickConvertToInvoice();
	}
	
	@Ignore("legacy")
	@Test
	public void salesOrderLinkExistsAndInvoiceLinkExists(){
		SalesOrderPage salesOrderPage = goToFirstDocument(DocumentTabs.ORDERS, DocStatus.Submitted, SalesOrderPage.class);
		salesOrderPage = (SalesOrderPage) salesOrderPage.clickViewInClassicStyle();
		InvoicePage invoicePage = salesOrderPage.clickConvertToInvoice();
		
		assertTrue(invoicePage.isSalesOrderLinkDisplayed());
		
		salesOrderPage = invoicePage.clickSalesOrderLink();
		assertTrue(salesOrderPage.isInvoiceLinkDisplayed());
	}
	
}
