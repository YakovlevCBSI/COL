package com.cbsi.col.pageobject.home;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.customers.AccountsPage;
import com.cbsi.col.pageobject.documents.DocumentsPage;
import com.cbsi.col.pageobject.purchaseorders.PurchaseOrdersTab;
import com.cbsi.col.pageobject.suppliers.SuppliersPage;

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
	
	@FindBy(css=".crm-tab-bar a#tab-customers")
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
	
	@FindBy(css="li#li-customer-view a")
	private WebElement CustomerView;
	
	@FindBy(css="li#li-document-active a")
	private WebElement DocumentActive;
	
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
	
	public PurchaseOrdersTab goToPurchaseOrdersPage(){
		PurhcaseOrders.click();
		return PageFactory.initElements(driver, PurchaseOrdersTab.class);
	}
	
	public boolean IsAccountsTabDisplayed(){
		return Accounts.isDisplayed();
	}
	
	public boolean IsProductsTabDisplayed(){
		return Products.isDisplayed();
	}
	
	public boolean IsServicesTabDisplayed(){
		return Services.isDisplayed();
	}
	
	public boolean IsDocumentsTabDisplayed(){
		return Documents.isDisplayed();
	}
	
	public boolean IsSuppliersTabDisplayed(){
		return Suppliers.isDisplayed();
	}
	
	public boolean IsPurchaseOrdersTabDisplayed(){
		return PurhcaseOrders.isDisplayed();
	}
	
	public boolean IsCustomerViewTabDisplayed(){
		return CustomerView.isDisplayed();
	}
	
	public boolean isDocumentActiveTabDisplayed(){
		return DocumentActive.isDisplayed();
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
	
//	@FindBy(css="div.crm-side-pane div a#crm-toggle-controlpane")
//	private WebElement ControlPanel;
	
	@FindBy(css="div#crm-controlpane-header a")
	private WebElement ControlPanelArrow;
	
	public boolean isControlPaneExpanded(){
		if(ControlPanelArrow.getAttribute("data-state").contains("expanded")){
			return true;
		}
		
		return false;
	}
	

	public HomePage expandControlPanel(){
		if(!isControlPaneExpanded()){
			ControlPanelArrow.click();
		}
		
		while(!isControlPaneExpanded()){
			forceWait(100);
		}
		
		return this;
	}
	
	public HomePage collapeControlPanel(){
		if(isControlPaneExpanded()){
			ControlPanelArrow.click();
		}
		
		while(isControlPaneExpanded()){
			forceWait(100);
		}
		
		return this;
	}
	
	
	@FindBy(css="li.accordion-group.group-section div[id *= '-admin']")
	private WebElement AdminDiv;
	
	@FindBy(css="li.accordion-group.group-section div[id *= '-reports']")
	private WebElement ReportsDiv;
	
	@FindBy(css="li.accordion-group.group-section div[id *= '-selectors']")
	private WebElement SelectorsDiv;
	
	@FindBy(css="li.accordion-group.group-section div[id *= '-storesites']")
	private WebElement StoreSitesDiv;
	
	@FindBy(css="li.accordion-group.group-section div[id *= '-recent']")
	private WebElement RecentDiv;
	
	@FindBy(css="li.accordion-group.group-section div[id *= '-favorites']")
	private WebElement FavoritesDiv;
	
	@FindBy(css="li.accordion-group.group-section div[id *= '-allcatalogs']")
	private WebElement AllCatalogsDiv;
	
	public boolean areIconsDisplayed(){
		return AdminDiv.isDisplayed() &&
				ReportsDiv.isDisplayed() &&
				SelectorsDiv.isDisplayed() &&
				RecentDiv.isDisplayed() &&
				FavoritesDiv.isDisplayed() &&
				AllCatalogsDiv.isDisplayed();
	}
	
	//********************** Charts Section ********************//
	
	public WebElement getTable(String tableName){
		List<WebElement> tables = driver.findElements(By.cssSelector("table.graph"));
		for(WebElement table: tables){
//			System.out.println(table.findElement(By.xpath("tbody/tr/th/h2")).getText());
			if(table.findElement(By.xpath("tbody/tr/th/h2")).getText().toLowerCase().contains(tableName.toLowerCase())){
				return table;
			}
		}
		return null;
	}
	
	public boolean isSalesChartDisplayed(){
		return getTable("Sales Chart").isDisplayed();
	}
	
	public boolean isQuoteTableDisplayed(){
		return getTable("Quote").isDisplayed();
	}
	
	public boolean isQuotesOnProposalTableDisplayed(){
		return getTable("Quotes on Proposal").isDisplayed();
	}
	
	public boolean isSalesOrderTableDisplayed(){
		return getTable("Sales Order").isDisplayed();
	}
	
	public boolean isFollowUpDocsDisplayed(){
		return getTable("Follow-up Docs").isDisplayed();
	}
	
	public boolean isRmasTableDisplayed(){
		return getTable("RMAs").isDisplayed();
	}
	
	public boolean isOrganizerNotesTableDisplayed(){
		return getTable("Organizer Notes").isDisplayed();
	}
	
	public boolean isOrganizerTasksTableDisplayed(){
		return getTable("Organizer Tasks").isDisplayed();
	}
	
	public By getLinkText(String text){
		return By.linkText(text.replace("_", " "));
	}

	//----------------- top bar -------------------------//
	
	@FindBy(css="li#crm-item-inbox")
	private WebElement Inbox;
	
	@FindBy(css="li#crm-item-organizer")
	private WebElement Organizer;
	
	@FindBy(css="li#crm-item-user")
	private WebElement User;
	
	@FindBy(css="li.user-info span.username")
	private WebElement Username;
	
	@FindBy(css="li.user-info span.email")
	private WebElement Email;
	
	public boolean isInboxDisplayed(){
		return Inbox.isDisplayed();
	}
	
	public boolean isOrganizerDisplayed(){
		return Organizer.isDisplayed();
	}
	
	public boolean isUserDisplayed(){
		return User.isDisplayed();
	}
	
	public boolean isUserNameDisplayed(){
		return Username.isDisplayed();
	}

	public boolean isEmailDisplayed(){
		return Email.isDisplayed();
	}

	
}
