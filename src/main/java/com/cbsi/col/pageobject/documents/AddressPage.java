package com.cbsi.col.pageobject.documents;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cbsi.col.pageobject.home.ColBasePage;

public class AddressPage extends ColBasePage{
	public final Logger logger = LoggerFactory.getLogger(AddressPage.class);

	public AddressPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		logger.info(getClass().getName());
		waitForTextToBeVisible(5000, "Addresses", "h1");
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

	@FindBy(css="a#btn-add-shipping-address")
	private WebElement Add;

	public void setFirstName(String firstName) {
		FirstName.clear();
		FirstName.sendKeys(firstName);
	}

	public void setLastName(String  lastName) {
		LastName.clear();
		LastName.sendKeys(lastName);
	}

	public void setEmail(String email) {
//		Email.clear();
		Email.sendKeys(email);
	}

	public void setAddress(String address) {
//		Address.clear();
		Address.sendKeys(address);
	}

	public void setCity(String city) {
//		City.clear();
		City.sendKeys(city);
	}

	public void setZip(String zip) {
//		Zip.clear();
		Zip.sendKeys(zip);;
	}
	
	@FindBy(css="input[name='copy_b_to_s']")
	private WebElement CopyToShipping;
	
	@FindBy(css="select#shipping_list")
	private WebElement ShppingAddressDropdown;
	
	public AddressPage clickCopyToShipping(){
		CopyToShipping.click();
		return this;
	}
	
	public AddressPage clickAddShipping(){
		Add.click();
		forceWait(1500);
		return PageFactory.initElements(driver, AddressPage.class);
	}

	public String getSelectedShippingAddress(){
		for(WebElement w: ShppingAddressDropdown.findElements(By.xpath("option"))){
			if(w.getAttribute("value").equals("-1")) continue;
			
			if(w.getAttribute("selected") != null){
				return w.getText().trim();
			}
		}
		
		return null;
	}
	//-----------------------Bottom bar options---------------------//
	@FindBy(linkText="Save")
	private WebElement Save;

	public OrderOptionsPage clickSave(){
		Save.click();
		return PageFactory.initElements(driver, OrderOptionsPage.class);
	}
	
	public <T>T clickSave(Class clazz){
		Save.click();
		return (T)PageFactory.initElements(driver, clazz);
	}
}
