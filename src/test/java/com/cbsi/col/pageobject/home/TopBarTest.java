package com.cbsi.col.pageobject.home;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.test.foundation.ColBaseTest;

public class TopBarTest extends ColBaseTest{
	public TopBarTest(String url, String browser) {
		super(url, browser);
		// TODO Auto-generated constructor stub
	}

	@Test//#5586
	public void topBarHoverIconsAreDisplayed(){
		TopBar topbar = PageFactory.initElements(driver, TopBar.class);
		assertTrue(topbar.isOrganizerDropdownDisplayed());
		assertTrue(topbar.isInboxDropdownDisplayed());
		assertTrue(topbar.isUserDropdownDisplayed());
		
	}
}
