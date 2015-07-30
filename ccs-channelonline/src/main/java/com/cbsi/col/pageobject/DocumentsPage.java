package com.cbsi.col.pageobject;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DocumentsPage extends ColBasePage{
	public DocumentsPage(WebDriver driver){
		super(driver);
		waitForPageToLoad(By.linkText("Standard"));
	}
	
	@FindBy(css="input#addProductKeyword")
	private WebElement AddProductSearchBox;
	
	@FindBy(css="button#add-product-submit")
	private WebElement Search;
	
	@FindBy(css="button#next-action_save")
	private WebElement Save;
	
	public DocumentsPage clickSave(){
		Save.click();
		
		return this;
	}
	
	public ProductsPage searchProduct(String productName){
		AddProductSearchBox.sendKeys(productName);
		Search.click();
		
		return PageFactory.initElements(driver, ProductsPage.class);
	}
	
	public List<String> getDescriptionTitles(){
		List<WebElement> descriptions = driver.findElements(By.cssSelector("div[id^='lineDescriptionOne']"));
		List<String> descriptionList = new ArrayList<String>();
		for(WebElement description: descriptions){
			descriptionList.add(description.getText());
		}
		
		return descriptionList;

	}
}
