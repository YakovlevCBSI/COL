package com.cbsi.fcat.pageobject.sidebar;

import org.openqa.selenium.WebDriver;

import com.cbsi.fcat.pageobject.foundation.BasePage;

public class SSOUsersPage extends BasePage{

	public SSOUsersPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForTextToBeVisible("List all SSO Users", "span");
	}

}
