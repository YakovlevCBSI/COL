package com.cbsi.tests.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.TimeoutException;

public class MapProductsDialog extends BasePage{

	public MapProductsDialog(WebDriver driver) {
		super(driver);
		waitForPageToLoad(By.cssSelector("div#mappingDialog div.content.catalog"));
		// TODO Auto-generated constructor stub
		
		//waitforpage to Load.
	}
	
	@FindBy(css="#mappedFlag")
	private WebElement mappedFlag;
	
	public boolean isMapped(){
		if(mappedFlag.getText().toLowerCase().contains("none")){
			return false;
		}
		
		return true;
	}
	@FindBy(css="div.buttons a[onclick*='save']")
	private WebElement Save;
	public ProductsCatalogPage clickSave(){
		Save.click();
		waitForElementToBeInvisible(By.cssSelector("div#mappingDialog div.content.catalog"));
		forceWait(500);
		return PageFactory.initElements(driver, ProductsCatalogPage.class);
	}
	
	public boolean isSaveDisabled(){
		return Save.getAttribute("class").contains("disabled");
	}
	@FindBy(css="div.buttons a[onclick*='close']")
	private WebElement Cancel;
	
	public ProductsCatalogPage clickCancel(){
		Cancel.click();
		waitForElementToBeInvisible(By.cssSelector("div#mappingDialog div.content.catalog"));
		forceWait(500);
		return PageFactory.initElements(driver, ProductsCatalogPage.class);
		
	}
	
	@FindBy(css="span.gp-icon.minus")
	private WebElement Unmap;
	
	public MapProductsDialog clickUnmap(){
		Unmap.click();
		return this;
	}
	
	@FindBy(css="input[id='manufacturer-name-mapper']")
	private WebElement ManufactuererName;
	
	public MapProductsDialog searchName(String searchText){	
		waitForElementToClickable(By.cssSelector("input[id='manufacturer-name-mapper']"));
		//forceWait(3);
		deleteText();
		ManufactuererName.sendKeys(searchText);
		try{
			waitForElementToBeVisible(By.cssSelector("tbody#mapping-table-body"));
		}catch(TimeoutException t){
			System.out.println("skipping wait for IE.");
		}
		
		String loadText = "div.splash-image";
		
		waitForElementToBeVisible(By.cssSelector(loadText));
		waitForElementToBeInvisible(By.cssSelector(loadText));
		
		return this;
	}


	@FindBy(css="tbody#mapping-table-body")
	WebElement tbody;
	public MapProductsDialog selectAnItemFromResult(int nthResult){
		WebElement result = refreshStaleElement(By.cssSelector("tbody#mapping-table-body")).findElement(By.xpath("tr[" + nthResult + "]/td[3]/a"));
		customWait(5);
		result.click();
		/**
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		return this;
	}
	
	public void deleteText(){
		try{
			int wordLength = this.ManufactuererName.getAttribute("value").length();
			for(int i=0; i<wordLength; i++){
				this.ManufactuererName.sendKeys(Keys.BACK_SPACE);
			}
		}catch(NullPointerException e){
			System.out.println("deleteText is ignored...");
		}
	}
}
