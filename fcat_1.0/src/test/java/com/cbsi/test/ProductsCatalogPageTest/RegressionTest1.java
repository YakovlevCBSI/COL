package com.cbsi.test.ProductsCatalogPageTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.tests.Foundation.AllBaseTest;
import com.cbsi.tests.PageObjects.AddProductPopup;
import com.cbsi.tests.PageObjects.CatalogsPage;
import com.cbsi.tests.PageObjects.EditProductPopupPage;
import com.cbsi.tests.PageObjects.MapProductsDialog;
import com.cbsi.tests.PageObjects.ProductsCatalogPage;
import com.cbsi.tests.util.ElementConstants;

public class RegressionTest1 extends AllBaseTest{

	@Rule
	public ExpectedException exception = ExpectedException.none();
	public ProductsCatalogPage  productsCatalogPage = null;
	protected static final String mf = "ATI Technologies";
	protected static final String mfPn = "0030620R";
	protected static final String sku = "11694840";
	

	public RegressionTest1(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}
	
	
	@Test
	public void TableRenderIssue_CCSQ1246(){
		CatalogsPage catalogsPage = PageFactory.initElements(driver, CatalogsPage.class);
		ProductsCatalogPage productsCatalogPage = catalogsPage.goToCatalogWithSomeNumberOfProducts(30, 1500);
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
	public void SaveDisabledWhenNoChangesAreMadeOnMappingDialog_CCSQS1256() throws InterruptedException{
		ProductsCatalogPage prodcutsCatalogPage = navigateToProductsCatalogPage();
		MapProductsDialog mapDialog = prodcutsCatalogPage.clickNotMappedOrMappedIcon();
		assertTrue(mapDialog.isSaveDisabled());
	}
	
	@Test
	public void Error500WhenYouSaveInEdit_CCSQS1281(){
		productsCatalogPage = navigateToProductsCatalogPage();
		EditProductPopupPage eppp = productsCatalogPage.clickEdit();
		eppp.clickSave();
		
		assertTrue(hasNoError());
	}
	 
	@Test
	public void addProductWithNoIdValue(){
		productsCatalogPage = navigateToProductsCatalogPage();
		AddProductPopup addProductPopup = productsCatalogPage.clickAddProduct();
		addProductPopup.setId("");
		addProductPopup.setMf("blah");
		addProductPopup.setMfpn("blah");
		
		exception.expect(NullPointerException.class);
		addProductPopup.clickSave();
	}
	
	@Test
	public void addProductWithNoMfValue(){
		productsCatalogPage = navigateToProductsCatalogPage();
		AddProductPopup addProductPopup = productsCatalogPage.clickAddProduct();
		addProductPopup.setId("blah");
		addProductPopup.setMf("");
		addProductPopup.setMfpn("blah");
		
		exception.expect(NullPointerException.class);
		addProductPopup.clickSave();
	}
	
	@Test
	public void addProductWithNoMfpnValue(){
		productsCatalogPage = navigateToProductsCatalogPage();
		AddProductPopup addProductPopup = productsCatalogPage.clickAddProduct();
		addProductPopup.setId(getRandomNumber());
		addProductPopup.setMf(getRandomNumber());
		addProductPopup.setMfpn("");
		
		exception.expect(NullPointerException.class);
		addProductPopup.clickSave();
	}
	
	@Test
	public void canAddWithIdAndUpc(){
		String tempId="";
		productsCatalogPage = navigateToProductsCatalogPage();
		AddProductPopup addProductPopup = productsCatalogPage.clickAddProduct();
		tempId = getRandomNumber();
		System.out.println(tempId);
		addProductPopup.setId(tempId);
		addProductPopup.setUpcEan(getRandomNumber());
		productsCatalogPage = addProductPopup.clickSave();
		productsCatalogPage.setProductToUse(tempId).clickAction(ElementConstants.DELETE);
		productsCatalogPage.clickYes();		
		assertTrue(hasNoError());
	}
	
	@Test
	public void canAddWithIdAndMfAndMfPn(){
		String tempId="";
		productsCatalogPage = navigateToProductsCatalogPage();
		AddProductPopup addProductPopup = productsCatalogPage.clickAddProduct();
		tempId = getRandomNumber();
		addProductPopup.setId(tempId);
		addProductPopup.setMf(getRandomNumber());
		addProductPopup.setMfpn(getRandomNumber());
		productsCatalogPage = addProductPopup.clickSave();
		productsCatalogPage.setProductToUse(tempId).clickAction(ElementConstants.DELETE);
		productsCatalogPage.clickYes();		
		assertTrue(hasNoError());
	}
	
/**	
	@Test
	public void IconsToolTipVisible(){
		ProductsCatalogPage productsCatalogPage = navigateToProductsCatalogPage();
		productsCatalogPage.hoverOverMappedOrNotMappedIcon();
	}
*/
	@Test
	public void ErrorWhenYouDeleteManualMapItem(){
		ProductsCatalogPage productsCatalogPage = navigateToProductsCatalogPage();
		productsCatalogPage.mapUnmappedItem("Sony", 1);
		String tempElement = productsCatalogPage.getRowThatWasMapped(); // get text id here then use theid to clic.
		productsCatalogPage.clickReturnToList();
		
		ProductsCatalogPage productsCatalogPage_after = navigateToProductsCatalogPage();
		
		productsCatalogPage_after.setProductToUse(tempElement).clickAction(ElementConstants.DELETE);
		productsCatalogPage_after.clickYes();
		
		assertTrue(hasNoError());
		
	}

	@Test
	public void ErrorWhenYouDownload_CCSQS1251(){
		ProductsCatalogPage productsCatalogPage = navigateToProductsCatalogPage();
		productsCatalogPage.clickDownload();

		assertTrue(hasNoError());
	}
	
	@Test
	public void SpecialCharacterInSearchCausesError_CCSQS1267(){
		String searchText = "() {} []";
		ProductsCatalogPage productsCatalogPage = navigateToProductsCatalogPage();
		productsCatalogPage.searchFor("pid", searchText);
		
		assertTrue(hasNoError());
	}	
	
	@Test
	public void exactMatchWhenMappingAProduct() throws InterruptedException{
		String id = getRandomNumber();
		String mf = "Lexmark";
		String mfPn = "69G8256";
		System.out.println("id: " + id);
		
		ProductsCatalogPage productsCatalogPage = navigateToProductsCatalogPage();
		AddProductPopup addPopup = productsCatalogPage.clickAddProduct();
		addPopup.setId(id);
		addPopup.setMf(mf);
		addPopup.setMfpn(mfPn);
		productsCatalogPage = addPopup.clickSave();
		productsCatalogPage.setProductToUse(id);
		MapProductsDialog mapDialog = (MapProductsDialog) productsCatalogPage.clickAction(ElementConstants.MAP);
		assertEquals(mfPn, mapDialog.getMappedMfPn());
		
		
		
	}
}
