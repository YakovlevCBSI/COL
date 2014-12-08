package com.cbsi.tests.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductsCatalogPage extends BasePage{
	public ProductsCatalogPage(WebDriver driver){
		super(driver);
		waitForPageToLoad();

	}
	
	@Override
	public void waitForPageToLoad(){
		waitForPageToLoad(By.cssSelector("table[class*='catalog']"));
	}
	@FindBy(css="a.nav-button.next")
	private WebElement goRight;
	
	@FindBy(css="div#page-number-container-low")
	private WebElement currentPage;
	public ProductsCatalogPage clickGoRight(){
		String pageNum = currentPage.getText();
		goRight.click();
		
		long startTime = System.currentTimeMillis();
		while(System.currentTimeMillis() - startTime < 30000){
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
