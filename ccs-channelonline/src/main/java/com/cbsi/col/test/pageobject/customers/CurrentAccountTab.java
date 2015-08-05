package com.cbsi.col.test.pageobject.customers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.cbsi.col.pageobject.ColBasePage;

public class CurrentAccountTab extends AccountsPage{

	public CurrentAccountTab(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForPageToLoad(By.cssSelector("li.active a[href*='Customers/view']"));
	}

}
