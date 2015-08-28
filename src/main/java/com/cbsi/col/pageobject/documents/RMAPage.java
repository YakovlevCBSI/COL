package com.cbsi.col.pageobject.documents;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RMAPage extends DocumentsBasePage{

	public RMAPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForTextToBeVisible("RMA", "span");
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
