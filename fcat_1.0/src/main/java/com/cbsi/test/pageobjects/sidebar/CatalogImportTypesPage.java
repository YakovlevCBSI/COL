package com.cbsi.test.pageobjects.sidebar;

import org.openqa.selenium.WebDriver;

import com.cbsi.tests.PageObjects.BasePage;

public class CatalogImportTypesPage extends BasePage{

	public CatalogImportTypesPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForTextToBeVisible("List all Catalog Import Types", "span");
	}

}
