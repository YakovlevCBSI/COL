package com.cbsi.test.DetailsPageTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import com.cbsi.tests.Foundation.AllBaseTest;
import com.cbsi.tests.PageObjects.DetailsPage;

public class RegressionTest extends AllBaseTest{
	
	public RegressionTest(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}

	//--------Manual DetailsPAge------//
	@Test
	public void Manual_NameShows(){
		DetailsPage detailsPage = navigateToDetailsPage(false);
		assertFalse(detailsPage.getName().isEmpty());
	}
	
	@Test
	public void Manual_MarketShows(){
		DetailsPage detailsPage = navigateToDetailsPage(false);
		assertFalse(detailsPage.getMarket().isEmpty());
	}
	
	@Test
	public void Manual_CatalogCodeShows(){
		DetailsPage detailsPage = navigateToDetailsPage(false);
		assertFalse(detailsPage.getCatalogCode().isEmpty());
	}
	
	@Test
	public void Manual_UpdateMethodShows(){
		DetailsPage detailsPage = navigateToDetailsPage(false);
		assertEquals(DetailsPage.MANUAL, detailsPage.getUpdateMethod());
	}
	
	@Test
	public void Manual_FileLocationDoesNotShow(){
		DetailsPage detailsPage = navigateToDetailsPage(false);
		
		exception.expect(NoSuchElementException.class);
		detailsPage.getFileLocation();
	}
	
	@Test
	public void Manual_ScheduleDoesNotShow(){
		DetailsPage detailsPage = navigateToDetailsPage(false);

		exception.expect(NoSuchElementException.class);
		detailsPage.getSchedule();
		
	}
	
	//--------Automatic FTP  DetailsPAge------//
	@Test
	public void FTP_NameShows(){
		DetailsPage detailsPage = navigateToDetailsPage(true);
		assertFalse(detailsPage.getName().isEmpty());
	}
	
	@Test
	public void FTP_MarketShows(){
		DetailsPage detailsPage = navigateToDetailsPage(true);
		assertFalse(detailsPage.getMarket().isEmpty());
	}
	
	@Test
	public void FTP_CatalogCodeShows(){
		DetailsPage detailsPage = navigateToDetailsPage(true);
		assertFalse(detailsPage.getCatalogCode().isEmpty());
	}
	
	@Test
	public void FTP_UpdateMethodShows(){
		DetailsPage detailsPage = navigateToDetailsPage(true);
		assertEquals(detailsPage.getUpdateMethod(), DetailsPage.AUTOMATIC);
	}
	
	@Test
	public void FTP_FileLocationShows(){
		DetailsPage detailsPage = navigateToDetailsPage(true);
		assertFalse(detailsPage.getFileLocation().isEmpty());
	}
	
	@Test
	public void FTP_ScheduleShows(){
		DetailsPage detailsPage = navigateToDetailsPage(true);
		assertFalse(detailsPage.getSchedule().isEmpty());
	}
	
	@Rule
	public ExpectedException exception = ExpectedException.none();

	public DetailsPage navigateToDetailsPage(boolean isAutomatic){
//		maximizeWindow();
		if(isAutomatic){
			catalogsPage.setMyCatalogToAutomaticCatalog();
		}
		else {
			catalogsPage.setMyCatalogToManualCatalog();
		}
		
		DetailsPage dp = catalogsPage.clickDetails();
		return dp;
	}
}
