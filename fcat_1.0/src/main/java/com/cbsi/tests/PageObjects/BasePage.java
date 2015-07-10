package com.cbsi.tests.PageObjects;

import java.util.concurrent.TimeUnit;

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
	
	protected String tempFileName = System.currentTimeMillis() + "";
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
}
