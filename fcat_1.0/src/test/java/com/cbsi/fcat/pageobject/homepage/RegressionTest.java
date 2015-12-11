package com.cbsi.fcat.pageobject.homepage;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.cbsi.fcat.pageobject.foundation.EmbedBaseTest;
import com.cbsi.fcat.pageobject.homepage.FCatLoginPage;
import com.cbsi.fcat.pageobject.others.EmbedPage;

public class RegressionTest extends EmbedBaseTest{

	public RegressionTest(String URL, String browser) {
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
