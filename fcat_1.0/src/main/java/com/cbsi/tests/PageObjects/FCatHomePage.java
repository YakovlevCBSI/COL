package com.cbsi.tests.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cbsi.test.pageobjects.sidebar.AttributeTypePage;
import com.cbsi.test.pageobjects.sidebar.CatalogImportTypesPage;
import com.cbsi.test.pageobjects.sidebar.CatalogTypePage;
import com.cbsi.test.pageobjects.sidebar.ContentTypePage;
import com.cbsi.test.pageobjects.sidebar.ItemIdentificationTypePage;
import com.cbsi.test.pageobjects.sidebar.PartiesPage;
import com.cbsi.test.pageobjects.sidebar.PredefinedListsPage;
import com.cbsi.test.pageobjects.sidebar.RolesPage;
import com.cbsi.test.pageobjects.sidebar.SSOUsersPage;
import com.cbsi.test.pageobjects.sidebar.UsersPage;
import com.cbsi.test.pageobjects.sidebar.UsersPartyAssignmentPage;
import com.cbsi.test.pageobjects.sidebar.UsersRoleAssignmentPage;

public class FCatHomePage extends BasePage{

	public FCatHomePage(WebDriver driver) {
		super(driver);
		waitForPageToLoad();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void waitForPageToLoad(){
		new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#main")));
		
	}
	@FindBy(css="li#i_catalogs a")
	private WebElement Catalogs;
	
	public CatalogsPage goToCatalogs(){
		Catalogs.click();
		
		//firefox needs wait here. Otherwise throws an exception while inititating catalogPage object.
		customWait(20);
		
		return PageFactory.initElements(driver, CatalogsPage.class);
	}
	
	@FindBy(css="li#i_workflow_dashboard a")
	private WebElement Dashboards;
	
	public DashboardPage goToDashboard(){
		Dashboards.click();
		customWait(20);
		
		return PageFactory.initElements(driver, DashboardPage.class);
	}
	
	@FindBy(css="li#i_master a")
	private WebElement Master;
	
	public MasterPage goToMaster(){
		Master.click();
		
		return PageFactory.initElements(driver, MasterPage.class);
	}
	
	@FindBy(css="span a[href*='fcat/logout']")
	private WebElement Logout;
	
	public FCatLoginPage logOut(){
		Logout.click();
		return PageFactory.initElements(driver, FCatLoginPage.class);
	}
	
	@FindBy(css="div#menu ul#_menu li#c_security")
	private WebElement sideBar;
	public boolean isSideBarPresent(){
		return sideBar.isDisplayed();
	}
	
	@FindBy(css="[id*='_security']")
	private WebElement Security;
	
	@FindBy(css="[id*='_itemmetadata']")
	private WebElement ItemMetadata;
	
	@FindBy(css="[id*='_workflows']")
	private WebElement Workflows;
	
	public boolean isSecurityDisplayed(){
		try{
			Security.isDisplayed();
		}catch(NoSuchElementException e){
			return false;
		}
		
		return true;
	}
	
	public boolean isItemMetadataDisplayed(){
		try{
			ItemMetadata.isDisplayed();
		}catch(NoSuchElementException e){
			return false;
		}
	
		return true;
	}
	
	public boolean isWorkflowsDisplayed(){
		try{
			Workflows.isDisplayed();
		}catch(NoSuchElementException e){
			return false;
		}
		
		return true;
	}

	
	//----------------------- navigation to sidebar ----------------------//
	
	@FindBy(linkText="Parties")
	private WebElement Parties;
	
	@FindBy(linkText="Roles")
	private WebElement Roles;
	
	@FindBy(linkText="Users")
	private WebElement Users;
	
	@FindBy(linkText="Users Role Assignment")
	private WebElement UsersRoleAssignment;
	
	@FindBy(linkText="Users Party Assignment")
	private WebElement UsersPartyAssignment;
	
	@FindBy(linkText="SSO Users")
	private WebElement SSOUsers;
	
	@FindBy(linkText="Catalog Type")
	private WebElement CatalogType;
	
	@FindBy(linkText="Catalog Import Types")
	private WebElement CatalogImportTypes;
	
	@FindBy(linkText="Attribute Type")
	private WebElement AttributeType;
	
	@FindBy(linkText="Content Type")
	private WebElement ContentType;
	
	@FindBy(linkText="Item Identification Type")
	private WebElement ItemIdentificationType;
	
	@FindBy(linkText="Predefined Lists")
	private WebElement PredefinedLists;
	
	public PartiesPage goToParties(){
		Parties.click();
		return PageFactory.initElements(driver, PartiesPage.class);
	}
	
	public RolesPage goToRoles(){
		Roles.click();
		return PageFactory.initElements(driver, RolesPage.class);
	}
	
	public UsersPage gotoUsers(){
		Users.click();
		return PageFactory.initElements(driver, UsersPage.class);
	}
	
	public UsersRoleAssignmentPage goToUsersRoleAssignment(){
		UsersRoleAssignment.click();
		return PageFactory.initElements(driver, UsersRoleAssignmentPage.class);
	}
	
	public UsersPartyAssignmentPage goToUsersPartyAssignment(){
		UsersPartyAssignment.click();
		return PageFactory.initElements(driver, UsersPartyAssignmentPage.class);
	}
	
	public SSOUsersPage goToSSOUsers(){
		SSOUsers.click();
		return PageFactory.initElements(driver, SSOUsersPage.class);
	}
	
	public CatalogTypePage goToCatalogType(){
		CatalogType.click();
		return PageFactory.initElements(driver, CatalogTypePage.class);
	}
	
	public CatalogImportTypesPage goToCatalogImportType(){
		CatalogImportTypes.click();
		return PageFactory.initElements(driver, CatalogImportTypesPage.class);
	}
	
	public AttributeTypePage goToAttributeType(){
		AttributeType.click();
		return PageFactory.initElements(driver, AttributeTypePage.class);
	}
	
	public ContentTypePage goToContentType(){
		ContentType.click();
		return PageFactory.initElements(driver, ContentTypePage.class);
	}
	
	public ItemIdentificationTypePage goToItemIdentification(){
		ItemIdentificationType.click();
		return PageFactory.initElements(driver, ItemIdentificationTypePage.class);
	}
	
	public PredefinedListsPage goToPredefinedLists(){
		PredefinedLists.click();
		return PageFactory.initElements(driver, PredefinedListsPage.class);
	}

}
