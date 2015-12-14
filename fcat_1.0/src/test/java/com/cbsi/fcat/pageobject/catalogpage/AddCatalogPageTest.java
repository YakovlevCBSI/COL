package com.cbsi.fcat.pageobject.catalogpage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import net.jcip.annotations.NotThreadSafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.fcat.pageobject.catatlogpage.AddCatalogPage;
import com.cbsi.fcat.pageobject.catatlogpage.CatalogsPage;
import com.cbsi.fcat.pageobject.catatlogpage.DetailsPage;
import com.cbsi.fcat.pageobject.catatlogpage.MappingPage;
import com.cbsi.fcat.pageobject.catatlogpage.ProductsCatalogPage;
import com.cbsi.fcat.pageobject.catatlogpage.UploadPopupPage;
import com.cbsi.fcat.pageobject.catatlogpage.DetailsPage.InfoType;
import com.cbsi.fcat.pageobject.catatlogpage.DetailsPage.ProcessingQueue;
import com.cbsi.fcat.pageobject.catatlogpage.MappingPage.CNetFields;
import com.cbsi.fcat.pageobject.foundation.AllBaseTest;
import com.cbsi.fcat.util.GlobalVar;

public class AddCatalogPageTest extends AllBaseTest{

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
	private String ExceUrl=GlobalVar.ftpURL + "Test/Excel.xlsx";
	private String USERNAME = GlobalVar.ftpUserName;
	private String PASSWORD = GlobalVar.ftpPassword;

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
	public void automaticUploadWithInvalidPassword(){
		AddCatalogPage addCatalogPage = navigateToAddcatalogPage(true);
		addCatalogPage.typeFileAndUserInfoAll(URL, USERNAME, "bad");
		UploadPopupPage uploadPopupPage= addCatalogPage.fillInName();
		uploadPopupPage.clickGetFile();
		
		assertTrue(hasNoError());
	}

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
	public void uploadFullFileXMLManual(){
		MappingPage mappingPage = UploadFullFile("XML.xml", "XML");
		DetailsPage detailsPage = mappingPage.automap();
		
		assertTrue(detailsPage.FileUploadIsDone());
	}
	
	@Test
	public void uploadFullFileTxtWithDoubleByteCharacter(){
		MappingPage mappingPage = UploadFullFile("Korean_Catalog.txt", "TXT");
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