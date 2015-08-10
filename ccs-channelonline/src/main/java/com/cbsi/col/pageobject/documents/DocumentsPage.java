package com.cbsi.col.pageobject.documents;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.text.WordUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
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
//	public boolean hasQuote(int docNumber){
//		List<WebElement> docNumbers = driver.findElements(By.xpath("//td[2]/a"));
//		for(WebElement singleDoc: docNumbers){
//			if(singleDoc.getText().equals(docNumber+"")){
//				return true;
//			}
//		}
//		return false;
//	}
	
	public boolean hasQuote(int docNumber){
		return findDataRowByName(docNumber)==null? false:true;
	}
	
	public boolean hasProposal(int docNumber){
		return findDataRowByName(docNumber, false)==null? false:true;

	}
	
	public QuotePage goToQuote(int quoteNumber){
		WebElement docLink = findDataRowByName(quoteNumber);
		docLink.click();
		
		return PageFactory.initElements(driver, QuotePage.class);
	}
	
	private WebElement ViewQuote;
	
	static int currentPage=0;
	private static List<WebElement> dataColumns;
	
	public WebElement findDataRowByName(int quoteNumber){
		return findDataRowByName(quoteNumber, true);
	}
	
	public WebElement findDataRowByName(int quoteNumber, boolean docNumberSecondcolumn){
		if(docNumberSecondcolumn){
			dataColumns = driver.findElements(By.cssSelector("table.costandard tbody tr td:nth-child(2) a"));
		}else{
			dataColumns = driver.findElements(By.cssSelector("table.costandard tbody tr td:nth-child(1) a"));
		}
		
		for(WebElement dataColumn: dataColumns){
//			System.out.println(dataColumn.getText());
			if(dataColumn.getText().contains(quoteNumber+"")){
				return dataColumn;
			}
		}
		
		List<WebElement> pageList = driver.findElements(By.cssSelector("tr.footer td a"));
		if(currentPage-1 >=0){
			int removePage = currentPage;
			while(removePage >0){
				removePage--;
				pageList.remove(removePage);
			}
		}
		if(pageList.size() >=1){
			currentPage++;
			pageList.get(0).click();
			waitForTextToBeVisible("Documents", "h1");
			dataColumns=null;
			findDataRowByName(quoteNumber, docNumberSecondcolumn);
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
	
	public DocumentsPage switchToTab(DocumentTabs tab){
		driver.findElement(By.linkText(WordUtils.capitalizeFully(tab.toString()))).click();
		waitForElementToBeInvisible(By.cssSelector("select#time_limit"));
		
		return PageFactory.initElements(driver, DocumentsPage.class);
	}
	

	@FindBy(css="select#time_limit")
	private WebElement TimeDropDown;
	public DocumentsPage filterByDate(Time days){
		TimeDropDown.click();
		driver.findElement(By.cssSelector("option[value='" + days.toString().toLowerCase() + "'")).click();
		
		try{
			waitForElementToBeInvisible(By.cssSelector("select#time_limit"), 5);
		}catch(TimeoutException e){
			
		}
		
		return PageFactory.initElements(driver, DocumentsPage.class);
	}
	
	public enum Time{
		ALL,
		TODAY,
		LAST7,
		LAST30
	}
	
	public enum DocumentTabs{
		ALLQUOTESANDORDERS,
		QUOTES,
		PROPOSALS,
		ORDERS,
		INVOICES,
		RMAS
	}
	
}
