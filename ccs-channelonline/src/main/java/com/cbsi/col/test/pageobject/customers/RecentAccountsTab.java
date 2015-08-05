package com.cbsi.col.test.pageobject.customers;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.cbsi.col.pageobject.ColBasePage;

public class RecentAccountsTab extends AccountsPage{
	public RecentAccountsTab(WebDriver driver){
		super(driver);
//		waitForPageToLoad(By.cssSelector("table.costandard"));
//		waitForElementToBeInvisible(By.linkText("Recent Accounts"));
		forceWait(500);
//		waitForElementToBeVisible(By.linkText("Recent Accounts"));

	}

	
}
