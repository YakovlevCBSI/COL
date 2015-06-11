package com.cbsi.test.UploadPopupPage;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.tests.Foundation.AllBaseTest;
import com.cbsi.tests.PageObjects.CatalogsPage;
import com.cbsi.tests.PageObjects.ProductsCatalogPage;
import com.cbsi.tests.PageObjects.UploadPopupPage;

public class RegressionTest extends AllBaseTest{

	public RegressionTest(String URL, String browser) {
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
		boolean displayed = driver.findElement(By.cssSelector("div.overlay-header-close-button div")).isDisplayed();

		assertTrue(uploadPopupPage.isCrossDisplayed());
		assertTrue(	uploadPopupPage.isTitleDisplayed());
		
	}
	
	@Test
	public void isTopbaDisplayedFromProductsPage(){
		catalogsPage.setMyCatalog();
		ProductsCatalogPage productsCatalogsPage = catalogsPage.goToProductsCatalogPage();
		UploadPopupPage uploadPopupPage = productsCatalogsPage.clickUploadFile();
		
		assertTrue(uploadPopupPage.isCrossDisplayed());
		assertTrue(	uploadPopupPage.isTitleDisplayed());
	}

	
}
