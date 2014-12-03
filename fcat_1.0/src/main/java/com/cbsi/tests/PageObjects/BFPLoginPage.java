package com.cbsi.tests.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.tests.util.GlobalVar;

public class BFPLoginPage extends BasePage{
	public BFPLoginPage(WebDriver driver){
		super(driver);
		//wiat for page to load
	}
	
	@FindBy(css="input#EmailOrLogin")
	private WebElement userNameField;
	
	@FindBy(css="input#Password")
	private WebElement passwordField;
	
	
	@FindBy(css="a.link-button.navy")
	private WebElement Submit;
	
	public CatalogsPage loginToHomePage(){
		userNameField.click();
		userNameField.sendKeys(GlobalVar.BFPId);
		passwordField.click();
		passwordField.sendKeys(GlobalVar.BFPPw);
		
		Submit.click();
		
		return PageFactory.initElements(driver, CatalogsPage.class);
	}
}
