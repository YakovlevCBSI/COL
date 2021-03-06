package com.cbsi.fcat.pageobject.homepage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.fcat.pageobject.foundation.BasePage;
import com.cbsi.fcat.util.GlobalVar;

public class FCatLoginPage extends BasePage{

	public FCatLoginPage(WebDriver driver) {
		super(driver);
		waitForPageToLoad(By.cssSelector("div[role='region']"));
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

	
	private String adminU = GlobalVar.LocalId;
	private String adminP= GlobalVar.LocalPw;
	private String adminPProd =GlobalVar.ProdPw;
			
;	public FCatHomePage loginToHomePage(){
	
//		customWait(10);
//		LoginLink.click();
//		
//		waitForElementToBeVisible(By.cssSelector("#j_username"));
//		usernameField.sendKeys(adminU);
//		
//		customWait(20);
//		//passwordField.click();
//		passwordField.sendKeys(adminP);
//		
//		Submit.click();
//		
//		return PageFactory.initElements(driver, FCatHomePage.class);
		return loginToHomePage(adminU, adminP);
	}

	public FCatHomePage loginToHomePage(String username, String pw){
		customWait(5);
		LoginLink.click();
		
		customWait(20);
		//usernameField.click();
		usernameField.sendKeys(username);
		
		customWait(20);
		//passwordField.click();
		passwordField.sendKeys(pw);
		
		Submit.click();
		return PageFactory.initElements(driver, FCatHomePage.class);
}

//Testing bad credit
	public FCatLoginPage loginToHomePageFail(String username, String pw){
		customWait(5);
		LoginLink.click();
		
		customWait(20);
		//usernameField.click();
		usernameField.sendKeys(username);
		
		customWait(20);
		//passwordField.click();
		passwordField.sendKeys(pw);
		
		Submit.click();
		return this;
	}

	@FindBy(css="span a[href='login']")
	private WebElement loginLink;
	
	public boolean isBeforeLoginPage(){
		return loginLink.isDisplayed();
	}
	
	@FindBy(css="div.errors p")
	private WebElement error;
	
	public String getErrorMessage(){
		return error.getText();
	}

}
