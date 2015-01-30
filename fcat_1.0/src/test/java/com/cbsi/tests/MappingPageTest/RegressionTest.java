package com.cbsi.tests.MappingPageTest;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.tests.Foundation.AllBaseTest;
import com.cbsi.tests.PageObjects.AddCatalogPage;
import com.cbsi.tests.PageObjects.CatalogsPage;
import com.cbsi.tests.PageObjects.MappingPage;
import com.cbsi.tests.PageObjects.UploadPopupPage;

public class RegressionTest extends AllBaseTest{
	public RegressionTest(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}
	
	//private String tempFile = "";
	
	@Test
	public void CCSQS1063() throws InterruptedException{
		MappingPage mappingPage = navigateToMappingPage();
		assertTrue(mappingPage.isSaveDisabled());
		
	}
	
	@Test
	public void UrlEncodingTextsHaveSameStyles() throws InterruptedException{
		MappingPage mappingPage = navigateToMappingPage();
		assertTrue(mappingPage.areElementsSameStyles(mappingPage.getFileEncodingElements()));
		
	}

	@After
	public void cleanUp(){
		super.cleanUpThenDeleteTemp();
	}
	
	
	//-------------------------------------------  Helper Method --------------------------------------------//

	public MappingPage navigateToMappingPage() throws InterruptedException{
		CatalogsPage catalogsPage = PageFactory.initElements(driver, CatalogsPage.class);
		AddCatalogPage addCatalogsPage = catalogsPage.goToAddCatalog();

		/** set file name to cleanup**/
		tempFile = addCatalogsPage.getTempFileName();
		
		UploadPopupPage uploadPopupPage = addCatalogsPage.fillInName();
		uploadPopupPage.clickUploadFile();
		uploadPopupPage = uploadLocalFileOSSpecific(uploadPopupPage).clickNext();
		
		MappingPage mappingPage = (MappingPage) uploadPopupPage.clickNextAfterUpload(true);
		return mappingPage;
	}
}
