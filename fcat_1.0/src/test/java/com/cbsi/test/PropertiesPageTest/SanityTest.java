package com.cbsi.test.PropertiesPageTest;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
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
	
	@Rule 
	public Timeout globalTimeout = new Timeout(350000);
	
	@After
	public void cleanUp(){
		super.cleanUpThenDeleteTemp();

	}
	
	private String URL= GlobalVar.ftpURL + "Test/myFullFile.txt";
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
	
	//----------------------------com methods---------------//
	

	

	
	
}
