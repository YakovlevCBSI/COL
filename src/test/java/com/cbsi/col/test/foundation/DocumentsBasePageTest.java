package com.cbsi.col.test.foundation;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.customers.AccountsPage;
import com.cbsi.col.pageobject.customers.AllAccountsTab;
import com.cbsi.col.pageobject.customers.CurrentAccountTab;
import com.cbsi.col.pageobject.customers.RecentAccountsTab;
import com.cbsi.col.pageobject.customers.AccountsPage.AccountType;
import com.cbsi.col.pageobject.documents.DocumentsBasePage;
import com.cbsi.col.pageobject.documents.DocumentsPage;
import com.cbsi.col.pageobject.documents.OrderOptionsPage;
import com.cbsi.col.pageobject.documents.QuotePage;
import com.cbsi.col.pageobject.documents.SalesOrderPage;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.PriceCalculator.ShippingTypes;
import com.cbsi.col.pageobject.documents.DocumentsPage.DocumentTabs;
import com.cbsi.col.pageobject.home.SearchPopup.QueryColumn;
import com.cbsi.col.pageobject.home.SearchPopup.QueryOption;
import com.cbsi.col.pageobject.products.ProductsPage;
import com.cbsi.col.pageobject.products.ProductsPage.Action;

public class DocumentsBasePageTest extends ColBaseTest{
	
	public RecentAccountsTab recentCustomersPage;
	public DocumentsPage documentPage;
	
	protected long quoteNumber;
	protected long orderNumber;
	protected long invoiceNumber;
	
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
		createQuote(AccountType.CUSTOMER);
	}
	
	public void createQuote(AccountType type){
		CurrentAccountTab currentAccountPage = null;
		try{
//			currentAccountPage=  customersPage.goToAllAcountsTab().setFilterByAccountType(type).clickViewCustomer("Qa_");  //If no qa customer exists, create one.
			currentAccountPage=  customersPage.goToRecentAccountsTab().clickViewCustomer("Qa_");  //If no qa customer exists, create one.

//			AllAccountsTab accountPage= customersPage.goToAllAcountsTab().searchFor(QueryOption.Customers, false, QueryColumn.All, "Qa_customer_", AllAccountsTab.class);
//			currentAccountPage = accountPage.clickViewCustomer("Qa_customer_");
		}catch(NullPointerException e){
			recentCustomersPage = createAccount(type);
//			AllAccountsTab  accountPage = recentCustomersPage.goToAllAcountsTab().searchFor(QueryOption.Customers, false, QueryColumn.All, "Qa_customer_", AllAccountsTab.class);
			currentAccountPage = recentCustomersPage.clickViewCustomer("Qa_customer_");
			
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
	
//	public void convertToSalesOrder(){
//		createQuote();
//		QuotePage quotePage = documentPage.goToQuote(quoteNumber);
//		
//		SalesOrderPage orderPageAdress = quotePage.clickConvertToOrder();
//		orderPageAdress.setFirstName("Quality");
//		orderPageAdress.setLastName("Assurance");
//		orderPageAdress.setEmail("shefali.ayachit@cbsi.com");
//		orderPageAdress.setAddress(address);
//		orderPageAdress.setCity(city);
//		orderPageAdress.setZip(zip);
//		
//		SalesOrderPage salesOrderPagePayment = orderPageAdress.clickSave();
//		SalesOrderPage salesOrderPage = salesOrderPagePayment.setPoNumberAndPaymentMethod(123, Payment.MoneyOrder).clickSave();
//		orderNumber = salesOrderPage.getDocNumber();
//		
//		documentPage = salesOrderPage.goToHomePage().goToDocumentsPage().switchToTab(DocumentTabs.ORDERS);
//		assertTrue(documentPage.hasSalesOrder(orderNumber));
//	}
	
	/**
	 * workaround added due to convert order bug.
	 * Once the fix works, delete this and uncomment above method.
	 */
	public void convertToSalesOrderOnly(){
		convertToSalesOrder(false);
	}
	
	public void convertToSalesOrder(){
		convertToSalesOrder(true);
	}
	public void convertToSalesOrder(boolean createQuote){
		convertToSalesOrder(createQuote, AccountType.CUSTOMER);
	}
	
	public void convertToSalesOrder(boolean createQuote, AccountType type){
		if(createQuote){
			createQuote(type);
		}
		
		QuotePage quotePage = documentPage.goToQuote(quoteNumber);
		quotePage.getPriceCalculator().setShipingType(ShippingTypes.Manual);
		quotePage = (QuotePage) quotePage.clickSave();
		
		SalesOrderPage salesOrderPage = null;
		boolean convertOrderSuccess = false;
		int retry=1;
		while(convertOrderSuccess == false){
			SalesOrderPage orderPageAdress = null;
			try{
				orderPageAdress = quotePage.clickConvertToOrder();
				orderPageAdress.setFirstName("Quality");
				orderPageAdress.setLastName("Assurance");
				orderPageAdress.setEmail("shefali.ayachit@cbsi.com");
				orderPageAdress.setAddress(address);
				orderPageAdress.setCity(city);
				orderPageAdress.setZip(zip);
				orderPageAdress.clickCopyToShipping();
				salesOrderPage = orderPageAdress.clickSave();

			}catch(NoSuchElementException e){
				salesOrderPage = PageFactory.initElements(driver, SalesOrderPage.class);
			}catch(Exception e){
				e.printStackTrace();
	
				System.out.println("cannot focus on element. Moving on...");
				System.out.println("----------------------------------------");
				
			}
//			try{
//				salesOrderPage = salesOrderPage.setPoNumberAndPaymentMethod(123, Payment.MoneyOrder);
//				salesOrderPage = salesOrderPage.clickSave();
//				convertOrderSuccess = true;
//			}catch(NullPointerException e){
//				e.printStackTrace();
//				System.out.println("convert order failed... retry "+ retry);
//				retry ++;
//				 quotePage = PageFactory.initElements(driver, QuotePage.class);
//			}
			try{
				OrderOptionsPage orderOptionsPage = PageFactory.initElements(driver, OrderOptionsPage.class);
				orderOptionsPage = orderOptionsPage.setPoNumberAndPaymentMethod(123, com.cbsi.col.pageobject.documents.OrderOptionsPage.Payment.MoneyOrder);
				salesOrderPage = (SalesOrderPage) orderOptionsPage.clickSave(SalesOrderPage.class);
				convertOrderSuccess = true;
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("convert order failed... retry "+ retry);
				retry ++;
				 quotePage = PageFactory.initElements(driver, QuotePage.class);
			}
		}
		orderNumber = salesOrderPage.getDocNumber();
		
		documentPage = salesOrderPage.goToHomePage().goToDocumentsPage().switchToTab(DocumentTabs.ORDERS);
		assertTrue(documentPage.hasSalesOrder(orderNumber));
	}
}
