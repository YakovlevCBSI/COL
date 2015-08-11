package com.cbsi.col.pageobject.customers;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.ColBasePage;
import com.cbsi.col.pageobject.documents.DocumentsPage;
import com.cbsi.col.pageobject.documents.QuotePage;

public class AccountsPage extends ColBasePage{
	public AccountsPage(WebDriver driver){
		super(driver);
		try{
			waitForPageToLoad(By.cssSelector("form#customerForm h1"), 10);
		}catch(Exception e){
			
		}
	}
	
	//-------------------- List of sub-tabs ----------------------//
	@FindBy(css="ul.nav li a[href*='Customers/view']")
	private WebElement CurrentAccount;
	
	@FindBy(linkText="All Accounts")
	private WebElement AllAccounts;
	
	@FindBy(linkText="Companies")
	private WebElement Companies;
	
	@FindBy(linkText="Contacts")
	private WebElement Contacts;
	
	@FindBy(linkText="Recent Accounts")
	private WebElement RecentAccounts;
	
	public CurrentAccountTab goToCurrentAccountTab(){
		CurrentAccount.click();
		return PageFactory.initElements(driver, CurrentAccountTab.class);
	}
	
	public AllAccountsTab goToAllAcountsTab(){
		AllAccounts.click();
		return PageFactory.initElements(driver, AllAccountsTab.class);
	}
	
	public CompaniesTab goToComapniesTab(){
		Companies.click();
		return PageFactory.initElements(driver, CompaniesTab.class);
	}
	
	public ContactsTab goToContactsTab(){
		Contacts.click();
		return PageFactory.initElements(driver, ContactsTab.class);
	}
	
	public RecentAccountsTab goToRecentAccountsTab(){
		RecentAccounts.click();
		return PageFactory.initElements(driver, RecentAccountsTab.class);
	}
	
	//-----------------------------------------------------------------//
	@FindBy(linkText="Create Account")
	private WebElement CreateNewCustomer;
	
	public CreateAccountPage clickCreateNewCustomer(String accountType){
//		waitForElementToBeClickable(By.cssSelector("a[href='https://ccs.stage.channelonline.com/acme/home/Customers/create']"));
		List<WebElement> list = driver.findElements(By.cssSelector("a"));
//		for(WebElement e: list){
//			System.out.println(e.getAttribute("href"));
//		}
//		forceWait(3000);
		CreateNewCustomer.click();

		waitForTextToBeVisible("Create Account");
		CreateAccountPopup createAccountPopup = PageFactory.initElements(driver, CreateAccountPopup.class);
		return createAccountPopup.pickAccountType(accountType);
	}
	
	private List<WebElement> dataColumns;
	
	public boolean hasCompany(String companyName){
		return findDataRowByName(companyName)!=null?true:false;
	}
	
	@FindBy(css="button#delete-customer-btn")
	private WebElement DeleteInPopup;
	
	public AccountsPage deleteCompany(String companyName){
		WebElement dataRow = findDataRowByName(companyName);
		WebElement deleteButton = dataRow.findElement(By.xpath("../td/a[contains(@id,'delete')]"));
		deleteButton.click();
		
		acceptAlert();
//		waitForElementToBeVisible(By.cssSelector("button#delete-customer-btn"));	
//		DeleteInPopup.click();
		
		return PageFactory.initElements(driver, AccountsPage.class);
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
	
	@FindBy(css="a[title='View Customer']")
	private WebElement ViewCustomer;
	
	public AccountsPage clickViewCustomer(){
		ViewCustomer.click();
		return this;
	}
	
	public CurrentAccountTab clickViewCustomer(String companyName){
		WebElement dataRow = findDataRowByName(companyName);
		ViewCustomer = dataRow.findElement(By.xpath("../td/a[contains(@title,'View Customer')]"));
		ViewCustomer.click();
		
		waitForElementToBeInvisible(By.xpath("td/a[contains(@title,'View Customer')]"));
		return PageFactory.initElements(driver, CurrentAccountTab.class);
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
		
		public CreateAccountPage pickAccountType(String accountType){
			this.accountType = accountType.toLowerCase();
			
			if(this.accountType.equals("customer")){
				System.out.println("clicked customer");
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
//			waitForElementToBeVisible(By.cssSelector("button#save-tpl-btn"));
//			OK.click();
			OK.click();
			return PageFactory.initElements(driver, CreateAccountPage.class);
		}	
	}
	
	@FindBy(linkText="Recent Accounts")
	private WebElement RecentCustomers;
	public RecentAccountsTab goToRecentCustomersTab(){
		RecentCustomers.click();
		return PageFactory.initElements(driver, RecentAccountsTab.class);
	}
}
