package Smoke;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.cbsi.tests.Foundation.BaseTest;
import com.cbsi.tests.PageObjects.FCatHomePage;
import com.cbsi.tests.PageObjects.FCatLoginPage;

public class SecurityTest extends BaseTest{
	public SecurityTest(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}

	@Test
	public void login(){
		FCatLoginPage loginPage = PageFactory.initElements(driver, FCatLoginPage.class);
		FCatHomePage homePage = loginPage.loginToHomePage();
		assert(homePage.isSideBarPresent());
		
	}
	
	@Test
	public void logout(){
		
	}
}
