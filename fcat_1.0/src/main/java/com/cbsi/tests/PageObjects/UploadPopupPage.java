package com.cbsi.tests.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cbsi.tests.util.ImageNavigation;

public class UploadPopupPage extends BasePage{
	public UploadPopupPage(WebDriver driver){
		super(driver);
		waitForPageToLoad();
	}
	
	public void waitForPageToLoad(){
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.fileinput-button")));
	}
	
	@FindBy(css="label#lb_ChangeImportSettings")
	private WebElement SetupColumn;
	
	@FindBy(css="label#lb_HeadersInFirstRow")
	private WebElement FirstRow;
	
	@FindBy(css="a.selectBox span.selectBox-arrow")
	private WebElement FileType;
	
	@FindBy(css="input[data-val-required^='Please choose']")
	//@FindBy(css="div.tab-panel div.fileinput-button")
	//@FindBy(linkText="Upload File")
	public WebElement UploadFile;
	
	@FindBy(css="div.tab-panel p a.next-button")
	private WebElement Next;
	
	@FindBy(linkText="Cancel")
	private WebElement Cancel;
	
	public UploadPopupPage clickUploadFile(){
		waitForElementToClickable("input[data-val-required^='Please choose']");
		customWait(5);
	
		Actions actions = new Actions(driver);
		//actions.moveByOffset(100, 0).click().build().perform();
		actions.moveToElement(UploadFile, 1000, 20).click().build().perform();
		return this;
	}
	
	public UploadPopupPage clickNext(){
		waitForElementToClickable("div.tab-panel p a.next-button");
		Next.click();
		return this;
	}
	
	@FindBy(css="div#backButtonContainer a#progressNextButton")
	private WebElement NextAfterUpload;
	public MappingPage clickNextAfterUpload(){
		NextAfterUpload.click();
		return PageFactory.initElements(driver, MappingPage.class);		
	}
	
	@FindBy(css="div.upload-result")
	private WebElement uploadResult;
	
	@FindBy(css="div.progress-upload-text")
	private WebElement progress;
	
	public boolean uploadResult(){
		if(uploadResult.getText().contains("successfully") && progress.getText().contains("100%")){
			return true;
		}
		return false;
	}
	
	public UploadPopupPage uploadFile(String fileName){
		ImageNavigation imageNavigation = new ImageNavigation();
		clickDocuments();
		imageNavigation.clickImageTarget(fileName);
		clickOK();
		return this;
	}
	
	public void clickDocuments(){
		new ImageNavigation().clickImageTarget("Documents");
	}
	
	public void clickOK(){
		new ImageNavigation().clickImageTarget("OK");
	}
	

	public String getProgress(){
		int count =0;
		String status= "";
		long startTime= System.currentTimeMillis();
		while(System.currentTimeMillis() - startTime < 40000){
			if (!status.equals(progress.getText())){
				status = progress.getText();
				System.out.println(status);
				if(status.contains("100")) break;
			}
			count++;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return progress.getText();
	}
	
	public UploadPopupPage uploadLocalFileFromFinder() throws InterruptedException{
		Thread.sleep(1500);
		WebElement fileInput = null;
		try{
			fileInput = driver.findElement(By.cssSelector("input[type='file']"));
			System.out.println("found input type file");
		}catch(NoSuchElementException e){
			System.out.println("didn't find input type file");
		}
		
		fileInput.sendKeys("/Users/alpark/Documents/LondonDrugsTxt.csv");
		
		return this;
	}
}
