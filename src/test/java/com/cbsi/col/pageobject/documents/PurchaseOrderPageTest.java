package com.cbsi.col.pageobject.documents;

import org.junit.Test;

import com.cbsi.col.pageobject.documents.SalesOrderPage.Doc;
import com.cbsi.col.test.foundation.DocumentsBasePageTest;

public class PurchaseOrderPageTest extends DocumentsBasePageTest{

	public PurchaseOrderPageTest(String url, String browser) {
		super(url, browser);
		// TODO Auto-generated constructor stub
	}
	
	@Test
	public void createPurchaseOrder(){
		int docNumber;
		convertToSalesOrder();
		
		SalesOrderPage salesOrderPage = documentPage.goToOrder(orderNumber);
		PurchaseOrderPage purchaseOrderPage = ((SalesOrderPage) salesOrderPage.selectProductFromTable(1)).selectCreateDoc(Doc.CreatePO);
	}
	
	@Test
	public void createRMAFromPurchaseOrder(){
		int docNumber;
		convertToSalesOrder();
		
		SalesOrderPage salesOrderPage = documentPage.goToOrder(orderNumber);
	}
}
