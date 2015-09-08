package com.cbsi.col.pageobject.purchaseorders;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.documents.SalesOrderPage;
import com.cbsi.col.pageobject.home.ColBasePage;

public class PurchaseOrdersTab extends ColBasePage{

	public PurchaseOrdersTab(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForTextToBeVisible("Purchase Orders", "font");
	}
	@FindBy(xpath="//tr[4]/td/table")
	private WebElement Table;
	
	public void getTableAsMaps(){
		return;
	}
	
	static int currentPage=0;
	private static List<WebElement> dataColumns;

	public boolean hasPoNumberLinkedToSo(int salesOrderNumber){
		return findDataRowByName(salesOrderNumber)==null?false:true;
	
	}
	
	public PurchaseOrderPage clickViewPoNumberLinkedToSo(int salesOrderNumber){
		 findDataRowByName(salesOrderNumber).findElement(By.xpath("../../td[3]/a")).click();
		 return PageFactory.initElements(driver, PurchaseOrderPage.class);
	}
	
	public SalesOrderPage clickViewSoNumber(int salesOrderNumber){
		 findDataRowByName(salesOrderNumber).click();
		 return PageFactory.initElements(driver, SalesOrderPage.class);
	}
	
	public WebElement findDataRowByName(int quoteNumber){
		dataColumns = Table.findElements(By.xpath("tbody/tr/td[13]/a"));

		for(WebElement dataColumn: dataColumns){
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
			return findDataRowByName(quoteNumber);
		}
		
		System.out.println("did not find orderNumber in purchaseOrderTab...");
		return null;		
	}
	
	
}
