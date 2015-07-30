package com.cbsi.col.test.pageobject.customers;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.ColBasePage;
import com.cbsi.col.pageobject.DocumentsPage;

public class CustomersPage extends ColBasePage{
	public CustomersPage(WebDriver driver){
		super(driver);
		waitForPageToLoad(By.cssSelector("form#customerForm h1"));
	}
	
	@FindBy(css="td a[href*='createQuote?']")
	private WebElement CreateQuote;
	
	@FindBy(linkText="Create New Customer")
	private WebElement CreateNewCustomer;
	
	public DocumentsPage ClickCreateQuote(){
		CreateQuote.click();
		
		return PageFactory.initElements(driver, DocumentsPage.class);
	}
	
	public CreateNewCustomerPage clickCreateNewCustomer(String accountType){
//		waitForElementToBeClickable(By.cssSelector("a[href='https://ccs.stage.channelonline.com/acme/home/Customers/create']"));
		List<WebElement> list = driver.findElements(By.cssSelector("a"));
//		for(WebElement e: list){
//			System.out.println(e.getAttribute("href"));
//		}
//		forceWait(3000);
		CreateNewCustomer.click();

		forceWait(5000);
		CreateAccountPopup createAccountPopup = PageFactory.initElements(driver, CreateAccountPopup.class);
		return createAccountPopup.pickAccountType(accountType);
	}
	
	private List<WebElement> dataColumns;
	
	public boolean hasCompany(String companyName){
		return findDataRowByName(companyName)!=null?true:false;
	}
	
	@FindBy(css="button#delete-customer-btn")
	private WebElement DeleteInPopup;
	
	public CustomersPage deleteCompany(String companyName){
		WebElement dataRow = findDataRowByName(companyName);
		WebElement deleteButton = dataRow.findElement(By.xpath("../td/a[contains(@id,'delete')]"));
		deleteButton.click();
		
		waitForElementToBeVisible(By.cssSelector("button#delete-customer-btn"));
		DeleteInPopup.click();
		
		return PageFactory.initElements(driver, CustomersPage.class);
	}
	
	public WebElement findDataRowByName(String companyName){
		dataColumns = driver.findElements(By.cssSelector("table.costandard tbody tr td:nth-child(3)"));

		for(WebElement dataColumn: dataColumns){
			if(dataColumn.getText().contains(companyName)){
				return dataColumn;
			}
		}
		
		return null;		
	}
	
	public static class CreateAccountPopup extends ColBasePage{
		private String accountType;
		public CreateAccountPopup(WebDriver driver){
//			forceWait(5000);
			super(driver);
			waitForElementToBeVisible(By.cssSelector("div.modal-header h3"));
		}
		
		@FindBy(css="input[value='customer']")
		private WebElement Customer;
		
		@FindBy(css="input[value='lead']")
		private WebElement Lead;
		
		@FindBy(css="input[value='prospect']")
		private WebElement Prospect;
		
		@FindBy(css="input[value='partner']")
		private WebElement Partner;
		
		@FindBy(css="input[value='vendor']")
		private WebElement Vendor;
		
		@FindBy(css="input[value='generic']")
		private WebElement Generic;
		
		@FindBy(css="button#save-tpl-btn")
		private WebElement OK;
		
		public CreateNewCustomerPage pickAccountType(String accountType){
			this.accountType = accountType.toLowerCase();
			
			if(this.accountType.equals("customer")){
				Customer.click();
			}else if(this.accountType.equals("lead")){
				Lead.click();
			}else if(this.accountType.equals("prospect")){
				Prospect.click();
			}else if(this.accountType.equals("partner")){
				Partner.click();
			}else if(this.accountType.equals("vendor")){
				Vendor.click();
			}else if(this.accountType.equals("generic")){
				Generic.click();
			}
			forceWait(500);
			OK.click();
			return PageFactory.initElements(driver, CreateNewCustomerPage.class);
		}	
	}
	
	@FindBy(linkText="Recent Customers")
	private WebElement RecentCustomers;
	public RecentCustomersPage goToRecentCustomersPage(){
		RecentCustomers.click();
		return PageFactory.initElements(driver, RecentCustomersPage.class);
	}
}
