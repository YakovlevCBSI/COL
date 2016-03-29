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


public class QuotePageTest extends DocumentsBasePageTest{

	public QuotePageTest(String url, String browser) {
		super(url, browser);
		// TODO Auto-generated constructor stub
	}
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void createQuote(){
		super.createQuote();
	}
	
	public QuotePage quotePage;
	
	@Test
	public void createBundleInQuote(){
		createQuote();
		quotePage = documentPage.goToQuote(quoteNumber);
		quotePage.selectProductFromTable(1);
		quotePage = (QuotePage)quotePage.selectFromLineActions(LineActions.Convert_to_Bundle);
		quotePage.setBundleHeader("test1");
		quotePage.setBundleDesc("test description");
		quotePage = (QuotePage)quotePage.clickSaveLineItem();
		
		assertEquals("test1",quotePage.getBundleHeader());
		assertEquals("test description", quotePage.getBundleDesc());
	}
	
	@Test
	public void copyToNewQuoteInDocumentsTable(){
		createQuote();
		QuotePage quotePage = documentPage.goToQuote(quoteNumber);
		long oldQuote = quotePage.getQuoteNumber();
		
		QuotePage quotePageNew = quotePage.clickCopyToNewQuote();
		long newQuote = quotePageNew.getQuoteNumber();
		
		assertFalse(oldQuote + " : " + newQuote, oldQuote == newQuote);	
	}
	
	@Test
	public void copyToNewQuoteInQuote(){
		createQuote();
		QuotePage quotePage = documentPage.goToQuote(quoteNumber);
		quotePage = quotePage.clickCopyToNewQuote();
		long newQuoteNumber = quotePage.getQuoteNumber();
		
		assertFalse(quoteNumber == newQuoteNumber);
	}
	
	@Test
	public void deleteProductFromQuote(){
		String mfPn = "";
		createQuote();
		
		QuotePage quotePage = documentPage.goToQuote(quoteNumber);
		quotePage = (QuotePage) quotePage.selectProductFromTable(1);
		
		mfPn = quotePage.getMfPnsFromTable(1).get(0).toString();	
		quotePage = (QuotePage) quotePage.selectFromLineActions(LineActions.Delete);
		
		System.out.println(mfPn);
		System.out.println(quotePage.getMfPnsFromTable(1).get(0).toString());
		
		assertFalse(mfPn.equals( quotePage.getMfPnsFromTable(1).get(0).toString()));
	}
	
	@Test
	public void sendQuoteToPrint(){
		createQuote();
		
		QuotePage quotePage = documentPage.goToQuote(quoteNumber);
		SendPage sendPage = quotePage.clickSend();
		sendPage.clickPrint();
		
		assertTrue(sendPage.isPrintPrviewOpen());
	}

	@Test
	public void sendQuoteViaEmail(){
		createQuote();
		
		QuotePage quotePage = documentPage.goToQuote(quoteNumber);
		SendPage sendPage = quotePage.clickSend();
		sendPage.clickEmail();
		
		assertTrue(sendPage.isEmailBoxOpen());
		//Actually send the mail.
		
	}
	
	@Ignore("Not yet ready... needs file io logic")
	@Test
	public void sendQuoteAsPDF(){
		createQuote();
		
		QuotePage quotePage = documentPage.goToQuote(quoteNumber);
		SendPage sendPage = quotePage.clickSend();
		sendPage.clickPDF();
		
		assertTrue(sendPage.isFileDownloaded());
		
	}

	@Test
	public void quantityChangeUpdatesPrice(){
		createQuote();
		
		QuotePage quotePage = documentPage.goToQuote(quoteNumber);
		quotePage.setQtyInTable(1, 5);
		double unitPriceFirstRow = quotePage.getUnitPriceFromTable(1);
		double expectedPrice = Double.parseDouble(String.format("%.2f", unitPriceFirstRow*5));

		assertTrue("expected: " +  expectedPrice + " / actual: " + quotePage.getTotalPriceFromTable(1),
				expectedPrice == quotePage.getTotalPriceFromTable(1));
	}
	
	@Test
	public void priceCalculatorSum(){
		CurrentAccountTab currentAccountPage=  customersPage.goToAllAcountsTab().setFilterByAccountType(AccountType.CUSTOMER).clickViewCustomer("Qa");
		
		QuotePage quotePage = currentAccountPage.clickCreateQuote();
		quotePage.searchProduct("lenovo").checkCompareBoxes(1,2,3).selectAction(Action.AddToQuote);;
		
		quotePage = PageFactory.initElements(driver, QuotePage.class);
		
		logger.debug("quoteNumber; " + quotePage.getDocNumber());
		
		PriceCalculator priceCalculator = quotePage.getPriceCalculator();
		
		priceCalculator.setTaxOn(3.25);
		
		assertTrue(priceCalculator.getTaxOn() == 3.25);
		
		assertTrue(priceCalculator.getTaxedSubTotal() + " : " + priceCalculator.getExpectedTaxedSubTotal(), 
					priceCalculator.getTaxedSubTotal() == priceCalculator.getExpectedTaxedSubTotal());
		assertTrue(priceCalculator.getNonTaxableSubTotal() + " : " + priceCalculator.getExpectedNontaxableSubTotal(),
					priceCalculator.getNonTaxableSubTotal() == priceCalculator.getExpectedNontaxableSubTotal());
		assertTrue(priceCalculator.getTotal() + " : " +  priceCalculator.getExpectedTotal(),
					priceCalculator.getTotal() == priceCalculator.getExpectedTotal());
		
		quotePage.clickSave();
	}
	
	private static final String warrantyMfpn = "SYS-2027R-N3RF4+-EW2";
	private static final String warranyMfPnProd = "ACCX020-A1OK";
	
	@Test //#5569
	public void addServiceItem(){
		createQuote();
		QuotePage quotePage = documentPage.goToQuote(quoteNumber);
		quotePage = (QuotePage) quotePage.searchExactProduct(GlobalProperty.isProd?warranyMfPnProd:warrantyMfpn);
		
		quotePage.clickSave();
//		DocumentsPage documnetsPage = quotePage.goToDocumentsPage().switchToTab(DocumentTabs.QUOTES);
		DocumentsPage documnetsPage = quotePage.goToDocumentsPage().switchToTab(DocumentTabs.QUOTES).setFilterByModifiedBy("All");
		assertTrue(documnetsPage.hasQuote(quoteNumber));	
	}
	
	@Test
	public void addTaskInQuote(){
		QuotePage quotePage = goToFirstOpenQuote();

		int oldTaskNum = quotePage.getTasks();
		
		quotePage = (QuotePage) quotePage.addTask("test1", "test description");
		quotePage = (QuotePage) quotePage.clickSave();
		quotePage.refresh();

		int newTaskNum = quotePage.getTasks();
		
		assertTrue(oldTaskNum +1 == newTaskNum); 
	}
	
	@Test
	public void changeDate(){
		
	}
	

	@Test
	public void restoreDeletedQuote(){
		
	}
	
	@Test
	public void liveCost(){
		createQuote();
		
		QuotePage quotePage = documentPage.goToQuote(quoteNumber);
		quotePage = (QuotePage)quotePage.clickLiveCost();
	}
	
	@Test
	public void mergeQuote(){
		createQuote();
		
		QuotePage quotePage = documentPage.goToQuote(quoteNumber);
		double totalOld = quotePage.getPriceCalculator().getTotal();
		
		MergePopup mergePopup = (MergePopup) quotePage.selectFromAddImportUpdate(AddImportUpdates.Merge_into_this_Document);
		mergePopup = mergePopup.selectFromList(DocList.Recent).clickSearch();
		mergePopup.selectOneDoc().clickCopyIntoSelectedQuote();
		
		quotePage = PageFactory.initElements(driver, QuotePage.class);
		
		assertFalse(totalOld + " / " + quotePage.getPriceCalculator().getTotal(), 
				totalOld == quotePage.getPriceCalculator().getTotal());	
	}
	
	@Test
	public void quickAddProduct(){
		createQuote();
		
		QuotePage quotePage = documentPage.goToQuote(quoteNumber);
		QuickAddProductPopup quickAddProductPopup = (QuickAddProductPopup) quotePage.selectFromAddImportUpdate(AddImportUpdates.Quick_Add_Product);
		ProductsPage productPage = quickAddProductPopup.search("ibm");
		productPage.checkCompareBoxes(1).selectAction(Action.AddToQuote);
		
		quotePage = PageFactory.initElements(driver, QuotePage.class);
		
		//verify quick add product displays here.
	}
	
	@Test
	public void createRevisionThenRevert(){
		createQuote();
		
		QuotePage quotePage = documentPage.goToQuote(quoteNumber);
		List<LinkedHashMap<String, String>> productMapBeforeRevision = quotePage.getTableAsMaps();
		quotePage.clickSaveAsNewRevision("test1");
		
		quotePage.searchProduct("ibm").checkCompareBoxes(1,2).selectAction(Action.AddToQuote);
		
		QuotePage quotePageNew =  PageFactory.initElements(driver, QuotePage.class);
		quotePageNew.clickSave();

		quotePageNew.goToRevisionsTab();
		
		assertTrue(quotePageNew.hasRevision("test1"));
		
		quotePageNew = quotePageNew.clickRevert("test1");
		quotePageNew = (QuotePage)quotePageNew.clickSave();
		
		logger.debug("------- get Table After revision -----------");
		List<LinkedHashMap<String, String>> productMapAfterRevision = quotePageNew.getTableAsMaps();
		
		assertTrue(productMapBeforeRevision.equals(productMapAfterRevision));
	}
	
	@Test
	public void createRevisionOnItemdsWithBundleThenRevert(){
		createBundleInQuote();
		
		List<LinkedHashMap<String, String>> productMapBeforeRevision = quotePage.getTableAsMaps();
		quotePage.clickSaveAsNewRevision("test1");

		quotePage.selectProductFromTable(1);
		quotePage = (QuotePage) quotePage.selectFromLineActions(LineActions.Delete);
		
		quotePage.clickSave();
		quotePage = (QuotePage) quotePage.goToRevisionsTab().clickRevert("test1").clickSave();
		
		List<LinkedHashMap<String, String>> productMapAfterRevision = quotePage.getTableAsMaps();
		
		assertTrue(productMapBeforeRevision.equals(productMapAfterRevision));
	}
	
	@Test
	public void companyLinkRedirectsToCurrentAccount(){
		QuotePage quotePage = goToFirstOpenQuote();

		String expectedName = quotePage.getCompanyName();
		CurrentAccountTab currentAccountPage = quotePage.clickCompanyLink();
		String companyName = currentAccountPage.getCompany();
		
		assertTrue(companyName.contains(expectedName));
	}
	
	@Test
	public void originalQuotePreservesLastModifiedAfterCopyToNew(){
		Long quoteNumber = null;
		String timeStamp = "";
		
		QuotePage quotePage = goToFirstDocument(DocumentTabs.QUOTES, DocStatus.Open, false, QuotePage.class);
		quoteNumber = quotePage.getQuoteNumber();
		
		DocumentsPage documentsPage = quotePage.goToDocumentsPage().switchToTab(DocumentTabs.QUOTES).filterByStatus(Status.QUOTES_OPEN);
		List<LinkedHashMap<String, String>> columnMaps = documentsPage.getTableAsMaps(Constants.DOCNUMBER, quoteNumber+"");
		timeStamp = columnMaps.get(0).get(Constants.LASTMODIFIED);
		
		quotePage = documentsPage.goToQuote(quoteNumber);
		QuotePage copiedQuote = quotePage.clickCopyToNewQuote();
		
		copiedQuote.goToDocumentsPage().goToDocumentsPage().switchToTab(DocumentTabs.QUOTES).filterByStatus(Status.QUOTES_OPEN);
		List<LinkedHashMap<String, String>> columnMapsNew  = documentsPage.getTableAsMaps(Constants.DOCNUMBER, quoteNumber.toString());
		
		System.out.println(timeStamp + " : " + columnMapsNew.get(0).get(Constants.LASTMODIFIED));
		assertEquals(timeStamp, columnMapsNew.get(0).get(Constants.LASTMODIFIED));
	}

//	@Test
//	public void deleteHotList(){
//
////		QuotePage quotePage = homePage.goToDocumentsPage().switchToTab(DocumentTabs.QUOTES).goToQuote(1);
////
////		quotePage = (QuotePage) quotePage.clickAddToHotList();
////		
////		QuoteHotListPage quoteHotListPage = quotePage.goToHomePage().navigateToSideBar(Favorites.Quote_Hot_List_Setting, quoteNumber,QuoteHotListPage.class);
//		QuoteHotListPage quoteHotListPage = customersPage.goToHomePage().navigateToSideBar(Favorites.Quote_Hot_List_Setting, quoteNumber,QuoteHotListPage.class);
//
//		quoteHotListPage.deleteQuote(quoteNumber);
//
//		homePage=  PageFactory.initElements(driver, HomePage.class);
//		quoteHotListPage= homePage.navigateToSideBar(Favorites.Quote_Hot_List_Setting, quoteNumber, QuoteHotListPage.class);
//		assertFalse(quoteHotListPage.hasDoc(quoteNumber));
//	}
	
	@Test
	public void deleteQuoteShowsInRecycleBin(){
		createQuote();
		documentPage = documentPage.deleteQuoteByDocNumber(quoteNumber);
		RecycleBinPage recyclePage = documentPage.clickRecycleBin();
		assertTrue(recyclePage.hasDoc(quoteNumber));
	}
	
	@Test
	public void restoreQuoteFromRecycleBin(){
		createQuote();
		documentPage = documentPage.deleteQuoteByDocNumber(quoteNumber);
		RecycleBinPage recyclePage = documentPage.clickRecycleBin();
		recyclePage.restoreByDocNumber(quoteNumber);
		
		assertFalse(recyclePage.hasDoc(quoteNumber));
		
		DocumentsPage documentPage = (DocumentsPage) recyclePage.goToDocumentsPage().switchToTab(DocumentTabs.QUOTES);
		documentPage.hasDoc(quoteNumber);
	}
	
//	@Test
//	public void addSubTotalAndBundleSubTotal_old(){
//		createQuote();
//		QuotePage quotePage = documentPage.goToQuote(quoteNumber);
//
//		PriceCalculator priceCalculator = ((DocumentsBasePage) addSubtotalBundleWorkFlow(quotePage).clickSave()).getPriceCalculator();
//		logger.debug("subTotal: " + priceCalculator.getTaxedSubTotal());
//
//		assertTrue(priceCalculator.getSubtotal() + "", 3200 == priceCalculator.getSubtotal() || 2509.50 == priceCalculator.getSubtotal());
//	}
	
	@Test
	public void addSubTotalAndBundleSubTotal(){
		createQuote();
		QuotePage quotePage = documentPage.goToQuote(quoteNumber);

		PriceCalculator priceCalculator = ((DocumentsBasePage) addSubtotalBundleWorkFlow(quotePage).clickSave()).getPriceCalculator();
		logger.debug("subTotal: " + priceCalculator.getTaxedSubTotal());
		List<LinkedHashMap<String, String>> maps = quotePage.getTableAsMaps();
		
		double taxedSubTotalWithoutBundle = 0;
		double taxedSubTotal = 0;

		double bundleSubTotalActual = 0;
		double bundleSubTotalExepcted = 0;
		double bundleSubTotalSinglePriceActual = 0;
		
		boolean isInBundle = false;
		
		for(LinkedHashMap<String, String> map: maps){
				
			if(!isInBundle && map.get("description").contains("Bundle Header")) {
				isInBundle = true;
			}
			
			else if(isInBundle && map.get("description").contains("Bundle Subtotal")){
				bundleSubTotalActual = Double.parseDouble(StringUtil.cleanCurrency(map.get("total")));
				bundleSubTotalSinglePriceActual = Double.parseDouble(map.get("price"));
				System.out.println("bunel subtotalActual: " + bundleSubTotalActual);
				isInBundle = false;
			}
			
			else if(isInBundle){
				bundleSubTotalExepcted += Integer.parseInt(map.get("qty")) * Double.parseDouble(map.get("price"));
				System.out.println("is in bundle condition." + bundleSubTotalExepcted);
			}
			
			else if (!map.get("qty").isEmpty() && !isInBundle){
				taxedSubTotalWithoutBundle += Integer.parseInt(map.get("qty")) * Double.parseDouble(map.get("price"));
				System.out.println("last condition: "+ taxedSubTotalWithoutBundle);
			}
		}
		
		taxedSubTotal = taxedSubTotalWithoutBundle + bundleSubTotalActual;
		
				
		System.out.println("calculated taxedsubtotal is " + taxedSubTotal);

		assertTrue(priceCalculator.getSubtotal() + " : " + taxedSubTotal, 
				priceCalculator.getSubtotal()==taxedSubTotal);
		
		assertTrue(bundleSubTotalSinglePriceActual + ": " + bundleSubTotalExepcted, 
				bundleSubTotalSinglePriceActual == bundleSubTotalExepcted);
	}
	
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
