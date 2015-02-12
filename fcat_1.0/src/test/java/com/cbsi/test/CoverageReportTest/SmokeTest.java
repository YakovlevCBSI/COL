package com.cbsi.test.CoverageReportTest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import com.cbsi.tests.Foundation.FormBaseTest;
import com.cbsi.tests.PageObjects.CoverageReportPage;

public class SmokeTest extends FormBaseTest{

	public SmokeTest(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}
	public CoverageReportPage coverageReport;
	
	@Test
	public void generateCoverageReportWithoutError(){
		navigateToCR();
		coverageReport.generateCoverageReport();
		assertTrue(isCoverageNotificationDisplayed());
		assertTrue(hasNoError());
	}
	
	@Test
	public void downloadCoverageReportWithoutError(){
		navigateToCR();
		coverageReport.downloadReport();
		
		assertTrue(hasNoError());
	}

	public void navigateToCR(){
		catalogsPage.setMyCatalogToAutomaticCatalog();
		this.coverageReport= catalogsPage.clickCoverageReport();
		this.coverageReport.resizeCoverageWindow(600);
		coverageReport.forceWait(3000);
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
