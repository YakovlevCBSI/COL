package com.cbsi.test.pageobjects.sidebar;

import org.junit.Before;
import org.junit.Test;

import com.cbsi.tests.Foundation.AllBaseTest;
import com.cbsi.tests.Foundation.FormBaseTest;
import com.cbsi.tests.PageObjects.FCatHomePage;

public class SanityTest extends FormBaseTest{

	public SanityTest(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}
	
	public FCatHomePage fcatHomePage;
	
	@Before
	public void startUp(){
		super.startUp();
		fcatHomePage = catalogsPage.goBack();
	}

	@Test
	public void loadCatalogTypePage(){
		fcatHomePage.goToCatalogType();
	}
	
	@Test
	public void loadAttributeTypePage(){
		fcatHomePage.goToAttributeType();
	}
	
	@Test
	public void loadCatalogImportTypesPage(){
		fcatHomePage.goToCatalogImportType();
	}
	
	@Test
	public void loadContentTypePage(){
		fcatHomePage.goToContentType();
	}
	
	@Test
	public void loadDashboardPage(){
		fcatHomePage.goToDashboard();
	}
	
	@Test
	public void loadItemIdentificationTypePage(){
		fcatHomePage.goToItemIdentification();
	}
	
	@Test
	public void loadMasterPage(){
		fcatHomePage.goToMaster();
	}
	
	@Test
	public void loadPartiesPage(){
		fcatHomePage.goToParties();
	}
	
	@Test
	public void loadPredefinedListPage(){
		fcatHomePage.goToPredefinedLists();
	}
	
	@Test
	public void loadRolesPage(){
		fcatHomePage.goToRoles();
	}
	
	@Test
	public void loadSSOUsersPage(){
		fcatHomePage.goToSSOUsers();
	}
	
	@Test
	public void loadUsersPage(){
		fcatHomePage.gotoUsers();
	}
	
	@Test
	public void loadUsersPartyAssignmentPage(){
		fcatHomePage.goToUsersPartyAssignment();
	}
	
	@Test
	public void loaUsersRoleAssignmentPage(){
		fcatHomePage.goToUsersRoleAssignment();
	}
}
