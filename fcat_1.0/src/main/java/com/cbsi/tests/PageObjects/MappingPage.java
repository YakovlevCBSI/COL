package com.cbsi.tests.PageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MappingPage extends BasePage{
	public MappingPage(WebDriver driver) {
		super(driver);
		waitForPageToLoad();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void waitForPageToLoad(){
		waitForPageToLoad(By.cssSelector("div.panel div.content"));
	}
	
	@FindBy(css="a#save_mappings_button")
	private WebElement Save;
	public MappingPage clickSave(){
		waitForElementToBeVisible("a#save_mappings_button");
		Save.click();
		return this;
	}
	
	public boolean isSaveDisabled(){
		System.out.println("save value; "+ Save.getAttribute("href").trim().toLowerCase());
		return Save.getAttribute("class").trim().toLowerCase().contains("disabled");
	}
	
	@FindBy(css="td.cnet-fields-encoding a span.selectBox-label")
	private WebElement DefaultFileEncoding;
	
	/**
	 * All elements that are in dropdown box of fileencoding has rel value assigned.
	 * Get these elements and return as List.
	 * @return
	 */
	public List<WebElement> getFileEncodingElements(){
		List<WebElement> fileEncodingTexts = new ArrayList<WebElement>();
		fileEncodingTexts.add(DefaultFileEncoding);
		DefaultFileEncoding.click();
		
		List<WebElement> dropdownTexts = driver.findElements(By.cssSelector("ul li a"));

		for(WebElement e: dropdownTexts){
			if(!e.findElement(By.xpath("../a")).getText().isEmpty()){
				fileEncodingTexts.add(e);
			}
		}
	
		return fileEncodingTexts;
		
	}
	
	/**
	 * If list contains elements with styles that are different from one anther
	 * return false;
	 * @param elements
	 * @return
	 */
	public boolean areElementsSameStyles(List<WebElement> elements){
		//String color ="";
		String fontWeight ="";
		String fontFamily ="";
		String fontSize = "";
		int count = 0;
		for(WebElement e: elements){
		
			if(		(//e.getCssValue("color") != color||
					!(e.getCssValue("font-weight").equals(fontWeight) ||
					e.getCssValue("font-family").equals(fontFamily) ||
					e.getCssValue("font-size").equals(fontSize))) &&
					count >= 1
				)
					  
			{
				System.out.println("=====================");
				System.out.println(e.getCssValue("color") );
				System.out.println(e.getCssValue("font-weight"));
				System.out.println(e.getCssValue("font-family"));
				System.out.println(e.getCssValue("font-size") );
				System.out.println("=====================");
				return false;
			}
			
			System.out.println(count + " / " + e.getText() );
			System.out.println(e.getCssValue("color") );
			System.out.println(e.getCssValue("font-weight"));
			System.out.println(e.getCssValue("font-family"));
			System.out.println(e.getCssValue("font-size") );
			//color = e.getCssValue("color");
			fontWeight = e.getCssValue("font-weight");
			fontFamily = e.getCssValue("font-family");
			fontSize = e.getCssValue("font-size");
			count++;  
		}
		
		
		return true;
	}
	
}
