package com.cbsi.col.pageobject.customers;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.home.ColBasePage;

public class CreateAccountPage extends AccountBasePage{
	public CreateAccountPage(WebDriver driver){
		super(driver);
		waitForPageToLoad(By.cssSelector("ul.nav.nav-pipes"));
	}
	
	public CreateAccountPage(WebDriver driver, boolean waitForTextLoad){
		super(driver);
		
		if(waitForTextLoad){
			waitForTextToBeVisible(15000,"Company Info");
		}
		
	}

	

	public CreateAccountPage setDefaultContact(){
		DesignateThisPerson.click();
		return this;
	}
	
	public CreateAccountPage setDefaultPaymentToCod(){
		scrollToView(Next);
		DefaultPaymentOption.click();
		forceWait(3000);
		COD.click();
		return this;
	}
	
	public CreateAccountPage setContactInfo_CompanyName(String companyName){
		List<WebElement> companyNameDivs= driver.findElements(By.cssSelector("div.control-group label[for='name']"));
		WebElement companyNameDiv = null;
		for(WebElement w: companyNameDivs){
			if(w.getText().contains("Company Name")){
				companyNameDiv = w;
				break;
			}
		}
		
		WebElement companyInput = companyNameDiv.findElement(By.xpath("../div/input[@id='name']"));
		companyInput.sendKeys(companyName);
		
		return this;
	}

}
