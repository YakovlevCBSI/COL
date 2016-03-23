package com.cbsi.fcat.pageobject.catatlogpage;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cbsi.fcat.pageobject.foundation.BasePage;

public class AddProductPopup extends BasePage{
	public final static Logger logger = LoggerFactory.getLogger(AddProductPopup.class);

	public AddProductPopup(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForPageToLoad(By.cssSelector("div#editDialog"));
		waitForPageToLoad(By.cssSelector("td.field-value"));
		
	}

	@FindBy(css="table.fcat-tbl tbody tr td input[name='id']")
	private WebElement id;
	
	@FindBy(css="table.fcat-tbl tbody tr td input[name='mf']")
	private WebElement mf;
	
	@FindBy(css="table.fcat-tbl tbody tr td input[name='mfPn']")
	private WebElement mfpn;
	
	@FindBy(css="table.fcat-tbl tbody tr td input[name='upcEan']")
	private WebElement upcEan;
	
	@FindBy(css="table.fcat-tbl tbody tr td input[name='skuId']")
	private WebElement cnetSkuId;
		
	@FindBy(linkText="Save")
	private WebElement Save;
	
	@FindBy(css="div#errorMsg")
	private WebElement ErrorMessage;
	
	private WebElement inventory;
	private WebElement price;
	private WebElement productUrl;
	
	public void setId(String text){
		id.sendKeys(text);
	}
	
	public void setMf(String text){
		mf.sendKeys(text);
	}
	
	public void setMfpn(String text){
		mfpn.sendKeys(text);
	}
	
	public void setUpcEan(String text){
		upcEan.sendKeys(text);
	}
	
	public void setSkuId(String text){
		cnetSkuId.sendKeys(text);
	}
	
	public ProductsCatalogPage clickSave(){
		Save.click();
		forceWait(1000);
		
		/**
		 * Javascript alert is removed from UI.
		new WebDriverWait(driver, 1000).until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        if(alert.getText().contains("be mapped")){
        	throw new NullPointerException("Unable to add product: " + alert.getText());
        }
        alert.accept();
        */
		
        forceWait(1000); //change this to wait for splash screen.
		return PageFactory.initElements(driver, ProductsCatalogPage.class);
	}
	
	public String clickSaveFail(){
		Save.click();
		forceWait(1000);
		/**
		new WebDriverWait(driver, 1000).until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        String alertText=  alert.getText();
        alert.accept();
        */
		
		return ErrorMessage.getText();
	}
	
	@FindBy(linkText="Cancel")
	private WebElement Cancel;
	
	public AddCatalogPage clickCancel(){
		Cancel.click();
		return PageFactory.initElements(driver, AddCatalogPage.class);

	}
	

}

