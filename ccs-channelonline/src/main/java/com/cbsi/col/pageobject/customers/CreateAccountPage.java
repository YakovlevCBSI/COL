package com.cbsi.col.pageobject.customers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.ColBasePage;

public class CreateAccountPage extends ColBasePage{
	public CreateAccountPage(WebDriver driver){
		super(driver);
//		waitForPageToLoad(By.cssSelector("ul.nav.nav-pipes"));
		waitForTextToBeVisible(15000,"Create New Customer", "h1");
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
	
	public CreateAccountPage setCompanyName(String companyName){
		CompanyName.sendKeys(companyName);
		return this;
	}
	
	public CreateAccountPage setAddress(String address){
		Address.sendKeys(address);
		return this;
	}
	
	public CreateAccountPage setCity(String city){
		City.sendKeys(city);
		return this;
	}
	
	public CreateAccountPage setZip(String zip){
		Zip.sendKeys(zip);
		return this;
	}
	
	public CreateAccountPage clickNext(){
		Next.click();
		forceWait(500);
		
		return PageFactory.initElements(driver, CreateAccountPage.class);
	}
	
	public CreateAccountPage clickFinish(){
		Next.click();
		forceWait(500);
		
		return PageFactory.initElements(driver, CreateAccountPage.class);
	}
	
	public CreateAccountPage clickCancel(){
		Next.click();
		forceWait(500);
		
		return PageFactory.initElements(driver, CreateAccountPage.class);
	}
	
	public CreateAccountPage clickPrevious(){
		Previous.click();
		forceWait(500);
		
		return PageFactory.initElements(driver, CreateAccountPage.class);
	}
}
