package com.cbsi.col.pageobject.documents;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jetty.websocket.api.SuspendToken;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.customers.AccountsPage;
import com.cbsi.col.pageobject.customers.CurrentAccountTab;
import com.cbsi.col.pageobject.customers.AccountsPage.AccountType;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.DocStatus;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.DocumentState;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.ImportConfigPopup;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.MergePopup;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.MergePopup.DocList;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.PriceCalculator.ShippingTypes;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.QuickAddProductPopup;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.SendPage;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.UpdateMarginMarkupPopup;
import com.cbsi.col.pageobject.documents.DocumentsPage.Status;
import com.cbsi.col.pageobject.documents.QuotePage;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.AddImportUpdates;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.LineActions;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.PriceCalculator;
import com.cbsi.col.pageobject.documents.DocumentsPage.DocumentTabs;
import com.cbsi.col.pageobject.home.ColBasePage.Document;
import com.cbsi.col.pageobject.home.ColBasePage.Table;
import com.cbsi.col.pageobject.home.HomePage;
import com.cbsi.col.pageobject.products.AddToCatalogsPage;
import com.cbsi.col.pageobject.products.ProductsPage;
import com.cbsi.col.pageobject.products.ProductsPage.Action;
import com.cbsi.col.test.foundation.DocumentsBasePageTest;
import com.cbsi.col.test.util.Constants;
import com.cbsi.col.test.util.GlobalProperty;
import com.cbsi.col.test.util.StringUtil;


public class QuotePageExtTest extends DocumentsBasePageTest{

	public QuotePageExtTest(String url, String browser) {
		super(url, browser);
		// TODO Auto-generated constructor stub
	}
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	public void createQuote(){
		super.createQuote();
	}
	
	public QuotePage quotePage;
	

	@Test
	public void productLineItemDisplaysAllColumns(){
		createQuote();
		QuotePage quotePage = documentPage.goToQuote(quoteNumber);
		

		assertTrue(quotePage.tableColumnsAreDisplayed());
	}
	
	@Test
	public void lineAction_CopyLine(){
		createQuote();
		
		QuotePage quotePage = documentPage.goToQuote(quoteNumber);
		
		quotePage = (QuotePage) quotePage.selectProductFromTable(1);
		List<LinkedHashMap<String,String>> maps = quotePage.getTableAsMaps();

		quotePage = (QuotePage) quotePage.selectFromLineActions(LineActions.Copy_Line);
		List<LinkedHashMap<String,String>> mapsCopied = quotePage.getTableAsMaps();

		int count =0;
		
		for(LinkedHashMap<String, String> LinkedHashMap: mapsCopied){	
			//after copy line action, count how many of copied item exists in product table.
			if(LinkedHashMap.get("mfrpart#").contains(maps.get(0).get("mfrpart#"))){
				count++;
			}		
		}
		
		assertTrue(count==2);
		assertTrue(mapsCopied.size() ==4);
	}
	
	@Test
	public void lineAction_Compare(){
		createQuote();
		
		QuotePage quotePage = documentPage.goToQuote(quoteNumber);
		quotePage = (QuotePage) quotePage.selectProductFromTable(1,2);
		ComparisonPage comPage = (ComparisonPage)quotePage.selectFromLineActions(LineActions.Compare);
	}
	
	@Test
	public void lineAction_AddToCatalog(){
		createQuote();
		
		QuotePage quotePage = documentPage.goToQuote(quoteNumber);
		quotePage.selectProductFromTable(1);
		AddToCatalogsPage addCatalogsPage= (AddToCatalogsPage) quotePage.selectFromLineActions(LineActions.Add_to_Catalogs);
		
	}
	
	@Test
	public void lineAction_InsertSubTotalHeader(){
		createQuote();
		QuotePage quotePage = documentPage.goToQuote(quoteNumber);
		quotePage.selectProductFromTable(1);
		quotePage = (QuotePage) quotePage.selectFromLineActions(LineActions.Insert_Subtotal_Header);
		quotePage.setBundleHeader("");
		quotePage = (QuotePage) quotePage.clickSaveLineItem();
		
		assertEquals("[Subtotal Header]",quotePage.getSubTotalHeader());	
	}
	
	@Test
	public void lineAction_AddBlankLine_AddHorizontalLine(){
		createQuote();
		QuotePage quotePage = documentPage.goToQuote(quoteNumber);
		quotePage = (QuotePage) quotePage.selectProductFromTable(1);
		quotePage = (QuotePage) quotePage.selectFromLineActions(LineActions.Add_Blank_Line);
		
		quotePage = (QuotePage) quotePage.selectProductFromTable(1);
		quotePage = (QuotePage) quotePage.selectFromLineActions(LineActions.Add_Horizontal_Line);
		
		List<LinkedHashMap<String, String>> maps = quotePage.getTableAsMaps();

		logger.debug("mapSize: " +maps.size());
		assertEquals(Table.H_Line.toString(), maps.get(1).get(Table.Other.toString()));
		assertEquals(Table.B_Line.toString(), maps.get(2).get(Table.Other.toString()));	
	}

	@Test
	public void lineAction_LinkLine(){
		createQuote();
		QuotePage quotePage = documentPage.goToQuote(quoteNumber);
		quotePage = (QuotePage) quotePage.selectProductFromTable(1,2);
		quotePage.selectFromLineActions(LineActions.Link_Lines);
	}
	
	@Test
	public void editBillTo(){
//		QuotePage quotePage = homePage.goToDocumentsPage().switchToTab(DocumentTabs.QUOTES).goToQuote(1);
		QuotePage quotePage = homePage.goToDocumentsPage().switchToTab(DocumentTabs.QUOTES).setFilterByModifiedBy("All").goToQuote(1);

		quotePage.clickBillTo();
	}
	
	@Test
	public void editShipToSaveNewAddress(){
		String firstName = "qa"+ System.currentTimeMillis();
		String lastName = "test"+System.currentTimeMillis();
		
		QuotePage quotePage = goToFirstOpenQuote();
		
		quotePage.getPriceCalculator().setShipingType(ShippingTypes.Manual);
		
		String billTo = quotePage.getBillTo();
		String shipTo = quotePage.getShipTo();

		AddressPage addressPage = quotePage.clickShipTo();
		
		addressPage.setFirstName(firstName);
		addressPage.setLastName(lastName);		
		addressPage.clickCopyToShipping();
		
		addressPage = addressPage.clickAddShipping();
		
		assertTrue(addressPage.getSelectedShippingAddress()+ "|" + firstName ,addressPage.getSelectedShippingAddress().startsWith(firstName));
		
		assertNotEquals(shipTo, addressPage.getSelectedShippingAddress());
		quotePage = addressPage.clickSave(QuotePage.class);

		assertNotEquals(billTo + " / " + quotePage.getBillTo(), billTo, quotePage.getBillTo());
		assertNotEquals(shipTo + " / " + quotePage.getShipTo(),shipTo, quotePage.getShipTo());
	}
	
	@Test
	public void editOrderOptions(){
		createQuote();
		QuotePage quotePage = documentPage.goToQuote(quoteNumber);
//		QuotePage quotePage = homePage.goToDocumentsPage().switchToTab(DocumentTabs.QUOTES).goToQuote(1);
		quotePage.clickOrderOptions();
	}
	
	@Test
	public void lineActionBarIsSticky(){
		createQuote();
		QuotePage quotePage = documentPage.goToQuote(quoteNumber);
		
		quotePage.setDocumentNotes("a");
		quotePage.forceWait(2000);
		
		assertTrue(quotePage.isLineActionsDropdownDisplayed());
		assertTrue(quotePage.isReorderLinesDisplayed());
		assertTrue(quotePage.isAddImportUpdateDropdownDisplayed());
		assertTrue(quotePage.isLiveCost());
	}
	
	@Test
	public void reorderProducts(){
		createQuote();
		QuotePage quotePage = documentPage.goToQuote(quoteNumber);
		
		Map<String, String> firstProduct = (Map<String, String>) quotePage.getTableAsMaps().get(0);
		quotePage = (QuotePage) ((QuotePage) quotePage.clickReorderLine()).reorder(1,3);
		
		assertFalse(quotePage.getTableAsMaps().get(0).equals(firstProduct));
	}
	
	@Test
	public void getStatus(){
		createQuote();
		QuotePage quotePage = documentPage.goToQuote(quoteNumber);
		
		assertEquals(DocumentState.Open.toString(), quotePage.getDocumentState());
		
		quotePage.clickSend().clickEmail().clickSendEmail();
	}
	
	@Test
	public void lockedQuoteIsNotEditable(){
		createQuote();
		QuotePage quotePage = documentPage.goToQuote(quoteNumber);
		quotePage = (QuotePage)quotePage.clickElectronicSignature();

		assertEquals(DocumentState.Out_For_E_Sign.toString(), quotePage.getDocumentState());

		assertFalse(quotePage.isReorderLinesDisplayed());
		assertFalse(quotePage.isAddImportUpdateDropdownDisplayed());
		assertFalse(quotePage.isLiveCost());
		
		assertFalse(quotePage.isShipToEnabled());
		assertFalse(quotePage.isBillToEnabled());
	}
	
	@Ignore("fix failing issue.")
	@Test
	public void saveAndCloseOpensNextDocument(){
		QuotePage quotePage = goToFirstOpenQuote();
		
		List<HashMap<Document, String>> allDocuments = quotePage.getAllDocuments();		
		String first_id = allDocuments.get(1).get(Document.ID);
		String first_doctype = allDocuments.get(1).get(Document.TYPE);
		
		quotePage.clickSaveAndClose();

		DocumentsBasePage<?> doc = (DocumentsBasePage<?>) PageFactory.initElements(driver, getDocumentClassByName(first_doctype));
	
		assertEquals(first_id, doc.getDocNumber()+"");
	}

	@Test
	public void addProductLoadTest(){
		CurrentAccountTab currentAccountPage=  customersPage.clickViewCustomer("Qa");
		
		QuotePage quotePage = currentAccountPage.clickCreateQuote();
		ProductsPage productPage = quotePage.searchProduct("lenovo").clickCheckAll();
		
		long startTime = System.currentTimeMillis();
		
		productPage.selectAction(Action.AddToQuote);
		quotePage = PageFactory.initElements(driver, QuotePage.class);
	
		long endTime = System.currentTimeMillis();
		
		assertTrue("Time: " + (endTime - startTime) +" ms",endTime - startTime <15000);
	}
	
	@Ignore("in progress")
	@Test
	public void exportToAutoTaskShowAlert(){
		QuotePage quotePage = goToFirstOpenQuote();
//		q
		
	}
	
//	@Ignore("in progress")
	@Test
	public void importCustomConfig(){
		QuotePage quotePage = goToFirstOpenQuote();
		int productTableSize = quotePage.getTableAsMaps().size();
		
		ImportConfigPopup importConfig = (ImportConfigPopup) quotePage.selectFromAddImportUpdate(AddImportUpdates.Import_Config);
		importConfig.clickGenericImport().clickChooseFile("ImportConfig.xls");
		quotePage = (QuotePage) importConfig.clickSave();
		
		List<LinkedHashMap<String, String>> tableMaps = quotePage.getTableAsMaps();
		LinkedHashMap<String, String> lastTableMap = tableMaps.get(tableMaps.size()-1);
		
		assertTrue(productTableSize + 1 == tableMaps.size());
		assertTrue(lastTableMap.get("mfrpart#").equals("GLC-T="));
		assertFalse(lastTableMap.get("description").isEmpty());
		assertFalse(lastTableMap.get("listprice").isEmpty());
		assertFalse(lastTableMap.get("qty").isEmpty());
		assertFalse(lastTableMap.get("price").isEmpty());
		assertFalse(lastTableMap.get("total").isEmpty());


	}
	
	@Test
	public void markUpNotAppliedOnBundleAndSubTotalProduct(){
		String price1 = "";
		String price2 = "";
		String price3 = "";
		
		createQuote();
		QuotePage quotePage = documentPage.goToQuote(quoteNumber);
		quotePage = (QuotePage) ((QuotePage) quotePage.selectProductFromTable(2)).selectFromLineActions(LineActions.Convert_to_Bundle);
		quotePage.setBundleHeader("123");
		quotePage.setBundleDesc("desc");
				
		quotePage = (QuotePage) ((QuotePage)quotePage.selectProductFromTable(5)).selectFromLineActions(LineActions.Add_Subtotal);
		
		List<LinkedHashMap<String, String>> tableBeforeMarkup = quotePage.getTableAsMaps();
		
		price1 = tableBeforeMarkup.get(0).get("total");
		price2 = tableBeforeMarkup.get(2).get("total");
		price3 = tableBeforeMarkup.get(4).get("total");
		
		quotePage.clickCheckAll();
		UpdateMarginMarkupPopup updateMarginPopup = (UpdateMarginMarkupPopup) quotePage.selectFromAddImportUpdate(AddImportUpdates.Update_MarginMarkup);
		quotePage = updateMarginPopup.setApply(10).clickApplyMarkup(QuotePage.class);
		
		List<LinkedHashMap<String, String>> tableAfterMarkedUp = quotePage.getTableAsMaps();

		quotePage.printTable(tableBeforeMarkup);
		quotePage.printTable(tableAfterMarkedUp);
		
		assertNotEquals(price1, tableAfterMarkedUp.get(0).get("total"));
		assertNotEquals(price2, tableAfterMarkedUp.get(2).get("total"));
		assertNotEquals(price3, tableAfterMarkedUp.get(4).get("total"));
	}
	
	@Test
	public void copyToNewQuoteHasLineActionItemsAndProductTable(){
		QuotePage quotePage = goToFirstOpenQuote();
		int productSize = quotePage.getTableAsMaps().size();
		
		quotePage = quotePage.clickCopyToNewQuote();
		
		assertTrue(productSize == quotePage.getTableAsMaps().size());
		
		assertTrue(quotePage.isLineActionsDropdownDisplayed());
		assertTrue(quotePage.isReorderLinesDisplayed());
		assertTrue(quotePage.isAddImportUpdateDropdownDisplayed());
	}
	
	@Test
	public void profitStaysSameAfterBundleThenUnbundle(){
		
		String itemProfit = "";
		String itemCost = "";
		String itemGp = "";
		String shippingProfit = "";
		String shippingCost = "";
		String shippingGp = "";
		String profit = "";
		String cost = "";
		String gp = "";
		
		createQuote();
		quotePage = documentPage.goToQuote(quoteNumber);
		quotePage.selectProductFromTable(1, 2);
		quotePage = (QuotePage)quotePage.selectFromLineActions(LineActions.Convert_to_Bundle);
		quotePage = (QuotePage) ((QuotePage)quotePage.setBundleHeader("test1")).clickSaveLineItem();
		
		PriceCalculator priceCalc =  quotePage.getPriceCalculator();
		
//		itemProfit = priceCalc.getItemProfit();
//		itemCost = priceCalc.getItemCost();
//		itemGp = priceCalc.getGP();
//		shippingProfit = priceCalc.getShippingProfit();
//		shippingCost = priceCalc.getShippingCost();
//		shippingGp = priceCalc.getShippingGP();
		profit = priceCalc.getProfit();
		cost = priceCalc.getCost();
		gp = priceCalc.getGP();
		
		quotePage.setQtyInTable(4, 10);
		quotePage = (QuotePage) quotePage.selectProductFromTable(1,2,3,4);
		quotePage = (QuotePage) quotePage.selectFromLineActions(LineActions.Unbundle);
		
		PriceCalculator priceCalcNew = quotePage.getPriceCalculator();
		
//		assertTrue(itemProfit == priceCalcNew.getItemProfit());
//		assertTrue(itemCost == priceCalcNew.getItemCost());
//		assertTrue(itemGp == priceCalcNew.getItemGP());
//		assertTrue(shippingProfit == priceCalcNew.getShippingProfit());
//		assertTrue(shippingCost == priceCalcNew.getShippingCost());
		assertTrue(profit + " : " + priceCalcNew.getProfit(), profit.equals(priceCalcNew.getProfit()));
		assertTrue(cost + " : " + priceCalcNew.getCost(), cost .equals(priceCalcNew.getCost()));
		assertTrue(gp + " : " + priceCalcNew.getGP(), gp.equals(priceCalcNew.getGP()));
		
	}
//	@Test
//	public void cleanUpCompanies() throws Exception{
//		AccountsPage accountPage = homePage.goToAccountsPage();
//		while(true){
//			List<WebElement> deleteCustomers = driver.findElements(By.cssSelector("tr td"));
//			for(WebElement e: deleteCustomers){
//				if(e.getText().toLowerCase().startsWith("qa_customer")){
//					WebElement delete = e.findElement(By.xpath("../td/a[contains(@id,'delete')]"));
//					delete.click();
//					Thread.sleep(500);
//					Alert alert = driver.switchTo().alert();
//					alert.accept();
//					break;
//				}
//			}
//			Thread.sleep(500);
//		}
//	}
	
//	

	
//	@Test
//	public void cleanUpDocuments(){
//		DocumentsPage docPage  = homePage.goToDocumentsPage();
//		while(true){
//			try{
//			docPage = docPage.deleteQuotesByCompnayName("qacustomer_");
//			}catch(NullPointerException e){
//				driver.findElement(By.linkText("Modified By")).click();
//
//			}
//			
//		}
//	}
	
	public Class<?> getDocumentClassByName(String name){
		name = name.replaceAll("\\s", "");
		
		try {
			return Class.forName("com.cbsi.col.pageobject.documents."+name + "Page");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
