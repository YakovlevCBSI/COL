package com.cbsi.fcat.pageobject.catalogpage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.jcip.annotations.NotThreadSafe;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriverException;

import com.cbsi.fcat.pageobject.catatlogpage.AddCatalogPage;
import com.cbsi.fcat.pageobject.catatlogpage.CatalogsPage;
import com.cbsi.fcat.pageobject.catatlogpage.DetailsPage;
import com.cbsi.fcat.pageobject.catatlogpage.DetailsPage.UploadStatus;
import com.cbsi.fcat.pageobject.catatlogpage.UploadPopupPage;
import com.cbsi.fcat.pageobject.catatlogpage.UploadPopupPage.UploadType;
import com.cbsi.fcat.pageobject.foundation.AllAndSecureBaseTest;

@NotThreadSafe
public class CatalogsPageTest extends AllAndSecureBaseTest{

	public CatalogsPageTest(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}

	@Test
	public void UploadFullFileManualOnExisting() throws InterruptedException{
		DetailsPage detailsPage = uploadFileWihtoutMapping(true);
		assertTrue("Status showed '" + detailsPage.getStatus() + "'",detailsPage.getStatus().equals(UploadStatus.DONE.toString()));
	}

	@Test
	public void UploadIncrementalManualOnExisting() throws InterruptedException{
		DetailsPage detailsPage = uploadFileWihtoutMapping(false);
		assertTrue("Status showed '" + detailsPage.getStatus() + "'", detailsPage.getStatus().equals(UploadStatus.DONE.toString()));
	}
	
	private String lastLoaded = "";
	@Test
	public void LastLoadedColumnIsUpdated_1303() throws InterruptedException{
		DetailsPage detailsPage = uploadFileWihtoutMapping(false);
		CatalogsPage catalogsPageNew = detailsPage.clickReturnToList();
		catalogsPageNew.setMyCatalogToManualCatalog();
		assertFalse(lastLoaded.equals(catalogsPageNew.getMyCatalogLastLoaded()));
	}
	
	@Test
	public void LastLoadedShowsLocalTimeZone_1274() throws InterruptedException{
		DetailsPage detailsPage = uploadFileWihtoutMapping(false);
		CatalogsPage catalogsPageNew = detailsPage.clickReturnToList();
		String time = catalogsPageNew.setMyCatalogToManualCatalog().getMyCatalogLastLoaded().replaceAll("[^\\d]", " ");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd HH");
		String localTime = sdf.format(new Date());
		
		System.out.println("localtime: " + localTime);
		System.out.println("displayed time: " + time);
		
		assertTrue(time.contains(localTime) || localTime.split("\\s")[3].equals("23"));  //check if the date is different by one minute.
	}

	@Test
	public void marketLableIsDisplayed(){
		String market = "";
		String code = "";
		
		AddCatalogPage propertyPage = catalogsPage.setMyCatalogToManualCatalog().clickEdit();
		market = propertyPage.pickRandomMarket();
		code = propertyPage.getCodeByCountry(market);

		System.out.println(market);
		CatalogsPage catalogsPage = propertyPage.setMarket(market).clickSave();
		catalogsPage.setMyCatalogToManualCatalog();
		
		assertEquals(code, catalogsPage.getMarketByCatalog(catalogsPage.getMyCatalog()));
		
	}
	

	
	//-------------------------------------------  Helper Method --------------------------------------------//
	
	public DetailsPage uploadFileWihtoutMapping(boolean isFull) throws InterruptedException{
		//CatalogsPage catalogsPage = PageFactory.initElements(driver, CatalogsPage.class);
		CatalogsPage catalogsPage = new CatalogsPage(driver);
		catalogsPage.setMyCatalogToManualCatalog();
		this.lastLoaded = catalogsPage.getMyCatalogLastLoaded();
		 UploadPopupPage uploadPopupPage = catalogsPage.clickUpload();

		//since default is unchecked, check it you wanna upload as FullFile.
		if(!isFull){
			uploadPopupPage.selectDropBoxOption(UploadType.TXT).clickUploadFile();
			uploadPopupPage.uploadLocalFileFromFinder("small").clickNext();

		}
		else{
			uploadPopupPage.checkFullFile().selectDropBoxOption(UploadType.TXT).clickUploadFile();
			uploadPopupPage.uploadLocalFileFromFinder("big").clickNext();
		}
		
		uploadPopupPage.waitForProgress();
//		if(!uploadPopupPage.getProgress().contains("100%")){
//			throw new WebDriverException("failed at file upload...");
//		}
		
		return (DetailsPage)uploadPopupPage.clickNextAfterUpload(false);
	
	}
	
	
}
