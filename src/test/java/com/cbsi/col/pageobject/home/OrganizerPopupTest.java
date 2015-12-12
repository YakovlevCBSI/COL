package com.cbsi.col.pageobject.home;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.cbsi.col.pageobject.home.OrganizerPopup.OrganizerTabs;
import com.cbsi.col.pageobject.home.OrganizerPopup.SendItemPopup;
import com.cbsi.col.test.foundation.ColBaseTest;

public class OrganizerPopupTest extends ColBaseTest{

	public OrganizerPopupTest(String url, String browser) {
		super(url, browser);
		// TODO Auto-generated constructor stub
	}
		
	@Before
	public void startUp(){
		super.startUp();
	}
	
	String testItem = "qa_" + System.currentTimeMillis();
	@Test
	public void addNote(){
		OrganizerPopup organizerPopup = homePage.fromTopbar().clickAddNote();
		organizerPopup.quickSaveItem(testItem);
		organizerPopup.clickRefresh();
		
		assertTrue(organizerPopup.hasItem(OrganizerPopup.SUBJECT, testItem));
	}
	
	@Test
	public void taskItemHasFormat(){
		OrganizerPopup organizerPopup = homePage.fromTopbar().clickAddNote();
		organizerPopup.quickSaveItem(testItem);
		organizerPopup.clickConverToTask();
		
		//go to task tab.
				
		//assert note does not exist.
		//assert task exists.
		
	}
	
	@Test
	public void convertNoteToTask(){
		OrganizerPopup organizerPopup = homePage.fromTopbar().clickAddNote();
		organizerPopup.quickSaveItem(testItem);
		organizerPopup.clickConverToTask();
		organizerPopup.quit();
		
		OrganizerPopup taskPopup = homePage.fromTopbar().clickAddTask().switchTab(OrganizerTabs.Task);
		assertTrue(taskPopup.hasItem(OrganizerPopup.SUBJECT, testItem));
	}
	
	@Test
	public void addTask(){
		OrganizerPopup organizerPopup = homePage.fromTopbar().clickAddTask();
		organizerPopup.quickSaveItem(testItem);
		System.out.println("Test: "  + testItem);
		organizerPopup.quit();
		OrganizerPopup taskPopup = homePage.fromTopbar().clickAddTask().switchTab(OrganizerTabs.Task);
		assertTrue(taskPopup.hasItem(OrganizerPopup.SUBJECT, testItem));
		
	}
	
	@Test
	public void deleteNote(){
		OrganizerPopup organizerPopup = homePage.fromTopbar().clickAddNote();
		organizerPopup.quickSaveItem(testItem);
		organizerPopup.clickRefresh();
		
		organizerPopup = organizerPopup.clickCheckBoxItem(testItem).clickDelete();
		assertFalse(organizerPopup.hasItem(OrganizerPopup.SUBJECT, testItem));
	}
	
	@Test
	public void deleteTask() throws InterruptedException{
		OrganizerPopup organizerPopup = homePage.fromTopbar().clickAddTask();
		organizerPopup.quickSaveItem(testItem);
		
		organizerPopup.clickRefresh();
		
		organizerPopup = organizerPopup.clickCheckBoxItem(testItem).clickDelete();
		assertFalse(organizerPopup.hasItem(OrganizerPopup.SUBJECT, testItem));
	}
	
	@Test
	public void sendEmailForTask(){
		OrganizerPopup organizerPopup = homePage.fromTopbar().clickAddTask();
		organizerPopup.quickSaveItem(testItem);
		
		SendItemPopup sendItem = organizerPopup.clickEmail();
		sendItem.setTo(companyName+"@email.com").clickSend();
		
		organizerPopup.quit();
	}
	
	@Test
	public void sendEmailForNote(){
		OrganizerPopup organizerPopup = homePage.fromTopbar().clickAddNote();
		organizerPopup.quickSaveItem(testItem);
		
		SendItemPopup sendItem = organizerPopup.clickEmail();
		sendItem.setTo(companyName+"@email.com").clickSend();
		
		organizerPopup.quit();
	}
	
	@Test
	public void sortNote(){
		
	}

	@Test
	public void sortTask(){
		
	}
}
