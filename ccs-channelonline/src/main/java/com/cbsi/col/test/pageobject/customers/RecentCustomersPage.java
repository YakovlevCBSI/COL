package com.cbsi.col.test.pageobject.customers;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.cbsi.col.pageobject.ColBasePage;

public class RecentCustomersPage extends CustomersPage{
	public RecentCustomersPage(WebDriver driver){
		super(driver);
		waitForPageToLoad(By.cssSelector("table.costandard"));
	}

	
}
