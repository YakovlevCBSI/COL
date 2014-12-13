package com.cbsi.tests.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MapProductsDialog extends BasePage{

	public MapProductsDialog(WebDriver driver) {
		super(driver);
		waitForPageToLoad(By.cssSelector("div#mappingDialog div.content.catalog"));
		// TODO Auto-generated constructor stub
		
		//waitforpage to Load.
	}
	
	
	@FindBy(css="div.buttons a[onclick*='save']")
	private WebElement Save;
	public ProductsCatalogPage clickSave(){
		Save.click();
		return PageFactory.initElements(driver, ProductsCatalogPage.class);
	}
	
	@FindBy(css="div.button a[onclick*='close']")
	private WebElement Cancel;
	
	@FindBy(css="input[id='manufacturer-name-mapper']")
	private WebElement ManufactuererName;
	
	public MapProductsDialog searchName(String searchText){
		ManufactuererName.sendKeys(searchText);
		waitForElementToBeVisible(By.cssSelector("tbody#mapping-table-body"));
		waitForElementToBeInvisible(By.cssSelector("tbody.loading"));
		waitForElementToBeVisible(By.cssSelector("tbody#mapping-table-body"));
		return this;
	}
	
	@FindBy(css="tbody#mapping-table-body")
	WebElement tbody;
	public MapProductsDialog selectAnItemFromResult(int nthResult){
		WebElement result = refreshStaleElement(tbody).findElement(By.xpath("tr[" + nthResult + "]/td[3]/a"));
		customWait(5);
		result.click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}
}