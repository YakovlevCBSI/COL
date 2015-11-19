package com.cbsi.col.pageobject.home;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.ListUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cbsi.col.pageobject.customers.AccountsPage;
import com.cbsi.col.pageobject.documents.DocumentsPage;
import com.cbsi.col.pageobject.documents.ScratchPadPage;
import com.cbsi.col.pageobject.home.SearchPopup.QueryColumn;
import com.cbsi.col.pageobject.home.SearchPopup.QueryOption;
import com.cbsi.col.pageobject.products.ProductsPage;
import com.cbsi.col.pageobject.purchaseorders.PurchaseOrdersTab;
import com.cbsi.col.pageobject.suppliers.SuppliersPage;
import com.cbsi.col.test.util.StringUtil;
import com.google.common.primitives.Ints;

public class ColBasePage {
	protected WebDriver driver;
	
	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public ColBasePage(WebDriver driver){
		this.driver= driver;
	}
	
	public Actions action;
	
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
		waitForTextToBeVisible(10000, text, "h1", "h2", "h3");
	}
	
	public void waitForTextToBeVisible(long milliSeconds, String text){
		waitForTextToBeVisible(milliSeconds, text, "h1", "h2", "h3");
	}
	
	public void waitForTextToBeVisible(String text, String...tagNames){
		waitForTextToBeVisible(15000, text, tagNames);
	}
	
	public boolean waitForTextToBeVisible(long milliSeconds, String text, String...tagNames){
		String[] tags = tagNames;
		WebElement headerOnWait= null;
		long start = System.currentTimeMillis();
		
	
		outerLoop:
		while(System.currentTimeMillis() - start < milliSeconds){
			logger.info("searching Text...[" + text + "]");

			List<WebElement> headers  = null;
			
			for(String tag: tagNames){
				List<WebElement> header1s = driver.findElements(By.cssSelector(tag));
				
				if(tags.length >=2 && headers != null) headers = ListUtils.union(headers, header1s);
				else headers =header1s;
			}
			
//			for(WebElement h: headers){
//			for(int i=headers.size()-1; i>=0 ; i--){
			for(int i=0; i<headers.size() ; i++){

				logger.debug("collected search Text:  " + headers.get(i).getText());
				try{
					if(headers.get(i).getText().isEmpty()) continue;
					
					if(headers.get(i).getText().contains(text)){
						headerOnWait = headers.get(i);
						return true;
					}
				}catch(Exception e){
					
				}
			}
			
			forceWait(100);
		}
		
		logger.info("Wait for: " + headerOnWait.getText());
		while(!headerOnWait.isDisplayed() && (System.currentTimeMillis() - start < milliSeconds)){
			forceWait(100);	
		}
		
		return false;
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
	
	public void waitForAlert(){
		boolean alertExists = false;
		
		while(!alertExists){
			try{
				Alert alert = driver.switchTo().alert();
				alertExists = true;
			}catch(NoAlertPresentException e){
			
			}
			forceWait(100);
		}
	}
	public boolean isAlertPresent(){
		boolean alertExists = false;

		long start = System.currentTimeMillis();
		while(!alertExists && System.currentTimeMillis() - start < 1500){
			try{
				Alert alert = driver.switchTo().alert();
				return true;
			}catch(NoAlertPresentException e){
			
			}
			forceWait(100);
		}
		
		return alertExists;
	}
	public String getAlertMessage(){
		Alert alert = driver.switchTo().alert();
		return alert.getText();
	}
	
	public Actions getActions(){
		if(action == null){
			action = new Actions(driver);
		}
		
		return action;
	}
	
	public void cleanInput(WebElement input){
		String inputText = input.getAttribute("value");
		int textLength = inputText.length();
		while(textLength >0){
			input.sendKeys(Keys.BACK_SPACE);
			input.sendKeys(Keys.DELETE);
			textLength --;
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
	
	public PurchaseOrdersTab goToPurchaseOrdersPage(){
		return goToHomePage().goToPurchaseOrdersPage();
	}
	
	//------------------ common navigation methods-----------------------//
//	@FindBy(css="a#tab-home span")
	@FindBy(css="a#tab-home")
	private WebElement Home;
//	public HomePage goToHomePage(){
//		scrollToView(Home);
//		Home = refreshStaleElement(By.cssSelector("a#tab-home span"));
//		Home.click();		
//		return PageFactory.initElements(driver, HomePage.class);
//	}
	
	public HomePage goToHomePage(){
		scrollToView(Home);
		Home = refreshStaleElement(By.cssSelector("a#tab-home span"));
		Home.click();	
		try{
			logger.debug("Home is enabled");	
			return PageFactory.initElements(driver, HomePage.class);
		}catch(Exception e){
			if(driver.findElement(By.cssSelector("button#navigate-away-confirm-btn")).isDisplayed()){
				driver.findElement(By.cssSelector("button#navigate-away-confirm-btn")).click();
			}
		}
		return PageFactory.initElements(driver, HomePage.class);
	}
	
	//------------top bar-----------//
	public AccountsPage searchAccount(String searchText){
		SearchPopup searchPopup = PageFactory.initElements(driver, SearchPopup.class);
		return searchPopup.searchAccount(searchText);
	}
	
	public <T> T  searchFor(QueryOption option, boolean containsText,  QueryColumn column, String searchText, Class clazz){		
		SearchPopup searchPopup = PageFactory.initElements(driver, SearchPopup.class);
		return searchPopup.searchFor(option, containsText,  column, searchText, clazz);
	}
	
	public OrganizerPopup goToOrganizer(){
		TopBar topbar = PageFactory.initElements(driver, TopBar.class);
		return topbar.clickOrganizer();
	}
	//--------------------------frame switch----------------------------//
	public void switchFrame(){
		waitForElementToBeVisible(By.cssSelector("iframe"));
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.cssSelector("iframe")));
	}
	
	public void switchFrame(By by){
//		waitForElementToBeVisible(By.cssSelector("iframe"));
//		logger.debug("iframe is visible");
		driver.switchTo().defaultContent();
		logger.debug("switched to defaultContent");
		driver.switchTo().frame(driver.findElement(by));
		logger.debug("Exit switchFrame method.");
	}
	
	public void switchBack(){
		driver.switchTo().defaultContent();
	}
	
	public void waitForQuickLoad(){
		waitForQuickLoad(5);
	}
	
	public void waitForQuickLoad(int second){
		try{
			waitForElementToBeVisible(By.cssSelector("div#loading-modal"), second);
		}catch(TimeoutException e){
			
		}
		waitForElementToBeInvisible(By.cssSelector("div#loading-modal"), 15);
	}
	
	
	//--------------------------window switch----------------------------//
	
	protected String oldWindow;
	
	public void switchToNewWindow(){
		oldWindow = driver.getWindowHandle();
		
		while(driver.getWindowHandles().size() <=1){
			forceWait(300);
			logger.info("waiting for new window...");
		}
		
		for(String winHandle: driver.getWindowHandles()){
			logger.info(winHandle);
		}
		
		for(String winHandle: driver.getWindowHandles()){
			driver.switchTo().window(winHandle);
		}
	}
	
	private static int i=1;
	public void switchToNextWindow(){
		while(driver.getWindowHandles().size() <=1){
			forceWait(300);
			logger.info("waiting for new window...");
		}
		System.out.println(new ArrayList<String>(driver.getWindowHandles()).get(i));
		driver.switchTo().window(new ArrayList<String>(driver.getWindowHandles()).get(i));
		i++;
	}
	
	public void closeCurrentWindow(){
		driver.close();
	}
	
	public void switchToOldWindow(){
		closeCurrentWindow();
		driver.switchTo().window(oldWindow);
	}
	
	//-------------------- parse table such as search results, etc-------------------------------------//
	
	public enum Table{
		H_Line{
			public String toString() {
				return "hline";}
		},
		B_Line{
			public String toString() {
				return "bline";}
		},
		Other{
			public String toString(){
				return "other";
			}
		}
	}
	
	public List<LinkedHashMap<String, String>> getTableAsMaps(WebElement table, int...skipColumnNums){
		return getTableAsMaps(1, table, skipColumnNums); //get 2nd td element text for seach result.
	}
	
	public List<LinkedHashMap<String, String>> getTableAsMaps(int getNthTdElement, WebElement table, int...skipColumnNums){
		
		//collect headers and table rows to use.
		ArrayList<WebElement> headerElements = (ArrayList<WebElement>) table.findElements(By.xpath("thead/tr/th"));	
		List<WebElement> trs = table.findElements(By.xpath("tbody/tr"));
		
		logger.debug("headerElements size: " + headerElements.size());
		logger.debug("trs size: " + trs.size());
		
		List<LinkedHashMap<String, String>>  maps= new ArrayList< LinkedHashMap<String, String>>();

		// if no table rows exists, there is no data return empty maps.
		if(trs.size() ==0 || trs == null){
			logger.debug("Exit due to trs ==0");
			return maps;
		}
		if(trs.size() <=1 && trs.get(0).findElement(By.xpath("td")).getText().toLowerCase().contains("no result")){
			logger.debug("NO RESULT FOUND");
			return maps;
		}
		

		for(int j=0; j<trs.size(); j++){
			logger.debug("trs class = '"+trs.get(j).getAttribute("class") + "'");
//			if(trs.get(j).getAttribute("class").contains("collapsible") || (trs.get(j).getAttribute("data-itemtype") !=null && !trs.get(j).getAttribute("data-itemtype").contains("product"))) {	//skip collapsible columns on product table.
			if(trs.get(j).getAttribute("class").contains("collapsible") ) {	//skip collapsible columns on product table.
				continue; 
			}

			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
			String tr =null;
			
			if((tr = trs.get(j).getAttribute("data-itemtype"))!= null && tr.contains("line")){
				logger.warn("Passing first line item conditioin: "+ tr);
				map.put(Table.Other.toString(), trs.get(j).getAttribute("data-itemtype"));
			}
			else{
				for(int i=0; i< headerElements.size(); i++){		
					if(Arrays.asList(skipColumnNums).contains(i)){	//skip data that is explicitly set to exclude
						logger.debug("skipping column " + i);
						continue;
					}
					String data =null;
					try{
						data = trs.get(j).findElement(By.xpath("td[" + (i+getNthTdElement) + "]")).getText();
					}catch(NoSuchElementException e){
						return maps;
					}
					logger.debug(StringUtil.cleanTableKey(headerElements.get(i).getText()) + " : " + data  + " | ");
					map.put(StringUtil.cleanTableKey(headerElements.get(i).getText()), data==null?"":data);
				}
			}
			System.out.println();
			maps.add(map);
		}
			
		
		return maps;
	}
	
	//------------ detect if perl or java based---------//
	public boolean isPerl(){
		return driver.getCurrentUrl().endsWith(".epl");
	}
	
	//------------------------ find data from table  -----------------------//
	
	static int currentPage=0;
	private static List<WebElement> dataColumns;
	
	public WebElement findDataRowByName(String quoteNumber, int nthColumnToLookFor){
		return findDataRowByName(quoteNumber, nthColumnToLookFor, false);
	}
	
	public WebElement findDataRowByName(String quoteNumber, int nthColumnToLookFor, boolean goNextPage){
		return findDataRowByName(quoteNumber, nthColumnToLookFor, "a", goNextPage);
	}
	
	public WebElement findDataRowByName(String quoteNumber, int nthColumnToLookFor, String addPath, boolean goNextPage){

		dataColumns = driver.findElements(By.cssSelector("table.costandard tbody tr td:nth-child(" + nthColumnToLookFor + ") " + addPath));
		
		logger.debug("data column size " + dataColumns.size());
		for(WebElement dataColumn: dataColumns){
			logger.debug(dataColumn.getText());
			if(dataColumn.getText().contains(quoteNumber)){
				logger.debug("found doc# " + quoteNumber);
				return dataColumn;
			}
		}
		
		if(goNextPage){
			List<WebElement> pageList = driver.findElements(By.cssSelector("tr.footer td a"));
			if(currentPage-1 >=0){
				int removePage = currentPage;
				while(removePage >0){
					removePage--;
					pageList.remove(removePage);
				}
			}
			if(pageList.size() >=1){
				currentPage++;
				pageList.get(0).click();
				waitForTextToBeVisible("Documents", "h1");
				dataColumns=null;
				return findDataRowByName(quoteNumber, nthColumnToLookFor, addPath, true);
			}
		}
		
		return null;		
	}
	
	///filter///
	
	@FindBy(css="select#time_limit")
	private WebElement TimeDropdown;
	public <T> T setFilterByDate(Time days){
		TimeDropdown.click();
		driver.findElement(By.cssSelector("option[value='" + days.toString().toLowerCase() + "'")).click();
		
		try{
			waitForElementToBeInvisible(By.cssSelector("select#time_limit"), 5);
		}catch(TimeoutException e){
			
		}
		
		return (T)PageFactory.initElements(driver, this.getClass());
	}
	
	
	public enum Time{
		ALL,
		TODAY,
		LAST7,
		LAST30
	}
}
