package com.cbsi.tests.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FCatHomePage extends BasePage{

	public FCatHomePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(css="li#i_catalogs a")
	private WebElement Catalogs;
	
	public CatalogsPage goToCatalogs(){
		Catalogs.click();
		
		//firefox needs wait here. Otherwise throws an exception while inititating catalogPage object.
		customWait(20);
		
		return PageFactory.initElements(driver, CatalogsPage.class);
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

}
