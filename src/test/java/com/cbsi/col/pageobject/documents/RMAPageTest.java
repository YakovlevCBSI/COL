package com.cbsi.col.pageobject.documents;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.cbsi.col.pageobject.documents.DocumentsPage.DocumentTabs;
import com.cbsi.col.pageobject.documents.RMAPage.Reasons;
import com.cbsi.col.pageobject.documents.SalesOrderPage.Doc;

public class RMAPageTest extends DocumentsBasePageTest{

	public RMAPageTest(String url, String browser) {
		super(url, browser);
		// TODO Auto-generated constructor stub
	}
	
	@Test
	public void createRmaFromSalesOrder(){
		int rmaNumber;
		convertToSalesOrder();
		SalesOrderPage salesOrderPage = documentPage.goToOrder(orderNumber);
		RMAPage rmaPage = ((SalesOrderPage) salesOrderPage.selectProductFromTable(1)).selectCreateDoc(Doc.CreateRMA);
		
		rmaNumber = rmaPage.getDocNumber();
		
		rmaPage.selectReasonForReturn(Reasons.Dead_On_Arrival);
		rmaPage.clickSave();
		
		documentPage = rmaPage.goToDocumentsPage().switchToTab(DocumentTabs.RMAS);
		assertTrue(documentPage.hasRma(rmaNumber));	
	}
	

	@Test
	public void createRmaFromPurchaseOrder(){
		
	}
	
	

}
