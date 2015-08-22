package com.cbsi.col.pageobject.customers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.home.ColBasePage;

public class CreateAccountPage extends ColBasePage{
	public CreateAccountPage(WebDriver driver){
		super(driver);
//		waitForPageToLoad(By.cssSelector("ul.nav.nav-pipes"));
		waitForTextToBeVisible(15000,"Create", "h1");
	}
	
	@FindBy(css="#company")
	private WebElement CompanyName;
	
	@FindBy(css="#address1")
	private WebElement Address;
	
	@FindBy(css="#city")
	private WebElement City;
	
	@FindBy(css="#zip")
	private WebElement Zip;

	
	@FindBy(css="input#fname")
	private WebElement FirstName;
	
	@FindBy(css="input#lname")
	private WebElement LastName;
	
	@FindBy(css="input#email")
	private WebElement Email;
	
	@FindBy(linkText="Next")
	private WebElement Next;
	
	@FindBy(css="button[title='Save']")
	private WebElement Finish;
	
	@FindBy(linkText="Cancel")
	private WebElement Cancel;
	
	@FindBy(linkText="Previous")
	private WebElement Previous;
	
	@FindBy(linkText="Save")
	private WebElement Save;
	
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
	
	public CreateAccountPage setFirstName(String firstName){
		FirstName.sendKeys(firstName);
		return this;
	}
	
	public CreateAccountPage setLastName(String lastName){
		LastName.sendKeys(lastName);
		return this;
	}
	
	public CreateAccountPage setEmail(String email){
		Email.sendKeys(email);
		return this;
	}
	
	public CreateAccountPage clickNext(){
		Next.click();
		forceWait(500);
		
		return PageFactory.initElements(driver, CreateAccountPage.class);
	}
	
	public CreateAccountPage clickFinish(){
		Finish.click();
		forceWait(2000);
		
		return PageFactory.initElements(driver, CreateAccountPage.class);
	}
	
	public CreateAccountPage clickCancel(){
		Cancel.click();
		forceWait(500);
		
		return PageFactory.initElements(driver, CreateAccountPage.class);
	}
	
	public CreateAccountPage clickPrevious(){
		Previous.click();
		forceWait(500);
		
		return PageFactory.initElements(driver, CreateAccountPage.class);
	}
	
	public CurrentAccountTab clickSave(){
		Save.click();
		forceWait(500);
		
		return PageFactory.initElements(driver, CurrentAccountTab.class);
	}
	

}
