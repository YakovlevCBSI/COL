package com.cbsi.col.pageobject.documents;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.cbsi.col.pageobject.home.ColBasePage;

public class ProposalPage extends ColBasePage{

	public ProposalPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForTextToBeVisible("Proposal (Draft)", "span");
	}
	
	@FindBy(css="input#quoteNumber")
	private WebElement quoteNumber;
	
	@FindBy(css="button[title='Save']")
	private WebElement Save;
	
	@FindBy(css="button[id*='next-action_markReady']")
	private WebElement PrepareForESign;
	
	@FindBy(css="button#next-action_send")
	private WebElement Send;

	
	public int getQuoteNumber(){
		return Integer.parseInt(quoteNumber.getAttribute("value"));
	}
	
	public ProposalPage clickSave(){
		waitForQuickLoad();
		Save.click();
		waitForQuickLoad();
		return this;
	}
	
	public ProposalPage clickPrePareForESign(){
		PrepareForESign.click();
		return this;
	}
	
	public ProposalPage clickSend(){
		Send.click();
		return this;
	}
	

}
