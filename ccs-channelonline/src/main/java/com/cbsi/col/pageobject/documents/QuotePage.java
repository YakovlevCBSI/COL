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
		waitForTextToBeVisible("Quote (Open)", "span");
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
		return this;
	}
	
	public SalesOrderPage clickConvertToOrder(){

		ConvertToOrder.click();
		return PageFactory.initElements(driver, SalesOrderPage.class);
	}
}
