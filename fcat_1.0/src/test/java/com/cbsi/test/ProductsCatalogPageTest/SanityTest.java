package com.cbsi.test.ProductsCatalogPageTest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.cbsi.tests.Foundation.AllBaseTest;
import com.cbsi.tests.PageObjects.ProductsCatalogPage;

public class SanityTest extends AllBaseTest{

	public SanityTest(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}
	
	@Test
	public void statsDataAreDisplayed(){
		ProductsCatalogPage productsCatalogPage = navigateToProductsCatalogPage();
		assertTrue(productsCatalogPage.TotalProducts.isDisplayed() 
					&& productsCatalogPage.Mapped.isDisplayed() 
					&& productsCatalogPage.NotMapped.isDisplayed()
					);
	}

	@Test
	public void StatsValuesAreDisplayed(){
		ProductsCatalogPage productsCatalogPage = navigateToProductsCatalogPage();
		assertTrue(productsCatalogPage.MappedValue.isDisplayed()
					&& productsCatalogPage.TotalProductsValue.isDisplayed()
					&& productsCatalogPage.NotMappedValue.isDisplayed()
					);
	}
	
	@Test
	public void FilterBoxesAreDisplayed(){
		ProductsCatalogPage productsCatalogPage = navigateToProductsCatalogPage();
		assertTrue(productsCatalogPage.ProductIDInput.isDisplayed()
					&& productsCatalogPage.ManufacturerNameInput.isDisplayed()
					&& productsCatalogPage.ManufacturerParNumberInput.isDisplayed()
					);
	}
	
	@Test
	public void pageElementsAreDisplayed(){
		ProductsCatalogPage productsCatalogPage = navigateToProductsCatalogPage();
		assertTrue(productsCatalogPage.pageNumber.isDisplayed()
					&& productsCatalogPage.pageSelector.isDisplayed()
					);
	}
	
	@Test
	public void stickyButtonsAreDisplayed(){
		ProductsCatalogPage productsCatalogPage = navigateToProductsCatalogPage();
		assertTrue(productsCatalogPage.DownloadIcon.isDisplayed()
					&& productsCatalogPage.UploadIcon.isDisplayed()
					&& productsCatalogPage.AddProductIcon.isDisplayed()
					&& productsCatalogPage.ShowDetailsIcon.isDisplayed()
					&& productsCatalogPage.EditFileIcon.isDisplayed()

					);
	}
}
