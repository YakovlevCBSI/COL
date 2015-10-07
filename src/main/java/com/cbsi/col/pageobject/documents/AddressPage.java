package com.cbsi.col.pageobject.documents;

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
		waitForTextToBeVisible(1000, "Addresses", "b");
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
		Email.clear();
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
	
	public AddressPage clickCopyToShipping(){
		CopyToShipping.click();
		return this;
	}
	
	
	//-----------------------Bottom bar options---------------------//
	@FindBy(linkText="Save")
	private WebElement Save;

	public OrderOptionsPage clickSave(){
		Save.click();
		return PageFactory.initElements(driver, OrderOptionsPage.class);
	}
}
