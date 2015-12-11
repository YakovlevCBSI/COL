package com.cbsi.fcat.pageobject.sidebar;

import org.openqa.selenium.WebDriver;

import com.cbsi.fcat.pageobject.foundation.BasePage;

public class CatalogImportTypesPage extends BasePage{

	public CatalogImportTypesPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForTextToBeVisible("List all Catalog Import Types", "span");
	}

}
