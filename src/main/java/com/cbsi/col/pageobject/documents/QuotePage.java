package com.cbsi.col.pageobject.documents;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cbsi.col.pageobject.home.ColBasePage;


public class QuotePage extends DocumentsBasePage{
	public final Logger logger = LoggerFactory.getLogger(QuotePage.class);

	public QuotePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
//		waitForPageToLoad(By.cssSelector("div h1"));
		logger.info(getClass().getName());
		waitForTextToBeVisible(20000, "Quote (", "h1 span");
		initializePriceCalculator();
	}

	public long getQuoteNumber(){
		return super.getDocNumber();
	}

	@FindBy(linkText="Revisions")
	private WebElement Revisions;
	
	public QuotePage goToRevisionsTab(){
		Revisions.click();
		waitForTextToBeVisible("Revisions", "h1");
		
		return this;
	}
	
	@FindBy(css="Table.costandard")
	private WebElement Table;
	
	public boolean hasRevision(String description){
		List<WebElement> descriptionColumns = Table.findElements(By.xpath("tbody/tr/td[7]"));
		for(WebElement e: descriptionColumns){
			if(e.getText().toLowerCase().contains(description.toLowerCase())){
				return true;
			}
		}
		return false;
	}
	
	@FindBy(css="td a[href*='revertToRevision']")
	private WebElement Revert;
	public QuotePage clickRevert(String description){
		List<WebElement> descriptionColumns = Table.findElements(By.xpath("tbody/tr/td[7]"));
		WebElement revertButton;
		for(WebElement e: descriptionColumns){
			if(e.getText().toLowerCase().contains(description.toLowerCase())){
				Revert = e.findElement(By.xpath("../td[4]/a"));
				logger.debug("found a matching revert element");
				break;
			}
		}
		
		Revert.click();
		return PageFactory.initElements(driver, QuotePage.class);
	}

	//----------------------- inner page object: copy to new quote  -----------------------//
	public static class CopyToNewQuotePage extends ColBasePage{

		public CopyToNewQuotePage(WebDriver driver) {
			super(driver);
			// TODO Auto-generated constructor stub
			waitForElementToBeVisible(By.cssSelector("iframe"));
			waitForTextToBeVisible("Copy to New Quote", "h3");
			waitForQuickLoad();
		}
		
		@FindBy(css="input[name='refresh']")
		private WebElement Refresh;
		
		@FindBy(css="input#open_newcb")
		private WebElement CopyAndOpenTheNewQuote;
		
		@FindBy(css="input#open_old")
		private WebElement CopyAndRemainInTheOldQuote;
		
		@FindBy(css="button#create-copynewquote-btn")
		private WebElement Create;
		
		@FindBy(css="button#cancel-copynewquote-btn")
		private WebElement Cancel;
		
		public QuotePage clickCreate(){
			logger.debug("Clicking Create");
			Create.click();
			
			waitForQuickLoad();
			return PageFactory.initElements(driver, QuotePage.class);
		}
		
		@FindBy(css="td input[type='text']")
		private WebElement CustomerAndContactSearch;
		public QuotePage setCustomerAndContactSearch(String text){
			switchFrame();
			
			CustomerAndContactSearch.sendKeys(text);
			forceWait(300);
			getActions().sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.RETURN).build().perform();
			
			switchBack();
			return PageFactory.initElements(driver, QuotePage.class);
		}
	}

	
}
