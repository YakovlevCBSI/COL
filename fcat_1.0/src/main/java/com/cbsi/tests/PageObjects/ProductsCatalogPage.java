package com.cbsi.tests.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductsCatalogPage extends CatalogsPage{
	public ProductsCatalogPage(WebDriver driver){
		super(driver);
		waitForPageToLoad();
	}
	
	@FindBy(css="a.nav-button.next")
	private WebElement goRight;
	
	@FindBy(css="div#page-number-container-low")
	private WebElement currentPage;
	public ProductsCatalogPage clickGoRight(){
		String pageNum = currentPage.getText();
		goRight.click();
		
		long startTime = System.currentTimeMillis();
		while(System.currentTimeMillis() - startTime < 20000){
			if(!pageNum.equals(refreshStaleElement(currentPage).getText())){
				break;
			}
		}
		
		if(pageNum.equals(refreshStaleElement(currentPage).getText())){
			throw new NoSuchElementException("Page was still loading...");
		}
		
		return this;
	}
	public boolean isTableCorrectlyRendered(){
		try{
			driver.findElement(By.cssSelector("table.catalog-product-list tbody tr td"));
		}catch(NoSuchElementException e){
			return false;
		}
		return true;
	}
	
}
