package com.cbsi.test.PropertiesPageTest;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.tests.Foundation.AllBaseTest;
import com.cbsi.tests.PageObjects.AddCatalogPage;
import com.cbsi.tests.PageObjects.CatalogsPage;

public class RegressionTest extends AllBaseTest{

	public RegressionTest(String URL, String browser) {
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
