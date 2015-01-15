package com.cbsi.test.PropertiesPageTest;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.tests.Foundation.AllBaseTest;
import com.cbsi.tests.PageObjects.AddCatalogPage;
import com.cbsi.tests.PageObjects.CatalogsPage;
import com.cbsi.tests.PageObjects.MappingPage;
import com.cbsi.tests.PageObjects.UploadPopupPage;
import com.cbsi.tests.util.GlobalVar;

public class SanityTest extends AllBaseTest{

	public SanityTest(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}
	private String tempFile = "";
	
	@After
	public void cleanUp(){
		super.cleanUpThenDeleteTemp(tempFile);

	}
	
	private String URL= GlobalVar.ftpURL + "Test/myFullFile.txt";
	private String USERNAME = GlobalVar.ftpUserName;
	private String PASSWORD = GlobalVar.ftpPassword;

	@Test
	public void automaticUploadInvalidURL(){
		AddCatalogPage addCatalogPage = navigateToAddcatalogPage(true);
		addCatalogPage.typeFileAndUserInfoAll("ftp://test.com", USERNAME, PASSWORD);
		
		try{ addCatalogPage.fillInName();} catch(RuntimeException e){}
		
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
	
	@Test
	public void UploadFullFileAutomaticFromScratch(){
		AddCatalogPage addCatalogPage = navigateToAddcatalogPage(true);
		addCatalogPage.typeFileAndUserInfoAll(URL, USERNAME, PASSWORD);
		UploadPopupPage uploadPopupPage= addCatalogPage.fillInName();
		uploadPopupPage.clickGetFile().clickNextAfterUpload(true);
		
		assertTrue(hasNoError());
	}
		
	@Test
	public void UploadFullFileManualFromScracth() throws InterruptedException{
		MappingPage mappingPage = UploadFullFile();
		
		assertTrue(hasNoError());
	}
	
	public AddCatalogPage navigateToAddcatalogPage(boolean isAutomatic){
		CatalogsPage catalogsPage = PageFactory.initElements(driver, CatalogsPage.class);
		AddCatalogPage addCatalogsPage = catalogsPage.goToAddCatalog();
		
		this.tempFile = addCatalogsPage.getTempFileName();
		
		if(isAutomatic){
			addCatalogsPage.switchToAutomatic();
			
		}
		
		return addCatalogsPage;
	}
	
	public MappingPage UploadFullFile() throws InterruptedException{
		UploadPopupPage uploadPopupPage = navigateToAddcatalogPage(false).fillInName();
		uploadPopupPage.clickUploadFile();
		uploadPopupPage = uploadLocalFileOSSpecific(uploadPopupPage).clickNext();
		
		MappingPage mappingPage = (MappingPage) uploadPopupPage.clickNextAfterUpload(true);
		return mappingPage;
	}
	
	
	
}