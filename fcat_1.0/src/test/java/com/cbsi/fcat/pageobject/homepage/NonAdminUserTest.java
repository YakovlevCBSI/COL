package com.cbsi.fcat.pageobject.homepage;

import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.fcat.pageobject.catatlogpage.CatalogsPage;
import com.cbsi.fcat.pageobject.foundation.BaseTest;
import com.cbsi.fcat.pageobject.foundation.FormBaseTest;
import com.cbsi.fcat.pageobject.homepage.BFPLoginPage;
import com.cbsi.fcat.pageobject.homepage.FCatHomePage;
import com.cbsi.fcat.pageobject.homepage.FCatLoginPage;

public class NonAdminUserTest extends FormBaseTest{
	public NonAdminUserTest(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}

	@Override
	@Before
	public void startUp(){
		driver = configureDrivers();
		driver.get(getURL());
		setDisplayToVm();
		loginAsNonAdmin();
	}
	
	private String username = "albert";
	private String password = "park";
	
	private FCatHomePage homePage;
	@Test
	public void AdminSpecificUiNotDisplayedOnCatalogsPage(){
		assertFalse(homePage.isSecurityDisplayed());
		assertFalse(homePage.isItemMetadataDisplayed());
		assertFalse(homePage.isWorkflowsDisplayed());
	}
	
	@Test
	public void AdminSpecificUiNotDisplayedOnProductsPage(){
		CatalogsPage catalogsPage = homePage.goToCatalogs();
		assertFalse(catalogsPage.isWorkflowDashboardDisplayed());
	}
	
	public void loginAsNonAdmin() {
		FCatLoginPage loginPage = PageFactory.initElements(driver, FCatLoginPage.class);
		homePage = loginPage.loginToHomePage(username, password);//.goToHomePage();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
