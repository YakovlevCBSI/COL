package com.cbsi.col.pageobject.documents;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.ColBasePage;

public class SalesOrderPage extends ColBasePage{

	public SalesOrderPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForPageToLoad(By.cssSelector("form div p"));
		waitForTextToBeVisible("Addresses", "b");
	}
	
	@FindBy(css="#billing_FirstName")
	private WebElement FirstName;
	
	@FindBy(css="#billing_LastName")
	private WebElement LastName;
	
	@FindBy(css="#billing_Email")
	private WebElement Email;
	
	@FindBy(css="#billing_Address1")
	private WebElement Address;
	
	@FindBy(css="#billing_City")
	private WebElement City;
	
	@FindBy(css="#billing_Zip")
	private WebElement Zip;

	public void setFirstName(String firstName) {
		FirstName.sendKeys(firstName);
	}

	public void setLastName(String  lastName) {
		LastName.sendKeys(lastName);
	}

	public void setEmail(String email) {
		Email.sendKeys(email);
	}

	public void setAddress(String address) {
		Address.sendKeys(address);
	}

	public void setCity(String city) {
		City.sendKeys(city);
	}

	public void setZip(String zip) {
		Zip.sendKeys(zip);;
	}
	
	@FindBy(css="input[name='copy_b_to_s']")
	private WebElement CopyToShipping;
	
	public SalesOrderPage clickCopyToShipping(){
		CopyToShipping.click();
		return this;
	}
	
	
	//-----------------------Bottom bar options---------------------//
	@FindBy(linkText="Save")
	private WebElement Save;

	@FindBy(linkText="Cancel")
	private WebElement Cancel;
	
	
	//There is an addToOrder(?) page. work on it whenever needed.
	public SalesOrderPage clickSave(){
		Save.click();
		waitForPageToLoad(By.cssSelector("input[name='ponumber']"));
		return this;
//		return PageFactory.initElements(driver, AddToQuotePage.class);
	}

	public SalesOrderPage clickCancel(){
		Cancel.click();
		return this;
	}
	
	//-----------------------Sales order payment page---------------------//
	@FindBy(css="input[name='ponumber']")
	private WebElement PONumber;
	
	@FindBy(css="input[type='radio][value='po']")
	private WebElement Terms;
	
	@FindBy(css="input[type='radio][value='money_order']")
	private WebElement MoneyOrder;
	
	@FindBy(css="input[type='radio][value='cod']")
	private WebElement COD;
	
	@FindBy(css="select[name='delivery']")
	private WebElement DeliveryMethod;
//	@FindBy(css="input[type='radio][value='money_order']")
//	private WebElement Leasing;
	
	public SalesOrderPage selectDelieveryMethod(Delivery option){
		DeliveryMethod.click();
		List<WebElement> options = driver.findElements(By.cssSelector("select[name='delivery'] option"));
		
		switch(option){
			case UPSNextDayAir:
					options.get(0).click();
				break;
			case UPS2ndDayAir:
					options.get(1).click();
			default:
		}
		return this;
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
