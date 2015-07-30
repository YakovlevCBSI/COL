package com.cbsi.col.pageobject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductsPage extends ColBasePage{
	public ProductsPage(WebDriver driver){
		super(driver);
		waitForPageToLoad(By.cssSelector("h4"));
	}
	
	@FindBy(css="a[title href*='item_add']")
	private WebElement AddToQuote;
	
	public DocumentsPage addProductsToQuote(int...nThCheckBoxes){
		checkCompareBoxes(nThCheckBoxes);
		AddToQuote.click();
		
		return PageFactory.initElements(driver, DocumentsPage.class);
	}
	
	public ProductsPage checkCompareBoxes(int...nThCheckBoxes){
		List<WebElement> checkBoxes = driver.findElements(By.cssSelector("input[id^='checkmultiple']"));
		
		for(int n: nThCheckBoxes){
			checkBoxes.get(n-1).click();
		}
		
		return this;
	}
	
	public String[] getProductNames(int...nthProduct){
		String[] foundTitles = new String[nthProduct.length];
		List<WebElement> descriptionTitles = driver.findElements(By.cssSelector("font.BodyActionText a"));
		
		int count=0;
		for(int n: nthProduct){
			foundTitles[count] = descriptionTitles.get(n).getText();
		}
		
		return foundTitles;
	}
	
}
