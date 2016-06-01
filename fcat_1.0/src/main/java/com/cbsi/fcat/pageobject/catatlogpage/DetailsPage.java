package com.cbsi.fcat.pageobject.catatlogpage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cbsi.fcat.pageobject.foundation.BasePage;

public class DetailsPage extends BasePage{
	public final static Logger logger = LoggerFactory.getLogger(DetailsPage.class);

	public DetailsPage(WebDriver driver){
		super(driver);
		waitForPageToLoad();
		waitForTextToBeVisible(20, "Processing Queue", "div.processing-queue-achor"); //look for a specific path, in case there is a large workflow.

		forceWait(500);
	}
	
	public static final String AUTOMATIC= "Automatic";
	public static final String MANUAL= "Manual Upload";
	
	@FindBy(xpath="//div[@class='catalog-properties-left']/div[2]/label")
	private WebElement Name;
	
	@FindBy(xpath="//div[@class='catalog-properties-left']/div[4]/label")
	private WebElement Market;
	
	@FindBy(xpath="//div[@class='catalog-properties-left']/div[6]/label")
	private WebElement CatalogCode;
	
	@FindBy(xpath="//div[@class='catalog-properties-right']/div[2]/label")
	private WebElement UpdateMethod;
	
	@FindBy(xpath="//div[@class='catalog-properties-right']/div[4]/label")
	private WebElement FileLocation;
	
	@FindBy(xpath="//div[@class='catalog-properties-right']/label")
	private WebElement Schedule;
	
	public String getName(){
		return Name.getText();
	}
	
	public String getMarket(){
		return Market.getText();
	}
	
	public String getCatalogCode(){
		return CatalogCode.getText();
	}
	
	public String getUpdateMethod(){
		return UpdateMethod.getText();
	}
	
	public String getFileLocation(){
		return FileLocation.getText();
	}
	
	public String getSchedule(){
		return Schedule.getText();
	}
	
	@Override
	public void waitForPageToLoad(){
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.editor-field-indent")));
	}
	
	@FindBy(xpath="//tbody/tr[1]")
	private WebElement FirstProcessingRow;
	
	public String getFileName(){
		return FirstProcessingRow.findElement(By.xpath("td[1]/span[2]")).getText();
	}
	
//	private static String uploadStatus = "";
	public String getStatus(){
		WebElement status=null;
		try{
			status = FirstProcessingRow.findElement(By.xpath("//td[contains(@class,'status')]/span"));
		}catch(StaleElementReferenceException e){
			FirstProcessingRow = refreshStaleElement(By.xpath("//td[contains(@class,'status')]/span"));
			status = FirstProcessingRow.findElement(By.xpath("//td[contains(@class,'status')]/span"));
		}
		scrollToView(status);
		while(status.getText().equals(UploadStatus.INPROGRESS.toString())){
			logger.info("waiting for deatils progress toc complete.");
			refresh();
			status = refreshStaleElement(By.xpath("//tbody/tr[1]/td[contains(@class,'status')]/span"));
		}
//		uploadStatus =status.getText();
//		return uploadStatus;
		return status.getText();
	}
	
	@FindBy(css="span.text.current")
	private WebElement Modified;
	
	public String getModified(){
		return Modified.getText();
	}
	
	public boolean FileUploadIsDone(){
		while(getStatus().equals(UploadStatus.INPROGRESS.toString())){		
			refresh();
			forceWait(1000);
		}
		if(getStatus().equals(UploadStatus.DONE.toString())){
			logger.info("file status shows DONE");
			return true;
		}
		
		return false;
	}
	
	public DetailsPage expandDetails(){
		boolean isClickable = false;
		long startTime = System.currentTimeMillis();
		
		while(!isClickable && System.currentTimeMillis()- startTime <15000){
			try{
				FirstProcessingRow = refreshStaleElement(By.xpath("//tbody/tr[1]"));
				scrollToView(FirstProcessingRow);
				FirstProcessingRow.click();
				
				isClickable = true;
			}catch(WebDriverException e){
				forceWait(500);
				refresh();
			}
		}
		return this;
	}
	
	public String getProcessingQueueMessage(ProcessingQueue DifferenceParseOrFileUpload, InfoType messageOrStatusOrModified){
		return getProcessingQueueMessage(DifferenceParseOrFileUpload, messageOrStatusOrModified, false);
	}
	
	public Boolean storeAndMapExist;
	public Boolean diffExists;
	
	public String getProcessingQueueMessage(ProcessingQueue DifferenceParseOrFileUpload, InfoType messageOrStatusOrModified, boolean detailedMessage){
		WebElement whichDetailedMessageRow = null;
		String rowNum="";
		
		//check if element is staled.
		FirstProcessingRow = refreshStaleElement(By.cssSelector("tbody tr:nth-child(1)"));
		
		if(!FirstProcessingRow.getTagName().equals("tr"))
			FirstProcessingRow = FirstProcessingRow.findElement(By.xpath("../../../tr[1]"));
		
		rowNum = FirstProcessingRow.getAttribute("name").split("-")[1].trim();
	
		//check if this is full or upload catalog by counting status columns.
		List<WebElement> statusList = FirstProcessingRow.findElements(By.xpath("../tr[contains(@name,'status-row-for-" + rowNum+"')]"));

		if(storeAndMapExist == null){
			if(statusList.size() >=5){
				logger.debug("size 5 condition");
				storeAndMapExist = true;
				diffExists = true;
			}else if(statusList.size() ==2){
				logger.debug("size 2 condition");
				storeAndMapExist= false;
				diffExists = false;
			}else{
				logger.debug("last condition");
				storeAndMapExist= false;
				diffExists = true;
			}
		}

		logger.info("storeAndMapExists: " + storeAndMapExist + "\n" + "diffExists: " + diffExists);
		int nthTr= 0;
		String details ="";
		if(DifferenceParseOrFileUpload.equals(ProcessingQueue.DIFFERENCE)){
			if(!storeAndMapExist) {
				whichDetailedMessageRow = FirstProcessingRow.findElement(By.xpath("../tr[2]"));
				nthTr= 2;
			}
			else {
				whichDetailedMessageRow = FirstProcessingRow.findElement(By.xpath("../tr[4]"));
				nthTr= 4;
			}
		
		}
		else if(DifferenceParseOrFileUpload.equals(ProcessingQueue.PARSE)){
			logger.info("ProcessingQueue Parse condition at work");
			
			if(!diffExists && !storeAndMapExist)  {
				whichDetailedMessageRow = FirstProcessingRow.findElement(By.xpath("../tr[2]"));
				nthTr= 2;
			}
			else if(!storeAndMapExist){
				whichDetailedMessageRow = FirstProcessingRow.findElement(By.xpath("../tr[3]"));
				nthTr= 3;
			}
			else {
				whichDetailedMessageRow = FirstProcessingRow.findElement(By.xpath("../tr[5]"));
				nthTr= 5;
			}
			
		}
		else if(DifferenceParseOrFileUpload.equals(ProcessingQueue.FILEUPLOAD)){
			if(!diffExists && !storeAndMapExist)  {
				whichDetailedMessageRow = FirstProcessingRow.findElement(By.xpath("../tr[3]"));
				nthTr= 3;
			}
			else if(!storeAndMapExist) {
				whichDetailedMessageRow = FirstProcessingRow.findElement(By.xpath("../tr[4]"));
				nthTr= 4;
			}
			else {
				whichDetailedMessageRow = FirstProcessingRow.findElement(By.xpath("../tr[6]"));
				nthTr= 6;
			}
		}
		else if (DifferenceParseOrFileUpload.equals(ProcessingQueue.STORE)){
			whichDetailedMessageRow = FirstProcessingRow.findElement(By.xpath("../tr[2]"));
			nthTr= 2;
		}
		else if(DifferenceParseOrFileUpload.equals(ProcessingQueue.MAP)){
			whichDetailedMessageRow = FirstProcessingRow.findElement(By.xpath("../tr[3]"));
			nthTr= 3;
		}

		if(messageOrStatusOrModified.equals(InfoType.MESSAGE)){
			if(detailedMessage){
				whichDetailedMessageRow = FirstProcessingRow.findElement(By.xpath("../tr[" + (nthTr+1)+ "]"));
				details = whichDetailedMessageRow.findElement(By.xpath("td/p")).getText();
			}
			else{
				details = whichDetailedMessageRow.findElement(By.xpath("td[contains(@class,'name-column')]")).getText();
			}
		}
		else if(messageOrStatusOrModified.equals(InfoType.STATUS)){
			details = whichDetailedMessageRow.findElement(By.xpath("td[contains(@class,'status-column')]/span")).getText();
		}
		else if(messageOrStatusOrModified.equals(InfoType.MODIFIED)){
			details = whichDetailedMessageRow.findElement(By.xpath("td[@class='date-column']/span")).getText();
		}
		
		return details;
	}

	public enum ProcessingQueue{
		DIFFERENCE, PARSE, FILEUPLOAD, MAP, STORE;
	}
	
	public enum InfoType{
		MESSAGE, STATUS, MODIFIED;
	}
	
	public enum UploadStatus{
		DONE,
		INPROGRESS{
			public String toString(){
				return "In progress";
			}
		},
		ERROR
	}
}
