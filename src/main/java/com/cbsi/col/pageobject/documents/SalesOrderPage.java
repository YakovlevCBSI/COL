package com.cbsi.col.pageobject.documents;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.home.ColBasePage;
import com.cbsi.col.pageobject.purchaseorders.PurchaseOrdersTab;

public class SalesOrderPage extends DocumentsBasePage{
	public SalesOrderPage(WebDriver driver) throws Exception {
		super(driver);
		// TODO Auto-generated constructor stub
		
		waitForMultiHeader();
	}
	
	public void waitForMultiHeader() throws Exception{		
		long start = System.currentTimeMillis();
		boolean headerFound= false;
		while(System.currentTimeMillis() - start <= 10000){
			try{
//				waitForPageToLoad(By.cssSelector("form div p"));

				if(waitForTextToBeVisible(1000, "Addresses", "b")) {
					System.out.println("Found Address header. Break!");
					headerFound = true;
					break;
				}
			}catch(Exception e){
				
			}
			
			try{
				if(waitForTextToBeVisible(1000, "Sales Order (", "span")) {
					System.out.println("Found Sales Order Header. Break!");
					headerFound = true;
					break;
				}
			}catch(Exception e){
				
			}
		}
		
		if(!headerFound) throw new Exception("failed to invoke Sales order page");
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
		
		try{
			Save.click();
			waitForElementToBeVisible(By.cssSelector("input[name='ponumber']"), 10);
		}catch(TimeoutException e){
			waitForTextToBeVisible("Sales Order (Submitted)", "span");
		}catch(UnhandledAlertException e){
			acceptAlert();
		}catch(NoSuchElementException e){
			super.clickSave(); //if save not found, click the button from parent class.
		}
//		return this;
		return PageFactory.initElements(driver, SalesOrderPage.class);
	}

	public SalesOrderPage clickCancel(){
		Cancel.click();
		return this;
	}

	public static class CreatePoPopup extends ColBasePage{

		public CreatePoPopup(WebDriver driver) {
			super(driver);
			// TODO Auto-generated constructor stub
			switchFrame(By.cssSelector("iframe"));
			waitForTextToBeVisible("Create POs", "h3");
		}
		@FindBy(css="span.btn-save")
		private WebElement CreatePos;
		
		public PurchaseOrdersTab clickCreatePos(){
			CreatePos.click();
			switchBack();
			return PageFactory.initElements(driver, PurchaseOrdersTab.class);
		}
	}
}
