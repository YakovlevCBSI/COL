package com.cbsi.tests.PageObjects;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.ListUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cbsi.tests.util.GlobalVar;

public abstract class BasePage {
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
	
	public void waitForElementToClickable(By by){
		new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(by));
	}
	
	public void waitForElementToBeVisible(By by){
		new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(by));
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
	
	protected String tempFileName = getHostname() + System.currentTimeMillis() + "";
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
	
	public String escapeHtml(String text){
		return text.replace("<", "&lt;").replace(">","&gt;");
	}
	
	
	public static String getHostname(){
		String hostName="";
		try{
		    InetAddress addr;
		    addr = InetAddress.getLocalHost();
		    hostName = addr.getHostName();
		}
		catch (UnknownHostException ex){
		    System.out.println("Hostname can not be resolved");
		}
		
		return hostName;
	}
	
}
