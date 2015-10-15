package com.cbsi.col.pageobject.documents;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.cbsi.col.pageobject.documents.DocumentsPage.DocumentTabs;
import com.cbsi.col.pageobject.documents.DocumentsPage.Status;
import com.cbsi.col.test.foundation.ColBaseTest;
import com.cbsi.col.test.foundation.DocumentsBasePageTest;
import com.cbsi.col.test.util.TableUtil;

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
	public void FilterByQuotesOpen(){
		documentsPage = documentsPage.switchToTab(DocumentTabs.ALLQUOTESANDORDERS);
		documentsPage = documentsPage.filterByStatus(Status.QUOTES_OPEN);
		List<HashMap<String, String>> maps= documentsPage.getTableAsMaps();
		assertTrue(TableUtil.tableMapHasWord(maps,"status", "Open", false));
	}
	
	@Test
	public void FilterByOrdersSubmitted(){
		documentsPage = documentsPage.switchToTab(DocumentTabs.ALLQUOTESANDORDERS);
		documentsPage = documentsPage.filterByStatus(Status.ORDERS_SUBMITTED);
		List<HashMap<String, String>> maps= documentsPage.getTableAsMaps();
		assertTrue(TableUtil.tableMapHasWord(maps,"status", "Submitted", false));
	}
	
	@Ignore("today is off by timezone") 
	@Test
	public void FilterByInvoicesDue(){
		documentsPage = documentsPage.switchToTab(DocumentTabs.ALLQUOTESANDORDERS);
		documentsPage = documentsPage.filterByStatus(Status.INVOICES_DUE);
		List<HashMap<String, String>> maps= documentsPage.getTableAsMaps();
		assertTrue(TableUtil.tableMapHasWord(maps,"status", "Due", false));
	}
	
	@Test
	public void FilterByModifiedUser(){
		String userName = "Park, Albert";
		
		documentsPage = documentsPage.switchToTab(DocumentTabs.ALLQUOTESANDORDERS);
		documentsPage = documentsPage.setFilterByModifiedBy(userName);
		List<HashMap<String, String>> maps= documentsPage.getTableAsMaps();
		assertTrue(TableUtil.tableMapHasWord(maps,"modifiedby", userName, false));
	}
	
	@Test
	public void FilterByTodayShowsRecentlyCreatedDoc(){
		
	}
	
	@Test
	public void FilterByAllUsersOptionIsNotChanged(){
		documentsPage = documentsPage.switchToTab(DocumentTabs.ALLQUOTESANDORDERS);
		documentsPage = documentsPage.setFilterByModifiedBy("Modified By (All)");
		assertEquals("Modified By (All)", documentsPage.getFilterByModified());
	}
}
	