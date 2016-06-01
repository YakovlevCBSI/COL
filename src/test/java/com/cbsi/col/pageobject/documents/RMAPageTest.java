package com.cbsi.col.pageobject.documents;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.Point;

import com.cbsi.col.pageobject.documents.DocumentsBasePage.Doc;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.DocStatus;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.DocumentState;
import com.cbsi.col.pageobject.documents.DocumentsPage.DocumentTabs;
import com.cbsi.col.pageobject.documents.RMAPage.Reasons;
import com.cbsi.col.pageobject.purchaseorders.PurchaseOrderPage;
import com.cbsi.col.pageobject.purchaseorders.PurchaseOrdersTab;
import com.cbsi.col.pageobject.purchaseorders.PurchaseOrderPage.PoType;
import com.cbsi.col.pageobject.suppliers.SuppliersPage.SupplierTabs;
import com.cbsi.col.test.foundation.DocumentsBasePageTest;
import com.cbsi.col.test.util.LoginProperty;

public class RMAPageTest extends DocumentsBasePageTest{

	public RMAPageTest(String url, String browser) {
		super(url, browser);
		// TODO Auto-generated constructor stub
	}
	
	long rmaNumber;
	
	@Test
	public void createRmaFromSalesOrderThenFinalize(){
		
		convertToSalesOrder();
		SalesOrderPage salesOrderPage = documentPage.goToOrder(orderNumber);
		RMAPage rmaPage = (RMAPage) ((SalesOrderPage) (salesOrderPage.selectProductFromTable(1))).selectCreateDoc(Doc.CreateRMA);
		
		rmaNumber = rmaPage.getDocNumber();
		
		rmaPage.selectReasonForReturn(Reasons.Dead_On_Arrival);
		rmaPage.clickSave();
		
		documentPage = rmaPage.goToDocumentsPage().switchToTab(DocumentTabs.RMAS);
		assertTrue(documentPage.hasRma(rmaNumber));	
		
		rmaPage = documentPage.goToRma(rmaNumber);
		rmaPage = rmaPage.clickSubmit();
		
		assertEquals(DocumentState.Pending.toString(), rmaPage.getDocumentState());
		
		rmaPage = rmaPage.clickAuthorize();	
		assertEquals(DocumentState.Authorized.toString(), rmaPage.getDocumentState());
		
		rmaPage = rmaPage.clickFinalize();		
		assertEquals(DocumentState.Approved.toString(), rmaPage.getDocumentState());
		
		rmaPage.clickSend();
	}
	

	@Test
	public void createRmaFromPurchaseOrder(){
		convertToSalesOrder();
		SalesOrderPage salesOrderPage = documentPage.goToOrder(orderNumber);
		PurchaseOrdersTab purchaseOrderTab = (PurchaseOrdersTab) ((SalesOrderPage) salesOrderPage.selectProductFromTable(1)).selectCreateDoc(Doc.CreatePO);
		PurchaseOrderPage purchaseOrderPage = purchaseOrderTab.clickViewPoNumberLinkedToSo(orderNumber);
		purchaseOrderPage = purchaseOrderPage.setPoType(PoType.Manual).clickSave().clickConvertToSubmittedPo();
		RMAPage rmaPage = purchaseOrderPage.clickCreateRma();
		
		rmaNumber = rmaPage.getDocNumber();
		
		rmaPage.selectReasonForReturn(Reasons.Double_Shipped);
		rmaPage.clickSave();
		System.out.println("RAM Number: " + rmaNumber);
		
		
//		Something is up with switchign tab.
//		documentPage = rmaPage.goToDocumentsPage().switchToTab(DocumentTabs.RMAS);
//		rmaPage.goToSuppliersPage().switchToTab(SupplierTabs.View_POs).switchToTab(SupplierTabs.SupplierRMAs);
//		documentPage = documentPage.setFilterByModifiedBy(getUserName());
//		assertTrue(documentPage.hasRma(rmaNumber));R
		
	}
	
	@Test//CCSCOL-6626
	public void productTableIsInCenter(){
		RMAPage rmaPage = goToFirstDocument(DocumentTabs.RMAS, DocStatus.Approved, true, RMAPage.class);
		Point point = rmaPage.getProductTableLocation();	
		assertTrue(point.getX() + "", point.getX() < 500);
	}
	
	@Test
	public void salesOrderLinkExists(){
		
	}
}
