package com.cbsi.col.pageobject.documents;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.ColBasePage;
import com.cbsi.col.pageobject.ProductsPage;

public class QuotePage extends ColBasePage{

	public QuotePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForPageToLoad(By.cssSelector("div h1"));
		waitForTextToBeVisible(15000, "Quote (Open)", "span");
	}
	
	@FindBy(css="input#addProductKeyword")
	private WebElement AddProductSearchBox;
	
	
	@FindBy(css="button#add-product-submit")
	private WebElement Search;
	
	public int getQuoteNumber(){
		String quoteNumber= driver.findElement(By.cssSelector("input#quoteNumber")).getAttribute("value");
		return Integer.parseInt(quoteNumber);
	}
	
	public ProductsPage searchProduct(String productName){
		AddProductSearchBox.sendKeys(productName);
		Search.click();
		
		return PageFactory.initElements(driver, ProductsPage.class);
	}
	
	//--------------------------  Bottom bar---------------------------//
	@FindBy(css="button#next-action_save")
	private WebElement Save;
	
	@FindBy(css="button#next-action_copyToQuote")
	private WebElement CopyToNewQuote;
	
	@FindBy(css="button[id^='next-action_order']")
	private WebElement ConvertToOrder;
	
	public QuotePage clickSave(){
		Save.click();
		forceWait(500);
		return this;
	}
	
	public QuotePage clickCopyToNewQuote(){
		CopyToNewQuote.click();
		CopyToNewQuotePage copyToNewQuotePage = PageFactory.initElements(driver, CopyToNewQuotePage.class);

		return copyToNewQuotePage.clickCreate();
	}
	
	public SalesOrderPage clickConvertToOrder(){

		ConvertToOrder.click();
		return PageFactory.initElements(driver, SalesOrderPage.class);
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
