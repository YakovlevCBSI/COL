package com.cbsi.col.pageobject.documents;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.cbsi.col.pageobject.documents.DocumentsBasePage.Doc;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.DocStatus;
import com.cbsi.col.pageobject.documents.DocumentsPage.DocumentTabs;
import com.cbsi.col.pageobject.purchaseorders.PurchaseOrderPage;
import com.cbsi.col.pageobject.purchaseorders.PurchaseOrdersTab;
import com.cbsi.col.pageobject.purchaseorders.PurchaseOrderPage.PoType;
import com.cbsi.col.pageobject.purchaseorders.PurchaseOrdersTab.PoTabs;
import com.cbsi.col.test.foundation.DocumentsBasePageTest;

public class PurchaseOrderPageTest extends DocumentsBasePageTest{

	public PurchaseOrderPageTest(String url, String browser) {
		super(url, browser);
		// TODO Auto-generated constructor stub
	}
	
	private PurchaseOrdersTab purchaseOrderTab;
	@Test
	public void createPurchaseOrderFromSalesOrder(){
		convertToSalesOrder();
		
		SalesOrderPage salesOrderPage = documentPage.goToOrder(orderNumber);
		purchaseOrderTab = (PurchaseOrdersTab) ((SalesOrderPage) salesOrderPage.selectProductFromTable(1)).selectCreateDoc(Doc.CreatePO);
		assertTrue(purchaseOrderTab.hasPoNumberLinkedToSo(orderNumber));
	}
	
	@Test
	public void createRMAFromPurchaseOrder(){
		convertToSalesOrder();
		
		SalesOrderPage salesOrderPage = documentPage.goToOrder(orderNumber);
		PurchaseOrdersTab purchaseOrderTab = (PurchaseOrdersTab) ((SalesOrderPage) salesOrderPage.selectProductFromTable(1)).selectCreateDoc(Doc.CreatePO);
		PurchaseOrderPage purchaseOrderPage = purchaseOrderTab.clickViewPoNumberLinkedToSo(orderNumber);
		purchaseOrderPage = purchaseOrderPage.setPoType(PoType.Manual).clickSave().clickConvertToSubmittedPo();
		
		assertEquals(purchaseOrderPage.getPoStatus(), DocStatus.Submitted.toString());
	}
	
	@Test
	public void purchaseOrderLinksToSalesOrder(){
		purchaseOrderTab = customersPage.goToPurchaseOrdersPage().switchToTab(PoTabs.View_POs);
		PurchaseOrderPage purchaseOrderPage = purchaseOrderTab.goToFirstDocument();
		purchaseOrderPage.clickSOLink().clickViewSalesOrder();
	}
}
