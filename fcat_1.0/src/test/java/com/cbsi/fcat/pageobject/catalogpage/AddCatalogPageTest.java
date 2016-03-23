package com.cbsi.fcat.pageobject.catalogpage;

import static org.junit.Assert.assertTrue;

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
import com.cbsi.fcat.pageobject.catatlogpage.MappingPage.CNetFields;
import com.cbsi.fcat.pageobject.catatlogpage.UploadPopupPage.UploadType;
import com.cbsi.fcat.pageobject.foundation.EmbedBaseTest;
import com.cbsi.fcat.util.GlobalVar;

public class AddCatalogPageTest extends EmbedBaseTest{

	public AddCatalogPageTest(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}
	
	private boolean needsCleanUp=true;
	
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
	private String sURL=GlobalVar.sftpURL + "Test/myFullFile.txt";
	private String xlsUrl= GlobalVar.ftpURL + "Test/Xls.xls";
	private String xlsxUrl=GlobalVar.ftpURL + "Test/Excel.xlsx";
	private String USERNAME = GlobalVar.ftpUserName;
	private String PASSWORD = GlobalVar.ftpPassword;

	@Test
	public void automaticUploadInvalidURL(){
		AddCatalogPage addCatalogPage = navigateToAddcatalogPage(true);
		addCatalogPage.setFileAndUserInfoAll("ftp://test.com", USERNAME, PASSWORD);
		addCatalogPage.fillInName().clickGetFile();
		try{ } catch(RuntimeException e){}
		
		assertTrue(hasNoError());
	}
	
	@Test
	public void automaticUploadWithInvalidUsername(){
		AddCatalogPage addCatalogPage = navigateToAddcatalogPage(true);
		addCatalogPage.setFileAndUserInfoAll(URL, "bad", PASSWORD);
		UploadPopupPage uploadPopupPage= addCatalogPage.fillInName();
		uploadPopupPage.clickGetFile();
		
		assertTrue(hasNoError());		
	}
	
	@Test
	public void automaticUploadWithInvalidPassword(){
		AddCatalogPage addCatalogPage = navigateToAddcatalogPage(true);
		addCatalogPage.setFileAndUserInfoAll(URL, USERNAME, "bad");
		UploadPopupPage uploadPopupPage= addCatalogPage.fillInName();
		uploadPopupPage.clickGetFile();
		
		assertTrue(hasNoError());
	}

	@Test
	public void UploadFullFileAutomaticFtpFromScratch(){
//		driver.manage().window().setSize(new Dimension(570, 500));
		AddCatalogPage addCatalogPage = navigateToAddcatalogPage(true);
		addCatalogPage.setFileAndUserInfoAll(URL, USERNAME, PASSWORD);
		UploadPopupPage uploadPopupPage= addCatalogPage.fillInName();
		MappingPage mappingPage = (MappingPage)uploadPopupPage.selectDropBoxOption(UploadType.TXT).clickGetFile().clickNextAfterUpload(true);
		DetailsPage detailsPage = mappingPage.automap();
		
		assertTrue(detailsPage.FileUploadIsDone());
	}
	
	@Test
	public void UploadFullFilAutomaticSftpFromScratch(){
		AddCatalogPage addCatalogPage = navigateToAddcatalogPage(true);
		addCatalogPage.setFileAndUserInfoAll(sURL, USERNAME, PASSWORD).setFullFile();
		UploadPopupPage uploadPopupPage= addCatalogPage.fillInName();
		MappingPage mappingPage = (MappingPage)uploadPopupPage.selectDropBoxOption(UploadType.TXT).clickGetFile().clickNextAfterUpload(true);
		DetailsPage detailsPage = mappingPage.automap();
		
		assertTrue(detailsPage.FileUploadIsDone());
	}
		
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
	public void uploadfullFileExcelXlsxManual(){
		MappingPage mappingPage = UploadFullFile("Excel.xlsx", UploadType.EXCEL);
		DetailsPage detailsPage = mappingPage.automap();
		
		assertTrue(detailsPage.FileUploadIsDone());
	}
	
	@Test
	public void uploadFullfileManualExcelXls(){
		MappingPage mappingPage = UploadFullFile("Xls.xls", UploadType.EXCEL);
		DetailsPage detailsPage = mappingPage.automap();
		
		assertTrue(detailsPage.FileUploadIsDone());
	}
	
	@Test
	public void uploadFullfileAutomaticFtplExcelXlsx(){
		AddCatalogPage addCatalogPage = navigateToAddcatalogPage(true);
		addCatalogPage.setFileAndUserInfoAll(xlsxUrl, USERNAME, PASSWORD);
		UploadPopupPage uploadPopupPage= addCatalogPage.fillInName();
		MappingPage mappingPage = (MappingPage)uploadPopupPage.selectDropBoxOption(UploadType.EXCEL).clickGetFile().clickNextAfterUpload(true);
		DetailsPage detailsPage = mappingPage.automap();
		
		assertTrue(detailsPage.FileUploadIsDone());
	}
	
	@Test
	public void uploadFullfileAutomaticFtplExcelXls(){
		AddCatalogPage addCatalogPage = navigateToAddcatalogPage(true);
		addCatalogPage.setFileAndUserInfoAll(xlsUrl, USERNAME, PASSWORD);
		UploadPopupPage uploadPopupPage= addCatalogPage.fillInName();
		MappingPage mappingPage = (MappingPage)uploadPopupPage.selectDropBoxOption(UploadType.EXCEL).clickGetFile().clickNextAfterUpload(true);
		DetailsPage detailsPage = mappingPage.automap();
		
		assertTrue(detailsPage.FileUploadIsDone());
	}
	
	@Test
	public void uploadFullFileXMLManual(){
		MappingPage mappingPage = UploadFullFile("XML.xml", UploadType.XML);
		DetailsPage detailsPage = mappingPage.automap();
		
		assertTrue(detailsPage.FileUploadIsDone());
	}
	
	@Test
	public void uploadFullFileTxtWithDoubleByteCharacter(){
		MappingPage mappingPage = UploadFullFile("Korean_Catalog.txt", UploadType.TXT);
		mappingPage.setCnetField(CNetFields.ProductId, 1);
		mappingPage.setCnetField(CNetFields.ManufacturerName, 2);
		mappingPage.setCnetField(CNetFields.ManufacturerPartNumber, 3);
		mappingPage.setCnetField(CNetFields.Price, 4);
		mappingPage.setCnetField(CNetFields.ProductNameOrDescription, 5);
//		mappingPage.setCnetField(CNetFields.AddToCartURL, 6);

		mappingPage.clickSave();
		
		DetailsPage detailsPage = PageFactory.initElements(driver, DetailsPage.class);
		assertTrue(detailsPage.FileUploadIsDone());
	}

	@Test
	public void dataValidationOnItemIds(){
		MappingPage mappingPage = UploadFullFile("validation.txt", UploadType.TXT);
		DetailsPage detailsPage = mappingPage.automap();
		assertTrue(detailsPage.FileUploadIsDone());

		detailsPage.expandDetails();
		String message = detailsPage.getProcessingQueueMessage(ProcessingQueue.PARSE, InfoType.MESSAGE, true);

		assertTrue(message.contains("Product ID must be at most 100 characters long"));
		assertTrue(message.contains("CNET SKU ID must be at most 50 characters long"));
		assertTrue(message.contains("UPC/EAN must be at most 50 characters long"));
		assertTrue(message.contains("Manufacturer Name must be at most 100 characters long"));
		assertTrue(message.contains("Manufacturer Part Number must be at most 100 characters long"));
	}

	@Test
	public void deliemiterIsSavedDependsOnFileType(){
		uploadFullFileXMLManual();
		DetailsPage detailsPage = PageFactory.initElements(driver, DetailsPage.class);
		UploadPopupPage uploadPopupInDetails = detailsPage.clickUploadFile();
		
		assertTrue(uploadPopupInDetails.getFileType()+"", uploadPopupInDetails.getFileType() == UploadType.XML);
		
		uploadPopupInDetails.clickCancel();
		
		CatalogsPage catalogsPage = detailsPage.clickReturnToList();
		UploadPopupPage uploadPopupInCatalogs = catalogsPage.setMyCatalog(tempFile).clickUpload();
		
		assertTrue(uploadPopupInCatalogs.getFileType()+"", uploadPopupInCatalogs.getFileType() == UploadType.XML);
		
	}
	
	public String getProcessedNumber(String queueMessage){
		System.out.println(queueMessage);
		String numInString = queueMessage.split(": ")[1].replace(".", "");
		return numInString;
	}
	

	//----------------------------com methods---------------//
	
	public void cleanUpAfterClass(){
		CatalogsPage catalogPageF = PageFactory.initElements(driver, CatalogsPage.class);
		catalogPageF.cleanUpLeftOverCatalogs();
	}	
}
