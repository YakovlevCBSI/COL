package com.cbsi.col.pageobject;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cbsi.col.pageobject.SearchPopup.QueryOption;
import com.cbsi.col.pageobject.customers.AccountsPage;
import com.cbsi.col.pageobject.documents.DocumentsPage;

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
		waitForPageToLoad(by, 30);
	}
	
	public void waitForPageToLoad(By by, int seconds){
		new WebDriverWait(driver, seconds).until(ExpectedConditions.presenceOfElementLocated(by));
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
	
	public void scrollToView(WebElement element){
		int elementPosition = element.getLocation().getY();
	   String js = String.format("window.scroll(0, %s)", elementPosition);
	   ((JavascriptExecutor)driver).executeScript(js);
	   forceWait(500);
	}
	
	public void forceWait(long time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//------------------ navigate to main tabs-----------------------//
	public AccountsPage goToAccountsPage(){
		return goToHomePage().goToAccountsPage();
	}
	
	public ProductsPage goToProductsPage(){
		return goToHomePage().goToProductsPage();
	}
	
	public ServicesPage goToServicesPage(){
		return goToHomePage().goToServicesPage();
	}
	
	public DocumentsPage goToDocumentsPage(){
		return goToHomePage().goToDocumentsPage();
	}
	
	public SuppliersPage goToSuppliersPage(){
		return goToHomePage().goToSuppliersPage();
	}
	
	public PurchaseOrdersPage goToPurchaseOrdersPage(){
		return goToHomePage().goToPurchaseOrdersPage();
	}
	
	//------------------ common navigation methods-----------------------//
	@FindBy(css="a#tab-home")
	private WebElement Home;
	public HomePage goToHomePage(){
		scrollToView(Home);
		Home = refreshStaleElement(By.cssSelector("a#tab-home"));
		Home.click();		
		return PageFactory.initElements(driver, HomePage.class);
	}
	
	//------------top bar-----------//
	public AccountsPage searchCustomer(String searchText){;
		return searchCustomer(searchText, false);
	}
	
	public AccountsPage searchCustomer(String searchText, boolean contains){
		SearchPopup searchPopup = PageFactory.initElements(driver, SearchPopup.class);
		return (AccountsPage) searchPopup.searchFor(QueryOption.Customers, contains, searchText);
	}
}
