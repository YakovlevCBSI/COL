package com.cbsi.test.UploadPopupPage;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.tests.Foundation.AllBaseTest;
import com.cbsi.tests.PageObjects.CatalogsPage;
import com.cbsi.tests.PageObjects.UploadPopupPage;

public class RegressionTest extends AllBaseTest{

	public RegressionTest(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}
	
	private String tempFile;
	
	@Test
	public void CCSQS1248() throws InterruptedException{

		CatalogsPage catalogPage= PageFactory.initElements(driver, CatalogsPage.class);

		UploadPopupPage uploadPopupPage = catalogPage.clickUpload();
		uploadPopupPage.clickUploadFile();
		//uploadPopupPage.uploadFile("LondonDrugsTxt");
		
		//Trying finder grab with selenium
		uploadPopupPage.uploadLocalFileFromFinder();
		uploadPopupPage.clickNext();
		//System.out.println(uploadPopupPage.getProgress());
		assertTrue(uploadPopupPage.getProgress().contains("100%"));
	}

}
