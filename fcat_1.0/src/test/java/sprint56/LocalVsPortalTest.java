package sprint56;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.cbsi.tests.Foundation.*;
import com.cbsi.tests.PageObjects.AddCatalogPage;
import com.cbsi.tests.PageObjects.CatalogsPage;
import com.cbsi.tests.PageObjects.FCatHomePage;

public class LocalVsPortalTest extends AllBaseTest{
	public LocalVsPortalTest(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}

	@Test
	public void headerShows(){
		FCatHomePage homePage = EasyLoginToLocal();
		CatalogsPage catalogsPage = homePage.goToCatalogs();
		AddCatalogPage addCatalogPage = catalogsPage.goToAddCatalog();
		assertTrue(addCatalogPage.isHeaderDisplayed());
		
	}
}
