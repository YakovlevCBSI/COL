package com.cbsi.col.pageobject.customers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.ColBasePage;
import com.cbsi.col.pageobject.documents.ProposalPage;
import com.cbsi.col.pageobject.documents.QuotePage;

public class CurrentAccountTab extends AccountsPage{

	public CurrentAccountTab(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForPageToLoad(By.cssSelector("li.active a[href*='Customers/view']"));
	}
	
	@FindBy(css="td a[href*='Docs/createQuote?']")
	private WebElement CreateQuote;
	
	@FindBy(css="div a[href*='Proposals/create?']")
	private WebElement CreateProposal;
	
	@FindBy(css="div a[href*='Rmas/createSales?']")
	private WebElement CreateRMA;
	
	@FindBy(css="div a[href*='list?deleted=']")
	private WebElement Recyclebin;
	
	public QuotePage ClickCreateQuote(){
		CreateQuote.click();
		forceWait(500);
		return PageFactory.initElements(driver, QuotePage.class);
	}
	
	public ProposalPage clickCreateProposal(){
		return clickCreateProposal(true);
	}
	
	public ProposalPage clickCreateProposal(boolean full){
		CreateProposal.click();
		forceWait(500);
		ProposalPopup proposalPopup = PageFactory.initElements(driver, ProposalPopup.class);
		
		if(full) proposalPopup.selectFull();
		else proposalPopup.selectQuick();
		
		return PageFactory.initElements(driver, ProposalPage.class);
	}
	
	public static class ProposalPopup extends ColBasePage{
	
		public ProposalPopup(WebDriver driver) {
			super(driver);
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
			ProposalFull.click();
			OK.click();
		}
		
		public void selectQuick(){
			TemplateDropdown.click();
			ProposalQuick.click();
			OK.click();
		}
	}
	
}
