package com.cbsi.col.pageobject.documents;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.cbsi.col.pageobject.documents.DocumentsPage.DocumentTabs;
import com.cbsi.col.pageobject.documents.RMAPage.Reasons;
import com.cbsi.col.pageobject.documents.SalesOrderPage.Doc;
import com.cbsi.col.pageobject.purchaseorders.PurchaseOrderPage;
import com.cbsi.col.pageobject.purchaseorders.PurchaseOrdersTab;
import com.cbsi.col.pageobject.purchaseorders.PurchaseOrderPage.PoType;
import com.cbsi.col.test.foundation.DocumentsBasePageTest;

public class RMAPageTest extends DocumentsBasePageTest{

	public RMAPageTest(String url, String browser) {
		super(url, browser);
		// TODO Auto-generated constructor stub
	}
	
	int rmaNumber;
	
	@Test
	public void createRmaFromSalesOrder(){
		
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
		convertToSalesOrder();
		
		SalesOrderPage salesOrderPage = documentPage.goToOrder(orderNumber);
		PurchaseOrdersTab purchaseOrderTab = ((SalesOrderPage) salesOrderPage.selectProductFromTable(1)).selectCreateDoc(Doc.CreatePO);
		PurchaseOrderPage purchaseOrderPage = purchaseOrderTab.clickViewPoNumberLinkedToSo(orderNumber);
		purchaseOrderPage = purchaseOrderPage.setPoType(PoType.Manual).clickSave().clickConvertToSubmittedPo();
		RMAPage rmaPage = purchaseOrderPage.clickCreateRma();
		
		rmaNumber = rmaPage.getDocNumber();
		
		rmaPage.selectReasonForReturn(Reasons.Double_Shipped);
		rmaPage.clickSave();
		
		documentPage = rmaPage.goToDocumentsPage().switchToTab(DocumentTabs.RMAS);
		assertTrue(documentPage.hasRma(rmaNumber));
		
	}
	
	

}
