package com.cbsi.col.pageobject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.test.pageobject.customers.CustomersPage;

public class HomePage extends ColBasePage{
	public HomePage(WebDriver driver){
		super(driver);
		waitForPageToLoad(By.cssSelector("#tab-home"));
	}
	
	@CacheLookup
	@FindBy(css="#crm-controlpanesectionlink-admin")
	private WebElement Admin;
	
	@CacheLookup
	@FindBy(css="#crm-controlpanesectionlink-recent")
	private WebElement Recent;
	
	@CacheLookup
	@FindBy(css="#crm-controlpanesectionlink-favorites")
	private WebElement Favorites;
	
	@FindBy(css="#crm-controlpaneaccordionlink-customers")
	private WebElement RecentCustomers;
	
	public CustomersPage goToRecentCustomer(String customerName){
		Recent.click();
		RecentCustomers.click();
		
		WebElement customerNameFound= null;
		List<WebElement> recentCustomers = driver.findElements(By.cssSelector("li a[target id^= 'crm-contronpanellink'] span"));
		for(WebElement recentCustomer: recentCustomers){
			if(recentCustomer.getText().contains(customerName)){
				customerNameFound = recentCustomer.findElement(By.xpath("../"));
				break;
			}
		}
		
		customerNameFound.click();
	
		return PageFactory.initElements(driver, CustomersPage.class);
	}

	//------------------------------- access tabs-------------------------------//
	
	@FindBy(css="a#tab-customers")
	private WebElement Customers;	
	
	@FindBy(css="a#tab-products")
	private WebElement Products;
	
	@FindBy(css="a#tab-services")
	private WebElement Services;
	
	@FindBy(css="a#tab-docs")
	private WebElement Documents;
	
	@FindBy(css="a#tab-supplier")
	private WebElement Suppliers;
	
	@FindBy(css="a#tab-po")
	private WebElement PurhcaseOrders;
	
	public  CustomersPage goToCustomersPage(){
		Customers.click();
		return PageFactory.initElements(driver, CustomersPage.class);
	}

	public ProductsPage goToProductsPage(){
		Products.click();
		return PageFactory.initElements(driver, ProductsPage.class);
	}
	
	public ServicesPage goToServicesPage(){
		Products.click();
		return PageFactory.initElements(driver, ServicesPage.class);
	}
	
	public DocumentsPage goToDocumentsPage(){
		Products.click();
		return PageFactory.initElements(driver, DocumentsPage.class);
	}
	
	public SuppliersPage goToSuppliersPage(){
		Products.click();
		return PageFactory.initElements(driver, SuppliersPage.class);
	}
	
	public PurchaseOrdersPage gotoPurchaseOrdersPage(){
		Products.click();
		return PageFactory.initElements(driver, PurchaseOrdersPage.class);
	}
	
}
