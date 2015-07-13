package com.cbsi.tests.PageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cbsi.tests.util.ElementConstants;

public class DetailsPage extends BasePage{
	public DetailsPage(WebDriver driver){
		super(driver);
		waitForPageToLoad();
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
	
	private static String uploadStatus = "";
	public String getStatus(){
		WebElement status=null;
		try{
			status = FirstProcessingRow.findElement(By.xpath("//td[contains(@class,'status')]/span"));
		}catch(StaleElementReferenceException e){
			FirstProcessingRow = refreshStaleElement(By.xpath("//td[contains(@class,'status')]/span"));
			status = FirstProcessingRow.findElement(By.xpath("//td[contains(@class,'status')]/span"));
		}
		scrollToView(status);
		while(status.getText().equals("In progress")){
			System.out.println("waiting for deatils progress toc complete.");
			refresh();
			status = refreshStaleElement(By.xpath("//tbody/tr[1]/td[contains(@class,'status')]/span"));
		}
		uploadStatus =status.getText();
		return uploadStatus;
	}
	
	public boolean FileUploadIsDone(){
		while(uploadStatus.equals(ElementConstants.INPROGRESS)){		
			refresh();
			forceWait(1000);
		}
		if(getStatus().equals(ElementConstants.DONE)){
			return true;
		}
		
		return false;
	}
	
	public DetailsPage expandDetails(){
		FirstProcessingRow = refreshStaleElement(By.xpath("//tbody/tr[1]"));
		FirstProcessingRow.click();
		return this;
	}
	
	public static Boolean storeAndMapExists;
	
	public String getProcessingQueueMessage(ProcessingQueue DifferenceParseOrFileUpload, InfoType messageOrStatusOrModified){
		WebElement whichDetailedMessageRow = null;
		String rowNum="";
		
		if(!FirstProcessingRow.getTagName().equals("tr"))
			FirstProcessingRow = FirstProcessingRow.findElement(By.xpath("../../../tr[1]"));
		
		rowNum = FirstProcessingRow.getAttribute("name").split("-")[1].trim();
	
		//check if this is full or upload catalog by counting status columns.
		if(storeAndMapExists == null){
			List<WebElement> statusList = FirstProcessingRow.findElements(By.xpath("../tr[contains(@name,'status-row-for-" + rowNum+"')]"));
			if(statusList.size() ==5){
				storeAndMapExists = true;
			}else{
				storeAndMapExists= false;
			}
		}

		String details ="";
		if(DifferenceParseOrFileUpload.equals(ProcessingQueue.DIFFERENCE)){
			if(!storeAndMapExists) whichDetailedMessageRow = FirstProcessingRow.findElement(By.xpath("../tr[2]"));
			else whichDetailedMessageRow = FirstProcessingRow.findElement(By.xpath("../tr[4]"));
		
		}
		else if(DifferenceParseOrFileUpload.equals(ProcessingQueue.PARSE)){
			if(!storeAndMapExists) whichDetailedMessageRow = FirstProcessingRow.findElement(By.xpath("../tr[3]"));
			else whichDetailedMessageRow = FirstProcessingRow.findElement(By.xpath("../tr[5]"));
			
		}
		else if(DifferenceParseOrFileUpload.equals(ProcessingQueue.FILEUPLOAD)){
			if(!storeAndMapExists) whichDetailedMessageRow = FirstProcessingRow.findElement(By.xpath("../tr[4]"));
			else whichDetailedMessageRow = FirstProcessingRow.findElement(By.xpath("../tr[6]"));
		}
		else if (DifferenceParseOrFileUpload.equals(ProcessingQueue.STORE)){
			whichDetailedMessageRow = FirstProcessingRow.findElement(By.xpath("../tr[2]"));
		}
		else if(DifferenceParseOrFileUpload.equals(ProcessingQueue.MAP)){
			whichDetailedMessageRow = FirstProcessingRow.findElement(By.xpath("../tr[3]"));
		}

		if(messageOrStatusOrModified.equals(InfoType.MESSAGE)){
			details = whichDetailedMessageRow.findElement(By.xpath("td[contains(@class,'name-column')]")).getText();
		}
		else if(messageOrStatusOrModified.equals(InfoType.STATUS)){
			details = whichDetailedMessageRow.findElement(By.xpath("td[contains(@class,'status-column']/span")).getText();
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
	
	@FindBy(linkText="Return to List")
	private WebElement ReturnToList;
	public CatalogsPage clickReturnToList(){
		waitForElementToClickable(By.linkText("Return to List"));
		ReturnToList.click();
		return PageFactory.initElements(driver, CatalogsPage.class);
	}
}
