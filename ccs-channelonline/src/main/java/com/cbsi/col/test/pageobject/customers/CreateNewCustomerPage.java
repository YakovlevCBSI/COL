package com.cbsi.col.test.pageobject.customers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.ColBasePage;

public class CreateNewCustomerPage extends ColBasePage{
	public CreateNewCustomerPage(WebDriver driver){
		super(driver);
		waitForPageToLoad(By.cssSelector("ul.nav.nav-pipes"));
	}
	
	@FindBy(css="#company")
	private WebElement CompanyName;
	
	@FindBy(css="#address1")
	private WebElement Address;
	
	@FindBy(css="#city")
	private WebElement City;
	
	@FindBy(css="#zip")
	private WebElement Zip;
	
	
	@FindBy(linkText="Next")
	private WebElement Next;
	
	@FindBy(linkText="Finish")
	private WebElement Finish;
	
	@FindBy(linkText="Cancel")
	private WebElement Cancel;
	
	@FindBy(linkText="Previous")
	private WebElement Previous;
	
	public CreateNewCustomerPage setCompanyName(String companyName){
		CompanyName.sendKeys(companyName);
		return this;
	}
	
	public CreateNewCustomerPage setAddress(String address){
		Address.sendKeys(address);
		return this;
	}
	
	public CreateNewCustomerPage setCity(String city){
		City.sendKeys(city);
		return this;
	}
	
	public CreateNewCustomerPage setZip(String zip){
		Zip.sendKeys(zip);
		return this;
	}
	
	public CreateNewCustomerPage clickNext(){
		Next.click();
		forceWait(500);
		
		return PageFactory.initElements(driver, CreateNewCustomerPage.class);
	}
	
	public CreateNewCustomerPage clickFinish(){
		Next.click();
		forceWait(500);
		
		return PageFactory.initElements(driver, CreateNewCustomerPage.class);
	}
	
	public CreateNewCustomerPage clickCancel(){
		Next.click();
		forceWait(500);
		
		return PageFactory.initElements(driver, CreateNewCustomerPage.class);
	}
	
	public CreateNewCustomerPage clickPrevious(){
		Previous.click();
		forceWait(500);
		
		return PageFactory.initElements(driver, CreateNewCustomerPage.class);
	}
}
