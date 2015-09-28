package com.cbsi.col.pageobject.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.ListUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cbsi.col.pageobject.customers.AccountsPage;
import com.cbsi.col.pageobject.documents.DocumentsPage;
import com.cbsi.col.pageobject.home.SearchPopup.QueryColumn;
import com.cbsi.col.pageobject.home.SearchPopup.QueryOption;
import com.cbsi.col.pageobject.products.ProductsPage;
import com.cbsi.col.pageobject.purchaseorders.PurchaseOrdersTab;
import com.cbsi.col.pageobject.suppliers.SuppliersPage;
import com.cbsi.col.test.util.StringUtil;

public class ColBasePage {
	protected WebDriver driver;
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
	
	public void waitForTextToBeVisilbe(long milliSeconds, String text){
		waitForTextToBeVisible(milliSeconds, text, "h1", "h2", "h3");
	}
	
	public void waitForTextToBeVisible(String text, String...tagNames){
		waitForTextToBeVisible(10000, text, tagNames);
	}
	
	public void waitForTextToBeVisible(long milliSeconds, String text, String...tagNames){
		String[] tags = tagNames;
		WebElement headerOnWait= null;
		long start = System.currentTimeMillis();
		
	
		outerLoop:
		while(System.currentTimeMillis() - start < milliSeconds){
			System.out.println("searching Text...[" + text + "]");

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
						break outerLoop;
					}
				}catch(Exception e){
					
				}
			}
			
			forceWait(100);
		}
		
		System.out.println("Wait for: " + headerOnWait.getText());
		while(!headerOnWait.isDisplayed() && (System.currentTimeMillis() - start < milliSeconds)){
			forceWait(100);	
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
	public HomePage goToHomePage(){
		scrollToView(Home);
		Home = refreshStaleElement(By.cssSelector("a#tab-home span"));
		Home.click();		
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
		waitForElementToBeVisible(By.cssSelector("iframe"));
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
	
	
	//--------------------------window switch----------------------------//
	
	protected String oldWindow;
	
	public void switchToNewWindow(){
		oldWindow = driver.getWindowHandle();
		
		while(driver.getWindowHandles().size() <=1){
			forceWait(300);
			System.out.println("waiting for new window...");
		}
		
		for(String winHandle: driver.getWindowHandles()){
			System.out.println(winHandle);
		}
		
		for(String winHandle: driver.getWindowHandles()){
			driver.switchTo().window(winHandle);
		}
	}
	
	private static int i=1;
	public void switchToNextWindow(){
		while(driver.getWindowHandles().size() <=1){
			forceWait(300);
			System.out.println("waiting for new window...");
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
	public List<HashMap<String, String>> getTableAsMaps(WebElement table, int...skipColumnNums){
		ArrayList<WebElement> headerElements = (ArrayList<WebElement>) table.findElements(By.xpath("thead/tr/th"));	
		int count=0;
		for(WebElement e: headerElements){
			System.out.println(count + " ; " + e.getText());count++;
		}
		List<WebElement> trs = table.findElements(By.xpath("tbody/tr"));
		List<HashMap<String, String>> maps= new ArrayList<HashMap<String, String>>();

		// if no table rows exists, there is no data return empty maps.
		if(trs.size() ==0 || trs == null){
			return maps;
		}
		if(trs.size() <=1 && trs.get(0).findElement(By.xpath("td")).getText().toLowerCase().contains("no result")){
			return maps;
		}
		
		if(skipColumnNums != null){
			for(int i=skipColumnNums.length-1; i >=0; i--){
				System.out.println(skipColumnNums[i]);
				headerElements.remove(skipColumnNums[i]);  //remove column (VIEW)
			}
		}
		
		String[] headerColumns = new String[headerElements.size()];
		for(int i=0; i < headerElements.size(); i++){
			if(!headerElements.get(i).getText().isEmpty()){
				headerColumns[i] = StringUtil.cleanTableKey(headerElements.get(i).getText());
			}else{
				headerColumns[i] = StringUtil.cleanTableKey(headerElements.get(i).findElement(By.xpath("a")).getText());
			}
//			System.out.println(headerColumns[i]);
		}
		
//		System.out.println("trs: " + trs.size());
		for(WebElement tr: trs){
			HashMap<String, String> map = new HashMap<String, String>();
			for(int i=0; i< headerColumns.length; i++){
				String data = tr.findElement(By.xpath("td[" + (i+2) + "]")).getText();
				System.out.print(headerColumns[i] + " : " + data  + " | ");
				map.put(headerColumns[i], data==null?"":data);
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
		return findDataRowByName(quoteNumber, nthColumnToLookFor, "a");
	}
	
	public WebElement findDataRowByName(String quoteNumber, int nthColumnToLookFor, String addPath){

		dataColumns = driver.findElements(By.cssSelector("table.costandard tbody tr td:nth-child(" + nthColumnToLookFor + ") " + addPath));
		
		for(WebElement dataColumn: dataColumns){
			System.out.println(dataColumn.getText());
			if(dataColumn.getText().contains(quoteNumber)){
				return dataColumn;
			}
		}
		
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
			return findDataRowByName(quoteNumber, nthColumnToLookFor);
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
