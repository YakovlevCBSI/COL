package com.cbsi.tests.PageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EditProductPopupPage extends BasePage{

	public EditProductPopupPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForPageToLoad();
	}
	
	@Override
	public void waitForPageToLoad(){
		waitForElementToBeVisible(By.cssSelector("#content > div.fancybox-wrap.fancybox-desktop.fancybox-type-html.fancybox-opened > div > div > div > div > div.overlay-body.dialog"));
	}
	
	@FindBy(linkText="Save")
	private WebElement Save;
	
	@FindBy(linkText = "Cancel")
	private WebElement Cancel;
	
	public ProductsCatalogPage clickSave(){
		Save.click();
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
		String fieldValueXpath = "../td[@class ='field-value']/input";
		
		List<WebElement> list = driver.findElements(By.cssSelector("#editProductForm > table > tbody > tr > td.field-name"));
		
		for(WebElement e: list){
			if(e.getText().toLowerCase().contains("product id")){
				productId = reverseFindElement(e, fieldValueXpath);
			}
			else if(e.getText().toLowerCase().contains("manufacturer name")){
				manufacturerName =reverseFindElement(e, fieldValueXpath);
			}
			else if(e.getText().toLowerCase().contains("manufacturer part number")){
				manufacturerPartNumber = reverseFindElement(e, fieldValueXpath);
			}
			else if(e.getText().toLowerCase().contains("cnet sku id")){
				cnetSkuId = reverseFindElement(e, fieldValueXpath);
			}
			else if(e.getText().toLowerCase().contains("product url")){
				productUrl = reverseFindElement(e, fieldValueXpath);
			}
			else if(e.getText().toLowerCase().contains("inventory")){
				inventory = reverseFindElement(e, fieldValueXpath);
			}
			else if(e.getText().toLowerCase().contains("price")){
				price = reverseFindElement(e, fieldValueXpath);
			}
			else if(e.getText().toLowerCase().contains("msrp")){
				msrp = reverseFindElement(e, fieldValueXpath);
			}
			else if(e.getText().toLowerCase().contains("upc/ean")){
				upcEan = reverseFindElement(e, fieldValueXpath);
			}
		}
		
		return this;
		
	}
	
	public WebElement reverseFindElement(WebElement e, String xpathExpression){
		return e.findElement(By.xpath(xpathExpression));
	}
}
