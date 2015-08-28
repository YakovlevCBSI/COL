package com.cbsi.col.pageobject.documents;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.documents.QuotePage.CopyToNewQuotePage;
import com.cbsi.col.pageobject.home.ColBasePage;
import com.cbsi.col.pageobject.home.ProductsPage;
import com.cbsi.col.test.util.StringUtil;

public class DocumentsBasePage<T> extends ColBasePage{

	public DocumentsBasePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public int getDocNumber(){
		int quoteInt;
		try{
			String quoteNumber= driver.findElement(By.cssSelector("div.control-column div.readonly-text")).getText();
			quoteNumber = quoteNumber.split("-")[0].trim();
			quoteInt = Integer.parseInt(quoteNumber);
		}catch(NumberFormatException e){			
			String quoteNumber= driver.findElement(By.cssSelector("input#quoteNumber")).getAttribute("value");
			quoteInt = Integer.parseInt(quoteNumber);
		}
		
		return quoteInt;
	}
	//--------------------------  Bottom bar---------------------------//
		@FindBy(css="button#next-action_save")
		private WebElement Save;
		
		@FindBy(css="button#next-action_copyToQuote")
		private WebElement CopyToNewQuote;
		
		@FindBy(css="button[id^='next-action_order']")
		private WebElement ConvertToOrder;
		
		@FindBy(css="button#next-action_send")
		private WebElement Send;
		
		@FindBy(css="div.btn-group.dropup button#next-action_cancelOrder")
		private WebElement CancelThisOrder;
		
		@FindBy(css="div button[id *= '_convertToInvoice']")
		private WebElement ConvertToInvoice;
		
		@FindBy(css="li a[id *= '_completeInvoice']")
		private WebElement CompletInvoice;
		
		public T clickSave(){
			Save.click();
			waitForQuickLoad();
			return (T)this;
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
		
		public T clickSend(){
			Send.click();
			return (T)this;
		}

		public InvoicePage clickConvertToInvoice(){
//			CancelThisOrder.findElement(By.xpath("../button[@id='ld-nextaction-caret']")).click();
			ConvertToInvoice.click();
			return PageFactory.initElements(driver, InvoicePage.class);
			
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
	//-------------------------------- email page-----------------------------------//
	public static class EmailPopup{
		
	}

	//-------------------------------- search func-----------------------------------//
		@FindBy(css="input#addProductKeyword")
		private WebElement AddProductSearchBox;	
		
		@FindBy(css="button#add-product-submit")
		private WebElement Search;
		
		public ProductsPage searchProduct(String productName){
			AddProductSearchBox.sendKeys(productName);
			Search.click();
			
			return PageFactory.initElements(driver, ProductsPage.class);
		}
			
	//--------------------------------  product table-----------------------------------//
		@FindBy(css="table.line-item-reorder")
		private WebElement productTable;
		public T selectProductFromTable(int...n){
			for(int nth: n){
				productTable.findElement(By.xpath("tbody/tr[" + nth + "]/td/label/input")).click();
			}
			return (T)this;
		}
}
