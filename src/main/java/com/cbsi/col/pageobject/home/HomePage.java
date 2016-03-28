package com.cbsi.col.pageobject.home;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.customers.AccountsPage;
import com.cbsi.col.pageobject.documents.DocumentsPage;
import com.cbsi.col.pageobject.documents.ScratchPadPage;
import com.cbsi.col.pageobject.products.ProductsPage;
import com.cbsi.col.pageobject.purchaseorders.PurchaseOrdersTab;
//import com.cbsi.col.pageobject.sidebar.QuoteHotListPage;
import com.cbsi.col.pageobject.suppliers.SuppliersPage;

public class HomePage extends ColBasePage{
	public HomePage(WebDriver driver){
		super(driver);
//		waitForPageToLoad(By.cssSelector("#tab-home"));
//		waitForTextToBeVisible("My Channel", "ul li.active a");
		waitForElementToBeVisible(By.cssSelector("li[class*='tab-bar'] 	a[id*='tab-home']"));
	}
	
	@CacheLookup
	@FindBy(css="a#crm-controlpanesectionlink-admin")
	private WebElement Admin;
	
	@CacheLookup
	@FindBy(css="#crm-controlpanesectionlink-recent")
	private WebElement Recent;
	
	@CacheLookup
	@FindBy(css="#crm-controlpanesectionlink-favorites")
	private WebElement Favorite;
	
	@FindBy(css="#crm-controlpanesectionlink-storesites")
	private WebElement StoreSite;
	
	@FindBy(css="#crm-controlpanesectionlink-storesites")
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
	
	@FindBy(css="li#crm-doc-menu")
	private WebElement RecentDocDropdown;
	
	public  AccountsPage goToAccountsPage(){
		forceWait(500); //wait for message alert to load.
		Accounts.click();
		return PageFactory.initElements(driver, AccountsPage.class);
	}

	public ProductsPage goToProductsPage(){
		forceWait(1000);
		Products.click();
		return PageFactory.initElements(driver, ProductsPage.class);
	}
	
	public ServicesPage goToServicesPage(){
		forceWait(1000);
		Services.click();
		return PageFactory.initElements(driver, ServicesPage.class);
	}
	
	public DocumentsPage goToDocumentsPage(){
		forceWait(1000);
		Documents.click();
		waitForQuickLoad();
		return PageFactory.initElements(driver, DocumentsPage.class);
	}
	
	public SuppliersPage goToSuppliersPage(){
		forceWait(1000);
		Suppliers.click();
		return PageFactory.initElements(driver, SuppliersPage.class);
	}
	
	public PurchaseOrdersTab goToPurchaseOrdersPage(){
		PurhcaseOrders.click();
		return PageFactory.initElements(driver, PurchaseOrdersTab.class);
	}
	
	public ScratchPadPage goToScratchPadPage(){
//		RecentDocDropdown.click();
		getActions().moveToElement(RecentDocDropdown, 5, 5).click().build().perform();		

		forceWait(500);
		
		List<WebElement> dropdowns = driver.findElements(By.cssSelector("li#crm-tab-bar-right ul li div ul li a"));
		if(!dropdowns.get(0).getText().toLowerCase().contains("scratch pad")){
//			dropdowns.get(dropdowns.size()-1).click();
			getActions().moveToElement(dropdowns.get(dropdowns.size()-1), 10, 10).click().build().perform();
		}
		else{
			dropdowns.get(0).click();
		}
		return PageFactory.initElements(driver, ScratchPadPage.class);
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
	
	@FindBy(css="a#crm-controlpanelink-edithotlist")
	private WebElement FavoritesSetting;
	
	@FindBy(css="a#crm-controlpaneaccordionlink-quotehotlist")
	private WebElement QuoteHotList;
	
	@SuppressWarnings("unchecked")
	public <T> T  navigateToSideBar(Favorites page, long docNumber, Class<?> clazz){
		if(!Favorite.findElement(By.xpath("../../div[2]")).getAttribute("class").contains("in")) 
			Favorite.click();
				
		if(!QuoteHotList.findElement(By.xpath("../../div[2]")).getAttribute("class").contains("in"))
			QuoteHotList.click();
		
		if(page == Favorites.Quote_Hot_List){
			boolean quoteExists = false;
			
			for(WebElement e: QuoteHotList.findElement(By.xpath("../../div[2]")).findElements(By.xpath("ul/li/a"))){
				if(e.getText().contains(docNumber+"")){
					e.click();
					quoteExists = true;
					break;
				}
			}
			
			if(!quoteExists){
				throw new NullPointerException("No doc found");
			}
		}
		else if(page == Favorites.Quote_Hot_List_Setting){
			FavoritesSetting.click();
		}

		refresh();
		return (T) PageFactory.initElements(driver, clazz);
	}
	
	public <T> T  navigateToSideBar(StoreSites page, Class<?> clazz){
		if(!StoreSite.findElement(By.xpath("../../div[2]")).getAttribute("class").contains("in")) 
			StoreSite.click();

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
	
	public enum Recent{
		foo;
		enum move{
			djfkld
		}
	}
	
	public enum Favorites{
		Quote_Hot_List,
		Quote_Hot_List_Setting
	}
	
	public enum StoreSites{
		Default_Store,
		Preview_Store
	}
	
	public enum AllCatalogs{
		
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
		forceWait(200);
		
		return this;
	}
	
	
	@FindBy(css="li.accordion-group.group-section div[id *= '-admin']")
	private WebElement AdminDiv;
	
//	@FindBy(css="li.accordion-group.group-section div[id *= '-reports']")
	@FindBy(css="#crm-controlpane-header-reports")
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
		try{
			return getTable("Organizer Notes").isDisplayed();
		}catch(NoSuchElementException e){
			logger.info("unable to find \"Organizer Notes\" table, incase prod, return \"Notes\" table");
		}
		
		return getTable("Notes").isDisplayed();
	}
	
	public boolean isOrganizerTasksTableDisplayed(){
		try{
			return getTable("Organizer Tasks").isDisplayed();
		}catch(NoSuchElementException e){
			logger.info("unable to find \"Organizer Tasks\" table, incase prod, return \"Tasks\" table");
		}

		return getTable("Tasks").isDisplayed();
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
	
	public TopBar fromTopbar(){
		return PageFactory.initElements(driver, TopBar.class);
	}

	
}
