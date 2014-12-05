package com.cbsi.tests.regression;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.tests.Foundation.BaseTest;
import com.cbsi.tests.PageObjects.CatalogsPage;
import com.cbsi.tests.PageObjects.UploadPopupPage;

public class CCSQS1248 extends BaseTest{

	public CCSQS1248(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}

	private String tempFile;
	@Test
	public void UploadFileViaFormBasedAuth() throws InterruptedException{

		CatalogsPage catalogPage= PageFactory.initElements(driver, CatalogsPage.class);
		
		UploadPopupPage uploadPopupPage = catalogPage.clickUpload();
		uploadPopupPage.clickUploadFile();
		//uploadPopupPage.uploadFile("LondonDrugsTxt");
		
		//Trying finder grab with selenium
		uploadPopupPage.uploadLocalFileFromFinder();
		uploadPopupPage.clickNext();
		//System.out.println(uploadPopupPage.getProgress());
		assertTrue(uploadPopupPage.getProgress().contains("100%"));
		
	/**
		//new ImageNavigation().clickImageTarget("LondonDrugsTxt.png");
	//String imageFile = "/Users/alpark/Documents/workspace/fcat_1.0/src/main/resources/ImageResources/LondonDrugsTxt.png";
		String imageFile = "/Users/alpark/Desktop/LondonDrugsTxt.png";
		
		Mouse mouse = new DesktopMouse();
		File file = new File(imageFile);
		Target target = new ImageTarget(file);
		ScreenRegion screenRegion = new DesktopScreenRegion();
		//screenRegion.wait(target, 5);
		
		mouse.click(screenRegion.find(target).getCenter());
	*/
	}
	
	
}
