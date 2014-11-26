package com.cbsi.tests.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MappingPage extends BasePage{
	public MappingPage(WebDriver driver) {
		super(driver);
		waitForPageToLoad();
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(css="a#save_mappings_button")
	private WebElement Save;
	public MappingPage clickSave(){
		waitForElementToBeVisible("a#save_mappings_button");
		Save.click();
		return this;
	}
	
	public boolean isSaveDisabled(){
		System.out.println("save value; "+ Save.getAttribute("href").trim().toLowerCase());
		return Save.getAttribute("class").trim().toLowerCase().contains("disabled");
	}
	
}
