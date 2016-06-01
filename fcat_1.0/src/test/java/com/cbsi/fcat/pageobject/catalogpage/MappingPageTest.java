package com.cbsi.fcat.pageobject.catalogpage;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.fcat.pageobject.catatlogpage.AddCatalogPage;
import com.cbsi.fcat.pageobject.catatlogpage.CatalogsPage;
import com.cbsi.fcat.pageobject.catatlogpage.MappingPage;
import com.cbsi.fcat.pageobject.catatlogpage.UploadPopupPage;
import com.cbsi.fcat.pageobject.catatlogpage.UploadPopupPage.UploadType;
import com.cbsi.fcat.pageobject.foundation.AllBaseTest;

public class MappingPageTest extends AllBaseTest{
	public MappingPageTest(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}
	
	//private String tempFile = "";
	
	@Test
	public void CCSQS1063() throws InterruptedException{
		MappingPage mappingPage = navigateToMappingPage();
		assertTrue(mappingPage.isSaveDisabled());
		
	}
	
	@Test
	public void UrlEncodingTextsHaveSameStyles() throws InterruptedException{
		MappingPage mappingPage = navigateToMappingPage();
		assertTrue(mappingPage.areElementsSameStyles(mappingPage.getFileEncodingElements()));
		
	}

	public static final	List<String> expectedHeaders = Arrays.asList(new String[]{"유저고유번호", "제조회사", "제조번호", "가격", "설명", "웹사이트"});		
	public static final	List<String> expectedPreviews = Arrays.asList(new String[]{"가1", "엘지", "가2", "$56.233", "컴퓨터", "http://wwww.lifeisgood.com"});
	
	@Test
	public void headersAndPreviewColumnsDisplayDoubleByteLanguage(){
		MappingPage mappingPage = UploadFullFile("Korean_Catalog.txt", UploadType.TXT);
		
		assertTrue(mappingPage.getHeaders().equals(expectedHeaders));
		assertTrue(mappingPage.getDataPreviews().equals(expectedPreviews));		
	}
	
	public static final	List<String> expectedPreviewsFromWhiteSpace = Arrays.asList(new String[]{"Item ID", "Manufacturer Name", "MFR Part #", "Product Description", "URL"});
	
	@Test
	public void parseHeaderWithWhiteSpace(){
		MappingPage mappingPage = UploadFullFile("whitespaceHeader.txt", UploadType.TXT);
		assertTrue(mappingPage.getHeaders().equals(expectedPreviewsFromWhiteSpace));
	}
	
	@Test
	public void longTextPushesCnetDropdownToRight(){
		MappingPage mappingPage = UploadFullFile("ProductUrlLong.txt", UploadType.TXT);
		
		assertTrue(mappingPage.getCnetFieldDropdownUpperRightX() < mappingPage.getPanelUpperRightX());
	}
	
	@Test
	public void dostNotHaveAHeader(){
		MappingPage mappingPage = UploadFullFile("whitespaceHeader.txt", UploadType.TXT, false);
		assertTrue(mappingPage.collectHeadersAsString().size() ==0);

	}
	
	@After
	public void cleanUp(){
		driver.close();
		super.cleanUpThenDeleteTemp();
	}
	
	
	//-------------------------------------------  Helper Method --------------------------------------------//

	public MappingPage navigateToMappingPage() throws InterruptedException{
		AddCatalogPage addCatalogsPage = navigateToAddcatalogPage(false);

		/** set file name to cleanup**/
		//tempFile = (addCatalogsPage.getTempFileName());
		
		UploadPopupPage uploadPopupPage = addCatalogsPage.fillInName();
		uploadPopupPage.clickUploadFile();
		uploadPopupPage = uploadLocalFileOSSpecific(uploadPopupPage).clickNext();
		
		MappingPage mappingPage = (MappingPage) uploadPopupPage.clickNextAfterUpload(true);
		return mappingPage;
	}
}
