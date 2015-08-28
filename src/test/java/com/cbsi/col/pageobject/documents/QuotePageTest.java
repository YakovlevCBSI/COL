package com.cbsi.col.pageobject.documents;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.customers.AccountsPage;
import com.cbsi.col.pageobject.customers.CurrentAccountTab;
import com.cbsi.col.pageobject.customers.RecentAccountsTab;
import com.cbsi.col.pageobject.documents.DocumentsPage;
import com.cbsi.col.pageobject.documents.QuotePage;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.PriceCalculator;
import com.cbsi.col.pageobject.documents.SalesOrderPage;
import com.cbsi.col.pageobject.documents.DocumentsPage.DocumentTabs;
import com.cbsi.col.pageobject.documents.RMAPage.Reasons;
import com.cbsi.col.pageobject.documents.SalesOrderPage.Doc;
import com.cbsi.col.pageobject.documents.SalesOrderPage.Payment;
import com.cbsi.col.pageobject.home.ProductsPage;
import com.cbsi.col.pageobject.home.ProductsPage.Action;
import com.cbsi.col.test.foundation.ColBaseTest;

public class QuotePageTest extends ColBaseTest{

	public RecentAccountsTab recentCustomersPage;
	public DocumentsPage documentPage;
	
	private int quoteNumber;
	private int orderNumber;
	private int invoiceNumber;
	
	public QuotePageTest(String url, String browser) {
		super(url, browser);
		// TODO Auto-generated constructor stub
	}
	@Before
	public void startUp(){
		super.startUp();
		navigateToCustomersPage();
		recentCustomersPage = createAccount();
	}
	
	@After
	public void cleanUp(){
		takeScreenshot();
		super.cleanUp();
		super.startUp();
		navigateToCustomersPage();
		customersPage.goToRecentAccountsTab().deleteCompany(companyName);
	}
	
	@Test
	public void createQuote(){
		CurrentAccountTab currentAccountPage=  recentCustomersPage.clickViewCustomer(companyName);
		
		QuotePage quotePage = currentAccountPage.clickCreateQuote();
		quoteNumber = quotePage.getQuoteNumber();
		System.out.println("Looking for quote#:  " + quoteNumber );
		ProductsPage productPage = quotePage.searchProduct("Lenovo");
		productPage.checkCompareBoxes(1,2,3);
		
		QuotePage quotePageNew = productPage.selectAction(Action.AddToQuote);
		quotePageNew.clickSave();
		
		documentPage = quotePageNew.goToDocumentsPage().switchToTab(DocumentTabs.QUOTES);		
		
		assertTrue("didnt find quote #" + quoteNumber, documentPage.hasQuote(quoteNumber));
	}
	
	@Test
	public void copyToNewQuote(){
		createQuote();
		QuotePage quotePage = documentPage.goToQuote(quoteNumber);
		int oldQuote = quotePage.getQuoteNumber();
		
		QuotePage quotePageNew = quotePage.clickCopyToNewQuote();
		int newQuote = quotePageNew.getQuoteNumber();
		
		assertFalse(oldQuote + " : " + newQuote, oldQuote == newQuote);
		
		
	}
	
	@Test
	public void convertToSalesOrder(){
		createQuote();
		QuotePage quotePage = documentPage.goToQuote(quoteNumber);
		
		SalesOrderPage orderPageAdress = quotePage.clickConvertToOrder();
		orderPageAdress.setFirstName("Quality");
		orderPageAdress.setLastName("Assurance");
		orderPageAdress.setEmail("shefali.ayachit@cbsi.com");
		orderPageAdress.setAddress(address);
		orderPageAdress.setCity(city);
		orderPageAdress.setZip(zip);
		
		SalesOrderPage salesOrderPagePayment = orderPageAdress.clickSave();
		SalesOrderPage salesOrderPage = salesOrderPagePayment.setPoNumberAndPaymentMethod(123, Payment.MoneyOrder).clickSave();
		orderNumber = salesOrderPage.getDocNumber();
		
		documentPage = salesOrderPage.goToHomePage().goToDocumentsPage().switchToTab(DocumentTabs.ORDERS);
		assertTrue(documentPage.hasSalesOrder(orderNumber));
		
	}
	
	@Test
	public void convertToInvoice(){
		convertToSalesOrder();
//		DocumentsPage recentDocumentsPage = PageFactory.initElements(driver, DocumentsPage.class);
		SalesOrderPage salesOrderPage = documentPage.goToOrder(orderNumber);
		InvoicePage invoicePage = salesOrderPage.clickConvertToInvoice();
		invoiceNumber = invoicePage.getInvoiceNumber();
		invoicePage.clickSave();
		documentPage = invoicePage.goToDocumentsPage().switchToTab(DocumentTabs.INVOICES);
		assertTrue(documentPage.hasInvoice(invoiceNumber));
	}
	
	@Test
	public void createRmaFromSalesOrder(){
		int rmaNumber;
		convertToSalesOrder();
		SalesOrderPage salesOrderPage = documentPage.goToOrder(orderNumber);
		RMAPage rmaPage = ((SalesOrderPage) salesOrderPage.selectProductFromTable(1)).selectCreateDoc(Doc.CreateRMA);
		
		rmaNumber = rmaPage.getDocNumber();
		
		rmaPage.selectReasonForReturn(Reasons.Dead_On_Arrival);
		rmaPage.clickSave();
		
		documentPage = rmaPage.goToDocumentsPage().switchToTab(DocumentTabs.RMAS);
		assertTrue(documentPage.hasRma(rmaNumber));	
	}
	
	@Test
	public void createPurchaseOrder(){
		int docNumber;
		convertToSalesOrder();
		
		SalesOrderPage salesOrderPage = documentPage.goToOrder(orderNumber);
		PurchaseOrderPage purchaseOrderPage = ((SalesOrderPage) salesOrderPage.selectProductFromTable(1)).selectCreateDoc(Doc.CreatePO);

	}

	@Test
	public void priceCalculatorSum() throws InterruptedException{
		CurrentAccountTab currentAccountPage=  recentCustomersPage.clickViewCustomer(companyName);
		
		QuotePage quotePage = currentAccountPage.clickCreateQuote();
		quotePage = quotePage.searchProduct("lenovo").checkCompareBoxes(1,2,3).selectAction(Action.AddToQuote);;
		
		PriceCalculator priceCalculator = quotePage.getPriceCalculator();
		
		priceCalculator.setTaxOn(3.25);
		
		assertTrue(priceCalculator.getTaxOn() == 3.25);
		
		System.out.println("WAITING FOR FINAL>..");
		Thread.sleep(20000);
		
		assertTrue(priceCalculator.getTaxedSubTotal() + " : " + priceCalculator.getExpectedTaxedSubTotal(), 
					priceCalculator.getTaxedSubTotal() == priceCalculator.getExpectedTaxedSubTotal());
		assertTrue(priceCalculator.getNonTaxableSubTotal() + " : " + priceCalculator.getExpectedNontaxableSubTotal(),
					priceCalculator.getNonTaxableSubTotal() == priceCalculator.getExpectedNontaxableSubTotal());
		assertTrue(priceCalculator.getTotal() + " : " +  priceCalculator.getExpectedTotal(),
					priceCalculator.getTotal() == priceCalculator.getExpectedTotal());
		
		quotePage.clickSave();
	}
	

	
//	@Test
//	public void cleanUpCompanies(){
//		recentCustomersPage = homePage.goToAccountsPage().goToRecentCustomersTab();
//		AccountsPage customersPage1 = null;
//		while(true){
//			customersPage1 = recentCustomersPage.deleteCompany("Qa");
//			recentCustomersPage= customersPage1.goToAccountsPage().goToRecentAccountsTab();
//			
//		}
//	}
	
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
