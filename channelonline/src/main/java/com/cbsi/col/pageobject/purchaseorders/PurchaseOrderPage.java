package com.cbsi.col.pageobject.purchaseorders;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.documents.DocumentsBasePage;
import com.cbsi.col.pageobject.documents.InvoicePage;
import com.cbsi.col.pageobject.documents.RMAPage;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.CreateRmaPopup;
import com.cbsi.col.pageobject.documents.SalesOrderPage;
import com.cbsi.col.pageobject.home.ColBasePage;

public class PurchaseOrderPage extends DocumentsBasePage{

	public PurchaseOrderPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForTextToBeVisible("Purchase Order", "h1");
		waitForPageToLoad(By.cssSelector("div#crm-footer div div[class^='sticky-accordion']"));
	}
	
	@FindBy(css="input#is_autofulfill_manual")
	private WebElement Manual;
	
	@FindBy(css="input#is_autofulfill_auto")
	private WebElement Autofulfill;
	
	@FindBy(css="a[title='Create RMA']")
	private WebElement CreateRma;
	
	@FindBy(css="td a[href*='submit_page(\"save\")']")
	private WebElement Save;
	
	@FindBy(css="td a#submitPOButton")
	private WebElement ConvertToSubmittedPo;
	
	@FindBy(css= "span#StatusSpan")
	private WebElement PoStatus;
	
	@FindBy(css="td font a[title ^='Linked to SO']")
	private WebElement SOLink;
	
	public SalesOrderLinkPage clickSOLink(){
		SOLink.click();
		return PageFactory.initElements(driver, SalesOrderLinkPage.class);
	}
	
	public String getPoStatus(){
		return PoStatus.getText();
	}
	
	public PurchaseOrderPage setPoType(PoType manualOrAuto){
		if(manualOrAuto == PoType.Manual){
			Manual.click();
		}else if(manualOrAuto == PoType.Autofulfill){
			Autofulfill.click();
		}
		
		return this;
	}

	public PurchaseOrderPage clickConvertToSubmittedPo(){
		ConvertToSubmittedPo.click();
		acceptAlert();
		forceWait(500); //wait for refresh.
		waitForTextToBeVisible("Purchase Order (Submitted)", "h1");
		return PageFactory.initElements(driver, PurchaseOrderPage.class);
	}
	
	public PurchaseOrderPage clickSave(){
		Save.click();
		return this;
	}
	
	public RMAPage clickCreateRma(){
		CreateRma.click();
		forceWait(500);
		CreateRmaPopup crp = PageFactory.initElements(driver, CreateRmaPopup.class);
		return crp.clickCreateRMA();
		
	}
	
	public enum PoType{
		Manual,
		Autofulfill
	}
	
	public static class SalesOrderLinkPage extends ColBasePage{

		public SalesOrderLinkPage(WebDriver driver) {
			super(driver);
			// TODO Auto-generated constructor stub
			waitForTextToBeVisible("Sales Order Links", "p.lead");
		}
		
		@FindBy(css="td font a font")
		private WebElement saleslink;
		
		public <T>T clickViewSalesOrder(){
			saleslink.click();
			waitForElementToBeVisible(By.cssSelector("div.page-header h1 span"));
			if(driver.findElement(By.cssSelector("h1 span")).getText().trim().startsWith("Sales"))
				return (T) PageFactory.initElements(driver, SalesOrderPage.class);
			
			return (T) PageFactory.initElements(driver, InvoicePage.class);
		}
	}
	

}
