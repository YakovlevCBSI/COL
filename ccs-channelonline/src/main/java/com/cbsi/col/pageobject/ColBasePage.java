package com.cbsi.col.pageobject;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cbsi.col.pageobject.SearchPopup.QueryOption;
import com.cbsi.col.test.pageobject.customers.CustomersPage;

public class ColBasePage {
	protected WebDriver driver;
	public ColBasePage(WebDriver driver){
		this.driver= driver;
	}
	
	public void customWait(int seconds){
		driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
	}
	
	public void quickWait(){
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}
	
	public void waitForPageToLoad(){
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.header h1")));
	}
	
	public void waitForPageToLoad(By by){
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(by));
	}
	
	public void waitForElementToBeClickable(String path){
		new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(By.cssSelector(path)));

	}
	
	public void waitForElementToBeClickable(By by){
		new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(by));
	}
	
	public void waitForElementToBeVisible(By by){
		new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(by));
	}
	
	public void waitForElementToBeVisible(String path){
		new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(path)));
	}
	
	public void waitForElementToBeInvisible(By by){
		new WebDriverWait(driver, 30).until(ExpectedConditions.invisibilityOfElementLocated(by));
	}
	
	public WebElement refreshStaleElement(By by){
		waitForElementToBeVisible(by);
		return driver.findElement(by);
	}
	
	public String getCurrentURL(){
		return driver.getCurrentUrl();
	}
	
	public void refresh(){
		driver.navigate().refresh();
	}
	
	public void forceWait(long time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//------------------ common navigation methods-----------------------//
    
	@FindBy(css="a#tab-home")
	private WebElement Home;
	
	public HomePage goToHomePage(){
		Home.click();		
		return PageFactory.initElements(driver, HomePage.class);
	}
	
	//------------top bar-----------//
	public CustomersPage searchCustomer(String searchText){;
		return searchCustomer(searchText, false);
	}
	
	public CustomersPage searchCustomer(String searchText, boolean contains){
		SearchPopup searchPopup = PageFactory.initElements(driver, SearchPopup.class);
		return (CustomersPage) searchPopup.searchFor(QueryOption.Customers, contains, searchText);
	}
}
