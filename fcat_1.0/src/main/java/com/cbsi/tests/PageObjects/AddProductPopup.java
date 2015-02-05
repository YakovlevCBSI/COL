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
	}

	@FindBy(xpath="//table[@class='fcat-tbl']/tbody/tr[1]/td[2]/input")
	private WebElement id;
	
	@FindBy(xpath="//table[@class='fcat-tbl']/tbody/tr[2]/td[2]/input")
	private WebElement mf;
	
	@FindBy(xpath="//table[@class='fcat-tbl']/tbody/tr[3]/td[2]/input")
	private WebElement mfpn;
	
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
	
	
	@FindBy(linkText="Save")
	private WebElement Save;
	
	public ProductsCatalogPage clickSave(){
		Save.click();
		forceWait(2000);
		new WebDriverWait(driver, 1000).until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        alert.accept();
		return PageFactory.initElements(driver, ProductsCatalogPage.class);
	}
	
	@FindBy(linkText="Cancel")
	private WebElement Cancel;
	
	public AddCatalogPage clickCancel(){
		Cancel.click();
		return PageFactory.initElements(driver, AddCatalogPage.class);

	}
	

}
