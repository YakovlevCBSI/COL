package com.cbsi.test.CatalogsPageTest;

import org.junit.Test;

import com.cbsi.tests.Foundation.FormBaseTest;
import com.cbsi.tests.PageObjects.FCatHomePage;
import com.cbsi.tests.PageObjects.PartyPopupPage;

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
	
	@Test
	public void clickBackImageFires() throws InterruptedException{
		FCatHomePage fcatHomePage = catalogsPage.clickBackIcon();
		Thread.sleep(5000);
	}
	
	@Test
	public void clickPartyChooserImageFires() throws InterruptedException{
		PartyPopupPage partyPopup= catalogsPage.clickPartyChooserIcon();
		Thread.sleep(5000);

	}
}
