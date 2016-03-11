package com.cbsi.fcat.pageobject.foundation;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.ListUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

import com.cbsi.fcat.pageobject.catatlogpage.CatalogsPage;
import com.cbsi.fcat.pageobject.catatlogpage.CoverageReportPage;
import com.cbsi.fcat.pageobject.catatlogpage.UploadPopupPage;
import com.cbsi.fcat.pageobject.homepage.FCatHomePage;
import com.cbsi.fcat.util.GlobalVar;

public abstract class BasePage {
	public final static Logger logger = LoggerFactory.getLogger(BasePage.class);

	protected WebDriver driver = null;
	protected Actions action;
	protected boolean isGrid = GlobalVar.isGrid;
	public BasePage(WebDriver driver){
		this.driver = driver;
	}
	
	public Actions getActions(){
		if(action ==null){
			action = new Actions(driver);
		}
		return action;
	}
	
	public FCatHomePage goToHomePage(){
		return goToHomePage(false);
	}
	
	public FCatHomePage goToHomePage(boolean clickLinkToGo){
		if(clickLinkToGo){
			driver.findElement(By.cssSelector("a[href='/fcat/']")).click();
		}
		
		return PageFactory.initElements(driver, FCatHomePage.class);
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
	
	public void waitForElementToClickable(String path){
		new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(By.cssSelector(path)));

	}
	
	public void waitForElementToClickable(WebElement e){
		new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(e));
	}
	
	public void waitForElementToClickable(By by){
		new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(by));
	}
	
	public void waitForElementToBeVisible(By by){
		waitForElementToBeVisible(30, by);
	}
	
	public void waitForElementToBeVisible(long time, By by){
		new WebDriverWait(driver, time).until(ExpectedConditions.visibilityOfElementLocated(by));
	}
	
	public void waitForElementToBeVisible(WebElement e){
		new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(e));
	}
	
	public void waitForElementToBeVisible(String path){
		new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(path)));
	}
	
	public void waitForElementToBeInvisible(By by){
		waitForElementToBeInvisible(by, 30000);
	}

	public void waitForElementToBeInvisible(By by, long timeInMilli){
		new WebDriverWait(driver, timeInMilli).until(ExpectedConditions.invisibilityOfElementLocated(by));
	}
	
	public WebElement refreshStaleElement(By by){
		waitForElementToBeVisible(by);
		return driver.findElement(by);
	}
	
	public void waitForTextToBeVisible(String text){
		waitForTextToBeVisible(text, "h1", "h2", "h3");
	}
	
	public void waitForTextToBeVisible(String text, String...tagNames){
		waitForTextToBeVisible(10, text, tagNames);
	}
	public void waitForTextToBeVisible(int seconds, String text, String...tagNames){
		String[] tags = tagNames;
		WebElement headerOnWait= null;
		long start = System.currentTimeMillis();
	
		logger.debug("waiting for text [" + text + "]");
		while(headerOnWait== null && (System.currentTimeMillis() - start < (seconds*1000))){
			
			List<WebElement> headers  = null;
			
			for(String tag: tagNames){
				List<WebElement> header1s = driver.findElements(By.cssSelector(tag));
				
				if(tags.length >=2 && headers != null) headers = ListUtils.union(headers, header1s);
				else headers =header1s;
				
				logger.debug("tag size found: " + headers.size());
			}
			
			for(WebElement h: headers){
				logger.debug("inner text: " + h.getText());
				try{
					if(h.getText().contains(text)){
						headerOnWait = h;
						logger.debug("found text [" + text + "]");
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
	
	public void waitForQuickLoad(){
		waitForQuickLoad(5);
	}
	
	public void waitForQuickLoad(int second){
		try{
			waitForElementToBeVisible(5, By.cssSelector("div.splash-image"));
		}catch(TimeoutException e){
			
		}
		waitForElementToBeInvisible(By.cssSelector("div.splash-image"), 5);
	}
	
	protected String tempFileName = getHostUserName() + System.currentTimeMillis() + "";
	public String getTempFileName(){
		return tempFileName;
	}
	
	public String getRandomNum(){
		return getTempFileName();
	}
	
	public void scrollToView(WebElement element){
			int elementPosition = element.getLocation().getY();
		   String js = String.format("window.scroll(0, %s)", elementPosition);
		   ((JavascriptExecutor)driver).executeScript(js);
		   forceWait(500);
	}
	
	public void scrollToViewXAndY(WebElement element){
		int elmenentPositionX = element.getLocation().getX();
		int elementPositionY = element.getLocation().getY();
	   String js = String.format("window.scroll(%s, %s)", elmenentPositionX, elementPositionY);
	   ((JavascriptExecutor)driver).executeScript(js);
	   forceWait(500);
	}
	
	public String escapeHtml(String text){
		return text.replace("<", "&lt;").replace(">","&gt;");
	}
	
	
//	public static String getHostname(){
//		String hostName="";
//		try{
//		    InetAddress addr;
//		    addr = InetAddress.getLocalHost();
//		    hostName = addr.getHostName();
//		}
//		catch (UnknownHostException ex){
//		    logger.info("Hostname can not be resolved");
//		}
//		
//		return hostName;
//	}
//	
	public String getHostUserName(){
		return System.getProperty("user.name");
	}
	
	//-------------------- Bottome Bar ----------------//
	@FindBy(linkText="Return to List")
	private WebElement ReturnToList;
	
	public CatalogsPage clickReturnToList(){
		try{
			waitForElementToClickable(By.linkText("Return to List"));
			ReturnToList.click();
		}catch(Exception e){
			
		}
		
		return PageFactory.initElements(driver, CatalogsPage.class);
	}
	
	@FindBy(css="a#upload-file")
	private WebElement UploadFile;
	
	public UploadPopupPage clickUploadFile(){
		UploadFile.click();
		return PageFactory.initElements(driver, UploadPopupPage.class);
	}
	
	public void acceptAlert(){
		forceWait(500);
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}
	
	public void navigateBack(){
		driver.navigate().back();
	}
	
}
