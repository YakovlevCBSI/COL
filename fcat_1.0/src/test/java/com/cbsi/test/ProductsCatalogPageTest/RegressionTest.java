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

public class RegressionTest extends AllBaseTest{

	public RegressionTest(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}
	
	public ProductsCatalogPage  productsCatalogPage = null;

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
	public void Error500OnWhenNoChangesAreMadeOnMappingDialog_CCSQS1256(){
		ProductsCatalogPage prodcutsCatalogPage = navigateToProductsCatalogPage();
		prodcutsCatalogPage.exitWithoutSaveOnMapppingDialog();
		
	
		
		assertTrue(hasNoError());
	}
	
	@Test
	public void Error500WhenYouSaveInEdit_CCSQS1281(){
		productsCatalogPage = navigateToProductsCatalogPage();
		EditProductPopupPage eppp = productsCatalogPage.clickEdit();
		eppp.clickSave();
		
		assertTrue(hasNoError());
		
	}
	
	 @Rule
	 public ExpectedException exception = ExpectedException.none();
	 
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
		addProductPopup.setId(tempId);
		addProductPopup.setUpcEan(getRandomNumber());
		ProductsCatalogPage productsPage = addProductPopup.clickSave();
		productsCatalogPage.setProductToUse(tempId).clickAction(ElementConstants.DELETE);
		productsPage.clickYes();		
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
		ProductsCatalogPage productsPage = addProductPopup.clickSave();
		productsCatalogPage.setProductToUse(tempId).clickAction(ElementConstants.DELETE);
		productsPage.clickYes();		
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
	
	//("1328")
	@Test
	public void UnableToDeleteProductIdWithHtml_1308() throws InterruptedException{
		String htmlText= "</table>" + (System.currentTimeMillis()+"").substring(4);
 		ProductsCatalogPage productsCatalogPage = navigateToProductsCatalogPageManual();
		System.out.println(htmlText);
		AddProductPopup addProductPopup = productsCatalogPage.clickAddProduct();
		addProductPopup.setId(htmlText);
		addProductPopup.setMf("some Text");
		addProductPopup.setMfpn("some Text");
		
		ProductsCatalogPage productsCatalogPageNew = addProductPopup.clickSave();
		
		productsCatalogPageNew.setProductToUse(htmlText).clickAction(ElementConstants.DELETE);
		
		productsCatalogPageNew.clickYes();
		
		assertTrue(hasNoError());
	}
	

	@Test
	public void UPCEanFieldSaveChanges_1295() throws InterruptedException{
		String time= System.currentTimeMillis() + "";
		ProductsCatalogPage productsCatalogPage = navigateToProductsCatalogPageManual();
		EditProductPopupPage editProductPage =productsCatalogPage.clickEdit();
		editProductPage.setData();
		
		//System.out.println(editProductPage.getManufacturerName());
		editProductPage.setManufacturerName(time);
		editProductPage.setManufacturerPartNumber(time);
		editProductPage.setUpcEan(time);
		editProductPage.setCnetSkuId(time);
		editProductPage.setInventory(time);
		//editProductPage.setPrice(time);
 		
		ProductsCatalogPage productsCatalogPageNew = editProductPage.clickSave();
		EditProductPopupPage editProductPageNew = productsCatalogPageNew.clickEdit().setData();
		
		assertEquals(time, editProductPageNew.getManufacturerName());
		assertEquals(time, editProductPageNew.getManufacturerPartNumber());
		assertEquals(time, editProductPageNew.getUpcEan());
		assertEquals(time, editProductPageNew.getCnetSkuId());
		assertEquals(time, editProductPageNew.getInventory());
		//assertEquals(time, editProductPageNew.getPrice());
		//assertEquals(time, editProductPageNew.getMsrp());
	}
	
	public static final String mfn= "SONY";
	public static String  mfpn= "paltov";
	public static String mfpn2 = "DL32S3000U";
	@Test
	public void AutomapAfterEditRefreshesMapIcon() throws InterruptedException{
		ProductsCatalogPage productsCatalogPage = navigateToProductsCatalogPage();
		MapProductsDialog mapDialog = productsCatalogPage.clickNotMappedOrMappedIcon();
		
		//--does this work??--//
		String rowMapped = productsCatalogPage.getRowThatWasMapped();
		System.out.println("mid check: " + rowMapped);
		ProductsCatalogPage productsCatalogPageNew = ifMappedUnmapItem(mapDialog);
		
		
		EditProductPopupPage editProduct = (EditProductPopupPage)productsCatalogPageNew.setProductToUse(rowMapped).clickAction(ElementConstants.EDIT);
		editProduct.setData();

		editProduct.setManufacturerName(mfn);
		
		mfpn = editProduct.getManufacturerPartNumber().toLowerCase().contains(mfpn)? mfpn2:mfpn;
		
		editProduct.setManufacturerPartNumber(mfpn);
		ProductsCatalogPage productsCatalogPageFinal = editProduct.clickSave();
		productsCatalogPageFinal.setProductToUse(rowMapped);

		assertTrue(productsCatalogPageFinal.isProductRowMapped());
	}
	
	//samsung s20c200b monitor
	public static String upcEan= "0000000057042";
	public static String upcEan2 = "0000000081351";
	
	@Test
	public void mapUpcEanWhenMfnameAndMfparnumberAreNotPresent() throws InterruptedException{
		ProductsCatalogPage productsCatalogPage = navigateToProductsCatalogPage();
		MapProductsDialog mapDialog =productsCatalogPage.clickNotMappedOrMappedIcon();
		
		String rowMapped = productsCatalogPage.getRowThatWasMapped();
		System.out.println(rowMapped);
		ProductsCatalogPage productsCatalogPageNew= ifMappedUnmapItem(mapDialog);
		productsCatalogPageNew.setProductToUse(rowMapped);
		
		EditProductPopupPage editProduct = (EditProductPopupPage)productsCatalogPageNew.setProductToUse(rowMapped).clickAction(ElementConstants.EDIT);
		editProduct.setData();
		
		upcEan = editProduct.getUpcEan().contains(upcEan)? upcEan2:upcEan;
		
		editProduct.setManufacturerName("blah");
		editProduct.setManufacturerPartNumber("blah");
		editProduct.setUpcEan(upcEan);
	
		ProductsCatalogPage productsCatalogPageFinal = editProduct.clickSave();	
		productsCatalogPageFinal.setProductToUse(rowMapped);

		assertTrue(productsCatalogPageFinal.isProductRowMapped());
		
	}
	
	
	@Test
	public void mapUpcEanWhenMfPnIsUpcEan(){
		ProductsCatalogPage productsCatalogPage = navigateToProductsCatalogPage();
		MapProductsDialog mapDialog =productsCatalogPage.clickNotMappedOrMappedIcon();
		
		String rowMapped = productsCatalogPage.getRowThatWasMapped();
		System.out.println(rowMapped);
		ProductsCatalogPage productsCatalogPageNew= ifMappedUnmapItem(mapDialog);
		productsCatalogPageNew.setProductToUse(rowMapped);
		
		EditProductPopupPage editProduct = (EditProductPopupPage)productsCatalogPageNew.setProductToUse(rowMapped).clickAction(ElementConstants.EDIT);
		editProduct.setData();
		
		upcEan = editProduct.getManufacturerPartNumber().contains(upcEan)? upcEan2:upcEan;

		
		editProduct.setManufacturerName("blah");
		editProduct.setManufacturerPartNumber(upcEan);
	
		ProductsCatalogPage productsCatalogPageFinal = editProduct.clickSave();	
		productsCatalogPageFinal.setProductToUse(rowMapped);

		assertTrue(productsCatalogPageFinal.isProductRowMapped());
	}
	
	@Test
	public void errorShowsForDuplicateCpn(){
		ProductsCatalogPage productsCatalogPage = navigateToProductsCatalogPage();
		String cpn = productsCatalogPage.getProductValue(1).get("product id");
		
		AddProductPopup addProductPopup = productsCatalogPage.clickAddProduct();
		addProductPopup.setId(cpn);
		addProductPopup.setMf("junk");
		addProductPopup.setMfpn("junk");
		
		String errorMessage = addProductPopup.clickSaveFail();
		
		assertEquals("The product already exists", errorMessage);
	}

	
	public void mapCountIsCorrect(){
		
	}
	
	@Test
	public void searchPidTest() throws Exception{
		ProductsCatalogPage productsCatalogPage = navigateToProductsCatalogPage(100, 600000);
		productsCatalogPage.searchFor("pid", "abc");
		productsCatalogPage.waitForSearch();

		//some wait needed for search loading.
		assertTrue(hasNoError());	
	}
	
	@Test
	public void searchPidMfTest() throws Exception{
		ProductsCatalogPage productsCatalogPage = navigateToProductsCatalogPage(100, 600000);
		productsCatalogPage.searchFor("pid", "abc");
		productsCatalogPage.searchFor("mf", "abc");
		productsCatalogPage.waitForSearch();
		
		//some wait needed for search loading.
		assertTrue(hasNoError());	
	}
	
	@Test
	public void searchMfTest() throws Exception{
		ProductsCatalogPage productsCatalogPage = navigateToProductsCatalogPage(100, 600000);
		productsCatalogPage.searchFor("mf", "abc");
		productsCatalogPage.waitForSearch(25000);
		
		//some wait needed for search loading.
		assertTrue(hasNoError());	
	}
	
	@Test
	public void searchMfpnTest() throws Exception{
		ProductsCatalogPage productsCatalogPage = navigateToProductsCatalogPage(100, 600000);
		productsCatalogPage.searchFor("mfPn", "abc");
		productsCatalogPage.waitForSearch(25000);
		
		//some wait needed for search loading.
		assertTrue(hasNoError());	
	}
	
	@Test
	public void searchPidMfpnTest() throws Exception{
		ProductsCatalogPage productsCatalogPage = navigateToProductsCatalogPage(100, 600000);
		productsCatalogPage.searchFor("pid", "abc");
		productsCatalogPage.searchFor("mf", "abc");
		productsCatalogPage.waitForSearch();
		
		//some wait needed for search loading.
		assertTrue(hasNoError());	
	}
	
	public ProductsCatalogPage ifMappedUnmapItem(MapProductsDialog mapDialog){
		ProductsCatalogPage productsCatalogPageNew= null;
		
		if(!mapDialog.isMapped()){
			productsCatalogPageNew= mapDialog.clickCancel();
		}
		else{
			System.out.println("UNMAPPING!");
			mapDialog.clickUnmap();
			productsCatalogPageNew = mapDialog.clickSave();
		}
		
		return productsCatalogPageNew;
	}
}
