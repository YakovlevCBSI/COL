package com.cbsi.fcat.pageobject.catalogpage;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.fcat.pageobject.catatlogpage.AddCatalogPage;
import com.cbsi.fcat.pageobject.catatlogpage.CatalogsPage;
import com.cbsi.fcat.pageobject.catatlogpage.SchedulePopup;
import com.cbsi.fcat.pageobject.foundation.AllBaseTest;

public class SchedulePopupTest extends AllBaseTest {

	public SchedulePopupTest(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	@Before
	public void startUp(){
		super.startUp();
		randomDay = SchedulePopup.generateRandomDay();
		randomDays = SchedulePopup.generateRandomDays();
	}
	
	private static String randomDay;
	private static String[] randomDays;
	
	

	@Test
	public void checkSelectedDayIsSaved() throws InterruptedException{
		if(getBrowser().toLowerCase().contains("firefox")) return;
		SchedulePopup schedulePopup = navigateToSchedule();

		schedulePopup.selectFrequency("Weekly").clearAllCheckBoxes().selectDays(randomDay);
		AddCatalogPage propertiesPageNew = schedulePopup.clickOK();
		propertiesPageNew.clickSaveNone();

		AddCatalogPage propertiesPageNew2 =  jumpToEdit();		
		SchedulePopup schedulePopup2 = propertiesPageNew2.clickSetSchedule();
		
		//Thread.sleep(3000);
		assertTrue( "manually checked day is " +randomDay, schedulePopup2.dayCheckBoxesAreChecked(randomDay));
	}
	
	@Test
	public void checkSelectedDaysAreDisplayed() throws InterruptedException{
		if(getBrowser().toLowerCase().contains("firefox")) return;
		SchedulePopup schedulePopup = navigateToSchedule();

		String[] excludedDays = schedulePopup.ExcludedDays(randomDays);
		printIncludeExcludeDays(randomDays, excludedDays);
		
		schedulePopup.selectFrequency("Weekly").clearAllCheckBoxes().selectDays(randomDays);
		AddCatalogPage propertiesPageNew = schedulePopup.clickOK();
		propertiesPageNew.clickSaveNone();

		//Instantiating a new page object due to unreachablebrowser exception.
		AddCatalogPage propertiesPageNew2 = jumpToEdit();

		String result = propertiesPageNew2.getScheduleResult();
	
		System.out.println("result: " + result);
		assertTrue(
				(result.contains(randomDays[0]) && result.contains(randomDays[1]) && result.contains(randomDays[2]))
				&& !(result.contains(excludedDays[0]) && result.contains(excludedDays[1]) && result.contains(excludedDays[2]) && result.contains(excludedDays[3]))
				);
	}
	
	public static final String days = ("monday,tuesday,wednesday,thursday,friday,saturday,sunday");
	
	@Test
	public void checkAllDaysAreDisplayed(){
		if(getBrowser().toLowerCase().contains("firefox")) return;
		SchedulePopup schedulePopup = navigateToSchedule();
		schedulePopup.selectFrequency("Weekly").clearAllCheckBoxes().selectDays("all");

		AddCatalogPage propertiesPageNew = schedulePopup.clickOK();
		propertiesPageNew.clickSaveNone();
		AddCatalogPage propertiesPageNew2 =  jumpToEdit();

		String result = propertiesPageNew2.getScheduleResult();
	
		System.out.println("result: " + result);
		assertTrue(
				result.toLowerCase().contains(days));
	}

	
	public SchedulePopup navigateToSchedule(){
		if(getBrowser().toLowerCase().contains("firefox")) assertTrue(true);
		AddCatalogPage propertiesPage = jumpToEdit();
		if(propertiesPage.doNotUseScheduleIsChecked()){
			propertiesPage.clickDoNotUseSchedule();
		}
		SchedulePopup schedulePopup = propertiesPage.clickSetSchedule();
		return schedulePopup;
	}
	
	//Fix to unreachable browser exceptoion. instantiate a new page object.s
	public AddCatalogPage jumpToEdit(){
		
		CatalogsPage catalogsPage = PageFactory.initElements(driver, CatalogsPage.class);
		catalogsPage.setMyCatalogToAutomaticCatalog();
		return catalogsPage.clickEdit();
	}
	
	public void printIncludeExcludeDays(String[] includes, String[] excludes){
		for(String s: includes){
			System.out.println("include : " + s);
		}
		for(String s: excludes){
			System.out.println("exclude: " + s);
		}
	}
	
	public void skipFirefox(){
		if(getBrowser().toLowerCase().contains("firefox")) assertTrue(true);
	}

	

}
