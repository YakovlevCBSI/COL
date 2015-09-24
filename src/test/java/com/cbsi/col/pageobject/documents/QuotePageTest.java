package com.cbsi.col.pageobject.documents;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.customers.AccountsPage;
import com.cbsi.col.pageobject.customers.CurrentAccountTab;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.MergePopup;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.MergePopup.DocList;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.QuickAddProductPopup;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.SendPage;
import com.cbsi.col.pageobject.documents.QuotePage;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.AddImportUpdates;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.LineActions;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.PriceCalculator;
import com.cbsi.col.pageobject.documents.DocumentsPage.DocumentTabs;
import com.cbsi.col.pageobject.products.ProductsPage;
import com.cbsi.col.pageobject.products.ProductsPage.Action;
import com.cbsi.col.test.foundation.DocumentsBasePageTest;


public class QuotePageTest extends DocumentsBasePageTest{

	public QuotePageTest(String url, String browser) {
		super(url, browser);
		// TODO Auto-generated constructor stub
	}
	
	@Test
	public void createQuote(){
		super.createQuote();
	}
	
	@Test
	public void createBundleInQuote(){
		createQuote();
		QuotePage quotePage = documentPage.goToQuote(quoteNumber);
		quotePage.selectProductFromTable(1);
		quotePage = (QuotePage)quotePage.selectFromLineActions(LineActions.Convert_to_Bundle);
		quotePage.setBundleHeader("test1");
		quotePage.setBundleDesc("test description");
		quotePage = (QuotePage)quotePage.clickSaveBundle();
		
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
	public void productComparisonInQuote(){
		createQuote();
		
		QuotePage quotePage = documentPage.goToQuote(quoteNumber);
		quotePage = (QuotePage) quotePage.selectProductFromTable(1,2);
		ComparisonPage comPage = (ComparisonPage)quotePage.selectFromLineActions(LineActions.Compare);
	}
	
	@Test
	public void quantityChangeUpdatesPrice(){
		createQuote();
		
		QuotePage quotePage = documentPage.goToQuote(quoteNumber);
		quotePage.setQtyInTable(1, 5);
		double unitPriceFirstRow = quotePage.getUnitPriceFromTable(1);

		assertTrue("expected: " +  unitPriceFirstRow * 5 + " / actual: " + quotePage.getTotalPriceFromTable(1),
				unitPriceFirstRow * 5 == quotePage.getTotalPriceFromTable(1));
	}
	
	@Test
	public void priceCalculatorSum() throws InterruptedException{
		CurrentAccountTab currentAccountPage=  customersPage.clickViewCustomer("Qa_");
		
		QuotePage quotePage = currentAccountPage.clickCreateQuote();
		quotePage = quotePage.searchProduct("lenovo").checkCompareBoxes(1,2,3).selectAction(Action.AddToQuote);;
		
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
	
	private String warrantyMfpn = "SYS-2027R-N3RF4+-EW2";
	@Test //#5569
	public void addServiceItem(){
		createQuote();
		QuotePage quotePage = documentPage.goToQuote(quoteNumber);
		quotePage = (QuotePage) quotePage.searchExactProduct(warrantyMfpn);
		
		quotePage.clickSave();
		DocumentsPage documnetsPage = quotePage.goToDocumentsPage().switchToTab(DocumentTabs.QUOTES);
		assertTrue(documnetsPage.hasQuote(quoteNumber));	
	}
	
	@Test
	public void addTaskInQuote(){
//		createQuote();
//		QuotePage quotePage = documentPage.goToQuote(quoteNumber);
		
		QuotePage quotePage = homePage.goToDocumentsPage().switchToTab(DocumentTabs.QUOTES).goToQuote(1);
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
		quotePage = productPage.checkCompareBoxes(1).selectAction(Action.AddToQuote);
		
		//verify quick add product displays here.
	}
	
	@Test
	public void createRevisionThenRevert(){
		createQuote();
		
		QuotePage quotePage = documentPage.goToQuote(quoteNumber);
		quotePage.clickSaveAsNewRevision("test1");
		quotePage.goToRevisionsTab();
		
		assertTrue(quotePage.hasRevision("test1"));
		
		quotePage = quotePage.clickRevert("test1");
		quotePage = (QuotePage)quotePage.clickSave();
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
}
