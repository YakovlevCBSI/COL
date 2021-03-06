package com.cbsi.col.pageobject.documents;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.documents.QuotePage.CopyToNewQuotePage;

public class ScratchPadPage extends DocumentsBasePage{

	public ScratchPadPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForTextToBeVisible(20000,"The \"Scratch Pad\" serves two purposes:", "p");
	}
	
	@FindBy(css="div#footer-actions div div div button#ld-nextaction-caret")
	private WebElement CopyToCustomerCaret;
	
	@FindBy(css="a[title='Empty Doc']")
	private WebElement EmptyDoc;
	
	@FindBy(css="button[title='Copy to Customer']")
	private WebElement CopyToCustomer;
	
	public ScratchPadPage clickEmptyDoc(){
		CopyToCustomerCaret.click();
		EmptyDoc.click();
		
		waitForQuickLoad();
		
		return PageFactory.initElements(driver, ScratchPadPage.class);
	}
	
	public QuotePage clickCopyToCustomer(){
		CopyToCustomer.click();
		
		CopyToNewQuotePage copyToNewQuotePage = PageFactory.initElements(driver, CopyToNewQuotePage.class);
		return copyToNewQuotePage.setCustomerAndContactSearch("qa");
	}

}
