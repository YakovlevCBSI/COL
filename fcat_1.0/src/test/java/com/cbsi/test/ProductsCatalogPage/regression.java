package com.cbsi.test.ProductsCatalogPage;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.tests.Foundation.AllBaseTest;
import com.cbsi.tests.PageObjects.CatalogsPage;
import com.cbsi.tests.PageObjects.ProductsCatalogPage;

public class regression extends AllBaseTest{

	public regression(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}
	
	@Test
	public void CCSQS1247(){
		CatalogsPage catalogsPage = PageFactory.initElements(driver, CatalogsPage.class);
		ProductsCatalogPage productsCatalogPage = catalogsPage.goToCatalogWithSomeNumberOfProducts(30);
		productsCatalogPage.clickGoRight();
		//wait until page number changes to veirfy.
		
		//to use image or vf using html element structure.
		assertTrue(productsCatalogPage.isTableCorrectlyRendered());
	}

}
