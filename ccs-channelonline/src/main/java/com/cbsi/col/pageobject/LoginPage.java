package com.cbsi.col.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.test.util.LoginProperty;

public class LoginPage extends ColBasePage{
	
	public LoginPage(WebDriver driver){
		super(driver);
		waitForPageToLoad(By.cssSelector("div#login_info"));
	}
	
	@FindBy(css="input#credential_0")
	private WebElement j_userName;
	
	@FindBy(css="input#credential_1")
	private WebElement j_password;
	
	@FindBy(css="input[type='button']")
	private WebElement submit;
	//
	public HomePage loginToHomePage(){
		return loginToHomePage(LoginProperty.testUser, LoginProperty.testPassword);
	}
	
	public HomePage loginToHomePage(String username, String password){
		j_userName.sendKeys(username);
		j_password.sendKeys(password);
		submit.click();
		
		return PageFactory.initElements(driver, HomePage.class);
	}
}
