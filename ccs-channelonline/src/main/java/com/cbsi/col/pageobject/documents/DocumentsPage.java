package com.cbsi.col.pageobject.documents;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.ColBasePage;
import com.cbsi.col.pageobject.customers.CurrentAccountTab;

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
	
	public QuotePage goToQuote(int quoteNumber){
		WebElement docLink = findDataRowByName(quoteNumber);
		docLink.click();
		
		return PageFactory.initElements(driver, QuotePage.class);
	}
	
	private List<WebElement> dataColumns;
	private WebElement ViewQuote;
	
	public WebElement findDataRowByName(int quoteNumber){
		dataColumns = driver.findElements(By.cssSelector("table.costandard tbody tr td:nth-child(2) a"));
		for(WebElement dataColumn: dataColumns){
			if(dataColumn.getText().contains(quoteNumber+"")){
				return dataColumn;
			}
		}
		return null;		
	}
	
	public QuotePage clickViewQuote(int quoteNumber){
		WebElement dataRow = findDataRowByName(quoteNumber);
		ViewQuote = dataRow.findElement(By.xpath("../td[2]/a"));
		ViewQuote.click();
		
		waitForElementToBeInvisible(By.xpath("td/a[contains(@title,'View Customer')]"));
		return PageFactory.initElements(driver, QuotePage.class);
	}
	
	public SalesOrderPage clickSalesOrder(int orderNumber){
		WebElement dataRow = findDataRowByName(orderNumber);
		ViewQuote = dataRow.findElement(By.xpath("../td[2]/a"));
		ViewQuote.click();
		
		waitForElementToBeInvisible(By.xpath("td/a[contains(@title,'View Customer')]"));
		return PageFactory.initElements(driver, SalesOrderPage.class);
	}
	
}
