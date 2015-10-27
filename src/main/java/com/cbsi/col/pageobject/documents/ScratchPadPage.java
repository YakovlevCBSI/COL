package com.cbsi.col.pageobject.documents;

import org.openqa.selenium.WebDriver;

public class ScratchPadPage extends DocumentsBasePage{

	public ScratchPadPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForTextToBeVisible(20000,"The \"Scratch Pad\" serves two purposes:", "p");
	}

}
