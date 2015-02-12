package com.cbsi.tests.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CoverageReportPage extends BasePage{

	public CoverageReportPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForPageToLoad(By.cssSelector("span#ui-dialog-title-dialog-modal"));
		forceWait(500);
	}

@FindBy(css="body > div.ui-dialog.ui-widget.ui-widget-content.ui-corner-all.ui-draggable.ui-resizable > div.ui-resizable-handle.ui-resizable-s")	
	private WebElement resizeArrow;
	
	@FindBy(css="button#searchButton")
	private WebElement Search;
	
	@FindBy(css="button#downloadButton")
	private WebElement Download;
	
	@FindBy(css="button#coverageReportButton")
	private WebElement Generate;
	
	/**
	 * Webdriver's every possible method to drag and drop is all broken for chrome and FF. Using the method below this one(edit dom using js)
	public CoverageReportPage resizeWindow(int x, int y){
		forceWait(2000);
		int count=0;
		while(count < 30){
		WebElement reisizeArrow2 = driver.findElement(By.cssSelector("body > div.ui-dialog.ui-widget.ui-widget-content.ui-corner-all.ui-draggable.ui-resizable > div.ui-resizable-handle.ui-resizable-s"));
		Actions action = new Actions(driver);
		action.contextClick(reisizeArrow2);
		action.doubleClick();
		action.moveToElement(reisizeArrow2).clickAndHold();
		action.moveByOffset(x, y);
		x+=count;
		y+=count;
		System.out.println(x + " / " + y);
		action.build().perform();
		action.release().build().perform();
		System.out.println("count: " + count);
		
		//----//
		action.dragAndDrop(reisizeArrow2, Download).build().perform();
		count++;
		action=null;
		}
		return this;
		
	}
	*/
	public void resizeCoverageWindow(int numheight){
		JavascriptExecutor js =(JavascriptExecutor)(driver);
		js.executeScript("document.getElementsByClassName(\"ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable\")[0].style.height=\"" + numheight + "px\";");
		js.executeScript("document.getElementsByClassName(\"ui-dialog-content\")[0].style.height=\"" + (numheight-100) +"px\";");
	
	}
	

	public CatalogsPage generateCoverageReport(){
		Generate.click();
		return PageFactory.initElements(driver, CatalogsPage.class);
	}
	
	public CoverageReportPage downloadReport(){
		Download.click();
		return this;
	}
}
