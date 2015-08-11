package com.cbsi.test.pageobjects.sidebar;

import org.openqa.selenium.WebDriver;

import com.cbsi.tests.PageObjects.BasePage;

public class ItemIdentificationTypePage extends BasePage{

	public ItemIdentificationTypePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForTextToBeVisible("List all Item Identification Types", "span");
	}

}
