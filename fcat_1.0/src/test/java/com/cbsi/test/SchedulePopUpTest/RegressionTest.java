package com.cbsi.test.SchedulePopUpTest;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.tests.Foundation.AllBaseTest;
import com.cbsi.tests.PageObjects.AddCatalogPage;
import com.cbsi.tests.PageObjects.CatalogsPage;
import com.cbsi.tests.PageObjects.SchedulePopup;

public class RegressionTest extends AllBaseTest {

	public RegressionTest(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	@Before
	public void startUp(){
		super.startUp();
		CatalogsPage catalogsPage = PageFactory.initElements(driver, CatalogsPage.class);
		catalogsPage.setMyCatalogToAutomaticCatalog();
		catalogsPage.clickEdit();
	}
	
	private static String randomDay;
	private static String[] randomDays;
	
	@BeforeClass
	public static void beforeStartup(){
		randomDay = SchedulePopup.generateRandomDay();
		randomDays = SchedulePopup.generateRandomDays();
	}
	

	@Test
	public void checkSelectedDayIsSaved() throws InterruptedException{
		SchedulePopup schedulePopup = navigateToSchedule();

		schedulePopup.selectFrequency("Weekly").clearAllCheckBoxes().selectDays(randomDay);
		AddCatalogPage propertiesPageNew = schedulePopup.clickOK();
		CatalogsPage catalogsPage = propertiesPageNew.clickSave();
		AddCatalogPage propertiesPageNew2 = catalogsPage.setMyCatalogToAutomaticCatalog().clickEdit();
		
		SchedulePopup schedulePopup2 = navigateToSchedule();
		
		Thread.sleep(3000);
		assertTrue( "manually checked day is " +randomDay, schedulePopup2.dayCheckBoxesAreChecked(randomDay));
	}
	
	@Test
	public void checkSelectedDaysAreDisplayed() throws InterruptedException{
		SchedulePopup schedulePopup = navigateToSchedule();

		String[] excludedDays = schedulePopup.ExcludedDays(randomDays);
		for(String s: randomDays){
			System.out.println("include: " + s);
		}
		for(String s: excludedDays){
			System.out.println("exclude: " + s);
		}
		schedulePopup.selectFrequency("Weekly").clearAllCheckBoxes().selectDays(randomDays);

		AddCatalogPage propertiesPageNew = schedulePopup.clickOK();

		CatalogsPage catalogsPage = propertiesPageNew.clickSave();
		AddCatalogPage propertiesPageNew2 = catalogsPage.setMyCatalogToAutomaticCatalog().clickEdit();

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
		SchedulePopup schedulePopup = navigateToSchedule();
		schedulePopup.selectFrequency("Weekly").clearAllCheckBoxes().selectDays("all");

		AddCatalogPage propertiesPageNew = schedulePopup.clickOK();

		CatalogsPage catalogsPage = propertiesPageNew.clickSave();
		AddCatalogPage propertiesPageNew2 = catalogsPage.setMyCatalogToAutomaticCatalog().clickEdit();

		String result = propertiesPageNew2.getScheduleResult();
	
		System.out.println("result: " + result);
		assertTrue(
				result.toLowerCase().contains(days));
	}

	
	public SchedulePopup navigateToSchedule(){
		AddCatalogPage propertiesPage = PageFactory.initElements(driver, AddCatalogPage.class);
		SchedulePopup schedulePopup = propertiesPage.clickSetSchedule();
		return schedulePopup;
	}
	

}
