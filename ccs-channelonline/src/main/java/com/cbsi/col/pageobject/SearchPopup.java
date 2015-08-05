package com.cbsi.col.pageobject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.customers.AccountsPage;

public class SearchPopup extends ColBasePage{
	public SearchPopup(WebDriver driver){
		super(driver);
		System.out.println("passed");
		clickDropDown();
		waitForPageToLoad(By.cssSelector("#search-query-settings"));
	}
	
	@FindBy(css="li[id*='item-search'] div a.header-search-toggle span")
	private WebElement dropdown;
	
	@FindBy(css="div.dropdown input#decor-search-string")
	private WebElement searchField;
	
	public SearchPopup clickDropDown(){
		//Need to call this in constructor. Find Element by using findElement method.
		dropdown = driver.findElement(By.cssSelector("li[id*='item-search'] div a.header-search-toggle span"));
		dropdown.click();
		return this;
	}
	@FindBy(css="input.decor-searchbutton")
	private WebElement Search;
	
	@FindBy(css="input[value='contains']")
	private WebElement contains;
	
	@FindBy(css="input[value='starts_with']")
	private WebElement startsWith;
	
	public ColBasePage searchFor(QueryOption option, String searchText){
		return searchFor(option, false, searchText);
	}
	
	public ColBasePage searchFor(QueryOption option, boolean containsText,  String searchText){
		if(containsText){
			contains.click();
		}else{
			startsWith.click();
		}
		
		if(option == QueryOption.Customers){
			getQueryOptionElement(QueryOption.Customers.toString()).click();
			searchField.clear();
			searchField.sendKeys(searchText);
			Search.click();
			waitForElementToBeInvisible(By.cssSelector("div.dropdown-menu-content"));

			return PageFactory.initElements(driver, AccountsPage.class);
		}
		
		return null;
	}
	
	public enum QueryOption{
		Products, 
		ServiceAndLabor, 
		Paragraphs, 
		QuotesAndOrders, 
		Proposals, 
		PurchaseOrders, 
		Customers, 
		Suppliers,
		PriceProfiles,
		Personnel,
		Catalogs,
		ProductNews
	}
	
	public WebElement getQueryOptionElement(String option){
		return driver.findElement(By.cssSelector("option[id^='ds_" +option.toLowerCase() +  "']"));
	}
	
	
}
