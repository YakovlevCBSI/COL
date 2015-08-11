package com.cbsi.test.pageobjects.sidebar;

import org.openqa.selenium.WebDriver;

import com.cbsi.tests.PageObjects.BasePage;

public class RolesPage extends BasePage{

	public RolesPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForTextToBeVisible("List all Roles", "span");
	}

}
