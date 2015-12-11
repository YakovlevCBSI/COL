package com.cbsi.test.pageobject.sidebar;

import org.openqa.selenium.WebDriver;

import com.cbsi.fcat.pageobject.foundation.BasePage;

public class UsersPartyAssignmentPage extends BasePage{

	public UsersPartyAssignmentPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForTextToBeVisible("List all Principal Party Assocs", "span");
	}

}
