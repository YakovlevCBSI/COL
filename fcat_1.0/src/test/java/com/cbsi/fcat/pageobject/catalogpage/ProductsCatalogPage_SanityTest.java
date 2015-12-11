package com.cbsi.fcat.pageobject.catalogpage;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.cbsi.fcat.pageobject.catatlogpage.ProductsCatalogPage;
import com.cbsi.fcat.pageobject.foundation.AllBaseTest;

public class ProductsCatalogPage_SanityTest extends AllBaseTest{

	public ProductsCatalogPage_SanityTest(String URL, String browser) {
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
	
	@Test
	public void verifyColumnsAreDisplayed(){
		ProductsCatalogPage productsCatalogPage = navigateToProductsCatalogPage();
		assertTrue(productsCatalogPage.productIdColumn.isDisplayed()
				&& productsCatalogPage.manufacturerColumn.isDisplayed()
				&& productsCatalogPage.partNumberColumn.isDisplayed()
				&& productsCatalogPage.upcEanColumn.isDisplayed()
				&& productsCatalogPage.mappedColumn.isDisplayed()
				);
	}
}
