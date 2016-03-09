package com.cbsi.col.pageobject.documents;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RMAPage extends DocumentsBasePage{

	public RMAPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForTextToBeVisible("RMA", "h1 span");
	}
	
	@FindBy(css="button[title^='Submit']")
	private WebElement Submit;
	
	@FindBy(css="button[title^='Authorize']")
	private WebElement Authorize;
	
	
	@FindBy(css="button[title^='Finalize']")
	private WebElement Finalize;
	
	public RMAPage clickSubmit(){
		Submit.click();
		waitForQuickLoad();
		
		return PageFactory.initElements(driver, RMAPage.class);
	}
	
	public RMAPage clickAuthorize(){
		Authorize.click();
		waitForQuickLoad();
		
		return PageFactory.initElements(driver, RMAPage.class);
	}
	
	public RMAPage clickFinalize(){
		Finalize.click();
		waitForQuickLoad();
		forceWait(500);

		return PageFactory.initElements(driver, RMAPage.class);
	}
	
	@FindBy(css="select[id ^='noteReason-']")
	private WebElement ReasonForReturn;
	
	public RMAPage selectReasonForReturn(Reasons reason){
		ReasonForReturn.click();
		driver.findElement(By.cssSelector("option[value='" + reason.toString().replaceAll("_", " "))).click();
		
		return this;
	}
	
	public enum Reasons{
		Dead_On_Arrival,
		Incorrect_Part,
		Item_Not_Needed,
		Double_Shipped,
		Other
	}

}
