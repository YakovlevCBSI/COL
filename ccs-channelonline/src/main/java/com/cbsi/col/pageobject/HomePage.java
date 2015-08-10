package com.cbsi.col.pageobject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.customers.AccountsPage;
import com.cbsi.col.pageobject.documents.DocumentsPage;

public class HomePage extends ColBasePage{
	public HomePage(WebDriver driver){
		super(driver);
		waitForPageToLoad(By.cssSelector("#tab-home"));
		waitForTextToBeVisible("My Channel", "a");
	}
	
	@CacheLookup
	@FindBy(css="a#crm-controlpanesectionlink-admin")
	private WebElement Admin;
	
	@CacheLookup
	@FindBy(css="#crm-controlpanesectionlink-recent")
	private WebElement Recent;
	
	@CacheLookup
	@FindBy(css="#crm-controlpanesectionlink-favorites")
	private WebElement Favorites;
	
	@FindBy(css="#crm-controlpaneaccordionlink-customers")
	private WebElement RecentCustomers;
	
	public AccountsPage goToRecentCustomer(String customerName){
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
	
		return PageFactory.initElements(driver, AccountsPage.class);
	}

	//------------------------------- access tabs-------------------------------//
	
	@FindBy(css="a#tab-customers")
	private WebElement Accounts;	
	
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
	
	public  AccountsPage goToAccountsPage(){
		Accounts.click();
		return PageFactory.initElements(driver, AccountsPage.class);
	}

	public ProductsPage goToProductsPage(){
		Products.click();
		return PageFactory.initElements(driver, ProductsPage.class);
	}
	
	public ServicesPage goToServicesPage(){
		Services.click();
		return PageFactory.initElements(driver, ServicesPage.class);
	}
	
	public DocumentsPage goToDocumentsPage(){
		Documents.click();
		return PageFactory.initElements(driver, DocumentsPage.class);
	}
	
	public SuppliersPage goToSuppliersPage(){
		Suppliers.click();
		return PageFactory.initElements(driver, SuppliersPage.class);
	}
	
	public PurchaseOrdersPage goToPurchaseOrdersPage(){
		PurhcaseOrders.click();
		return PageFactory.initElements(driver, PurchaseOrdersPage.class);
	}
	
	//********************** side bar ********************//
	
	@SuppressWarnings("unchecked")
	public <T> T  navigateToSideBar(Admin page, Class<?> clazz){
		if(!Admin.findElement(By.xpath("../../div[2]")).getAttribute("class").contains("in")) 
			Admin.click();

		By by = null;
		by = getLinkText(page.toString());		
		driver.findElement(by).click();
		return (T) PageFactory.initElements(driver, clazz);
	}
	
	public enum Admin{
		Account_Services,
		Catalog_Admin,
		Company_Settings,
		CPAS_Settings,
		Document_Templates,
		Favorites_Admin,
		Image_Gallery,
		Import_Export,
		Integration,
		Items_Admin,
		Marketing,
		Payment_Options,
		Personnel,
		Price_Profiles,
		PunchOut_Settings,
		StoreSite_Admin,
		Suppliers,
		System_Emails,
		Tax_Profile
	}
	
	public By getLinkText(String text){
		return By.linkText(text.replace("_", " "));
	}

}
