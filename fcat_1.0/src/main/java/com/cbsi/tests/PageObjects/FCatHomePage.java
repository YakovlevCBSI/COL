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

}
