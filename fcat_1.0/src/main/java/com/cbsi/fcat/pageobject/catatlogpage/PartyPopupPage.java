package com.cbsi.fcat.pageobject.catatlogpage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cbsi.fcat.pageobject.foundation.BasePage;

public class PartyPopupPage extends BasePage{
	public final static Logger logger = LoggerFactory.getLogger(PartyPopupPage.class);

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
		try{
			waitForPageToLoad();
		}catch(TimeoutException e){
			//in case search result is null.
		}
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
	
	public List<HashMap<String, String>> getSearchResultAsMap(){
		List<HashMap<String, String>> partyMaps = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> partyMap = new HashMap<String, String>();
		
		for(WebElement partyName: getSearchResult()){
			partyMap.put("partyname", partyName.getText());
			partyMap.put("partycode", partyName.findElement(By.xpath("../td[@class='party-code-column']")).getText());
		}
		
		partyMaps.add(partyMap);
		
		return partyMaps;
	}
	
	public CatalogsPage pickFromResult(){
		
		List<WebElement> list = null;
		if(searchText != null){
			list = driver.findElements(By.cssSelector("tr td.party-name-column"));
			logger.info("party name size: " + list.size());
			for(WebElement e: list){
				if(e.getText().toLowerCase().equals(this.searchText.toLowerCase())){
					WebElement plusIcon = e.findElement(By.xpath("../td[@class='action-column actions']/a"));
					plusIcon.click();
					break;
				}
			}
		}
		else if(searchCode != null){
			list = driver.findElements(By.cssSelector("tr td.party-code-column"));
			logger.info("party name size: " + list.size());

			for(WebElement e: list){
				logger.info("code: " + e.getText() + "|  search ; " + searchCode );
				if(e.getText().toLowerCase().equals(this.searchCode.toLowerCase()) ){
					WebElement plusIcon = e.findElement(By.xpath("../td[@class='action-column actions']/a"));
					plusIcon.click();
					break;
				}
			}	
		}

		forceWait(3000);
		
		return PageFactory.initElements(driver, CatalogsPage.class);
	}
	
	
	

}
