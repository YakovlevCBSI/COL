package com.cbsi.tests.PageObjects;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddProductPopup extends BasePage{

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
	
	private WebElement cnetSkuId;
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
	
	@FindBy(linkText="Save")
	private WebElement Save;
	
	public ProductsCatalogPage clickSave(){
		Save.click();
		forceWait(1000);
		new WebDriverWait(driver, 1000).until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        if(alert.getText().contains("be mapped")){
        	throw new NullPointerException("Unable to add product: " + alert.getText());
        }
        alert.accept();
        forceWait(1000); //change this to wait for splash screen.
		return PageFactory.initElements(driver, ProductsCatalogPage.class);
	}
	
	public String clickSaveFail(){
		Save.click();
		forceWait(1000);
		new WebDriverWait(driver, 1000).until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        return alert.getText();
	}
	
	@FindBy(linkText="Cancel")
	private WebElement Cancel;
	
	public AddCatalogPage clickCancel(){
		Cancel.click();
		return PageFactory.initElements(driver, AddCatalogPage.class);

	}
	

}

