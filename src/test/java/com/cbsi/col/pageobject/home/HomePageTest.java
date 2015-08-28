package com.cbsi.col.pageobject.home;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.cbsi.col.test.foundation.ColBaseTest;

public class HomePageTest extends ColBaseTest{

	public HomePageTest(String url, String browser) {
		super(url, browser);
		// TODO Auto-generated constructor stub
	}

	@Test
	public void chartsAndTableAreDisplayed(){
		assertTrue(homePage.isSalesChartDisplayed());
		assertTrue(homePage.isQuoteTableDisplayed());
		assertTrue(homePage.isQuotesOnProposalTableDisplayed());
		assertTrue(homePage.isSalesOrderTableDisplayed());
		assertTrue(homePage.isFollowUpDocsDisplayed());
		assertTrue(homePage.isRmasTableDisplayed());
		assertTrue(homePage.isOrganizerNotesTableDisplayed());
		assertTrue(homePage.isOrganizerTasksTableDisplayed());			
	}
	
	@Test
	public void mainTabsAreDisplayed(){
		assertTrue(homePage.IsAccountsTabDisplayed());
		assertTrue(homePage.IsProductsTabDisplayed());
		assertTrue(homePage.IsServicesTabDisplayed());
		assertTrue(homePage.IsDocumentsTabDisplayed());
		assertTrue(homePage.IsSuppliersTabDisplayed());
		assertTrue(homePage.IsPurchaseOrdersTabDisplayed());
		assertTrue(homePage.IsCustomerViewTabDisplayed());
		assertTrue(homePage.isDocumentActiveTabDisplayed());
	}
	
	@Test
	public void userIconsDisplayedOnTopBar(){
		assertTrue(homePage.isInboxDisplayed());
		assertTrue(homePage.isOrganizerDisplayed());
		assertTrue(homePage.isUserDisplayed());
		assertTrue(homePage.isUserNameDisplayed());
		assertTrue(homePage.isEmailDisplayed());
	}
}
