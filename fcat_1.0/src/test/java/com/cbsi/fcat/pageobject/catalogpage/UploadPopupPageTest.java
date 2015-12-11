package com.cbsi.fcat.pageobject.catalogpage;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.cbsi.fcat.pageobject.catatlogpage.ProductsCatalogPage;
import com.cbsi.fcat.pageobject.catatlogpage.UploadPopupPage;
import com.cbsi.fcat.pageobject.foundation.AllBaseTest;

public class UploadPopupPageTest extends AllBaseTest{

	public UploadPopupPageTest(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}
	
	private String tempFile;
	
	@Test
	public void CCSQS1248() throws InterruptedException{
		UploadPopupPage uploadPopupPage = UploadFile();
		assertTrue(uploadPopupPage.getProgress().contains("100%"));
	}

	@Test
	public void isTopbaDisplayedFromCatatlogsPage(){
		catalogsPage.setMyCatalog();
		UploadPopupPage uploadPopupPage = catalogsPage.clickUpload();

		assertTrue(uploadPopupPage.isCrossDisplayed());
		assertTrue(uploadPopupPage.isTitleDisplayed());
		
	}
	
	@Test
	public void isTopbaDisplayedFromProductsPage(){
		catalogsPage.setMyCatalog();
		ProductsCatalogPage productsCatalogsPage = catalogsPage.goToProductsCatalogPage();
		UploadPopupPage uploadPopupPage = productsCatalogsPage.clickUploadFile();
		
		assertTrue(uploadPopupPage.isCrossDisplayed());
		assertTrue(uploadPopupPage.isTitleDisplayed());
	}

	
}
