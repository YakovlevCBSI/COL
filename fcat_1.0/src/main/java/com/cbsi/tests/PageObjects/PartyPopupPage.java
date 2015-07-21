package com.cbsi.tests.PageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PartyPopupPage extends BasePage{

	public PartyPopupPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForPageToLoad();
	}

	@Override
	public void waitForPageToLoad(){
		forceWait(1500);
		waitForElementToBeVisible(By.cssSelector("tbody#party-chooser-table-body"));
	}
	
	@FindBy(css="input#party-name")
	private WebElement partyNameField;
	
	private String searchText;
	private String searchCode;
	public PartyPopupPage searchParty(String text){
		searchText = text;
		partyNameField.sendKeys(text);
		forceWait(1500);
		waitForPageToLoad();
		return this;
	}
	
	@FindBy(css="input#party-code")
	private WebElement partyCodeField;
	public PartyPopupPage searchCode(String code){
		searchCode = code;
		partyCodeField.sendKeys(code);
		forceWait(1500);
		waitForPageToLoad();
		return this;
	}
	@FindBy(linkText="Cancel")
	private WebElement Cancel;
	
	public CatalogsPage clickCancel(){
		Cancel.click();
		return PageFactory.initElements(driver, CatalogsPage.class);
	}
	
	@FindBy(css="a#nav-button-next-lower-mapping")
	private WebElement next;
	
	public PartyPopupPage clickNext(){
		next.click();
		forceWait(1500);
		waitForPageToLoad();
		next = refreshStaleElement(By.cssSelector("a#nav-button-next-lower-mapping"));
		return this;
	}
	
	@FindBy(css="a.nav-button.prev")
	private WebElement previous;
	
	public PartyPopupPage clickPrevious(){
		previous.click();
		waitForPageToLoad();
		return this;
	}
	
	@FindBy(css="tbody#party-chooser-table-body")
	private WebElement tbody;
	
	public List<WebElement> getSearchResult(){
		try{
			tbody = refreshStaleElement(By.cssSelector("tbody#party-chooser-table-body"));
		}catch(TimeoutException e){
			//this means result is zero.
			return null;
		}
		List<WebElement> searchElementsText = tbody.findElements(By.xpath("tr/td[@class='party-name-column'][1]"));
		return searchElementsText;
	}
	
	public int getResultNumber(){
		if(getSearchResult() == null){
			return 0;
		}
		return getSearchResult().size();
	}
	
	public List<String> searchResultToText(){
		List<String> textList = new ArrayList<String>();
		for(WebElement e: getSearchResult()){
			textList.add(e.getText());
		}
		return textList;
	}
	
	public CatalogsPage pickFromResult(){
		
		List<WebElement> list = null;
		if(searchText != null){
			list = driver.findElements(By.cssSelector("tr td.party-name-column"));
			for(WebElement e: list){
				if(e.getText().toLowerCase().equals(this.searchText.toLowerCase())){
					WebElement plusIcon = e.findElement(By.xpath("../td[@class='action-column']/a"));
					plusIcon.click();
					break;
				}
			}
		}
		else if(searchCode != null){
			list = driver.findElements(By.cssSelector("tr td.party-code-column"));
			for(WebElement e: list){
				if(e.getText().toLowerCase().equals(this.searchCode.toLowerCase()) ){
					WebElement plusIcon = e.findElement(By.xpath("../td[@class='action-column']/a"));
					plusIcon.click();
					break;
				}
			}	
		}

		forceWait(3000);
		
		return PageFactory.initElements(driver, CatalogsPage.class);
	}
	
	
	

}
