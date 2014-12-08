package com.cbsi.test.LoginPageTest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.cbsi.tests.Foundation.EmbedBaseTest;
import com.cbsi.tests.PageObjects.EmbedPage;
import com.cbsi.tests.PageObjects.FCatLoginPage;

public class Regression extends EmbedBaseTest{

	public Regression(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}
	
	@Test
	public void CCSQS1227(){
		EmbedPage embedPage = GotoEmbedPage();
		
		if(embedPage.getCurrentURL().contains("dev-")){return;}
		
		FCatLoginPage fcatLoginPage = embedPage.goToLoginPage();
		assertTrue(fcatLoginPage.isBeforeLoginPage());
	}
	

}
