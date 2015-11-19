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
		switchFrame();
		waitForElementToBeVisible(By.cssSelector("p"));
		switchBack();
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
	
	@FindBy(css="button[class*='delete-mul']")
	private WebElement Delete;
	
	@FindBy(css="button[id*='confirm-delete']")
	private WebElement ConfirmDelete;
	
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
			logger.debug("No note/tasks were found...");
		}
		
		return maps;	
	}
	
	public boolean hasItem(String attribute, String value){
		List<Map<String, String>> map = getNoteTaskItems();
		for(Map<String, String> item: map){
//			System.out.println(item.get(attribute));
//			System.out.println(item.get("time"));
			if(item.get(attribute).equals(value)){
				return true;
			}
		}
		
		return false;
	}
	
	public OrganizerPopup clickSave(){
		this.Save.click();
		forceWait(700); //closing right after causes a problem. Wait a bit.
		return this;
	}
	
	
	public OrganizerPopup clickRefresh(){
		Refresh.click();
		forceWait(2000);
//		waitForElementToBeVisible(By.cssSelector("p.title a"));
		return this;
	}
	
	@FindBy(css="button#convert-to-task-confirm-btn")
	private WebElement ConvertToTaskConfirm;
	public OrganizerPopup clickConverToTask(){
		waitForElementToBeVisible(By.cssSelector("button#convert-to-task-btn"));
		ConvertToTask.click();
		
		waitForElementToBeVisible(By.cssSelector("button#convert-to-task-confirm-btn"));
		ConvertToTaskConfirm.click();
		forceWait(700);
		return this;
	}
	
	public OrganizerPopup clickDelete(){
		List<WebElement> deletes = driver.findElements(By.cssSelector("button[class*='delete-mul']"));
		for(WebElement e: deletes){
			if(e.isDisplayed()) {
				e.click();
				break;
			}
		}
//		waitForElementToBeVisible(By.cssSelector("button[id*='confirm-delete']"));
		waitForElementToBeClickable(By.cssSelector("button[id*='confirm-delete']"));
		ConfirmDelete.click();
		return PageFactory.initElements(driver, OrganizerPopup.class);
	}
	
	public OrganizerPopup clickEmail(){
		this.Save.click();
		return this;
	}
	
	public OrganizerPopup clickCheckBoxItem(String title){

		while(driver.findElements(By.cssSelector("div p.title a")).size()==0){
			forceWait(500);
		}
		
		List<WebElement> list = driver.findElements(By.cssSelector("div p.title a"));

		logger.debug("number of checkboxes: " + list.size());
		
		for(WebElement e: list){
			if(e.getText().contains(title)){
				e.findElement(By.xpath("../../../div/div/div/label/input")).click();
				logger.info("checkbox " + title);
				break;
			}
		}
		
		return this;
	}
	
	@FindBy(css="ul.nav")
	private WebElement nav;
	public OrganizerPopup switchTab(OrganizerTabs tab){
		currentTab = tab;
		
		WebElement pickTab = nav.findElement(By.xpath("li/a[contains(@href,'#"+tab.toString().toLowerCase() + "')]"));
		
		if(pickTab.getAttribute("class").contains("active")) {
			return this;
		}
		else {
			pickTab.click();
			forceWait(500);
		}
		
		return PageFactory.initElements(driver, OrganizerPopup.class);
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
	
	public OrganizerPopup quickSaveItem(String title){
		setSubject(title);
		setContent("some content...");
		clickSave();
		
		forceWait(500);
		
		return this;
	}
	
}
