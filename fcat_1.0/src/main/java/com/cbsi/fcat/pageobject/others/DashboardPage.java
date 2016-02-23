package com.cbsi.fcat.pageobject.others;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.cbsi.fcat.pageobject.foundation.BasePage;

public class DashboardPage extends BasePage{
	public DashboardPage(WebDriver driver){
		super(driver);
		waitForPageToLoad(By.linkText("Catalog Integration Flows"));
	}
	
	@FindBy(css="select#search_status")
	private WebElement Status;
	
	@FindBy(css="button#submitButton")
	private WebElement Search;
	
	@FindBy(css="option[value='PENDING']")
	private WebElement Pending;
	
	@FindBy(css="option[value='COMPLETED']")
	private WebElement Completed;
	
	@FindBy(css="option[value='IN_PROGRESS']")
	private WebElement InProgress;
	
	@FindBy(css="option[value='ERROR']")
	private WebElement Error;
	
	public DashboardPage pickStatus(STATUS status){
		Status.click();	
		forceWait(500);
		
		WebElement optionToPick = null;
		if(status == STATUS.Pending)
			optionToPick = Pending;
		else if(status == STATUS.Completed)
			optionToPick = Completed;
		else if(status == STATUS.InProgress)
			optionToPick = InProgress;
		else if(status == STATUS.Error)
			optionToPick = Error;
		
		if(optionToPick != null)
			optionToPick.click();
		else throw new NoSuchElementException("No such option available to choose from");
		
		Search.click();
		forceWait(500);
		return this;
	}
	
	public List<String> getTodaysData(){
		List<String> todaysColumns = new ArrayList<String>();
		List<WebElement> createdColumns = driver.findElements(By.cssSelector("td[aria-describedby='maingrid_created']"));
		String todate = getCurrentDate();
		for(WebElement column: createdColumns){
			if(column.getText().contains(todate)){
				todaysColumns.add(column.findElement(By.xpath("../td[2]")).getAttribute("title"));
			}
		}
		
		if(todaysColumns.size() == 0)
			throw new NullPointerException("There are no available data from today.");
		
		return todaysColumns;
	}
	
	public static String getCurrentDate(){
		Date date = new Date();
		TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		int YEAR = cal.get(Calendar.YEAR);
		int MONTH = (cal.get(Calendar.MONTH)+1);
		int DAY = cal.get(Calendar.DAY_OF_MONTH);

		return YEAR+ "-" + (MONTH <10?"0"+MONTH:MONTH) + "-" + (DAY <10 ? "0"+DAY:DAY);
	}
	
	public void printStatus(String id){
		clickExpandButton(id);
		
		waitForElementToBeVisible(By.cssSelector("table#maingrid_" + id + "_t"));
		List<String> errorMessage = new ArrayList<String>();
		List<WebElement> errors = driver.findElements(By.cssSelector("td[aria-describedby='maingrid_" + id + "_t_error']"));
		
		for(WebElement error: errors){
			if(!(error.getText().equals("null") && error.getText() != null)){
				errorMessage.add(error.getText());
			}
		}
		
		if(errorMessage.size() >=1)
			System.out.println("------------------------------------------------------");
			System.out.println("ID: " + id);
			for(String error: errorMessage){
				System.out.println("MESSAGE: " + error);
			}
		clickExpandButton(id);
	}
	
	public void printDetailsMessage(){
		for(String id: getTodaysData()){
			printStatus(id);
		}
	}
	
	public List<Map<String, String>> getWorkflowInfoById(long id){
		List<Map<String, String>> workflows = new ArrayList<Map<String, String>>();
		Map<String, String> workflow = new HashMap<String, String>();
		
		WebElement Table = driver.findElement(By.cssSelector("table[id ^='maingrid_" + id+"']"));
		
//		System.out.println("Table: " +(Table == null));
		List<WebElement> trs = Table.findElements(By.xpath("tbody/tr[starts-with(@class,'ui-')]"));
		
//		System.out.println("trs: " + trs.size());
		for(WebElement tr: trs){
			List<WebElement> tds = tr.findElements(By.xpath("td[starts-with(@aria-describedby,'maingrid_" + id + "')]"));
			
//			System.out.println("tdsize: " +tds.size());
			for(WebElement e: tds){
//				System.out.println(e.getText());
				workflow.put(e.getAttribute("aria-describedby").split("t_")[1], e.getText());
			}
			
			workflows.add(workflow);
		}
		
		return workflows;		
	}
	
	WebElement expandButton;
	public void clickExpandButton(String id){
		expandButton = driver.findElement(By.cssSelector("tr[id='" + id + "'] td a"));
		expandButton.click();
	}
	
	public enum STATUS{
		Pending, 
		Completed, 
		InProgress, 
		Error;
	}
}
