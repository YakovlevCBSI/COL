package com.cbsi.col.pageobject.customers;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.documents.AddressPage;
import com.cbsi.col.pageobject.home.ColBasePage;

public class CreateAccountPage extends AccountBasePage{
	public CreateAccountPage(WebDriver driver){
		super(driver);

		if(IsFromCompanyInfoLink){
			waitForTextToBeVisible("Company Info", "h2");
		}
		else if(IsFromContactInfoLink){
			waitForTextToBeVisible("Contact Info", "h1");
		}
		else if(IsFromBillingAndShippingLink){
			waitForTextToBeVisible("Edit Billing & Shipping", "h1");
		}
		else{
			try{
				waitForPageToLoad(By.cssSelector("ul.nav.nav-pipes"));
			}catch(Exception e){
				waitForTextToBeVisible("Contact Info","fieldset legend"); //if lead page, wait for this.
			}
		}
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
	
	@FindBy(css="select[name='shipping_address_id'")
	private WebElement ShppingAddressDropdown;
	
	@FindBy(css="input[name='s_option'][value='new']")
	private WebElement New;
	
	@FindBy(css="input[name='s_option'][value='edit']")
	private WebElement Edit;

	public CreateAccountPage setNew(){
		New.click();
		return this;
	}
	
	public CreateAccountPage setEdit(){
		Edit.click();
		return this;
	}
	
	public String getSelectedShippingAddress(){
		for(WebElement w: ShppingAddressDropdown.findElements(By.xpath("option"))){
			if(w.getAttribute("value").equals("-1")) continue;
			
			if(w.getAttribute("selected") != null){
				return w.getText().trim();
			}
		}
		
		return null;
	}
	
	@Override
	public CurrentAccountTab clickSave(){
		Save.click();
		return PageFactory.initElements(driver, CurrentAccountTab.class);
	}

	
}
