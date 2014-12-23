package com.cbsi.tests.PageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
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
		waitForElementToBeVisible(By.cssSelector("tbody#party-chooser-table-body"));
	}
	
	@FindBy(css="input#party-name")
	private WebElement partyNameField;
	
	public PartyPopupPage searchParty(String text){
		partyNameField.sendKeys(text);
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
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		tbody = refreshStaleElement(By.cssSelector("tbody#party-chooser-table-body"));
		List<WebElement> searchElementsText = tbody.findElements(By.xpath("tr/td[@class='party-name-column']"));
		return searchElementsText;
	}
	
	public List<String> searchResultToText(){
		List<String> textList = new ArrayList<String>();
		for(WebElement e: getSearchResult()){
			textList.add(e.getText());
		}
		return textList;
	}

}
