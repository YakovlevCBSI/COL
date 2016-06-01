package com.cbsi.col.pageobject.customers;

import org.openqa.selenium.WebDriver;

import com.cbsi.col.pageobject.home.ColBasePage;

public class ContactInfoPage extends ColBasePage{

	public ContactInfoPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForTextToBeVisible("Accounts", "h4");
	}

}
