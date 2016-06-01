package com.cbsi.col.pageobject.customers;

import org.openqa.selenium.WebDriver;

import com.cbsi.col.pageobject.home.ColBasePage;

public class MergeCustomerPage extends ColBasePage{

	public MergeCustomerPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForTextToBeVisible("Merge Customer Records", "h1");
	}

}
