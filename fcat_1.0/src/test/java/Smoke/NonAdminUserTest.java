package Smoke;

import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.tests.Foundation.BaseTest;
import com.cbsi.tests.Foundation.FormBaseTest;
import com.cbsi.tests.PageObjects.BFPLoginPage;
import com.cbsi.tests.PageObjects.CatalogsPage;
import com.cbsi.tests.PageObjects.FCatHomePage;
import com.cbsi.tests.PageObjects.FCatLoginPage;

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
	public void AdminSpecificUiNotDisplayed(){
		assertFalse(homePage.isSecurityDisplayed());
		assertFalse(homePage.isItemMetadataDisplayed());
		assertFalse(homePage.isWorkflowsDisplayed());
	}
	
	public void loginAsNonAdmin() {
		FCatLoginPage loginPage = PageFactory.initElements(driver, FCatLoginPage.class);
		homePage = loginPage.loginToHomePage(username, password).goToHomePage();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
