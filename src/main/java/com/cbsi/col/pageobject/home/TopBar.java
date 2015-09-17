package com.cbsi.col.pageobject.home;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


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
	
}
