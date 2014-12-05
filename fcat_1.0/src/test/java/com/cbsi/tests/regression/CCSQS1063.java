package com.cbsi.tests.regression;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.tests.Foundation.BaseTest;
import com.cbsi.tests.PageObjects.AddCatalogPage;
import com.cbsi.tests.PageObjects.CatalogsPage;
import com.cbsi.tests.PageObjects.MappingPage;
import com.cbsi.tests.PageObjects.UploadPopupPage;

public class CCSQS1063 extends BaseTest{

	public CCSQS1063(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}


	private String tempFile = "";
	
	@Test
	public void DisableSaveIfRequiredFieldsNotFilled() throws InterruptedException{
		CatalogsPage catalogsPage = PageFactory.initElements(driver, CatalogsPage.class);
		AddCatalogPage addCatalogsPage = catalogsPage.goToAddCatalog();

		/** set file name to cleanup**/
		tempFile = addCatalogsPage.getTempFileName();
		
		UploadPopupPage uploadPopupPage = addCatalogsPage.fillInName();
		uploadPopupPage.clickUploadFile();
		uploadPopupPage.uploadLocalFileFromFinder();
		uploadPopupPage.clickNext();
		
		MappingPage mappingPage = uploadPopupPage.clickNextAfterUpload();
		assertTrue(mappingPage.isSaveDisabled());
		
	}


	
	@After
	public void cleanUp(){
		super.cleanUpThenDeleteTemp(tempFile);
	}

}
