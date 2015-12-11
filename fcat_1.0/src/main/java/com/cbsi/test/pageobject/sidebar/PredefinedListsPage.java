package com.cbsi.test.pageobject.sidebar;

import org.openqa.selenium.WebDriver;

import com.cbsi.fcat.pageobject.foundation.BasePage;

public class PredefinedListsPage extends BasePage{

	public PredefinedListsPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForTextToBeVisible("List all Predefined Lists", "span");
	}

}
