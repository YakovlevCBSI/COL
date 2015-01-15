package com.cbsi.tests.PageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SchedulePopup extends BasePage {

	public SchedulePopup(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForPageToLoad();
	}
	
	@Override
	public void waitForPageToLoad(){
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.fancybox-inner div div.overlay-header")));
	}
	
	@FindBy(css="a.selectBox")
	private WebElement hour;
	
	@FindBy(css="div.schedule-settings-right div a.selectBox")
	private WebElement Frequency;
	
	@FindBy(css="input[data-text='Monday']")
	private WebElement Monday;
	
	@FindBy(css="input[data-text='Tuesday']")
	private WebElement Tuesday;
	
	@FindBy(css="input[data-text='Wednesday']")
	private WebElement Wednesday;
	
	@FindBy(css="input[data-text='Thursday']")
	private WebElement Thursday;
	
	@FindBy(css="input[data-text='Friday']")
	private WebElement Friday;
	
	@FindBy(css="input[data-text='Saturday']")
	private WebElement Saturday;
	
	@FindBy(css="input[data-text='Sunday']")
	private WebElement Sunday;
	
	public SchedulePopup selectFrequency(String freq){
		Frequency.click();
		customWait(5);
		driver.findElement(By.linkText(freq)).click();
		quickWait();
		return this;
	}
	
	public SchedulePopup selectDays(String...days){
		List<WebElement> daysOnPage = driver.findElements(By.cssSelector("div.scheduleWeekDay div label.checkable"));
		if(days.length == 1 && days[0].toLowerCase().equals("all")){
			for(WebElement d: daysOnPage){
				d.click();
			}
		}
		else{
			for(int i=0; i< days.length; i++){
				//System.out.println("days contained: " + days[i]);
				for(WebElement e: daysOnPage){
					//System.out.println("daysOnPage: " + e.getAttribute("id").toLowerCase());
					if(e.getAttribute("id").toLowerCase().contains(days[i].toLowerCase())){
						//System.out.println("match: " + days[i]);
						e.click();
					}
				}
			}
		}
		return this;		
	}
	
	//do this before above method.
	public void clearDaysToDefault(){
		
	}
	
	public static String[] generateRandomDays(){
		String[] days = {"monday", "tueday", "wednesday", "thursday", "friday", "saturday", "sunday"};

		
		int rand1 = (int)(Math.random() * days.length);
		int rand2 = rand1+1;
		int rand3 = rand1+2;
		if(rand2 > days.length-1){
			rand2 = rand2 - days.length;
			rand3 = rand3 - days.length;
		}
		else if(rand3 > days.length -1){
			rand3 = rand3 - days.length;
		}
		
		System.out.println("days random: " + days[rand1] + "/ " + days[rand2] + " /" + days[rand3] );
		String[] randArray = {days[rand1], days[rand2], days[rand3]};
		
		return randArray;
		
	}
	@FindBy(linkText="OK")
	private WebElement OK;
	
	public AddCatalogPage clickOK(){
		OK.click();
		waitForElementToBeInvisible(By.linkText("OK"));
		return PageFactory.initElements(driver, AddCatalogPage.class);
	}

	public SchedulePopup clearAllCheckBoxes(){
		List<WebElement> checkedBoxes = driver.findElements(By.cssSelector("label[for^='ScheduleSetting_SelectedWeekDays_']"));
		for(WebElement e: checkedBoxes){
			if(e.getAttribute("class").contains("checked")){
				e.click();
			}
		}
		
		return this;
	}
	public static void main(String[] args){
		generateRandomDays();
	}
}
