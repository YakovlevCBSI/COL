package com.cbsi.col.pageobject.suppliers;

import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.home.ColBasePage;

public class CreateSupplierPage extends ColBasePage{

	public CreateSupplierPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForTextToBeVisible("Create New Supplier", "h1");
	}
	
	@FindBy(css="input[name='company']")
	private WebElement Company;
	
	public CreateSupplierPage setCompany(String name){
		Company.sendKeys(name);
		return this;
	}
	
	@FindBy(css="a[href*=':next()'")
	private WebElement Next;
	
	@FindBy(css="a[href*=':finish()']")
	private WebElement Finish;
	
	@FindBy(css="a[href*=':cancel()']")
	private WebElement Cancel;
	
	public SuppliersPage clickFinish(){
		Finish.click();
		acceptAlert();
		
		try{
			acceptAlert();
		}catch(NoAlertPresentException Ex){
			
		}
		
		return PageFactory.initElements(driver, SuppliersPage.class);
	}
}
