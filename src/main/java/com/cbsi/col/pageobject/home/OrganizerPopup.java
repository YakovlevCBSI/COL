package com.cbsi.col.pageobject.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrganizerPopup<T> extends ColBasePage{

	public OrganizerPopup(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		switchToNewWindow();
		waitForTextToBeVisible("Date Range", "h3");
//		waitForTextToBeVisible("Task", "span");
		forceWait(500);
	}

	@FindBy(css="input#subject")
	private WebElement Subject;
	
	@FindBy(css="")
	private WebElement Content;
	
	@FindBy(css="button#save-notetask-btn")
	private WebElement Save;
	
	@FindBy(css="button#update-filters-btn")
	private WebElement Refresh;
	
	@FindBy(css="button#convert-to-task-btn")
	private WebElement ConvertToTask;
	
	@FindBy(css="button#copy-notetask-btn")
	private WebElement Copy;
	
	@FindBy(css="button#delete-item-btn")
	private WebElement Delete;
	
	@FindBy(css="button#send-notetask-btn")
	private WebElement Email;
	
	@FindBy(css="a#org-return-results")
	private WebElement sidePanelButton;
	
	
	
	public OrganizerPopup expandSidePanel(){
		if(driver.findElement(By.cssSelector("body")).getAttribute("class").contains("result-view"))
			sidePanelButton.click();
		
		return this;
	}
	
	public OrganizerPopup closeSidePanel(){
		if(driver.findElement(By.cssSelector("body")).getAttribute("class").contains("edit-view"))
			sidePanelButton.click();
		
		return this;
	}
	
	public void setSubject(String subject){
		Subject.sendKeys(subject);
	}
	
	public String getSubject(){
		return Subject.getText();
	}
	
	public void setContent(String content){
		switchFrame();

		getActions().moveToElement(driver.findElement(By.cssSelector("body"))).click().build().perform();
		getActions().sendKeys(content).click().build().perform();
		
		switchBack();
	}
	
	public String getContent(){
		return Content.getText();
	}
	
	public final static String SUBJECT = "subject";
	public final static String COMPANY = "company";
	public final static String DOCTYPE = "docType";
	public final static String DOCNUMBER = "docnumber";
	public final static String TIME = "time";

	
	public List<Map<String, String>> getNoteTaskItems(){
		List<WebElement> taskBlocks = driver.findElements(By.cssSelector("div.tab-content div.active div div.organizer-result div[class *= 'info']"));
		List<Map<String, String>> maps = new ArrayList<Map<String, String>>();
		
		for(WebElement taskBlock: taskBlocks){
			Map<String, String> taskInfos = new HashMap<String, String>();
			WebElement detailBlock = taskBlock.findElement(By.xpath("div[contains(@class,'-details')]"));
			taskInfos.put(SUBJECT, detailBlock.findElement(By.xpath("p[1]/a")).getText());
			try{
				taskInfos.put(TIME, detailBlock.findElement(By.xpath("p[3]")).getText());
				
				taskInfos.put(COMPANY, detailBlock.findElement(By.xpath("p[2]")).getText().split(",")[0]);
				taskInfos.put(DOCNUMBER, detailBlock.findElement(By.xpath("p[2]")).getText().split(",")[1].split("(")[0].replace("#","").trim());
				taskInfos.put(DOCTYPE, detailBlock.findElement(By.xpath("p[2]")).getText().split(",")[1].split("(")[1].replace(")","").trim());
			}catch(Exception e){
//				System.out.println("This is note");
				taskInfos.put(TIME, detailBlock.findElement(By.xpath("p[2]")).getText());
			}

			maps.add(taskInfos);		
		}
		
		if(maps.isEmpty()){
			System.err.println("No note/tasks were found...");
		}
		
		return maps;	
	}
	
	public boolean hasItem(String attribute, String value){
		List<Map<String, String>> map = getNoteTaskItems();
		for(Map<String, String> item: map){
			System.out.println(item.get(attribute));
			System.out.println(item.get("time"));
			if(item.get(attribute).equals(value)){
				return true;
			}
		}
		
		return false;
	}
	
	public OrganizerPopup clickSave(){
		this.Save.click();
		return this;
	}
	
	
	public OrganizerPopup clickRefresh(){
		Refresh.click();
		forceWait(700);
//		waitForElementToBeVisible(By.cssSelector("div.organizer-result-details p a"));
		return this;
	}
	
	@FindBy(css="button#convert-to-task-confirm-btn")
	private WebElement ConvertToTaskConfirm;
	public OrganizerPopup clickConverToTask(){
		ConvertToTask.click();
		
		waitForElementToBeVisible(By.cssSelector("button#convert-to-task-confirm-btn"));
		ConvertToTaskConfirm.click();
		return this;
	}
	
	public OrganizerPopup clickDelete(){
		this.Save.click();
		return this;
	}
	
	public OrganizerPopup clickEmail(){
		this.Save.click();
		return this;
	}
	
	
	@FindBy(css="ul.nav")
	private WebElement nav;
	public OrganizerPopup switchTab(OrganizerTabs tab){
		currentTab = tab;
		nav.findElement(By.xpath("a[contains[@href,'#"+tab.toString() + "'])")).click();
		return this;
	}
	
	public void quit(){
//		closeCurrentWindow();
		switchToOldWindow();
		return;
	}
	private static OrganizerTabs currentTab;
	public enum OrganizerTabs{
		All,
		Note,
		Task
	}
	
}
