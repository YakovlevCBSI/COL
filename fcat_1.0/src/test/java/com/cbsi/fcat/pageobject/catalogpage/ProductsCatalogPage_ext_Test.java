package com.cbsi.fcat.pageobject.catalogpage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.fcat.pageobject.catatlogpage.AddProductPopup;
import com.cbsi.fcat.pageobject.catatlogpage.CatalogsPage;
import com.cbsi.fcat.pageobject.catatlogpage.DetailsPage;
import com.cbsi.fcat.pageobject.catatlogpage.EditProductPopupPage;
import com.cbsi.fcat.pageobject.catatlogpage.MapProductsDialog;
import com.cbsi.fcat.pageobject.catatlogpage.ProductsCatalogPage;
import com.cbsi.fcat.pageobject.catatlogpage.ProductsCatalogPage.ItemIds;
import com.cbsi.fcat.pageobject.foundation.AllBaseTest;
import com.cbsi.fcat.util.ElementConstants;

public class ProductsCatalogPage_ext_Test extends AllBaseTest{

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	private boolean needsCleanUp = false;
	public ProductsCatalogPage  productsCatalogPage = null;
	protected static final String mf = "ATI Technologies";
	protected static final String mfPn = "0030620R";
	protected static final String sku = "11694840";
	

	public ProductsCatalogPage_ext_Test(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}
	
	public static String noSearchFoundMessage = "No products matched your search. Try to change filter to broaden your search.";
	
	
	@After
	public void cleanUp(){
		if(needsCleanUp) {
			driver.close();
			super.cleanUpThenDeleteTemp();
		}
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
	
	public ProductsCatalogPage productPage;
	
	@Test
	public void checkMapCountAfterFileUpload() throws InterruptedException{
		needsCleanUp = true;
		
		DetailsPage detailsPage = UploadFullFile().automap();
		CatalogsPage catalogsPage = detailsPage.clickReturnToList();
		
		while(catalogsPage.getProductNumberByCatalog(tempFile) <=0){
			catalogsPage.refresh();
			catalogsPage = PageFactory.initElements(driver, CatalogsPage.class);
		}
		
		productPage = catalogsPage.goToCatalogByName(tempFile);
		assertTrue(productPage.getTotalProducts() == 7);
		assertTrue(productPage.getMapped() == 6);
		assertTrue(productPage.getNotMapped() == 1);	
	}
	
	@Test
	public void checkMapCountAfterDelete() throws InterruptedException{
		needsCleanUp = true;

		checkMapCountAfterFileUpload();
		
		int initMapped = productPage.getMapped();
		int initNotMapped = productPage.getNotMapped();
		int initTotal = productPage.getTotalProducts();
		
		productPage.setProductToUse();
		productPage = (ProductsCatalogPage) productPage.clickAction(ElementConstants.DELETE);
		productPage = productPage.clickYes();
		
		assertTrue("expected: " + (initMapped -1) + " / actual: " + productPage.getMapped(),initMapped - 1 == productPage.getMapped());
		assertTrue("expected: " + (initNotMapped -1) + " / actual: " + productPage.getNotMapped(), initNotMapped == productPage.getNotMapped());
		assertTrue("expected: " + (initTotal - 1) + " / actual: " + productPage.getTotalProducts(), initTotal - 1 == productPage.getTotalProducts());
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
	
	@Test
	public void searchPidTest() throws Exception{
		ProductsCatalogPage productsCatalogPage = navigateToProductsCatalogPage(100, 600000);
		productsCatalogPage.searchFor(ItemIds.ID, "abc");
		productsCatalogPage.waitForSearch();

		//some wait needed for search loading.
		assertTrue(hasNoError());	
	}
	
	@Test
	public void searchPidMfTest() throws Exception{
		ProductsCatalogPage productsCatalogPage = navigateToProductsCatalogPage(100, 600000);
		productsCatalogPage.searchFor(ItemIds.ID, "abc");
		productsCatalogPage.searchFor(ItemIds.MF, "abc");
		productsCatalogPage.waitForSearch();
		
		//some wait needed for search loading.
		assertEquals(noSearchFoundMessage, productsCatalogPage.getProductValue(1).get("message"));
		assertTrue(hasNoError());	
	}
	
	@Test
	public void searchMfTest() throws Exception{
		ProductsCatalogPage productsCatalogPage = navigateToProductsCatalogPage(100, 600000);
		productsCatalogPage.searchFor(ItemIds.MF, "abc");
		productsCatalogPage.waitForSearch(25000);
		
		//some wait needed for search loading.
		assertTrue(hasNoError());	
	}
	
	@Test
	public void searchMfpnTest() throws Exception{
		ProductsCatalogPage productsCatalogPage = navigateToProductsCatalogPage(100, 600000);
		productsCatalogPage.searchFor(ItemIds.MFPN, "abc");
		productsCatalogPage.waitForSearch(25000);
		
		//some wait needed for search loading.
		assertTrue(hasNoError());	
	}
	
	@Test
	public void messageShowWhenSearchResultNone() throws Exception{
		ProductsCatalogPage productsCatalogPage = navigateToProductsCatalogPage(100, 600000);
		productsCatalogPage.searchFor(ItemIds.ID, "abcdefgh21312321");
		productsCatalogPage.waitForSearch(25000);
		
		String noResultMsg = productsCatalogPage.getProductValue(1).get("message");
		
		assertEquals(noSearchFoundMessage, productsCatalogPage.getProductValue(1).get("message"));

	}
	
	@Test
	public void searchPidMfpnTest() throws Exception{
//		ProductsCatalogPage productsCatalogPage = navigateToProductsCatalogPage(100, 600000);
		ProductsCatalogPage productsCatalogPage = navigateToProductsCatalogPage(1,10);

		productsCatalogPage.searchFor(ItemIds.ID, "00");
		productsCatalogPage.searchFor(ItemIds.MF, "abc");
		productsCatalogPage.waitForSearch();
		
		//some wait needed for search loading.
		productsCatalogPage = PageFactory.initElements(driver, ProductsCatalogPage.class);
		assertEquals(noSearchFoundMessage, productsCatalogPage.getProductValue(1).get("message"));
		assertTrue(hasNoError());

	}
	
	@Test
	public void mapMfPnWithoutLeadingZero(){
		String tempId = getRandomNumber();
		ProductsCatalogPage productsCatalogPage = navigateToProductsCatalogPage();
		
		int initMapped = productsCatalogPage.getMapped();
		int initNotMapped = productsCatalogPage.getNotMapped();
		int initTotal = productsCatalogPage.getTotalProducts();
		
		AddProductPopup addProductPopup = productsCatalogPage.clickAddProduct();
		addProductPopup.setId(tempId);
		addProductPopup.setMf(mf);
		addProductPopup.setMfpn(mfPn.replaceFirst("00",""));
		
		productsCatalogPage = addProductPopup.clickSave();
		productsCatalogPage.setProductToUse(tempId);
		assertTrue(productsCatalogPage.isProductRowMapped());
		
		assertTrue(initMapped + 1 == productsCatalogPage.getMapped());
		assertTrue(initNotMapped == productsCatalogPage.getNotMapped());
		assertTrue(initTotal + 1 == productsCatalogPage.getTotalProducts());
		
		productsCatalogPage.clickAction(ElementConstants.DELETE);	
		productsCatalogPage.clickYes();
	}
	
	@Test
	public void mapMfMfpn(){
		String tempId = getRandomNumber();
		ProductsCatalogPage productsCatalogPage = navigateToProductsCatalogPage();
		
		int initMapped = productsCatalogPage.getMapped();
		int initNotMapped = productsCatalogPage.getNotMapped();
		int initTotal = productsCatalogPage.getTotalProducts();
		
		AddProductPopup addProductPopup = productsCatalogPage.clickAddProduct();
		addProductPopup.setId(tempId);
		System.out.println(tempId);
		addProductPopup.setMf(mf);
		addProductPopup.setMfpn(mfPn);
		
		productsCatalogPage = addProductPopup.clickSave();
		productsCatalogPage.setProductToUse(tempId);
		assertTrue(productsCatalogPage.isProductRowMapped());
		
		assertTrue("expected: " + (initMapped + 1)  + "/ actual; " +  productsCatalogPage.getMapped(), initMapped + 1 == productsCatalogPage.getMapped());
		assertTrue("expected: " + initNotMapped + "/ actual; " +  productsCatalogPage.getNotMapped(), initNotMapped == productsCatalogPage.getNotMapped());
		assertTrue("expected: "+ (initTotal + 1) + "/ actual; " + productsCatalogPage.getTotalProducts(), initTotal + 1 == productsCatalogPage.getTotalProducts());
		
		productsCatalogPage.clickAction(ElementConstants.DELETE);
		productsCatalogPage.clickYes();
	}
	
	@Ignore("unable to map by skuId due to mongo bug")
	@Test
	public void mapSkuId(){
		String tempId = getRandomNumber();
		ProductsCatalogPage productsCatalogPage = navigateToProductsCatalogPageManual();
		
		int initMapped = productsCatalogPage.getMapped();
		int initNotMapped = productsCatalogPage.getNotMapped();
		int initTotal = productsCatalogPage.getTotalProducts();
		
		AddProductPopup addProductPopup = productsCatalogPage.clickAddProduct();
		addProductPopup.setId(tempId);
		addProductPopup.setSkuId(sku);
		
		productsCatalogPage = addProductPopup.clickSave();
		productsCatalogPage.setProductToUse(tempId);
		assertTrue(productsCatalogPage.isProductRowMapped());
		
		assertTrue(initMapped + 1 == productsCatalogPage.getMapped());
		assertTrue(initNotMapped == productsCatalogPage.getNotMapped());
		assertTrue(initTotal + 1 == productsCatalogPage.getTotalProducts());
		
		productsCatalogPage.clickAction(ElementConstants.DELETE);
		productsCatalogPage.clickYes();
	}
	
	@Test
	public void ErrorWhenYouGoPreviousInMappingDialog(){
		ProductsCatalogPage productsCatalogPage = navigateToProductsCatalogPage();
		MapProductsDialog mapDialog = (MapProductsDialog) productsCatalogPage.setProductToUse().clickAction(ElementConstants.MAP);
		mapDialog.searchName("Sony");
		mapDialog = mapDialog.clickNext();
		mapDialog = mapDialog.clickPrevious();
		
		assertTrue(hasNoError());
	}
	
	@Test
	public void mapIconRefreshesOnSecondTimeMapping(){
		
	}
	
	@Test
	public void itemPerPageChangesItemContentsNotDisplayed(){
		ProductsCatalogPage productsCatalogPage = navigateToProductsCatalogPage();
		EditProductPopupPage editProduct = productsCatalogPage.selectItemNumberPerPage(50).setProductToUse().clickEdit();
		editProduct.setData();
		
		assertTrue(!editProduct.getManufacturerName().isEmpty() || !editProduct.getManufacturerPartNumber().isEmpty() || !editProduct.getUpcEan().isEmpty());
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
