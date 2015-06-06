package com.cbsi.test.CatalogsPageTest;

import org.junit.Test;

import com.cbsi.tests.Foundation.FormBaseTest;

public class FormLayoutTest extends FormBaseTest {

	public FormLayoutTest(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}
	
	@Test
	public void FormSpecificLayoutsShow(){
		catalogsPage.clickSearchParty();
	}
	
	@Test
	public void managecatalogLinkWork(){
		catalogsPage.goToManageCatalogs();
	}
	
	@Test
	public void BackLinkWorks(){
		catalogsPage.goBack();
	}
	
	@Test
	public void LogoutWorks(){
		catalogsPage.goToLogout();
	}
}
