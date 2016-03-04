package com.cbsi.fcat.pageobject.catalogpage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;

import com.cbsi.fcat.pageobject.catatlogpage.CoverageReportPage;
import com.cbsi.fcat.pageobject.catatlogpage.DetailsPage;
import com.cbsi.fcat.pageobject.catatlogpage.MappingPage;
import com.cbsi.fcat.pageobject.foundation.FormBaseTest;
import com.cbsi.fcat.pageobject.others.DashboardPage;
import com.cbsi.fcat.pageobject.others.DashboardPage.STATUS;

public class CovreageReportPageTest extends FormBaseTest{

	public CovreageReportPageTest(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}
	public CoverageReportPage coverageReport;
	
	@Rule 
	public Timeout globalTimeout = new Timeout(300000);
	
	@After
	public void tearDown(){
		driver.close();
		super.cleanUpThenDeleteTemp();
	}
	@Test
	public void generateCoverageReportWithoutError() throws InterruptedException{
		uploadFullFileThenReturnToCatalogsPage();
		navigateToCR();
		coverageReport.generateCoverageReport();
		coverageReport.waitForCoverageToGenerate();
		navigateToCR();
		coverageReport.generateCoverageReport();
		
		
		assertTrue(hasNoError());
	}
	
	@Test
	public void downloadCoverageReportWithoutError() throws InterruptedException{
		uploadFullFileThenReturnToCatalogsPage();
		navigateToCR();
		coverageReport.downloadReport();
		
		assertTrue(hasNoError());
	}
	
	@Test
	public void dashboardShowsCompleteAfterCoverageRun() throws InterruptedException{
		uploadFullFileThenReturnToCatalogsPage();
		
		navigateToCR();
		catalogsPage = coverageReport.generateCoverageReport();
		coverageReport.waitForCoverageToGenerate();
		
		DashboardPage dashboardPage = catalogsPage.goBack().goToDashboard();
		List<Map<String, String>> workflowMaps = dashboardPage.getMainWorkFlowInfoByCatId(catalogId);
		assertEquals(workflowMaps.get(0).get("status"), STATUS.COMPLETED.toString());	
	}

	@Test
	public void dtsCoverageShowsOnReport(){
		
	}
	public void navigateToCR(){
		catalogsPage.setMyCatalog(tempFiles.get(0));

		
		this.coverageReport= catalogsPage.clickCoverageReport();
		this.coverageReport.resizeCoverageWindow(600);
		coverageReport.forceWait(3000);
	}
	
	public void uploadFullFileThenReturnToCatalogsPage() throws InterruptedException{
		MappingPage mappingPage = UploadFullFile("sampleFile.txt");
		DetailsPage detailsPage = mappingPage.automap();
		catalogId = getCatIdByCatalogName(tempFiles.get(0));
		System.out.println("catalog ID under search is " + catalogId);
		catalogsPage = detailsPage.clickReturnToList();
	}
	
	public Long catalogId;
	
	public Long getCatIdByCatalogName(String fileName){
		String url = driver.getCurrentUrl();
		return Long.parseLong(url.substring(url.indexOf("=")+1));
	}
	
	
}
