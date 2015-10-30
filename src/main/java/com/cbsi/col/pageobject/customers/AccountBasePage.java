package com.cbsi.col.pageobject.customers;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.home.ColBasePage;

public class AccountBasePage extends ColBasePage{

	public AccountBasePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(css="#company")
	protected WebElement CompanyName;
	
	@FindBy(css="#address1")
	protected WebElement Address;
	
	@FindBy(css="#city")
	protected WebElement City;
	
	@FindBy(css="#zip")
	protected WebElement Zip;

	@FindBy(css="input#fname")
	protected WebElement FirstName;
	
	@FindBy(css="input#lname")
	protected WebElement LastName;
	
	@FindBy(css="input#email")
	protected WebElement Email;
	
	@FindBy(linkText="Next")
	protected WebElement Next;
	
//	@FindBy(css="button[title='Finish']")
	@FindBy(css="a[title='Finish']")
	protected WebElement Finish;
	
	@FindBy(linkText="Cancel")
	protected WebElement Cancel;
	
	@FindBy(linkText="Previous")
	protected WebElement Previous;
	
	@FindBy(linkText="Save")
	protected WebElement Save;
	
	@FindBy(css="button#update-acct-btn")
	protected WebElement SaveButton;
	
	@FindBy(css="select#default_payment_option_id")
	protected WebElement DefaultPaymentOption;
	
	@FindBy(css="option[value='2']")
	protected WebElement COD;
	
	
	@FindBy(css="input[name='copy_b_to_s']")
	private WebElement Copy;
	
	//----------------------Lead-speicifc fields-------------------------//
	@FindBy(css="input[id*='firstname']")
	private WebElement ContactInfo_FirstName;
	
	@FindBy(css="input[id*='lastname']")
	private WebElement ContactInfo_LastName;
	
	@FindBy(css="input#default")
	protected WebElement DesignateThisPerson;
	//--------------------------------------------------------------------//
	
	
	public <T>T clickSave(){

		Save.click();
		
		if(this instanceof EditAccountPage){
			return (T) PageFactory.initElements(driver, CurrentAccountTab.class);
		}
		
		return (T) PageFactory.initElements(driver, this.getClass());
	}
	
	public AccountBasePage clickCopy(){
		Copy.click();
		return this;
	}
	
	public AccountBasePage setCompanyName(String companyName){
		CompanyName.sendKeys(companyName);
		return this;
	}
	
	public AccountBasePage setAddress(String address){
		Address.sendKeys(address);
		return this;
	}
	
	public AccountBasePage setCity(String city){
		City.sendKeys(city);
		return this;
	}
	
	public AccountBasePage setZip(String zip){
		Zip.sendKeys(zip);
		return this;
	}
	
	public AccountBasePage setFirstName(String firstName){
		FirstName.sendKeys(firstName);
		return this;
	}
	
	public AccountBasePage setLastName(String lastName){
		LastName.sendKeys(lastName);
		return this;
	}
	
	public AccountBasePage setContactInfo_FirstName(String firstName){
		ContactInfo_FirstName.sendKeys(firstName);
		return this;
	}
	
	public AccountBasePage setContactInfo_LastName(String lastName){
		ContactInfo_LastName.sendKeys(lastName);
		return this;
	}
	
	public AccountBasePage setEmail(String email){
		Email.sendKeys(email);
		return this;
	}
	
	public AccountBasePage clickNext(){
		Next.click();
		forceWait(500);
		return PageFactory.initElements(driver, CreateAccountPage.class);
	}
	
	public AccountBasePage clickFinish(){
		Finish.click();
		forceWait(2000);
		
//		return PageFactory.initElements(driver, CreateAccountPage.class);
		return this;

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

	public CurrentAccountTab clickSaveButton(){
		SaveButton.click();
		forceWait(500);
		
		return PageFactory.initElements(driver, CurrentAccountTab.class);
	}
	
	public AccountBasePage setDefaultContact(){
		DesignateThisPerson.click();
		return this;
	}
	
	public AccountBasePage setDefaultPaymentToCod(){
		scrollToView(Next);
		DefaultPaymentOption.click();
		forceWait(3000);
		COD.click();
		return this;
	}
	
	public AccountBasePage setContactInfo_CompanyName(String companyName){
		List<WebElement> companyNameDivs= driver.findElements(By.cssSelector("div.control-group label[for='name']"));
		WebElement companyNameDiv = null;
		for(WebElement w: companyNameDivs){
			if(w.getText().contains("Company Name")){
				companyNameDiv = w;
				break;
			}
		}
		
		WebElement companyInput = companyNameDiv.findElement(By.xpath("../div/input[@id='name']"));
		companyInput.sendKeys(companyName);
		
		return this;
	}

}
