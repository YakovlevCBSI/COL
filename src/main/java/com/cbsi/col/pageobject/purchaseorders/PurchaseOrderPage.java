package com.cbsi.col.pageobject.purchaseorders;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.documents.DocumentsBasePage;
import com.cbsi.col.pageobject.documents.RMAPage;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.CreateRmaPopup;
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
		waitForTextToBeVisible("Purchase Order (Submitted)", "h1");
		return PageFactory.initElements(driver, PurchaseOrderPage.class);
	}
	
	public PurchaseOrderPage clickSave(){
		Save.click();
		return this;
	}
	
	public RMAPage clickCreateRma(){
		CreateRma.click();
		CreateRmaPopup crp = PageFactory.initElements(driver, CreateRmaPopup.class);
		return crp.clickCreateRMA();
		
	}
	
	public enum PoType{
		Manual,
		Autofulfill
	}
	

}
