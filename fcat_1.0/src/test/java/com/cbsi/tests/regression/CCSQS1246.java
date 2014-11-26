package com.cbsi.tests.regression;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.tests.Foundation.BaseTest;
import com.cbsi.tests.PageObjects.CatalogsPage;
import com.cbsi.tests.PageObjects.ProductsCatalogPage;

public class CCSQS1246 extends BaseTest{
	public CCSQS1246(String URL, String browser){
		super(URL, browser);
	}
	
	@Test
	public void mappingPageBroken(){
		CatalogsPage catalogsPage = PageFactory.initElements(driver, CatalogsPage.class);
		ProductsCatalogPage productsCatalogPage = catalogsPage.goToCatalogWithSomeNumberOfProducts(30);
		productsCatalogPage.clickGoRight();
		//wait until page number changes to veirfy.
		
		//to use image or vf using html element structure.
		assertTrue(productsCatalogPage.isTableCorrectlyRendered());
	}
}
