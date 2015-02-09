package com.cbsi.tests.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DetailsPage extends BasePage{
	public DetailsPage(WebDriver driver){
		super(driver);
		waitForPageToLoad();
	}
	
	@Override
	public void waitForPageToLoad(){
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table.processing-queue thead")));
	}
	
	@FindBy(xpath="//tbody/tr[1]")
	private WebElement FirstProcessingRow;
	
	public String getStatus(){
		WebElement status = FirstProcessingRow.findElement(By.xpath("//td[contains(@class,'status')]/span"));
		while(status.getText().equals("In progress")){
			System.out.println("waiting for deatils progress toc complete.");
			refresh();
			status = refreshStaleElement(By.xpath("//tbody/tr[1]/td[contains(@class,'status')]/span"));
		}
		return status.getText();
	}
	
	public DetailsPage expandDetails(){
		FirstProcessingRow.click();
		return this;
	}
	
	public String getProcessingQueueMessage(String DifferenceParseOrFileUpload, String messageOrStatus){
		WebElement firstRow = FirstProcessingRow.findElement(By.xpath("../tr[1]"));
		WebElement whichDetailedMessageRow = null;
		String details ="";
		if(DifferenceParseOrFileUpload.equals("difference")){
			whichDetailedMessageRow = FirstProcessingRow.findElement(By.xpath("../tr[@class='detailed'][1]"));
			
			
		}
		else if(DifferenceParseOrFileUpload.equals("parse")){
			whichDetailedMessageRow = FirstProcessingRow.findElement(By.xpath("../tr[@class='detailed'][2]"));
			
		}
		else if(DifferenceParseOrFileUpload.equals("fileUpload")){
			whichDetailedMessageRow = FirstProcessingRow.findElement(By.xpath("../tr[@class='detailed'][3]"));

		}
		
		if(messageOrStatus.equals("message")){
			details = whichDetailedMessageRow.findElement(By.xpath("td[@class='name-column']")).getText();
		}
		else if(messageOrStatus.equals("status")){
			details = whichDetailedMessageRow.findElement(By.xpath("td[@class='status-column']")).getText();
		}
		
		return details;
		
	}
	
	@FindBy(linkText="Return to List")
	private WebElement ReturnToList;
	public CatalogsPage clickReturnToList(){
		ReturnToList.click();
		return PageFactory.initElements(driver, CatalogsPage.class);
	}
}
