package com.cbsi.test.CoverageReportTest;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import com.cbsi.tests.Foundation.FormBaseTest;
import com.cbsi.tests.PageObjects.DetailsPage;
import com.cbsi.tests.PageObjects.CoverageReportPage;
import com.cbsi.tests.PageObjects.MappingPage;

public class SmokeTest extends FormBaseTest{

	public SmokeTest(String URL, String browser) {
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
		while(isCoverageNotificationDisplayed()){
			Thread.sleep(1000);
		}
		
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

	
	public void navigateToCR(){
		catalogsPage.setMyCatalog(tempFiles.get(0));

		
		this.coverageReport= catalogsPage.clickCoverageReport();
		this.coverageReport.resizeCoverageWindow(600);
		coverageReport.forceWait(3000);
	}
	
	public void uploadFullFileThenReturnToCatalogsPage() throws InterruptedException{
		MappingPage mappingPage = UploadFullFile("sampleFile.txt");
		DetailsPage detailsPage = mappingPage.automap();
		catalogsPage = detailsPage.clickReturnToList();
	}
	
	
	public boolean isCoverageNotificationDisplayed(){
		String h2Message="";
		long startTime = System.currentTimeMillis();
		while((System.currentTimeMillis() - startTime) < 10000){
			try{
				h2Message = driver.findElement(By.cssSelector("h2")).getText();
			}catch(NoSuchElementException e){
				
			}
			if(!h2Message.isEmpty()){
				System.out.println(h2Message);
				return true;
			}
		}
		return false;
		
	}
}
