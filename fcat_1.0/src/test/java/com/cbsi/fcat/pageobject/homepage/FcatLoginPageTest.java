package com.cbsi.fcat.pageobject.homepage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.fcat.pageobject.foundation.AllBaseTest;
import com.cbsi.fcat.pageobject.foundation.EmbedBaseTest;
import com.cbsi.fcat.pageobject.foundation.FormBaseTest;
import com.cbsi.fcat.pageobject.homepage.FCatHomePage;
import com.cbsi.fcat.pageobject.homepage.FCatLoginPage;
import com.cbsi.fcat.util.GlobalVar;


public class FcatLoginPageTest extends FormBaseTest{
	public FcatLoginPageTest(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Override
	@Before
	public void startUp(){
		System.out.println("Starting internal login...");
		startUpWithoutLogin();
	
	}
	
	@Test
	public void login(){	
		navigateToLoginPage();
		FCatHomePage homePage = PageFactory.initElements(driver, FCatHomePage.class);
		assertTrue(homePage.isSideBarPresent());		
		
	}
	
	@Ignore
	@Test
	public void Login_BadUser_BadPw(){
		navigateToLoginPage("BadUser","BadPassword");
		FCatLoginPage loginPage = PageFactory.initElements(driver, FCatLoginPage.class);
		assertEquals("Your login attempt was not successful, try again. Reason: Bad credentials .",loginPage.getErrorMessage());
		
	}
	
	@Ignore
	@Test
	public void Login_GoodUser_BadPw(){
		navigateToLoginPage(GlobalVar.LocalId,"badPasswrd");
		FCatLoginPage loginPage = PageFactory.initElements(driver, FCatLoginPage.class);
		System.out.println(loginPage.getErrorMessage());
		assertEquals("Your login attempt was not successful, try again. Reason: Bad credentials .", loginPage.getErrorMessage());
	}
	
	@Test
	public void logout(){
		navigateToLoginPage();
		FCatHomePage homePage = PageFactory.initElements(driver, FCatHomePage.class);
		FCatLoginPage fcatLoginPage = homePage.logOut();
		assertTrue(fcatLoginPage.isBeforeLoginPage());
	}
	
	public static final String directUrlToProductsPage= GlobalVar.stageServer + "fcatCatalog/catalogs#catalogProducts_catalogId=14724";
	@Test
	public void DoNotAccessCatalogPageWIthoutLogin(){
		driver.get(directUrlToProductsPage);
		assertEquals("HTTP Status 401 - Could not authorize",driver.findElement(By.cssSelector("h1")).getText());
//		FCatLoginPage loginPage = PageFactory.initElements(driver, FCatLoginPage.class);
		
	}
}
