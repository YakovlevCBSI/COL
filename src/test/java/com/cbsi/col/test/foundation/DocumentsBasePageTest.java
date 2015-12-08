package com.cbsi.col.test.foundation;

import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Before;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cbsi.col.pageobject.customers.AccountsPage;
import com.cbsi.col.pageobject.customers.AllAccountsTab;
import com.cbsi.col.pageobject.customers.CurrentAccountTab;
import com.cbsi.col.pageobject.customers.RecentAccountsTab;
import com.cbsi.col.pageobject.customers.AccountsPage.AccountType;
import com.cbsi.col.pageobject.documents.AddressPage;
import com.cbsi.col.pageobject.documents.DocumentsBasePage;
import com.cbsi.col.pageobject.documents.DocumentsPage;
import com.cbsi.col.pageobject.documents.OrderOptionsPage;
import com.cbsi.col.pageobject.documents.QuotePage;
import com.cbsi.col.pageobject.documents.SalesOrderPage;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.LineActions;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.PriceCalculator.ShippingTypes;
import com.cbsi.col.pageobject.documents.DocumentsPage.DocumentTabs;
import com.cbsi.col.pageobject.home.SearchPopup.QueryColumn;
import com.cbsi.col.pageobject.home.SearchPopup.QueryOption;
import com.cbsi.col.pageobject.products.ProductsPage;
import com.cbsi.col.pageobject.products.ProductsPage.Action;

public class DocumentsBasePageTest extends ColBaseTest{
	public final Logger logger = LoggerFactory.getLogger(DocumentsBasePage.class);

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
		createQuote(type, true);
	}
	
	public void createQuote(AccountType type, boolean goToDocumentsPage){
		CurrentAccountTab currentAccountPage = null;
		try{
//			currentAccountPage=  customersPage.goToAllAcountsTab().setFilterByAccountType(type).clickViewCustomer("Qa_");  //If no qa customer exists, create one.
			currentAccountPage=  customersPage.goToRecentAccountsTab().clickViewCustomer(companyNameCommon);  //If no qa customer exists, create one.

//			AllAccountsTab accountPage= customersPage.goToAllAcountsTab().searchFor(QueryOption.Customers, false, QueryColumn.All, "Qa_customer_", AllAccountsTab.class);
//			currentAccountPage = accountPage.clickViewCustomer("Qa_customer_");
		}catch(NullPointerException e){
			recentCustomersPage = createAccount(type);
//			AllAccountsTab  accountPage = recentCustomersPage.goToAllAcountsTab().searchFor(QueryOption.Customers, false, QueryColumn.All, "Qa_customer_", AllAccountsTab.class);
			currentAccountPage = recentCustomersPage.clickViewCustomer(companyNameCommon);
			
		}
		
		QuotePage quotePage = currentAccountPage.clickCreateQuote();
		quoteNumber = quotePage.getQuoteNumber();
		logger.info("Looking for quote#:  " + quoteNumber );
		ProductsPage productPage = quotePage.searchProduct("Lenovo");
		productPage.checkCompareBoxes(1,2,3);
		productPage.selectAction(Action.AddToQuote);
		
		QuotePage quotePageNew =  PageFactory.initElements(driver, QuotePage.class);
		quotePageNew.clickSave();
		
		if(goToDocumentsPage){
//			documentPage = quotePageNew.goToDocumentsPage().switchToTab(DocumentTabs.QUOTES);		
			documentPage = quotePageNew.goToDocumentsPage().switchToTab(DocumentTabs.QUOTES).setFilterByModifiedBy("All");		

			
			assertTrue("didnt find quote #" + quoteNumber, documentPage.hasQuote(quoteNumber));
		}
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
		QuotePage quotePage = null;
		
		if(createQuote){
			createQuote(type);
			quotePage = documentPage.goToQuote(quoteNumber);

		}else{
			long docNumber = 0;
			List<LinkedHashMap<String, String>> tableMaps = documentPage.getTableAsMaps();
			for(LinkedHashMap<String, String> tableMap: tableMaps){
				if(tableMap.get("status").equals("Open")){
					docNumber = Long.parseLong(tableMap.get("doc#"));
					break;
				}
			}
			
			quotePage = documentPage.goToQuote(docNumber);
		}
		
		quotePage.getPriceCalculator().setShipingType(ShippingTypes.Manual);
		
		quotePage = (QuotePage) quotePage.clickSave();
		
		quotePage.forceWait(1000);
		SalesOrderPage salesOrderPage = null;
		OrderOptionsPage orderOptionsPage = null;
		boolean convertOrderSuccess = false;
		int retry=1;
		while(!convertOrderSuccess){
			AddressPage orderPageAdress = null;

			try{
				orderPageAdress = quotePage.clickConvertToOrder();

				orderPageAdress.setFirstName("Quality");
				orderPageAdress.setLastName("Assurance");
				orderPageAdress.setEmail("shefali.ayachit@cbsi.com");
				orderPageAdress.setAddress(address);
				orderPageAdress.setCity(city);
				orderPageAdress.setZip(zip);
				orderPageAdress.clickCopyToShipping();
				orderOptionsPage = orderPageAdress.clickSave();

			}catch(NoSuchElementException e){
				salesOrderPage = PageFactory.initElements(driver, SalesOrderPage.class);
			}catch(Exception e){
//				logger.debug(e.printStackTrace());
	
				logger.info("cannot focus on element. Moving on...");
				logger.info("----------------------------------------");
				
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
				orderOptionsPage = PageFactory.initElements(driver, OrderOptionsPage.class);
				if(retry ==1){
					orderOptionsPage = orderOptionsPage.setPoNumberAndPaymentMethod(123, com.cbsi.col.pageobject.documents.OrderOptionsPage.Payment.Terms);
				}else {
					orderOptionsPage = orderOptionsPage.setPaymentMethod(com.cbsi.col.pageobject.documents.OrderOptionsPage.Payment.COD);

				}
				Thread.sleep(2000);
				salesOrderPage = (SalesOrderPage) orderOptionsPage.clickSave(SalesOrderPage.class);
				convertOrderSuccess = true;
			}catch(Exception e){
//				e.printStackTrace();
				logger.warn("convert order failed... retry "+ retry);
				retry ++;
				if(retry > 4){
					throw new NullPointerException("Convert To Order failed after  4 attempts");
				}
				 quotePage = PageFactory.initElements(driver, QuotePage.class);
			}
		}
		orderNumber = salesOrderPage.getDocNumber();
		
		documentPage = salesOrderPage.goToHomePage().goToDocumentsPage().switchToTab(DocumentTabs.ORDERS);
		assertTrue(documentPage.hasSalesOrder(orderNumber));
	}
	
	public DocumentsBasePage addSubtotalBundleWorkFlow(DocumentsBasePage quotePagePass){
		DocumentsBasePage quotePage = (DocumentsBasePage) ((DocumentsBasePage) quotePagePass.selectProductFromTable(1,2,3)).selectFromLineActions(LineActions.Delete);
		
//		logger.debug("Wait 5000 mili.........................");
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		ProductsPage productPage =quotePage.goToProductsPage();
		try{
			productPage.goToLinkText("System & Power Cables");
		}catch(Exception e){
			productPage.goToLinkText("Systems");
			productPage.goToLinkText("System & Power Cables");
		}
		productPage.checkCompareBoxes(1,2,3, 5).selectAction(Action.AddToQuote);
		
		quotePage = PageFactory.initElements(driver, quotePagePass.getClass());
		
		quotePage = (DocumentsBasePage) ((DocumentsBasePage) quotePage.selectProductFromTable(1)).selectFromLineActions(LineActions.Add_Subtotal);
		quotePage = (DocumentsBasePage) ((DocumentsBasePage) quotePage.selectProductFromTable(3, 4)).selectFromLineActions(LineActions.Convert_to_Bundle);
		
		DocumentsBasePage quotePageNew = (DocumentsBasePage) ((DocumentsBasePage) quotePage.selectProductFromTable(7)).selectFromLineActions(LineActions.Add_Subtotal);
		ProductsPage productPageNew =quotePageNew.goToProductsPage();
		try{
			productPage.goToLinkText("System & Power Cables");
		}catch(Exception e){
			productPage.goToLinkText("Systems");
			productPage.goToLinkText("System & Power Cables");
		}
		productPage.checkCompareBoxes(7).selectAction(Action.AddToQuote);
		quotePageNew = PageFactory.initElements(driver, quotePagePass.getClass());
		
		quotePageNew.setQtyInTable(1, 1);
		quotePageNew.setQtyInTable(4, 2);
		quotePageNew.setQtyInTable(5, 3);
		quotePageNew.setQtyInTable(6, 4);
		quotePageNew.setQtyInTable(7, 5);
		quotePageNew.setQtyInTable(9, 6);
		
		return quotePageNew;
	}
}
