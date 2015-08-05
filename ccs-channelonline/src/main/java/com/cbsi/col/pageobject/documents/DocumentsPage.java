package com.cbsi.col.pageobject.documents;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.ColBasePage;

public class DocumentsPage extends ColBasePage{
	public DocumentsPage(WebDriver driver){
		super(driver);
		waitForPageToLoad(By.cssSelector("div h1"));
	}

	public List<String> getDescriptionTitles(){
		List<WebElement> descriptions = driver.findElements(By.cssSelector("div[id^='lineDescriptionOne']"));
		List<String> descriptionList = new ArrayList<String>();
		for(WebElement description: descriptions){
			descriptionList.add(description.getText());
		}
		
		return descriptionList;
	}
	
	public void searchQuote(){
		
	}
	public boolean hasQuote(int docNumber){
		List<WebElement> docNumbers = driver.findElements(By.xpath("//td[2]/a"));
		for(WebElement singleDoc: docNumbers){
			if(singleDoc.getText().equals(docNumber+"")){
				return true;
			}
		}
		return false;
	}
}
