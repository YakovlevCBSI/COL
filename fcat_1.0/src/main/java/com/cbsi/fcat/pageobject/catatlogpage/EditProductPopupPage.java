package com.cbsi.fcat.pageobject.catatlogpage;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cbsi.fcat.pageobject.foundation.BasePage;
import com.cbsi.fcat.util.GlobalVar;

public class EditProductPopupPage extends BasePage{
	public final static Logger logger = LoggerFactory.getLogger(EditProductPopupPage.class);

	public EditProductPopupPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForPageToLoad();
	}
	
	@Override
	public void waitForPageToLoad(){
		waitForElementToBeVisible(By.cssSelector("#content > div.fancybox-wrap.fancybox-desktop.fancybox-type-html.fancybox-opened > div > div > div > div > div.overlay-body.dialog"));
		waitForElementToBeVisible(By.cssSelector(EditTable));
	}

	private static final String EditTable = "#editProductForm table tbody tr td";
	
	@FindBy(css="#saveButton")
	private WebElement Save;
	
	@FindBy(css = "#editDialog div div div a[onclick*='close']")
	private WebElement Cancel;
	
	public ProductsCatalogPage clickSave(){
		Save.click();
		waitForElementToBeInvisible(By.cssSelector("#content > div.fancybox-wrap.fancybox-desktop.fancybox-type-html.fancybox-opened > div > div > div > div > div.overlay-body.dialog"));

		return PageFactory.initElements(driver, ProductsCatalogPage.class);
	}
	
	public ProductsCatalogPage clickCancel(){
		Cancel.click();
		waitForElementToBeInvisible(By.linkText("Cancel"));
		return PageFactory.initElements(driver, ProductsCatalogPage.class);
	}
	
	//---each field config-------//
	public String getProductId(){
		return productId.getAttribute("value");
	}
	
	public String getManufacturerName(){
		return manufacturerName.getAttribute("value");

	}
	
	public String getManufacturerPartNumber(){
		return manufacturerPartNumber.getAttribute("value");
	}
	
	public String getCnetSkuId(){

			return cnetSkuId.getAttribute("value");

	}
	
	public String getProductURL(){
		return productUrl.getAttribute("value");
	}
	
	public String getInventory(){
		return inventory.getAttribute("value");
	}
	
	public String getPrice(){
		return price.getAttribute("value");
	}
	
	public String getUpcEan(){
		return upcEan.getAttribute("value");
	}
	
	public String getMsrp(){
		return msrp.getAttribute("value");
	}
	
	
	public void setManufacturerName(String...value){
		sendKeysHelper(manufacturerName, value);
	}
	
	public void setManufacturerPartNumber(String...value){
		sendKeysHelper(manufacturerPartNumber, value);
	}
	
	public void setCnetSkuId(String...value){
		sendKeysHelper(cnetSkuId, value);
	}
	
	public void setProductURL(String...value){
		sendKeysHelper(productUrl, value);
	}
	
	public void setInventory(String...value){
		sendKeysHelper(inventory, value);
	}
	
	public void setPrice(String...value){
		sendKeysHelper(price, value);
	}
	
	public void setUpcEan(String...value){
		sendKeysHelper(upcEan, value);
	}
	
	public void setMsrp(String...value){
		sendKeysHelper(msrp, value);
	}
	
	public static final String SELECT_ALL_MAC = Keys.chord(Keys.COMMAND, "a");
	public static final String SELECT_ALL_LINUX = Keys.chord(Keys.CONTROL, "a");

	public Actions action = new Actions(driver);
	public void sendKeysHelper(WebElement e, String...value){
		try{
			e.isDisplayed();
		}catch(NoSuchElementException error){
			return;
		}
		
		e.clear();
		
		if(driver instanceof FirefoxDriver){
			e.click();
			if(new GlobalVar().isJenkins()){
				e.sendKeys(SELECT_ALL_LINUX, value[0]);
			}else{
				e.sendKeys(SELECT_ALL_MAC, value[0]);
			}
		}else{
			//chrome, etc/
			deleteAllInput(e);
			e.sendKeys(value[0]);
		}
	}
	private WebElement productId;
	private WebElement manufacturerName;
	private WebElement manufacturerPartNumber;
	private WebElement cnetSkuId;
	private WebElement productUrl;
	private WebElement inventory;
	private WebElement price;
	private WebElement upcEan;
	private WebElement msrp;
	
	public EditProductPopupPage setData(){
		String fieldValueXpathInput = "../td[@class ='field-value']/input";
		String fieldValueXpathTextArea = "../td[@class ='field-value']/textarea";

		
		List<WebElement> list = driver.findElements(By.cssSelector("#editProductForm > table > tbody > tr > td.field-name"));
		
		for(WebElement e: list){
			if(e.getText().toLowerCase().contains("product id")){
				productId = reverseFindElement(e, fieldValueXpathInput);
			}
			else if(e.getText().toLowerCase().contains("manufacturer name")){
				manufacturerName =reverseFindElement(e, fieldValueXpathInput);
			}
			else if(e.getText().toLowerCase().contains("manufacturer part number")){
				manufacturerPartNumber = reverseFindElement(e, fieldValueXpathInput);
			}
			else if(e.getText().toLowerCase().contains("cnet sku id")){
				cnetSkuId = reverseFindElement(e, fieldValueXpathInput);
			}
			else if(e.getText().toLowerCase().contains("product url")){
				productUrl = reverseFindElement(e, fieldValueXpathTextArea);
			}
			else if(e.getText().toLowerCase().contains("inventory")){
				inventory = reverseFindElement(e, fieldValueXpathInput);
			}
			else if(e.getText().toLowerCase().contains("price")){
				price = reverseFindElement(e, fieldValueXpathInput);
			}
			else if(e.getText().toLowerCase().contains("msrp")){
				msrp = reverseFindElement(e, fieldValueXpathInput);
			}
			else if(e.getText().toLowerCase().contains("upc/ean")){
				upcEan = reverseFindElement(e, fieldValueXpathInput);
			}
		}
		
		return this;
		
	}
	
	public WebElement reverseFindElement(WebElement e, String xpathExpression){
		return e.findElement(By.xpath(xpathExpression));
	}
	
	public void deleteAllInput(WebElement e){
		while(!e.getAttribute("value").trim().isEmpty()){
			action.moveToElement(e).doubleClick().sendKeys(Keys.DELETE).build().perform();
		}
	}
}
