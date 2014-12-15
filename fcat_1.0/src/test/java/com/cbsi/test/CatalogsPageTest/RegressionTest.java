package com.cbsi.test.CatalogsPageTest;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.tests.Foundation.AllBaseTest;
import com.cbsi.tests.PageObjects.CatalogsPage;
import com.cbsi.tests.PageObjects.DetailsPage;
import com.cbsi.tests.PageObjects.UploadPopupPage;

public class RegressionTest extends AllBaseTest{

	public RegressionTest(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}
	
	@Test
	public void UploadFullFileSuccessfully() throws InterruptedException{
		DetailsPage detailsPage = uploadFileWihtoutMapping("full");
		assertTrue("Status showed '" + detailsPage.getStatus() + "'",detailsPage.getStatus().equals("DONE"));
	}
	
	@Test
	public void UploadIncremental() throws InterruptedException{
		DetailsPage detailsPage = uploadFileWihtoutMapping("incremental");
		assertTrue("Status showed '" + detailsPage.getStatus() + "'", detailsPage.getStatus().equals("DONE"));
	}
	
	public DetailsPage uploadFileWihtoutMapping(String fullOrIncremental) throws InterruptedException{
		CatalogsPage catalogsPage = PageFactory.initElements(driver, CatalogsPage.class);
		UploadPopupPage uploadPopupPage = catalogsPage.clickUpload();
		
		
		//since default is unchecked, check it you wanna upload as FullFile.
		if(fullOrIncremental.equals("incremental")){
			uploadPopupPage.selectDropBoxOption("CSV").clickUploadFile();
			uploadPopupPage.uploadLocalFileFromFinder("small").clickNext();

		}
		else if(fullOrIncremental.equals("full")){
			uploadPopupPage.checkFullFile().selectDropBoxOption("TXT").clickUploadFile();
			uploadPopupPage.uploadLocalFileFromFinder("big").clickNext();
		}
		
		if(!uploadPopupPage.getProgress().contains("100%")){
			throw new WebDriverException("failed at file upload...");
		}
		
		return (DetailsPage)uploadPopupPage.clickNextAfterUpload(false);
	
	}
}
