package com.cbsi.col.pageobject.documents;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.home.ColBasePage;

public class OrderOptionsPage<T> extends DocumentsBasePage{

	public OrderOptionsPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForTextToBeVisible("Verify your customer's payment", "p.lead");
	}
	
	//-----------------------Sales order payment page---------------------//
		@FindBy(css="input[name='ponumber']")
		private WebElement PONumber;
		
		@FindBy(css="input[type='radio'][value='po']")
		private WebElement Terms;
		
		@FindBy(css="input[type='radio'][value='money_order']")
		private WebElement MoneyOrder;
		
		@FindBy(css="input[type='radio'][value='cod']")
		private WebElement COD;
		
		@FindBy(css="select[name='delivery']")
		private WebElement DeliveryMethod;
//		@FindBy(css="input[type='radio][value='money_order']")
//		private WebElement Leasing;
		
		public OrderOptionsPage setPaymentMethod(Payment payment){
			switch(payment){
			case Terms:
				Terms.click();
				break;
			case MoneyOrder:
				MoneyOrder.click();
				driver.findElement(By.cssSelector("input[name='transaction_number']")).sendKeys("123");

				break;
			case COD:
				COD.click();
				break;

			default:
				break;
			}
			return this;

		}
		
		public OrderOptionsPage setPoNumber(int num){
			PONumber.sendKeys(num+"");
			return this;
		}
		
		public OrderOptionsPage setPoNumberAndPaymentMethod(int num, Payment payment){
			return setPoNumber(num).setPaymentMethod(payment);
		}
		
		public OrderOptionsPage selectDelieveryMethod(Delivery option){
			DeliveryMethod.click();
			List<WebElement> options = driver.findElements(By.cssSelector("select[name='delivery'] option"));
			
			switch(option){
				case UPSNextDayAir:
						options.get(0).click();
					break;
				case UPS2ndDayAir:
						options.get(1).click();
						break;
				default:
			}
			
			return this;
		}
		
//		@FindBy(css="#crm-main-pane-body > font > a:nth-child(1)")
		@FindBy(css="div#footer-actions div a.btn")
		private WebElement Save;
		
		public <T> T clickSave(Class clazz){
			System.out.println("save size : " +driver.findElements(By.cssSelector("div#footer-actions div a[href*='submit_page(\\'save\\')']")).size());
			for(WebElement w:driver.findElements(By.cssSelector("div#footer-actions div a[href*='submit_page(\\'save\\')']"))){
				if(w.isDisplayed()){
					Save = w;
					break;
				}
			}
			Save.click();
			System.out.println("Clicked on save. Invoking " + clazz.getName() + " now");
			return (T) PageFactory.initElements(driver, clazz);
		}
		
		public enum Payment{
			Terms,
			MoneyOrder,
			COD,
			Leasing
		}
		
		public enum Delivery{
			UPSNextDayAir,
			UPS2ndDayAir,
			UPSGround,
			UPS3DaySelect,
			UPS2ndDayAirAM,
			UPSNExtDayAirSaver,
			UPS3DaySelectResidential
		}

}
