package com.cbsi.col.pageobject.customers;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.customers.AccountsPage.AccountType;
import com.cbsi.col.pageobject.customers.AccountsPage.CreateAccountPopup;
import com.cbsi.col.pageobject.documents.AddressPage;
import com.cbsi.col.pageobject.documents.DocumentsPage;
import com.cbsi.col.pageobject.documents.ProposalPage;
import com.cbsi.col.pageobject.documents.QuotePage;
import com.cbsi.col.pageobject.home.ColBasePage;

public class CurrentAccountTab extends ColBasePage{

	public CurrentAccountTab(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
//		waitForPageToLoad(By.cssSelector("li.active a[href*='Customers/view']"));
		waitForTextToBeVisible("Account View", "h1 span");
	}
	
	@FindBy(linkText="Create Quote")
	private WebElement CreateQuote;
	
//	@FindBy(css="#createProposalFrame")
	@FindBy(linkText="Create Proposal")
	private WebElement CreateProposal;
	
	@FindBy(css="div a[href*='Rmas/createSales?']")
	private WebElement CreateRMA;
	
	@FindBy(css="div a[href*='list?deleted=']")
	private WebElement Recyclebin;
	
	@FindBy(linkText="Add a Contact")
	private WebElement AddAContact;
	
	@FindBy(linkText="Merge")
	private WebElement Merge;
	
	@FindBy(linkText="Delete")
	private WebElement Delete;
	
	@FindBy(linkText="Convert Account Type")
	private WebElement ConvertAccountType;
	
	@FindBy(css="a[id ^= 'billing']")
	private WebElement BillingAndShipping;
	
	public EditAccountPage  clickConvertAccountType(AccountType accountType){
		ConvertAccountType.click();
		
		waitForTextToBeVisible("Convert Account", "div h3");
		forceWait(500);
		CreateAccountPopup convertAccount = PageFactory.initElements(driver, CreateAccountPopup.class);
		convertAccount.pickAccountTypeSimple(accountType);
		
		return PageFactory.initElements(driver, EditAccountPage.class);
	}
	
	public CreateAccountPage clickAddAContact(){
		AddAContact.click();
		forceWait(500);
		
		return PageFactory.initElements(driver, CreateAccountPage.class);
	}
	
	public MergeCustomerPage clickMerge(){
		Merge.click();
		forceWait(500);
		
		return PageFactory.initElements(driver, MergeCustomerPage.class);
	}
	
	public AllAccountsTab clickDelete(){
		Delete.click();
		acceptAlert();
		
		return PageFactory.initElements(driver, AllAccountsTab.class);	
	}
	
	public QuotePage clickCreateQuote(){
		switchFrame();
		CreateQuote.click();
		forceWait(500);
//		switchBack();
		return PageFactory.initElements(driver, QuotePage.class);
	}
	
	public ProposalPage clickCreateProposal(){
		return clickCreateProposal(true);
	}
	
	public ProposalPage clickCreateProposal(boolean full){
		switchFrame();
		CreateProposal.click();

		waitForQuickLoad();

		ProposalPopup proposalPopup = PageFactory.initElements(driver, ProposalPopup.class);
		if(full) proposalPopup.selectFull();

		else proposalPopup.selectQuick();

		//-------------------- remove this block after this fix-----------------------/
		boolean isOnLoading = true;
		while(isOnLoading){
			try{
				waitForQuickLoad(10);	
				isOnLoading = false;
			}catch(Exception e){
				logger.info("stuck at loading. Try again.");
				refresh();
			}
		}
		//-----------------------------------------------------------------/
		
		return PageFactory.initElements(driver, ProposalPage.class);
	}
	
	public CreateAccountPage clickBillingAndShipping(){
		AccountBasePage.IsFromBillingAndShippingLink = true;
		BillingAndShipping.click();
		return PageFactory.initElements(driver, CreateAccountPage.class);
	}
	
//	@FindBy(css="button#delete-doc-btn")
//	private WebElement deleteDocButton;
//	public CurrentAccountTab deleteADocumentFromTable(){
//		switchFrame();
//		WebElement tr = findDataRowByName("1", 2);
//		tr.findElement(By.xpath("../../td[13]/input")).click();
//		quickWait();
//		deleteDocButton.click();
//		switchBack();
//		
//		return PageFactory.initElements(driver, CurrentAccountTab.class);
//	}
	
	public static class ProposalPopup extends ColBasePage{
	
		public ProposalPopup(WebDriver driver) {
			super(driver);
			waitForTextToBeVisible("Create Proposal", "h3");
			// TODO Auto-generated constructor stub
		}

		@FindBy(css="select#templateSource")
		private WebElement TemplateDropdown;
		
		@FindBy(css="#templateSource > option:nth-child(1)")
		private WebElement ProposalFull;
		
		@FindBy(css="#templateSource > option:nth-child(2)")
		private WebElement ProposalQuick;
		
		@FindBy(css="button#save-tpl-btn")
		private WebElement OK;
		
		@FindBy(css="button#create-tpl-cancel-btn")
		private WebElement Cancel;
		
		public void selectFull(){
			TemplateDropdown.click();
			forceWait(300);
			ProposalFull.click();
			forceWait(1000);
			OK.click();
		}
		
		public void selectQuick(){
			TemplateDropdown.click();
			forceWait(300);
			ProposalQuick.click();
			OK.click();
		}
	}
	
//	public void switchFrame(){  iFrame changed to Div.
//		waitForElementToBeVisible(By.cssSelector("iframe"));
//		switchFrame(By.cssSelector("iframe#jframe"));
//		waitForElementToBeVisible(By.linkText("Create Proposal"));
//	}
	
	public void switchFrame(){
		waitForElementToBeVisible(By.cssSelector("div#modal-wrapper div#main"));
		waitForElementToBeVisible(By.linkText("Create Proposal"));
		forceWait(500);
	}
	
	//-------------------------- Company details---------------------------//
	@FindBy(css="td#detailsnameAndNumber")
	private WebElement CompanyName;
	
	public String getCompany(){
		return CompanyName.getText();
	}
	
	//--------------------------- Contact pane ----------------------------//
	
	@FindBy(css="td#contactcname a.ctrl-icon-edit")
	private WebElement ContactNameEdit;
	
	@FindBy(css="select#contactcnamedropdown")
	private WebElement ContactDropdown;
	
	public CurrentAccountTab clickContactDropdown(){
		ContactDropdown.click();
		return this;
	}
	
	public CurrentAccountTab clickContactNameEdit(){
		ContactNameEdit.click();
		return this;
	}
	
	public List<String> getContacts(){
		clickContactNameEdit();
		waitForElementToBeVisible(ContactDropdown);
		List<String> contacts = new ArrayList<String>();
		for(WebElement w: ContactDropdown.findElements(By.xpath("option"))){
			contacts.add(w.getText());
		}
		
		return contacts;
	}
	//-------------------------- Go to iframe ---------------------------//
	
	public DocumentsPage getDocumentsPage(){
		switchFrame(); 
		forceWait(500);

		return 	PageFactory.initElements(driver, DocumentsPage.class);
	}
	
	public CurrentAccountTab exitDocumentsPage(){
		switchBack();
		return this;
	}
	
}
