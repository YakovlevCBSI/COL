package com.cbsi.test.CatalogsPageTest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.Dimension;

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
//		driver.manage().window().setSize(new Dimension(800, 600));
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
	
	@Test
	public void partyChooserNotDisplayDeletedParty(){
		PartyPopupPage partyPopup = catalogsPage.clickPartyChooserIcon();
		int resultNum = partyPopup.searchCode("Deleted_").getResultNumber();
		
		assertTrue(resultNum==0);
	}

}
