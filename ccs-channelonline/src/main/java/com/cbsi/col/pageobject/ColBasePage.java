package com.cbsi.col.pageobject;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.ListUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
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
		waitForElementToBeVisible(by, 30);
	}
	
	public void waitForElementToBeVisible(By by, long timeInSeconds){
		new WebDriverWait(driver, timeInSeconds).until(ExpectedConditions.visibilityOfElementLocated(by));
	}
	
	public void waitForElementToBeVisible(String path){
		new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(path)));
	}
	
	public void waitForElementToBeInvisible(By by){
		waitForElementToBeInvisible(by, 30);
	}
	
	public void waitForElementToBeInvisible(By by, long time){
		new WebDriverWait(driver, time).until(ExpectedConditions.invisibilityOfElementLocated(by));
	}
	
	public void waitForTextToBeVisible(String text){
		waitForTextToBeVisible(text, "h1", "h2", "h3");
	}
	
	public void waitForTextToBeVisible(String text, String...tagNames){
		String[] tags = tagNames;
		WebElement headerOnWait= null;
		long start = System.currentTimeMillis();
	
		while(headerOnWait== null && (System.currentTimeMillis() - start < 10000)){
			
			List<WebElement> headers  = null;
			
			for(String tag: tagNames){
				List<WebElement> header1s = driver.findElements(By.cssSelector(tag));
				
				if(tags.length >=2 && headers != null) headers = ListUtils.union(headers, header1s);
				else headers =header1s;
			}
			
			for(WebElement h: headers){
				try{
					if(h.getText().contains(text)){
						headerOnWait = h;
						break;
					}
				}catch(Exception e){
					
				}
			}
			
			forceWait(300);
		}
		
		while(!headerOnWait.isDisplayed() && (System.currentTimeMillis() - start < 10000)){
			forceWait(300);	
		}
		
		return;
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
	
	public void acceptAlert(){
		forceWait(500);
		Alert alert = driver.switchTo().alert();
		alert.accept();
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
	@FindBy(css="a#tab-home span")
	private WebElement Home;
	public HomePage goToHomePage(){
		scrollToView(Home);
		Home = refreshStaleElement(By.cssSelector("a#tab-home span"));
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
	
	//----------frame switch--------//
	public void switchFrame(By by){
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(by));
	}
	
	public void switchBack(){
		driver.switchTo().defaultContent();
	}
	
	public void waitForQuickLoad(){
		try{
			waitForElementToBeVisible(By.cssSelector("div#loading-modal"), 5);
		}catch(TimeoutException e){
			
		}
		waitForElementToBeInvisible(By.cssSelector("div#loading-modal"));
	}
	
}
