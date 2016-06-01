package com.cbsi.col.pageobject.suppliers;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cbsi.col.pageobject.suppliers.SuppliersPage.SupplierTabs;
import com.cbsi.col.test.foundation.ColBaseTest;
import com.cbsi.col.test.util.TableUtil;

public class SuppliersPageTest extends ColBaseTest{

	public SuppliersPageTest(String url, String browser) {
		super(url, browser);
		// TODO Auto-generated constructor stub
	}
	
	public SuppliersPage suppliersPage;
	public String companyName = System.currentTimeMillis()+"";
	
	@Before
	public void startUp(){
		super.startUp();
		suppliersPage = homePage.goToSuppliersPage().switchToTab(SupplierTabs.Supplier_List);
	}
	
	@After
	public void cleanUp(){
		takeScreenshot();
		super.cleanUp();
		startUp();
		suppliersPage.deleteSupplierByName(companyName);
	}
	@Test
	public void CreateNewSupplier(){
		CreateSupplierPage createSupplierPage = suppliersPage.clickCreateNewSupplier();
		suppliersPage = createSupplierPage.setCompany(companyName).clickFinish().switchToTab(SupplierTabs.Supplier_List);

		assertTrue(suppliersPage.hasSupplier(companyName));
	}
}
