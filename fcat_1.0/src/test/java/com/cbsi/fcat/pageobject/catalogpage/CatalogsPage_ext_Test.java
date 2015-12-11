package com.cbsi.fcat.pageobject.catalogpage;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.fcat.pageobject.catatlogpage.AddCatalogPage;
import com.cbsi.fcat.pageobject.catatlogpage.CatalogsPage;
import com.cbsi.fcat.pageobject.foundation.AllBaseTest;

public class CatalogsPage_ext_Test extends AllBaseTest{

	public CatalogsPage_ext_Test(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}
	/**
	 * Test this tmrwww to pass or fail.
	 */

	@Test
	public void isDefaultCatalogDisplayed(){
		catalogsPage.setMyCatalogToManualCatalog();
		AddCatalogPage addCatalogPage = catalogsPage.clickEdit();
		CatalogsPage defaultCatalogPage = addCatalogPage.clickSetAsDefault().clickSave();
		assertTrue(defaultCatalogPage.setMyCatalogToManualCatalog().isDefaultCatalog());
		
	}
	
	public String getExistingCatalogName(){
		CatalogsPage catalogsPage = PageFactory.initElements(driver, CatalogsPage.class);
		catalogsPage.setMyCatalogToManualCatalog();
		return catalogsPage.myCatalog.getText();  
	}
	


}
