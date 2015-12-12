package com.cbsi.col.pageobject.products;

import org.openqa.selenium.WebDriver;

import com.cbsi.col.pageobject.home.ColBasePage;

public class AddToCatalogsPage extends ColBasePage{

	public AddToCatalogsPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForPageToLoad();
	}
	
	public void waitForPageToLoad(){
		waitForTextToBeVisible("Add to Catalogs", "h1");
	}

}
