package com.cbsi.tests.PageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductsCatalogPage extends BasePage{
	public ProductsCatalogPage(WebDriver driver){
		super(driver);
		waitForPageToLoad();

	}
	
	@Override
	public void waitForPageToLoad(){
		waitForPageToLoad(By.cssSelector("table[class*='catalog-product-list']"));
	}
	@FindBy(css="a.nav-button.next")
	private WebElement goRight;
	
	@FindBy(css="div#page-number-container-low")
	private WebElement currentPage;
	public ProductsCatalogPage clickGoRight(){
		String pageNum = currentPage.getText();
		goRight.click();
		
		long startTime = System.currentTimeMillis();
		while(System.currentTimeMillis() - startTime < 30000){
			if(!pageNum.equals(refreshStaleElement(currentPage).getText())){
				break;
			}
		}
		
		if(pageNum.equals(refreshStaleElement(currentPage).getText())){
			throw new NoSuchElementException("Page was still loading...");
		}
		
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return PageFactory.initElements(driver, ProductsCatalogPage.class);
	}
	
	@FindBy(css="div[title='Not mapped']")
	private WebElement NotMappedIcon;
	public MapProductsDialog clickNotMappedIcon(){
		NotMappedIcon.click();
		return PageFactory.initElements(driver, MapProductsDialog.class);
	}
	
	public ProductsCatalogPage mapUnmappedItem(String searchText, int nthResult){
		MapProductsDialog mapProductsDialog = clickNotMappedIcon();
		mapProductsDialog.searchName(searchText).selectAnItemFromResult(nthResult);
		return mapProductsDialog.clickSave();
		
	}
	
	
	public boolean isTableCorrectlyRendered(){
		try{
			driver.findElement(By.cssSelector("table.catalog-product-list tbody tr td"));
		}catch(NoSuchElementException e){
			return false;
		}
		return true;
	}
	
	public boolean iconsShowCorrectly(){
		List<WebElement> iconList = driver.findElements(By.cssSelector("tr td.actions"));
		String gpicon= "gp-icon ";
		
		int count=0;
		for(WebElement e: iconList){
			
			String checkOrPlus = e.findElement(By.xpath("a[1]/div")).getAttribute("class");
			if(!(checkOrPlus.equals(gpicon + "check") || checkOrPlus.equals(gpicon + "plus"))){
				System.out.println("check " + checkOrPlus  + "/ " + count);
				return false;
			}
			
			String edit = e.findElement(By.xpath("a[2]/div")).getAttribute("class");
			if(!(edit.equals(gpicon +"edit"))){
				System.out.println("edit: " + edit + "/ " + count);
				return false;
			}
			
			String trash = e.findElement(By.xpath("a[3]/div")).getAttribute("class");
			if(!(trash.equals(gpicon + "trash"))){
				System.out.println("trash; " + trash + "/ " + count);
				return false;
			}
			
			count++;
		}
		
		return true;
	}
	
}
