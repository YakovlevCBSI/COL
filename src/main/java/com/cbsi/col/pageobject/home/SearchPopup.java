package com.cbsi.col.pageobject.home;

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
		waitForTextToBeVisible("QUERY SETTINGS", "h3");
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
//	@FindBy(css="input.decor-searchbutton")
	@FindBy(css="div[class^='span'] input[name='search_button']")
	private WebElement Search;
	
	@FindBy(css="input[value='contains']")
	private WebElement contains;
	
	@FindBy(css="input[value='starts_with']")
	private WebElement startsWith;
	
	public AccountsPage searchForAccount( String searchText){
		return searchFor(QueryOption.Customers, false, QueryColumn.Company, searchText,  AccountsPage.class);
	}
	
	public <T> T  searchFor(QueryOption option, boolean containsText,  QueryColumn column, String searchText, Class clazz){
		getQueryOptionElement(option.toString()).click();



		if(column != QueryColumn.All){
			driver.findElement(By.cssSelector("select#decor-search-field")).click();
			forceWait(500);
			driver.findElement(By.cssSelector("option[value='"+column.toString() + "']")).click();
			forceWait(500); //without wait, FF defaults back due to timing. Do not remove this.
		}
		
		if(contains.isDisplayed()){
			if(containsText){
				System.out.println("in contains condition....");
				contains.click();
				
			}else{
				startsWith.click();
			}
		}
		searchField.clear();
		searchField.sendKeys(searchText);
		
		forceWait(3000);
		Search.click();
		waitForElementToBeInvisible(By.cssSelector("div.dropdown-menu-content"));

		return (T)PageFactory.initElements(driver, clazz);

	}
	
	public enum QueryOption{
		Products, 
		ServiceAndLabor, 
		Paragraphs, 
		QuotesAndOrders{
			public String toString(){
				return "quotesorders";
			}
		}, 
		Proposals, 
		PurchaseOrders, 
		Customers, 
		Suppliers,
		PriceProfiles,
		Personnel,
		Catalogs,
		ProductNews
	}
	
	public enum QueryColumn{
		All	{
			public String toString(){
			return "all";
			}
		},
		Email{
			public String toString(){
				return "email";
			}
		},
		Company{
			public String toString(){
				return "name";
			}
		},
		CustomerNumber{
			public String toString(){
				return "customerNumber";
			}
		},
		Contact{
			public String toString(){
				return "contact";
			}
		},
		City{
			public String toString(){
				return "city";
			}
		},
		State{
			public String toString(){
				return "state";
			}
		}	
		
	}
	public WebElement getQueryOptionElement(String option){
		return driver.findElement(By.cssSelector("option[id^='ds_" +option.toLowerCase() +  "']"));
	}
	
	//--------------------------- quick search for storestie goes here ------------------------//
	
	@FindBy(css="select[id *='_search_type']")
	private WebElement searchDropdown;
	
	public <T> T quickSearchFor(QueryOption queryOption, boolean isKeyword, String text,  Class clazz){
		searchDropdown.click();
		forceWait(500);
		
		List<WebElement> options = searchDropdown.findElements(By.xpath("option"));
		for(WebElement option: options){
			if(option.getText().equalsIgnoreCase(queryOption.toString())){
				option.click();
				break;
			}
		}
		
		searchField.sendKeys(text);
		Search.click();
		
		return (T) PageFactory.initElements(driver, clazz);
		
	}

	
	
}
