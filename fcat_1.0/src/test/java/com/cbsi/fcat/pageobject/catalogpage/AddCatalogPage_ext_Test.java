package com.cbsi.fcat.pageobject.catalogpage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.fcat.pageobject.catatlogpage.AddCatalogPage;
import com.cbsi.fcat.pageobject.catatlogpage.CatalogsPage;
import com.cbsi.fcat.pageobject.catatlogpage.DetailsPage;
import com.cbsi.fcat.pageobject.catatlogpage.MappingPage;
import com.cbsi.fcat.pageobject.catatlogpage.UploadPopupPage;
import com.cbsi.fcat.pageobject.catatlogpage.DetailsPage.InfoType;
import com.cbsi.fcat.pageobject.catatlogpage.DetailsPage.ProcessingQueue;
import com.cbsi.fcat.pageobject.catatlogpage.DetailsPage.UploadStatus;
import com.cbsi.fcat.pageobject.catatlogpage.UploadPopupPage.UploadType;
import com.cbsi.fcat.pageobject.foundation.AllBaseTest;
import com.cbsi.fcat.util.FtpUtil;
import com.cbsi.fcat.util.GlobalVar;

public class AddCatalogPage_ext_Test extends AllBaseTest{
	private boolean needsCleanUp=true;
	
	public AddCatalogPage_ext_Test(String URL, String browser) {
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
	private String FtpExceUrl=GlobalVar.ftpURL + "Test/Excel.xlsx";
	private String USERNAME = GlobalVar.ftpUserName;
	private String PASSWORD = GlobalVar.ftpPassword;
	public String timeStampUrl = GlobalVar.ftpURL + "qa/diff/CatalogFile_[Y]_[M]_[D]_[h]_[m].txt";

	
	@Test
	public void uploadFullFileExcelAutomaticFtpFromScratch(){
		AddCatalogPage addCatalogPage = navigateToAddcatalogPage(true);
		addCatalogPage.setFileAndUserInfoAll(FtpExceUrl, USERNAME, PASSWORD);
		UploadPopupPage uploadPopupPage= addCatalogPage.fillInName();
		uploadPopupPage.selectDropBoxOption(UploadType.EXCEL);
		MappingPage mappingPage = (MappingPage)uploadPopupPage.clickGetFile().clickNextAfterUpload(true);
		DetailsPage detailsPage = mappingPage.automap();
		
		assertTrue(detailsPage.FileUploadIsDone());
	}
	
	@Test
	public void uploadFullFileTxtAutomaticHttpFromScratch(){
		UploadPopupPage uploadPopup = catalogsPage.setMyCatalogToManualCatalog().clickUpload();
		String sampleFileLocation = uploadPopup.getSampleFileUrl();
		
		uploadPopup.refresh();
		catalogsPage = PageFactory.initElements(driver, CatalogsPage.class);
		AddCatalogPage addCatalogPage = navigateToAddcatalogPage(true);
		addCatalogPage.setFileAndUserInfoAll(sampleFileLocation , USERNAME, PASSWORD);
		UploadPopupPage uploadPopupPage= addCatalogPage.fillInName();
		uploadPopupPage.selectDropBoxOption(UploadType.TXT);
		MappingPage mappingPage = (MappingPage)uploadPopupPage.clickGetFile().clickNextAfterUpload(true);
		DetailsPage detailsPage = mappingPage.automap();
		
		assertTrue(detailsPage.FileUploadIsDone());
	}
	
	@Test
	public void uploadIncrementalFileTimestampFromScartch(){
		int workflowSize = 6;
		String filename = "";
		
		updateFtpFileNames();
	
		AddCatalogPage addCatalogPage = navigateToAddcatalogPage(true);
		addCatalogPage.setFileAndUserInfoAll(timeStampUrl, USERNAME, PASSWORD);
		UploadPopupPage uploadPopupPage= addCatalogPage.fillInName();
		uploadPopupPage.selectDropBoxOption(UploadType.TXT);
		
		MappingPage mappingPage = (MappingPage)uploadPopupPage.clickGetFile().clickNextAfterUpload(true);
		DetailsPage detailsPage = mappingPage.automap();
		
		for(int i=0; i<workflowSize; i++){
			System.out.println(filename + " : " + detailsPage.getFileName() + " : " + i);

			if(i != workflowSize-1){
				System.out.println("in waiting for next workflow condition");
				while(filename.equals(detailsPage.getFileName())){
					detailsPage.refresh();
//					detailsPage.forceWait(50);
	
					detailsPage = PageFactory.initElements(driver, DetailsPage.class);	
				}
			}
			filename = detailsPage.getFileName();		
			
			assertTrue(detailsPage.FileUploadIsDone());
		}
	}
	
	@Test
	public void uploadFullFileTimestampFromScartch(){
		String filename = "";
		
		updateFtpFileNames();
	
		AddCatalogPage addCatalogPage = navigateToAddcatalogPage(true);
		addCatalogPage.setFileAndUserInfoAll(timeStampUrl, USERNAME, PASSWORD).setFullFile();
		UploadPopupPage uploadPopupPage= addCatalogPage.fillInName();
		uploadPopupPage.selectDropBoxOption(UploadType.TXT);
		
		MappingPage mappingPage = (MappingPage)uploadPopupPage.clickGetFile().clickNextAfterUpload(true);
		DetailsPage detailsPage = mappingPage.automap();
		
		assertTrue(detailsPage.FileUploadIsDone());
		
		filename = detailsPage.getFileName();
		
		detailsPage.forceWait(5000);
		detailsPage.refresh();

		detailsPage = PageFactory.initElements(driver, DetailsPage.class);	
		
		assertEquals(filename, detailsPage.getFileName());
	}


	@Test
	public void DelimiterMismatchManualTxtToExcel(){
		MappingPage mappingPage = UploadFullFile("Excel.xlsx", UploadType.TXT);
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
	public void DelimiterMismatchAutomaticFtpTxtToExcel(){
		AddCatalogPage addCatalogPage = navigateToAddcatalogPage(true);
		addCatalogPage.setFileAndUserInfoAll(FtpExceUrl, USERNAME, PASSWORD);
		UploadPopupPage uploadPopupPage= addCatalogPage.fillInName();
		uploadPopupPage.selectDropBoxOption(UploadType.TXT);
		MappingPage mappingPage = (MappingPage)uploadPopupPage.clickGetFile().clickNextAfterUpload(true);
		
	}
	
	@Test
	public void DelimiterMismatchTxtToCsvManual(){
		MappingPage mappingPage = UploadFullFile("London.csv", UploadType.CSV); //This csv file is really the txt inside with the extension of csv.
		
	
	}
	
	@Test
	public void uploadFileWithCpnMfUpcFails(){
		MappingPage mappingPage = UploadFullFile("CCSQS1604.txt", UploadType.TXT);
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
		
		assertTrue(detailsPage.FileUploadIsDone());
		detailsPage.expandDetails();
		
		detailsPage.customWait(20);

		assertEquals(getProcessedNumber(detailsPage.getProcessingQueueMessage(ProcessingQueue.STORE, InfoType.MESSAGE)),"7");
		assertEquals(getProcessedNumber(detailsPage.getProcessingQueueMessage(ProcessingQueue.MAP, InfoType.MESSAGE)),"7");
		assertEquals(getProcessedNumber(detailsPage.getProcessingQueueMessage(ProcessingQueue.DIFFERENCE, InfoType.MESSAGE)),"7");
		assertEquals(getProcessedNumber(detailsPage.getProcessingQueueMessage(ProcessingQueue.PARSE, InfoType.MESSAGE)),"7");
		assertEquals(getProcessedNumber(detailsPage.getProcessingQueueMessage(ProcessingQueue.FILEUPLOAD, InfoType.MESSAGE)),"1");	
	}
	
	@Test
	public void emptyFileStopsAtParsing() throws InterruptedException{
		needsCleanUp=false;

		catalogsPage.setMyCatalog();
		UploadPopupPage uploadPopup = catalogsPage.clickUpload();
		DetailsPage detailsPage = (DetailsPage) uploadPopup.uploadLocalFileFromResource("emptyFile.txt").selectDropBoxOption(UploadType.TXT).clickNext().clickNextAfterUpload(false);
		
		assertTrue(detailsPage.FileUploadIsDone());
		detailsPage.expandDetails();
		
		assertTrue(detailsPage.getProcessingQueueMessage(ProcessingQueue.PARSE, InfoType.MESSAGE), 
				detailsPage.getProcessingQueueMessage(ProcessingQueue.PARSE, InfoType.MESSAGE).contains("Total processed: No records."));
		assertEquals(detailsPage.getProcessingQueueMessage(ProcessingQueue.PARSE, InfoType.STATUS),
				detailsPage.getProcessingQueueMessage(ProcessingQueue.PARSE, InfoType.STATUS), UploadStatus.DONE.toString());
		assertTrue(detailsPage.getProcessingQueueMessage(ProcessingQueue.FILEUPLOAD, InfoType.MESSAGE), 
				detailsPage.getProcessingQueueMessage(ProcessingQueue.FILEUPLOAD, InfoType.MESSAGE).contains("Total processed: 1."));
		assertEquals(detailsPage.getProcessingQueueMessage(ProcessingQueue.FILEUPLOAD, InfoType.STATUS),
				detailsPage.getProcessingQueueMessage(ProcessingQueue.FILEUPLOAD, InfoType.STATUS), UploadStatus.DONE.toString());

	}
	
	@Test
	public void errorWhenYouSwitchConnectors(){
		AddCatalogPage addCatalogPage = navigateToAddcatalogPage(true);
		addCatalogPage.setFileAndUserInfoAll("ftp://something.com/file.txt", USERNAME, PASSWORD);
		UploadPopupPage uploadPopup = addCatalogPage.fillInName();
		
		addCatalogPage = uploadPopup.clickCancel();
		addCatalogPage.setFileLocation("http://google.com/file.txt");
		
		addCatalogPage = addCatalogPage.clickNext().clickCancel();
		
		assertTrue(hasNoError());
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
	
	public String getTodaysFileName(String filename){
		String[] filenames = filename.split("_");
		
		Date date = new Date();
		TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		String newFile = filename.replaceFirst(filenames[1], cal.get(Calendar.YEAR)+"")
					.replaceFirst(filenames[2], (cal.get(Calendar.MONTH)+1)+"")
					.replaceFirst(filenames[3], cal.get(Calendar.DAY_OF_MONTH) +"");
		
		return newFile;
	}
	
	public void updateFtpFileNames(){
		FtpUtil ftpUtil = new FtpUtil();
		ftpUtil.login();
		List<String> list = ftpUtil.listFilesInDir("download/fcat/qa/diff");
		
		
		for(String s: list){
			System.out.println(s);
			ftpUtil.renameFileNameInCurDir(s,getTodaysFileName(s));
		}
		
		ftpUtil.quit();
		
	}
	
	
}
