package com.cbsi.fcat.pageobject.catatlogpage;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cbsi.fcat.pageobject.foundation.BasePage;

public class MapProductsDialog extends BasePage{
	public final static Logger logger = LoggerFactory.getLogger(MapProductsDialog.class);

	public MapProductsDialog(WebDriver driver) {
		super(driver);
		waitForPageToLoad(By.cssSelector("div#mappingDialog div.content.catalog"));
		resizeMapDialog();
		
		// Firefox loads slow.  Wait for the navi button that might be cut off until visible.
		waitForPageToLoad(By.cssSelector("div#mappingDialog div div div.nav-bar.actions a.nav-button"));
	}
	
	public void resizeMapDialog(){
		int numheight = 720;
		JavascriptExecutor js =(JavascriptExecutor)(driver);
		js.executeScript("document.getElementsByClassName(\"fancybox-inner\")[0].style.width=\"" + numheight + "px\";");
//		js.executeScript("document.getElementsByClassName(\"ui-dialog-content\")[0].style.height=\"" + (numheight-100) +"px\";");
	}
	
	@FindBy(css="#mappedFlag")
	private WebElement mappedFlag;
	
	@FindBy(css="td#mappedMfPn")
	private WebElement mappedMfPn;
	
	public String getMappedMfPn(){
		return mappedMfPn.getText();
	}
	
	public boolean isMapped(){
		if(mappedFlag.getText().toLowerCase().contains("none")){
			return false;
		}
		
		return true;
	}
	@FindBy(css="div.buttons a[onclick*='save']")
	private WebElement Save;
	public ProductsCatalogPage clickSave(){
		Save.click();
		waitForElementToBeInvisible(By.cssSelector("div#mappingDialog div.content.catalog"));
		forceWait(500);
		return PageFactory.initElements(driver, ProductsCatalogPage.class);
	}
	
	public boolean isSaveDisabled(){
		return Save.getAttribute("class").contains("disabled");
	}
	@FindBy(css="div.buttons a[onclick*='close']")
	private WebElement Cancel;
	
	public ProductsCatalogPage clickCancel(){
		scrollToView(Cancel);
		Cancel.click();
		waitForElementToBeInvisible(By.cssSelector("div#mappingDialog div.content.catalog"));
		forceWait(500);
		return PageFactory.initElements(driver, ProductsCatalogPage.class);
		
	}
	
	@FindBy(css="span.gp-icon.minus")
	private WebElement Unmap;
	
	public MapProductsDialog clickUnmap(){
		Unmap.click();
		return this;
	}
	
	@FindBy(css="#mappingDialog div div.page-button-bar div.nav-bar.actions a[oldtitle='Next']")
	private WebElement Next;
	
	@FindBy(css="#mappingDialog div div.page-button-bar div.nav-bar.actions a[oldtitle='Previous']")
	private WebElement Previous;
	
	public MapProductsDialog clickNext(){
		driver.findElement(By.cssSelector("#mappingDialog div div.page-button-bar div.nav-bar.actions a[oldtitle='Next']")).click();
		waitForMapSearch();
		return PageFactory.initElements(driver, MapProductsDialog.class);
	}
	
	public MapProductsDialog clickPrevious(){
		driver.findElement(By.cssSelector("#mappingDialog div div.page-button-bar div.nav-bar.actions a[oldtitle='Previous']")).click();
		waitForMapSearch();
		return PageFactory.initElements(driver, MapProductsDialog.class);
	}
	
	@FindBy(css="input[id='manufacturer-name-mapper']")
	private WebElement ManufactuererName;
	
	@FindBy(css="input#manufacturer-pn-mapper")
	private WebElement ManufacturerPartNumber;
	
	public MapProductsDialog searchName(String searchText){	
		waitForElementToClickable(By.cssSelector("input[id='manufacturer-name-mapper']"));
		//forceWait(3);
		deleteText();
		ManufactuererName.sendKeys(searchText);

		waitForMapSearch();
		
		return this;
	}
	
	public MapProductsDialog searchMfPn(String searchText){	
		waitForElementToClickable(By.cssSelector("input[id='manufacturer-name-mapper']"));
		//forceWait(3);
		deleteTextMfPn();
		ManufactuererName.sendKeys(searchText);

		waitForMapSearch();
		
		return this;
	}
	
	public void waitForMapSearch(){
		String loadText = "tbody#mapping-table-body tr td div.overlay-body.splash div.splash-image";

		try{
			waitForElementToBeVisible(5, By.cssSelector(loadText));
		}catch(TimeoutException e){
			
		}
		waitForElementToBeInvisible(By.cssSelector(loadText));
	}


	@FindBy(css="tbody#mapping-table-body")
	WebElement tbody;
	public MapProductsDialog selectAnItemFromResult(int nthResult){
		WebElement result = refreshStaleElement(By.cssSelector("tbody#mapping-table-body")).findElement(By.xpath("tr[" + nthResult + "]/td[3]/a"));
		customWait(5);
		result.click();
		/**
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		return this;
	}
	
	public void deleteText(){
		try{
			int wordLength = this.ManufactuererName.getAttribute("value").length();
			for(int i=0; i<wordLength; i++){
				this.ManufactuererName.sendKeys(Keys.BACK_SPACE);
			}
		}catch(NullPointerException e){
			logger.info("deleteText is ignored...");
		}
	}
	
	public void deleteTextMfPn(){
		try{
			int wordLength = this.ManufacturerPartNumber.getAttribute("value").length();
			for(int i=0; i<wordLength; i++){
				this.ManufactuererName.sendKeys(Keys.BACK_SPACE);
			}
		}catch(NullPointerException e){
			logger.info("deleteText is ignored...");
		}
	}
	
}
