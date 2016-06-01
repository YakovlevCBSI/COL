package com.cbsi.col.pageobject.documents;

import org.openqa.selenium.WebDriver;

public class ComparisonPage extends DocumentsBasePage{

	public ComparisonPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForTextToBeVisible("Comparison Results", "h1");
	}

}
