package Smoke;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.tests.Foundation.AllBaseTest;
import com.cbsi.tests.Foundation.EmbedBaseTest;
import com.cbsi.tests.Foundation.FormBaseTest;
import com.cbsi.tests.PageObjects.FCatHomePage;
import com.cbsi.tests.PageObjects.FCatLoginPage;
import com.cbsi.tests.util.GlobalVar;


public class FormBaedSecurityTest extends FormBaseTest{
	public FormBaedSecurityTest(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}
	
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
		navigateToLoginPage(GlobalVar.LocalId,"badPassword");
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
	
	public static final String directUrlToProductsPage="http://fcat-engine.cloudapp.net:6600/fcat/fcatCatalog/catalogs#catalogProducts_catalogId=14724";
	@Test
	public void DoNotAccessCatalogPageWIthoutLogin(){
		driver.get(directUrlToProductsPage);
		FCatLoginPage loginPage = PageFactory.initElements(driver, FCatLoginPage.class);
		
	}
}
