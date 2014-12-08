package Smoke;

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
		
		FCatHomePage homePage = PageFactory.initElements(driver, FCatHomePage.class);
		assertTrue(homePage.isSideBarPresent());		
		
	}
	
	@Test
	public void logout(){
		FCatHomePage homePage = PageFactory.initElements(driver, FCatHomePage.class);
		FCatLoginPage fcatLoginPage = homePage.logOut();
		assertTrue(fcatLoginPage.isBeforeLoginPage());
	}
}
