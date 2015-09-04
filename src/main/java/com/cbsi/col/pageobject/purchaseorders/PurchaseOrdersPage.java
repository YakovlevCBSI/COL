package com.cbsi.col.pageobject.purchaseorders;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.cbsi.col.pageobject.home.ColBasePage;

public class PurchaseOrdersPage extends ColBasePage{

	public PurchaseOrdersPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForTextToBeVisible("View POs", "h1");
	}
	@FindBy(css="td table")
	private WebElement Table;
	
	public void getTableAsMaps(){
		return;
	}
	
	static int currentPage=0;
	private static List<WebElement> dataColumns;

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
			return findDataRowByName(quoteNumber, docNumberSecondcolumn);
		}
		
		return null;		
	}
	
	
}
