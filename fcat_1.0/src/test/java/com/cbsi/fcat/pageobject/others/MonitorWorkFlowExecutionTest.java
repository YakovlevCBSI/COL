package com.cbsi.fcat.pageobject.others;

import java.util.List;

import org.junit.Assume;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.cbsi.fcat.pageobject.foundation.FormBaseTest;
import com.cbsi.fcat.pageobject.homepage.FCatHomePage;
import com.cbsi.fcat.pageobject.others.DashboardPage;
import com.cbsi.fcat.pageobject.others.DashboardPage.STATUS;

public class MonitorWorkFlowExecutionTest extends FormBaseTest{
	@Rule
	public ExpectedException expect = ExpectedException.none();
	
	DashboardPage dashboardPage;
	
	public MonitorWorkFlowExecutionTest(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}

	@Test
	public void ErrorReport(){
		Assume.assumeTrue(getBrowser().contains("chrome"));
		navigateToDashBoardPageFromCatalogs();
		dashboardPage.pickStatus(STATUS.ERROR);
		
//		expect.expect(NullPointerException.class);
//		expect.expectMessage("no available data");
		dashboardPage.printDetailsMessage();
	}
	
	public void navigateToDashBoardPageFromCatalogs(){
		FCatHomePage homePage = catalogsPage.clickBackIcon();
		dashboardPage = homePage.goToDashboard();	
	}
}
