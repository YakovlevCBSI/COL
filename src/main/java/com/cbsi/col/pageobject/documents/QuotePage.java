package com.cbsi.col.pageobject.documents;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.home.ColBasePage;
import com.cbsi.col.pageobject.home.ProductsPage;
import com.cbsi.col.test.util.StringUtil;

public class QuotePage extends ColBasePage{

	public QuotePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
//		waitForPageToLoad(By.cssSelector("div h1"));
		waitForTextToBeVisible(15000, "Quote (Open)", "span");
		initializePriceCalculator();
	}
	
	@FindBy(css="input#addProductKeyword")
	private WebElement AddProductSearchBox;
	
	
	@FindBy(css="button#add-product-submit")
	private WebElement Search;
	
	public int getQuoteNumber(){
		String quoteNumber= driver.findElement(By.cssSelector("div.control-column div.readonly-text")).getText();
		quoteNumber = quoteNumber.split("-")[0].trim();
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
		waitForQuickLoad();
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
	
	//----------------------- inner page object: calculator  -----------------------//
	public PriceCalculator priceCalculator;
	
	public void initializePriceCalculator(){
		 priceCalculator = PageFactory.initElements(driver, PriceCalculator.class);
	}
	
	public PriceCalculator getPriceCalculator(){
		return priceCalculator;
	}
	
	public static class PriceCalculator extends ColBasePage{
		public PriceCalculator(WebDriver driver) {
			super(driver);
			// TODO Auto-generated constructor stub
		}

		@FindBy(css="input#taxableTotal")
		private WebElement TaxableItems;
		
		@FindBy(css="input#taxRate")
		private WebElement TaxOn;
		
		@FindBy(css="input#totalTaxAmount")
		private WebElement TotalTaxAmount;
		
		@FindBy(css="input#taxedTotal")
		private WebElement TaxedSubTotal;
		
		@FindBy(css="input#nonTaxableSubtotal")
		private WebElement NontaxableItems;
		
		@FindBy(css="input#shippingAmount")
		private WebElement ShippingAmount;
		
		@FindBy(css="input#miscAmount")
		private WebElement Misc;
		
		@FindBy(css="input#nonTaxableTotal")
		private WebElement NonTaxableSubTotal;
		
		@FindBy(css="input#total-credit")
		private WebElement Total;

		public double getTaxOn() {
			return Double.parseDouble(TaxOn.getAttribute("value"));
		}

		public void setTaxOn(double taxOn) {
			cleanInput(TaxOn);
			TaxOn.sendKeys(taxOn+"");
			TaxOn.sendKeys(Keys.TAB);
		}

		public double getShippingAmount() {
			return Double.parseDouble(StringUtil.cleanCurrency(ShippingAmount.getAttribute("value")));
		}

		public void setShippingAmount(double shippingAmount) {
			cleanInput(ShippingAmount);
			ShippingAmount.sendKeys(shippingAmount+"");
			ShippingAmount.sendKeys(Keys.TAB);
		}

		public double getMisc() {
			return Double.parseDouble(Misc.getAttribute("value"));
		}

		public void setMisc(double misc) {
			cleanInput(Misc);
			Misc.sendKeys(misc+"");
			Misc.sendKeys(Keys.TAB);
		}

		public double getTaxableItems() {
			return Double.parseDouble(StringUtil.cleanCurrency(TaxableItems.getAttribute("value")));
		}

		public double getTotalTaxAmount() {
			return Double.parseDouble(StringUtil.cleanCurrency(TotalTaxAmount.getAttribute("value")));
		}

		public double getTaxedSubTotal() {
			forceWait(300);
			return Double.parseDouble(StringUtil.cleanCurrency(TaxedSubTotal.getAttribute("value")));
		}

		public double getNontaxableItems() {
			return Double.parseDouble(StringUtil.cleanCurrency(NontaxableItems.getAttribute("value")));
		}

		public double getNonTaxableSubTotal() {
			forceWait(300);
			return Double.parseDouble(StringUtil.cleanCurrency(NonTaxableSubTotal.getAttribute("value")));
		}

		public double getTotal() {
			forceWait(300);
			return Double.parseDouble(StringUtil.cleanCurrency(Total.getAttribute("value")));
		}
		
		public double getExpectedTaxedSubTotal(){
			return round(getTaxableItems() + (getTaxableItems() * (getTaxOn()*0.01)),2);
		}
		
		public double getExpectedNontaxableSubTotal(){
			return round(getNontaxableItems() + getShippingAmount() + getMisc(), 2);
		}
		
		public double getExpectedTotal(){
			return getExpectedTaxedSubTotal() + getExpectedNontaxableSubTotal();
		}
		
		public static double round(double value, int places) {
		    if (places < 0) throw new IllegalArgumentException();

		    BigDecimal bd = new BigDecimal(value);
		    bd = bd.setScale(places, RoundingMode.HALF_UP);
		    return bd.doubleValue();
		}
	}
	


}
