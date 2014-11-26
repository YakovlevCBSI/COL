package sprint56;

import org.testng.annotations.Test;

import com.cbsi.tests.Foundation.BaseTest;
import com.cbsi.tests.PageObjects.AddCatalogPage;
import com.cbsi.tests.PageObjects.CatalogsPage;
import com.cbsi.tests.PageObjects.FCatHomePage;

public class LocalVsPortalTest extends BaseTest{
	public LocalVsPortalTest(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}

	@Test
	public void headerShows(){
		FCatHomePage homePage = EasyLogin();
		CatalogsPage catalogsPage = homePage.goToCatalogs();
		AddCatalogPage addCatalogPage = catalogsPage.goToAddCatalog();
		assert(addCatalogPage.isHeaderDisplayed());
		
	}
}
