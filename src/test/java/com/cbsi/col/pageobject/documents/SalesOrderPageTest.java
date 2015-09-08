package com.cbsi.col.pageobject.documents;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.cbsi.col.pageobject.documents.DocumentsBasePage.LineActions;
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
}
