package com.cbsi.tests.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EditProductPopupPage extends BasePage{

	public EditProductPopupPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForPageToLoad();
	}
	
	@Override
	public void waitForPageToLoad(){
		waitForElementToBeVisible(By.cssSelector("#content > div.fancybox-wrap.fancybox-desktop.fancybox-type-html.fancybox-opened > div > div > div > div > div.overlay-body.dialog"));
	}
	
	@FindBy(linkText="Save")
	private WebElement Save;
	
	@FindBy(linkText = "Cancel")
	private WebElement Cancel;
	
	public ProductsCatalogPage clickSave(){
		Save.click();
		return PageFactory.initElements(driver, ProductsCatalogPage.class);
	}
	
	public ProductsCatalogPage clickCancel(){
		Cancel.click();
		return PageFactory.initElements(driver, ProductsCatalogPage.class);
	}

}
