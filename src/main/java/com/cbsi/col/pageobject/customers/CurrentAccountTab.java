package com.cbsi.col.pageobject.customers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.documents.ProposalPage;
import com.cbsi.col.pageobject.documents.QuotePage;
import com.cbsi.col.pageobject.home.ColBasePage;

public class CurrentAccountTab extends ColBasePage{

	public CurrentAccountTab(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
//		waitForPageToLoad(By.cssSelector("li.active a[href*='Customers/view']"));
		waitForTextToBeVisible("Account View", "span");
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
	
	public QuotePage clickCreateQuote(){
		switchFrame();
		CreateQuote.click();
		forceWait(500);
		switchBack();
		return PageFactory.initElements(driver, QuotePage.class);
	}
	
	public ProposalPage clickCreateProposal(){
		return clickCreateProposal(true);
	}
	
	public ProposalPage clickCreateProposal(boolean full){
		switchFrame();
		CreateProposal.click();
		switchBack();

		waitForQuickLoad();
		
		ProposalPopup proposalPopup = PageFactory.initElements(driver, ProposalPopup.class);
		if(full) proposalPopup.selectFull();
		else proposalPopup.selectQuick();
		
		waitForQuickLoad();
		return PageFactory.initElements(driver, ProposalPage.class);
	}
	
	@FindBy(css="button#delete-doc-btn")
	private WebElement deleteDocButton;
	public CurrentAccountTab deleteADocumentFromTable(){
		switchFrame();
		WebElement tr = findDataRowByName("1", 2);
		tr.findElement(By.xpath("../../td[13]/input")).click();
		quickWait();
		deleteDocButton.click();
		switchBack();
		
		return PageFactory.initElements(driver, CurrentAccountTab.class);
	}
	
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
	
	public void switchFrame(){
		waitForElementToBeVisible(By.cssSelector("iframe"));
		switchFrame(By.cssSelector("iframe#jframe"));
		waitForElementToBeVisible(By.linkText("Create Proposal"));
	}
}
