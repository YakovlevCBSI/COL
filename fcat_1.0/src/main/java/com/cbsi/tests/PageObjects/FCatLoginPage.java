package com.cbsi.tests.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.tests.util.GlobalVar;

public class FCatLoginPage extends BasePage{

	public FCatLoginPage(WebDriver driver) {
		super(driver);
		waitForPageToLoad(By.cssSelector("div#header a"));
		// TODO Auto-generated constructor stub
	}

	
	@FindBy(id="j_username")
	private WebElement usernameField;
	
	@FindBy(id="j_password")
	private WebElement passwordField;
	
	@FindBy(id="proceed")
	private WebElement Submit;
	
	@FindBy(css="span a[href*='login']")
	private WebElement LoginLink;
	public FCatHomePage loginToHomePage(String username, String password){
		LoginLink.click();
		
		usernameField.click();
		usernameField.sendKeys(username);
		
		passwordField.click();
		passwordField.sendKeys(password);
		
		Submit.click();
		
		return PageFactory.initElements(driver, FCatHomePage.class);
	}
	
	private String adminU = GlobalVar.LocalId;
	private String adminP= GlobalVar.LocalPw;
			
;	public FCatHomePage loginToHomePage(){
	
		customWait(5);
		LoginLink.click();
		
		customWait(5);
		usernameField.click();
		usernameField.sendKeys(adminU);
		
		passwordField.click();
		passwordField.sendKeys(adminP);
		
		Submit.click();
		
		return PageFactory.initElements(driver, FCatHomePage.class);
	}

	@FindBy(css="span a[href='login']")
	private WebElement loginLink;
	
	public boolean isBeforeLoginPage(){
		return loginLink.isDisplayed();
	}


}
