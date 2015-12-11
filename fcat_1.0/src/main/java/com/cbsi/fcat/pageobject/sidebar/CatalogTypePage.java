package com.cbsi.fcat.pageobject.sidebar;

import org.openqa.selenium.WebDriver;

import com.cbsi.fcat.pageobject.foundation.BasePage;

public class CatalogTypePage extends BasePage{

	public CatalogTypePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForTextToBeVisible("List all Catalog Types", "span");
	}
	
	
	
	

}
