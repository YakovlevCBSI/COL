package com.cbsi.fcat.pageobject.sidebar;

import org.openqa.selenium.WebDriver;

import com.cbsi.fcat.pageobject.foundation.BasePage;

public class UsersRoleAssignmentPage extends BasePage{

	public UsersRoleAssignmentPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForTextToBeVisible("List all Role User Assignments", "span");
	}

}
