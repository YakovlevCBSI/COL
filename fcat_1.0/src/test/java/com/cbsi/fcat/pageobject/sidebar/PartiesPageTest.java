package com.cbsi.fcat.pageobject.sidebar;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cbsi.fcat.pageobject.catatlogpage.CatalogsPage;
import com.cbsi.fcat.pageobject.catatlogpage.PartyPopupPage;
import com.cbsi.fcat.pageobject.foundation.FormBaseTest;
import com.cbsi.fcat.pageobject.sidebar.PartiesPage.UpdatePartyPage;

public class PartiesPageTest extends FormBaseTest{

	public PartiesPageTest(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}

	public PartiesPage partyPage;
	public String tempPartyName = getRandomNumber();
	
	@Before
	public void startUp(){
		super.startUp();
		partyPage = catalogsPage.goBack().goToParties();
	}
	
	@After
	public void cleanUp(){
		driver.close();
		startUp();
		partyPage.deleteParty(tempPartyName);
	}
	
	@Test
	public void partyCreatedHasCode(){
		UpdatePartyPage createPartyPage = partyPage.clickCreateNewParty();
		createPartyPage.setPartyName(tempPartyName);
		createPartyPage.setDescription(tempPartyName);
		partyPage = createPartyPage.clickSave();
		
		System.out.println("--partyName: " + tempPartyName);
		
		CatalogsPage catalogsPage  = partyPage.goToHomePage().goToCatalogs();
		PartyPopupPage partyPopupPage = catalogsPage.clickPartyChooserIcon();
		partyPopupPage = partyPopupPage.searchParty(tempPartyName);
		
		assertNotNull(partyPopupPage.getSearchResultAsMap().get(0).get("partycode"));
	}
}
