package com.cbsi.col.pageobject.customers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cbsi.col.pageobject.documents.DocumentsPage;
import com.cbsi.col.pageobject.documents.QuotePage;
import com.cbsi.col.pageobject.home.ColBasePage;
import com.cbsi.col.pageobject.home.SearchPopup.QueryColumn;
import com.cbsi.col.pageobject.home.SearchPopup.QueryOption;
import com.cbsi.col.test.util.StringUtil;

public class AccountsPage extends ColBasePage{
	public final Logger logger = LoggerFactory.getLogger(ColBasePage.class);

	public AccountsPage(WebDriver driver){
		super(driver);
		try{
			waitForQuickLoad();
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
	private WebElement CreateAccount;
	
	public CreateAccountPage clickCreateNewAccount(AccountType accountType){
//		waitForElementToBeClickable(By.cssSelector("a[href='https://ccs.stage.channelonline.com/acme/home/Customers/create']"));
		List<WebElement> list = driver.findElements(By.cssSelector("a"));
//		for(WebElement e: list){
//			System.out.println(e.getAttribute("href"));
//		}
//		forceWait(3000);
		CreateAccount.click();

		waitForTextToBeVisible("Create Account");
		forceWait(500);
		CreateAccountPopup createAccountPopup = PageFactory.initElements(driver, CreateAccountPopup.class);
		return createAccountPopup.pickAccountType(accountType);
	}
	
	
	public boolean hasCompany(String companyName){
		return findDataRowByName(companyName)!=null?true:false;
	}
	
	public boolean hasCompany(String companyName, AccountType type){
		return findDataRowByName(companyName, type)!=null?true:false;
	}
	
	
//	//UNCOMMENT ABOVE ONCE THIS BUG IS FIXED 5667
//	public boolean hasCompany(String companyName){
//		AccountsPage accountPage = searchFor(QueryOption.Customers, true, QueryColumn.All, companyName, AccountsPage.class);
//		return accountPage.findDataRowByName(companyName)!=null?true:false;
//	}
	
	@FindBy(css="button#delete-customer-btn")
	private WebElement DeleteInPopup;
	
	public AccountsPage deleteCompany(String companyName){
		WebElement dataRow = findDataRowByName(companyName, null);
		WebElement deleteButton = dataRow.findElement(By.xpath("../td/a[contains(@id,'delete')]"));
		deleteButton.click();
		
		acceptAlert();
//		waitForElementToBeVisible(By.cssSelector("button#delete-customer-btn"));	
//		DeleteInPopup.click();
		
		return PageFactory.initElements(driver, AccountsPage.class);
	}
	
	public WebElement findDataRowByName(String companyName){
		return findDataRowByName(companyName, AccountType.CUSTOMER);
	}
	
	private static List<WebElement> dataColumns;
	int currentPage=0;
	public WebElement findDataRowByName(String companyName, AccountType type){
		dataColumns = driver.findElements(By.cssSelector("table.costandard tbody tr td:nth-child(3)"));
		for(WebElement dataColumn: dataColumns){
//			logger.debug(dataColumn.getText() + " / " + type.toString() + " / " + companyName);
//			logger.debug(dataColumn.findElement(By.xpath("../td[4]")).getText());
//			System.out.println(type != null);
//			System.out.println(dataColumn.findElement(By.xpath("../td[3]")).getText() + " / " + (companyName));
//			System.out.println(dataColumn.findElement(By.xpath("../td[4]")).getText() + " / " + (type.toString()));
			if(type != null && dataColumn.findElement(By.xpath("../td[3]")).getText().toLowerCase().contains(companyName.toLowerCase()) && dataColumn.findElement(By.xpath("../td[4]")).getText().toUpperCase().equals(type.toString())){  //only search for customer.

				logger.info("FOUND THE TEXT: " + companyName);
				return dataColumn;
			}
			else if(type == null && dataColumn.getText().toLowerCase().contains(companyName.toLowerCase())){
				return dataColumn;
			}
		}
		
		List<WebElement> pageList = driver.findElements(By.cssSelector("tr.footer td a"));
		if(currentPage-1 >=0){
			int removePage = currentPage;
			while(removePage >0){
				removePage--;
				pageList.remove(removePage);
			}
		}
		if(pageList.size() >=1){
			currentPage++;
			pageList.get(0).click();
			waitForTextToBeVisible("Accounts", "h1");
			dataColumns=null;
			return findDataRowByName(companyName, type);
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
//		setFilterByAccountType(AccountType.CUSTOMER);
		WebElement dataRow = findDataRowByName(companyName);
		if(dataRow == null){
			dataRow = findDataRowByName(companyName, AccountType.LEAD);
		}
		
		ViewCustomer = dataRow.findElement(By.xpath("../td/a[contains(@title,'View Customer')]"));
		
		if(ViewCustomer == null){
			ViewCustomer = dataRow.findElement(By.xpath("../td/a[contains(@title,'View Account')]"));
		}
		
		ViewCustomer.click();
		
		waitForElementToBeInvisible(By.xpath("td/a[contains(@title,'View Customer')]"));
		return PageFactory.initElements(driver, CurrentAccountTab.class);
	}
	
	public static class CreateAccountPopup extends ColBasePage{
		private String accountType;
		public CreateAccountPopup(WebDriver driver){
//			forceWait(5000);
			super(driver);
//			waitForElementToBeVisible(By.cssSelector("h3"));
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
		
		@FindBy(css="input[value='client']")
		private WebElement Generic;
		
		@FindBy(css="button#save-tpl-btn")
		private WebElement OK;
		
		public CreateAccountPage pickAccountType(AccountType accountType){
			pickAccountTypeSimple(accountType);
			return PageFactory.initElements(driver, CreateAccountPage.class);
		}	
		
		public void pickAccountTypeSimple(AccountType accountType){
			this.accountType = accountType.toString().toLowerCase();
			
			if(this.accountType.equals("customer")){
				logger.info("clicked customer");
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
			
			try{
				waitForElementToBeVisible(By.cssSelector("button#save-tpl-btn"), 3);
			}catch(Exception e){
				
			}
//			OK.click();
			
			try{
				OK.click();
			}catch(NoSuchElementException e){ //for convertAccount in Lead.
				OK = driver.findElement(By.xpath("//button[@id='convert-tpl-btn']"));
				OK.click();
			}
			
			waitForQuickLoad();
		}
	}
	
	@FindBy(linkText="Recent Accounts")
	private WebElement RecentCustomers;
	public RecentAccountsTab goToRecentCustomersTab(){
		RecentCustomers.click();
		return PageFactory.initElements(driver, RecentAccountsTab.class);
	}
	
	//------------------------ creat account method  --------------------//
	
	public enum AccountType{
		CUSTOMER,
		LEAD,
		PROSPECT,
		PARTNER,
		VENDOR,
		GENERIC	
	}
	
	//------------------------ table result from search  --------------------//

	@FindBy(css="table.costandard")
	WebElement table;
	
	public List<HashMap<String, String>> getTableAsMaps(){
		return getTableAsMaps(table, 0, 11);
	}
	
	//------------------------table filter------------------------//
	@FindBy(css="select#accountType")
	private WebElement TypeDropdown;
	
	public AccountsPage setFilterByAccountType(AccountType type){
		TypeDropdown.click();
		driver.findElement(By.cssSelector("option[value='" + StringUtil.camelCase(type.toString()) + "']")).click();
		forceWait(500);
		return PageFactory.initElements(driver, AccountsPage.class);
	}
}
