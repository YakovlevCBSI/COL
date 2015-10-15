package com.cbsi.test.PropertiesPageTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.tests.Foundation.AllBaseTest;
import com.cbsi.tests.PageObjects.AddCatalogPage;
import com.cbsi.tests.PageObjects.CatalogsPage;
import com.cbsi.tests.PageObjects.DetailsPage;
import com.cbsi.tests.PageObjects.MappingPage;
import com.cbsi.tests.PageObjects.UploadPopupPage;
import com.cbsi.tests.PageObjects.DetailsPage.InfoType;
import com.cbsi.tests.PageObjects.DetailsPage.ProcessingQueue;
import com.cbsi.tests.util.GlobalVar;

public class SanityTest2 extends AllBaseTest{
	private boolean needsCleanUp=true;
	
	public SanityTest2(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}
	@Rule 
	public Timeout globalTimeout = new Timeout(350000);
	
	@After
	public void cleanUp(){
		takeScreenshot();
		if(needsCleanUp) {
			driver.close();
			super.cleanUpThenDeleteTemp();
			
			CatalogsPage catalogsPageDirty=PageFactory.initElements(driver, CatalogsPage.class);
			catalogsPageDirty.cleanUpLeftOverCatalogs();
		}
	}
	
	
	private String URL= GlobalVar.ftpURL + "Test/myFullFile.txt";
	private String ExceUrl=GlobalVar.ftpURL + "Test/Excel.xlsx";
	private String USERNAME = GlobalVar.ftpUserName;
	private String PASSWORD = GlobalVar.ftpPassword;
	
	@Test
	public void uploadFullFileExcelAutomaticFromScratch(){
		AddCatalogPage addCatalogPage = navigateToAddcatalogPage(true);
		addCatalogPage.typeFileAndUserInfoAll(ExceUrl, USERNAME, PASSWORD);
		UploadPopupPage uploadPopupPage= addCatalogPage.fillInName();
		uploadPopupPage.selectDropBoxOption("Excel");
		MappingPage mappingPage = (MappingPage)uploadPopupPage.clickGetFile().clickNextAfterUpload(true);
		DetailsPage detailsPage = mappingPage.automap();
		
		assertTrue(detailsPage.FileUploadIsDone());
	}

	@Test
	public void DelimiterMismatchManualTxtToExcel(){
		MappingPage mappingPage = UploadFullFile("Excel.xlsx", "TXT");
	}
	
	@Test
	public void marketListSorted(){
		needsCleanUp = false;
		
		AddCatalogPage addCatalogPage = navigateToAddcatalogPage(false);
		List<String> orginalList =  addCatalogPage.getMarkets();
		List<String> listToSort =  copy(orginalList);
		sortList(listToSort);
		assertTrue(orginalList.equals(listToSort));
	}
	
	@Test
	public void DelimiterMismatchAutomaticTxtToExcel(){
		AddCatalogPage addCatalogPage = navigateToAddcatalogPage(true);
		addCatalogPage.typeFileAndUserInfoAll(ExceUrl, USERNAME, PASSWORD);
		UploadPopupPage uploadPopupPage= addCatalogPage.fillInName();
		uploadPopupPage.selectDropBoxOption("TXT");
		MappingPage mappingPage = (MappingPage)uploadPopupPage.clickGetFile().clickNextAfterUpload(true);
		
	}
	
	@Test
	public void DelimiterMismatchTxtToCsvManual(){
		MappingPage mappingPage = UploadFullFile("London.csv", "CSV"); //This csv file is really the txt inside with the extension of csv.
		
	
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
	
	
	@Test
	public void validateProcessingQueueMessageExists() throws InterruptedException{
		MappingPage mappingPage = UploadFullFile();
		DetailsPage detailsPage = mappingPage.automap();
		detailsPage.FileUploadIsDone();
		detailsPage.expandDetails();

		assertEquals(getProcessedNumber(detailsPage.getProcessingQueueMessage(ProcessingQueue.STORE, InfoType.MESSAGE)),"7");
		assertEquals(getProcessedNumber(detailsPage.getProcessingQueueMessage(ProcessingQueue.MAP, InfoType.MESSAGE)),"7");
		assertEquals(getProcessedNumber(detailsPage.getProcessingQueueMessage(ProcessingQueue.DIFFERENCE, InfoType.MESSAGE)),"7");
		assertEquals(getProcessedNumber(detailsPage.getProcessingQueueMessage(ProcessingQueue.PARSE, InfoType.MESSAGE)),"7");
		assertEquals(getProcessedNumber(detailsPage.getProcessingQueueMessage(ProcessingQueue.FILEUPLOAD, InfoType.MESSAGE)),"1");	
	}
	//----------------------------com methods---------------//
	public String getProcessedNumber(String queueMessage){
		System.out.println(queueMessage);
		String numInString = queueMessage.split(": ")[1].replace(".", "");
		return numInString;
	}
	
	public void cleanUpAfterClass(){
		CatalogsPage catalogPageF = PageFactory.initElements(driver, CatalogsPage.class);
		catalogPageF.cleanUpLeftOverCatalogs();
	}  
	
	private static void sortList(List<String> aItems){
	    Collections.sort(aItems, String.CASE_INSENSITIVE_ORDER);
	}
	
	private List<String> copy(List<String> list){
		List<String> stringList = new ArrayList<String>();
		for(String s: list){
			stringList.add(s);
		}
		
		return stringList;
	}
	
	
	
}
