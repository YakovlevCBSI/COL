package com.cbsi.test.ProductsCatalogPageTest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.tests.Foundation.AllBaseTest;
import com.cbsi.tests.PageObjects.CatalogsPage;
import com.cbsi.tests.PageObjects.ProductsCatalogPage;

public class RegressionTest extends AllBaseTest{

	public RegressionTest(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}
	
	public ProductsCatalogPage  productsCatalogPage = null;

	@Test
	public void TableRenderIssue_CCSQ1246(){
		CatalogsPage catalogsPage = PageFactory.initElements(driver, CatalogsPage.class);
		ProductsCatalogPage productsCatalogPage = catalogsPage.goToCatalogWithSomeNumberOfProducts(30);
		productsCatalogPage.clickGoRight();
		//wait until page number changes to veirfy.
		
		//to use image or vf using html element structure.
		assertTrue(productsCatalogPage.isTableCorrectlyRendered());
	}
	
	@Test
	public void ActionIconsDisplayCorrectly(){
		productsCatalogPage = navigateToProductsCatalogPage();
		assertTrue (productsCatalogPage.actionIconsRenderCorrectly());
		
	}
		
	@Test
	public void IconsDisplayCorrectlyAfterMapping_CCSQS1258(){
		productsCatalogPage = navigateToProductsCatalogPage().clickGoRight();
		productsCatalogPage.mapUnmappedItem("Sony", 1);
		assertTrue(productsCatalogPage.actionIconsRenderCorrectly());
	}
	
	//Test this to see if it really collects error.(wonder if its only js error or Any console error...?
	@Test
	public void Error404OnMappingAnExisitngItem_CCSQS1253(){
		productsCatalogPage = navigateToProductsCatalogPage();
		productsCatalogPage.mapUnmappedItem("Sony", 1);
		assertTrue(hasNoError());	
	}
	
	@Test
	public void Error400ErrorOnDeleteProduct(){
		
	}
	

	@Test
	public void Error500OnWhenNoChangesAreMadeOnMappingDialog_CCSQS1256(){
		ProductsCatalogPage prodcutsCatalogPage = navigateToProductsCatalogPage();
		prodcutsCatalogPage.exitWithoutSaveOnMapppingDialog();
		assertTrue(hasNoError());
	}
	
	@Test
	public void IconsToolTipVisible(){
		
	}
	

	@Test
	public void ErrorWhenYouDeleteManualMapItem(){
		ProductsCatalogPage productsCatalogPage = navigateToProductsCatalogPage();
		productsCatalogPage.mapUnmappedItem("Sony", 1);
		WebElement tempElement = productsCatalogPage.getRowThatWasMapped(); // get text id here then use theid to clic.
		
		CatalogsPage catalogsPage = productsCatalogPage.clickReturnToList();
		navigateToProductsCatalogPage();
		
		tempElement.findElement(By.xpath("../a[@class='trash']")).click();
		
		assertTrue(hasNoError());
		
	}
	


	public ProductsCatalogPage navigateToProductsCatalogPage(){
		CatalogsPage catalogsPage = PageFactory.initElements(driver, CatalogsPage.class);
		ProductsCatalogPage productsCatalogPage = catalogsPage.goToCatalogWithSomeNumberOfProducts(30);
		return productsCatalogPage;
	}
	
}
