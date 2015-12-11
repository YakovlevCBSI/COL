package com.cbsi.fcat.pageobject.sidebar;

import org.openqa.selenium.WebDriver;

import com.cbsi.fcat.pageobject.foundation.BasePage;

public class DashboardPage extends BasePage{

	public DashboardPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForTextToBeVisible("Catalog Integration Flows", "a");
	}

}
