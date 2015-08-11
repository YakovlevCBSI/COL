package com.cbsi.test.pageobjects.sidebar;

import org.openqa.selenium.WebDriver;

import com.cbsi.tests.PageObjects.BasePage;

public class AttributeTypePage extends BasePage{

	public AttributeTypePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForTextToBeVisible("List all Attribute Types", "span");
	}

}
