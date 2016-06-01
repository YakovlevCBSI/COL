package com.cbsi.col.pageobject.home;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class TopBar extends ColBasePage{


	public TopBar(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(css="li.item a#crm-inbox")
	private WebElement Inbox;
	
	@FindBy(css="li.item a#crm-organizer")
	private WebElement Organizer;
	
	@FindBy(css="li.item ul li a#crm-user")
	private WebElement User;
	
	@FindBy(css="a[class*='view-notetask-btn'][data-type='note']")
	private WebElement ViewNote;
	
	@FindBy(css="a[class*='add-notetask-btn'][data-type='note']")
	private WebElement AddNote;
	
	@FindBy(css="a[class*='view-notetask-btn'][data-type='task']")
	private WebElement ViewTask;
	
	@FindBy(css="a[class*='add-notetask-btn'][data-type='task']")
	private WebElement AddTask;
	
	public boolean isInboxDropdownDisplayed(){
		getActions().moveToElement(Inbox).build().perform();
		return Inbox.findElement(By.xpath("../div[contains(@class,'dropdown-menu')]")).isDisplayed();
	}
	
	public boolean isOrganizerDropdownDisplayed(){
		getActions().moveToElement(Organizer).build().perform();
		return Organizer.findElement(By.xpath("../div[contains(@class,'dropdown-menu')]")).isDisplayed();
	}
	
	public boolean isUserDropdownDisplayed(){
		getActions().moveToElement(User).build().perform();
		return User.findElement(By.xpath("../div[contains(@class,'dropdown-menu')]")).isDisplayed();
	}
	
	public OrganizerPopup clickOrganizer(){
		Organizer.click();
		return PageFactory.initElements(driver, OrganizerPopup.class);
	}
	
	public OrganizerPopup clickAddNote(){
		getActions().moveToElement(Organizer).build().perform();
		AddNote.click();
		
		return PageFactory.initElements(driver, OrganizerPopup.class);
	}
	
	public OrganizerPopup clickAddTask(){
		getActions().moveToElement(Organizer).build().perform();
		AddTask.click();
		
		return PageFactory.initElements(driver, OrganizerPopup.class);
	}
}
