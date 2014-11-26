package com.cbsi.tests.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddCatalogPage extends CatalogsPage {

	public AddCatalogPage(WebDriver driver){
		super(driver);
	}
	

	@FindBy(css="div.logo.en-us a.logo-url")
	private WebElement ContentSolutionsHeader;
	
	public boolean isHeaderDisplayed(){
		return driver.findElements(By.cssSelector("div.logo.en-us a.logo-url")).size() != 0;
	}

	@FindBy(css="input#Name") private WebElement name;
	@FindBy(linkText="Next") private WebElement Next;
	
	
	public UploadPopupPage fillInName(){
		//customWait(20);
		name.sendKeys(tempFileName);
		customWait(5);
		Next.click();
		return PageFactory.initElements(driver, UploadPopupPage.class);
	}
	


}
