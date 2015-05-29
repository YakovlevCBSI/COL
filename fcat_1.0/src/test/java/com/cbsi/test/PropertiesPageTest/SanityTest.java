package com.cbsi.test.PropertiesPageTest;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.tests.Foundation.AllBaseTest;
import com.cbsi.tests.PageObjects.AddCatalogPage;
import com.cbsi.tests.PageObjects.CatalogsPage;
import com.cbsi.tests.PageObjects.DetailsPage;
import com.cbsi.tests.PageObjects.MappingPage;
import com.cbsi.tests.PageObjects.UploadPopupPage;
import com.cbsi.tests.util.GlobalVar;

public class SanityTest extends AllBaseTest{

	public SanityTest(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}
	
	private boolean needsCleanUp=true;
	
	@Rule 
	public Timeout globalTimeout = new Timeout(350000);
	
	@After
	public void cleanUp(){
		if(needsCleanUp) {
			super.cleanUpThenDeleteTemp();
			
			CatalogsPage catalogsPageDirty=PageFactory.initElements(driver, CatalogsPage.class);
			catalogsPageDirty.cleanUpLeftOverCatalogs();
		}
	
		//cleanUpAfterClass();

	}
	
	
	private String URL= GlobalVar.ftpURL + "Test/myFullFile.txt";
	private String ExceUrl=GlobalVar.ftpURL + "Test/Excel.xlsx";
	private String USERNAME = GlobalVar.ftpUserName;
	private String PASSWORD = GlobalVar.ftpPassword;

	//@Ignore("pending dev")
	@Test
	public void automaticUploadInvalidURL(){
		AddCatalogPage addCatalogPage = navigateToAddcatalogPage(true);
		addCatalogPage.typeFileAndUserInfoAll("ftp://test.com", USERNAME, PASSWORD);
		addCatalogPage.fillInName().clickGetFile();
		try{ } catch(RuntimeException e){}
		
		assertTrue(hasNoError());
	}
	
	@Test
	public void automaticUploadWithInvalidUsername(){
		AddCatalogPage addCatalogPage = navigateToAddcatalogPage(true);
		addCatalogPage.typeFileAndUserInfoAll(URL, "bad", PASSWORD);
		UploadPopupPage uploadPopupPage= addCatalogPage.fillInName();
		uploadPopupPage.clickGetFile();
		
		assertTrue(hasNoError());		
	}
	
	@Test
	public void automaticUploadWithInvaludPassword(){
		AddCatalogPage addCatalogPage = navigateToAddcatalogPage(true);
		addCatalogPage.typeFileAndUserInfoAll(URL, USERNAME, "bad");
		UploadPopupPage uploadPopupPage= addCatalogPage.fillInName();
		uploadPopupPage.clickGetFile();
		
		assertTrue(hasNoError());
	}
	
	//@Ignore("oleg")
	@Test
	public void UploadFullFileAutomaticFromScratch(){
//		driver.manage().window().setSize(new Dimension(570, 500));
		AddCatalogPage addCatalogPage = navigateToAddcatalogPage(true);
		addCatalogPage.typeFileAndUserInfoAll(URL, USERNAME, PASSWORD);
		UploadPopupPage uploadPopupPage= addCatalogPage.fillInName();
		MappingPage mappingPage = (MappingPage)uploadPopupPage.clickGetFile().clickNextAfterUpload(true);
		DetailsPage detailsPage = mappingPage.automap();
		
		assertTrue(detailsPage.FileUploadIsDone());
	}
		
	//@Ignore("oleg")
	@Test
	public void UploadFullFileManualFromScracth() throws InterruptedException{
		MappingPage mappingPage = UploadFullFile();
		DetailsPage detailsPage = mappingPage.automap();
		
		assertTrue(detailsPage.FileUploadIsDone());
	}
	
	@Test
	public void UploadFullFileManualFromScratchUpcEanOnly() throws InterruptedException {
		MappingPage mappingPage = UploadFullFile();
		DetailsPage detailsPage = mappingPage.automap(true, false);
		
		assertTrue(detailsPage.FileUploadIsDone());
	}
	
	@Test
	public void UploadFullFilManualFromScratchSkuIdOnly() throws InterruptedException{
		MappingPage mappingPage = UploadFullFile();
		DetailsPage detailsPage = mappingPage.automap(false, true);
		
		assertTrue(detailsPage.FileUploadIsDone());
	}
	
	@Test
	public void uploadfullFileExcelManual(){
		MappingPage mappingPage = UploadFullFile("Excel.xlsx", "Excel");
		DetailsPage detailsPage = mappingPage.automap();
		
		assertTrue(detailsPage.FileUploadIsDone());

	}
	
	@Test
	public void uploadFullFileExcelAutomaticFromScratch(){
		AddCatalogPage addCatalogPage = navigateToAddcatalogPage(true);
		addCatalogPage.typeFileAndUserInfoAll(ExceUrl, USERNAME, PASSWORD);
		UploadPopupPage uploadPopupPage= addCatalogPage.fillInName();
		MappingPage mappingPage = (MappingPage)uploadPopupPage.clickGetFile().clickNextAfterUpload(true);
		DetailsPage detailsPage = mappingPage.automap();
		
		assertTrue(detailsPage.FileUploadIsDone());
	}
	
	@Test
	public void DelimiterMismatchManualTxtToExcel(){
		MappingPage mappingPage = UploadFullFile("Excel.xlsx", "TXT");
		DetailsPage detailsPage = mappingPage.automap();
		
		assertTrue(detailsPage.FileUploadIsDone());
	}
	
	@Test
	public void DelimiterMismatchAutomaticTxtToExcel(){
		AddCatalogPage addCatalogPage = navigateToAddcatalogPage(true);
		addCatalogPage.typeFileAndUserInfoAll(ExceUrl, USERNAME, PASSWORD);
		UploadPopupPage uploadPopupPage= addCatalogPage.fillInName();
		uploadPopupPage.selectDropBoxOption("TXT");
		MappingPage mappingPage = (MappingPage)uploadPopupPage.clickGetFile().clickNextAfterUpload(true);
		DetailsPage detailsPage = mappingPage.automap();
		
		assertTrue(detailsPage.FileUploadIsDone());
	}
	
	@Test
	public void DelimiterMismatchTxtToCsvManual(){
		MappingPage mappingPage = UploadFullFile("London.csv", "CSV"); //This csv file is really the txt inside with the extension of csv.
		DetailsPage detailsPage = mappingPage.automap();
		
		assertTrue(detailsPage.FileUploadIsDone());
	}
	
	@Test
	public void uploadFileWithCpnMfUpcFails(){
		MappingPage mappingPage = UploadFullFile("CCSQS1604.txt", "TXT");
		DetailsPage detailsPage = mappingPage.automap(false, false);
		assertTrue(detailsPage.FileUploadIsDone());
	}
	
	@Test
	public void dataValidationMessageShowsForEmptyFields(){
		needsCleanUp=false;
		
		AddCatalogPage addCatalogPage = navigateToAddcatalogPage(true);
		addCatalogPage.clickNextFail();
		
		assertTrue(addCatalogPage.displaysCatalogNameError());
		assertTrue(addCatalogPage.displaysCatalogCodeError());
		assertTrue(addCatalogPage.displaysFtpLocationError());
		assertTrue(addCatalogPage.displaysFtpUserNameError());
		assertTrue(addCatalogPage.displaysFtpPasswordError());
	}

	//----------------------------com methods---------------//
	
	public void cleanUpAfterClass(){
		CatalogsPage catalogPageF = PageFactory.initElements(driver, CatalogsPage.class);
		catalogPageF.cleanUpLeftOverCatalogs();
	}

	
	
}
