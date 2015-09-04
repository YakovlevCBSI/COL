package com.cbsi.col.pageobject.documents;

import static org.junit.Assert.assertTrue;

import org.junit.Before;

import com.cbsi.col.pageobject.customers.CurrentAccountTab;
import com.cbsi.col.pageobject.customers.RecentAccountsTab;
import com.cbsi.col.pageobject.customers.AccountsPage.AccountType;
import com.cbsi.col.pageobject.documents.DocumentsPage.DocumentTabs;
import com.cbsi.col.pageobject.documents.SalesOrderPage.Payment;
import com.cbsi.col.pageobject.home.ProductsPage;
import com.cbsi.col.pageobject.home.ProductsPage.Action;
import com.cbsi.col.test.foundation.ColBaseTest;

public class DocumentsBasePageTest extends ColBaseTest{
	
	public RecentAccountsTab recentCustomersPage;
	public DocumentsPage documentPage;
	
	protected int quoteNumber;
	protected int orderNumber;
	protected int invoiceNumber;
	
	public DocumentsBasePageTest(String url, String browser) {
		super(url, browser);
		// TODO Auto-generated constructor stub
	}

	@Before
	public void startUp(){
		super.startUp();
		navigateToCustomersPage();
//		recentCustomersPage = createAccount();
//		recentCustomersPage = pickRandomAccount();
	}
	
	public void createQuote(){
		CurrentAccountTab currentAccountPage = null;
		try{
			currentAccountPage=  customersPage.clickViewCustomer("Qa_");  //If no qa customer exists, create one.
		}catch(NullPointerException e){
			recentCustomersPage = createAccount(AccountType.CUSTOMER);
			currentAccountPage = recentCustomersPage.clickViewCustomer("Qa_");
			
		}
		
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
}
