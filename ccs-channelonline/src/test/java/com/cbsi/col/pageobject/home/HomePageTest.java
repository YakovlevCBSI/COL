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
	public void ChartsAndTableAreDisplayed(){
		assertTrue(homePage.isSalesChartDisplayed());
		assertTrue(homePage.isQuoteTableDisplayed());
		assertTrue(!homePage.isQuotesOnProposalTableDisplayed());
		assertTrue(homePage.isSalesOrderTableDisplayed());
		assertTrue(homePage.isFollowUpDocsDisplayed());
		assertTrue(homePage.isRmasTableDisplayed());
		assertTrue(homePage.isOrganizerNotesTableDisplayed());
		assertTrue(homePage.isOrganizerTasksTableDisplayed());			

	}
}
