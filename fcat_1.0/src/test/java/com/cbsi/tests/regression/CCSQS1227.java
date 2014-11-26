package com.cbsi.tests.regression;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.cbsi.tests.Foundation.BaseTest;
import com.cbsi.tests.PageObjects.EmbedPage;
import com.cbsi.tests.PageObjects.FCatLoginPage;
import com.cbsi.tests.util.GlobalVar;

public class CCSQS1227 extends BaseTest{
	public CCSQS1227(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}

	@Test
	public void adminPageAlwaysRequireAuth(){
		EmbedPage embedPage = GotoEmbedPage();
		if(embedPage.getCurrentURL().contains(GlobalVar.embedPath)){
			System.out.println("condition passed....");
			FCatLoginPage fcatLoginPage = embedPage.goToLoginPage();
			assertTrue(fcatLoginPage.isBeforeLoginPage());
		}
		
	}
}
