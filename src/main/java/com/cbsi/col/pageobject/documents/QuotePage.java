package com.cbsi.col.pageobject.documents;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.home.ColBasePage;


public class QuotePage extends DocumentsBasePage{

	public QuotePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
//		waitForPageToLoad(By.cssSelector("div h1"));
		waitForTextToBeVisible(20000, "Quote (", "span");
		initializePriceCalculator();
	}

	public int getQuoteNumber(){
		return super.getDocNumber();
	}

	//----------------------- inner page object: copy to new quote  -----------------------//
	public static class CopyToNewQuotePage extends ColBasePage{

		public CopyToNewQuotePage(WebDriver driver) {
			super(driver);
			// TODO Auto-generated constructor stub
			waitForElementToBeVisible(By.cssSelector("iframe"));
			switchFrame();
			waitForTextToBeVisible("Copy to New Quote", "h3");
		}
		
		@FindBy(css="input[name='refresh']")
		private WebElement Refresh;
		
		@FindBy(css="input#open_newcb")
		private WebElement CopyAndOpenTheNewQuote;
		
		@FindBy(css="input#open_old")
		private WebElement CopyAndRemainInTheOldQuote;
		
		@FindBy(css="a[href*='submit_page']")
		private WebElement Create;
		
		@FindBy(css="a[href*='java_close']")
		private WebElement Cancel;
		
		public QuotePage clickCreate(){
			Create.click();
			switchBack();
			return PageFactory.initElements(driver, QuotePage.class);
		}
		public void switchFrame(){
			System.out.println("starting frame switch...");
			switchFrame(By.cssSelector("iframe#modal-iframe"));
		}
	}

}
