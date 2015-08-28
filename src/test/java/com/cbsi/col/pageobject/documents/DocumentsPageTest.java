package com.cbsi.col.pageobject.documents;

import org.junit.Before;
import org.junit.Test;

import com.cbsi.col.test.foundation.ColBaseTest;

public class DocumentsPageTest extends ColBaseTest{

	public DocumentsPageTest(String url, String browser) {
		super(url, browser);
		// TODO Auto-generated constructor stub
	}

	public DocumentsPage documentsPage;
	
	@Before
	public void startUp(){
		super.startUp();
		documentsPage = homePage.goToDocumentsPage();
	}
	
	@Test
	public void sortTest(){
//		documentsPage.
	}
}
