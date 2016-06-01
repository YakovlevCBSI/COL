package com.cbsi.col.pageobject.documents;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.home.ColBasePage;

public class RecycleBinPage extends ColBasePage{

	public RecycleBinPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForTextToBeVisible("Recycle Bin", "h1");
	}
	
	public RecycleBinPage restoreByDocNumber(long docNumber){
		forceWait(1000);
		findDataRowByName(docNumber+"", 2).findElement(By.xpath("../../td[14]/input")).click();
		forceWait(1000);
		return PageFactory.initElements(driver, RecycleBinPage.class);
	}
	
	public boolean hasDoc(long docNumber){
		return findDataRowByName(docNumber+"", 2)==null?false:true;
	}

}
