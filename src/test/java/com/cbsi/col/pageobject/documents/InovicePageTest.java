package com.cbsi.col.pageobject.documents;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.cbsi.col.pageobject.documents.DocumentsPage.DocumentTabs;
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
	
}
