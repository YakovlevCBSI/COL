package com.cbsi.col.pageobject.documents;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.home.ColBasePage;

public class InvoicePage extends DocumentsBasePage{

	public InvoicePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		if(!isLegacyDocument())
			waitForTextToBeVisible("Invoice", "h1 span");
		else
			waitForTextToBeVisible("Invoice", "h1");

	}
	
	@FindBy(css="input[name='quoteNumber']")
	private WebElement InvoiceNumber;
	
	public int getInvoiceNumber(){
		return Integer.parseInt(InvoiceNumber.getAttribute("value").trim());
	}
	
	public SendPage clickSend(){
		super.clickSend();
		return PageFactory.initElements(driver, SendPage.class);
	}
	
	@FindBy(css="input#paymentDueDate")
	private WebElement DueDate;
	
	public InvoicePage clickSave(){
		DueDate.clear();
		waitForQuickLoad();
		return (InvoicePage) super.clickSave();
	}
	
	@FindBy(css="a.icon-doc-SalesOrder")
	private WebElement SalesOrderLink;
	
	public SalesOrderPage clickSalesOrderLink(){
		SalesOrderLink.click();
		return PageFactory.initElements(driver, SalesOrderPage.class);
	}
	
	public boolean isSalesOrderLinkDisplayed(){
		return SalesOrderLink.isDisplayed();
	}
	
	public static class DocSendPopup extends ColBasePage{

		public DocSendPopup(WebDriver driver) {
			super(driver);
			// TODO Auto-generated constructor stub
			waitForTextToBeVisible("Print", "button");
		}
		
		@FindBy(css="button#print-btn")
		private WebElement Print;
		
		@FindBy(css="button#email-btn")
		private WebElement Email;
		
		@FindBy(css="button#save-btn")
		private WebElement PDF;
		
		@FindBy(css="button#updateButton")
		private WebElement UpdatePreview;
		
		public DocSendPopup clickPrint(){
			Print.click();
			return this;
		}
		
		public DocSendPopup clickEmail(){
			EmailPopup emailPopup = PageFactory.initElements(driver, EmailPopup.class);
			
			return this;
		}
		
		public DocSendPopup clickPdf(){
			PDF.click();
			return this;
		}
		
		public DocSendPopup clickUpdatePreview(){
			UpdatePreview.click();
			return this;
		}
		
	}
}
