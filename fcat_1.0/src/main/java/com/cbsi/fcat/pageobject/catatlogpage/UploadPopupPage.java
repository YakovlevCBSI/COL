package com.cbsi.fcat.pageobject.catatlogpage;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cbsi.fcat.pageobject.foundation.BasePage;
import com.cbsi.fcat.util.ImageNavigation;

public class UploadPopupPage extends BasePage{
	public final static Logger logger = LoggerFactory.getLogger(UploadPopupPage.class);

	public UploadPopupPage(WebDriver driver){
		super(driver);
		waitForPageToLoad();
//		logger.debug("wait for page load done.");
		waitForTextToBeVisible("Upload Catalog", "div.overlay-header.dialog");

		forceWait(500);
	}
	
	public void waitForPageToLoad(){
		//new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.fileinput-button")));
		new WebDriverWait(driver, 40).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("p.link-button-bar")));
		customWait(5);
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
	
	private static final String CancelPath = "p a.x-cancel";
	@FindBy(css=CancelPath)
	private WebElement Cancel;

	@FindBy(linkText="Sample File")
	private WebElement SampleFile;
	
	private WebElement Title;

	private WebElement Cross;
	
	@FindBy(css="div.overlay-upload-catalog-body.dialog")
	private WebElement dialogBody;
		
	public void clickCancel(){
		waitForElementToBeVisible(Cancel);
		Cancel.click();
		waitForElementToBeInvisible(By.cssSelector(CancelPath));
	}
	
	public String getSampleFileUrl(){
		return SampleFile.getAttribute("href");
	}
	
	public UploadPopupPage clickSampleFile(){
		SampleFile.click();
		return this;
	}
	
	public UploadPopupPage clickHasHeader(){
		FirstRow.click();
		return this;
	}
	
	public boolean isTitleDisplayed(){
		Title = dialogBody.findElement(By.xpath("../div[contains(@class, 'overlay-header dialog')]"));	
		return Title.isDisplayed() && !Title.getText().isEmpty();
	}
	
	public boolean isCrossDisplayed(){
		Cross = dialogBody.findElement(By.xpath("../div[contains(@class, 'header-close-button')]"));
		return Cross.isDisplayed();
	}
	
	public UploadPopupPage clickUploadFile(){
		waitForElementToClickable("input[data-val-required^='Please choose']");
		customWait(5);
	
		Actions actions = new Actions(driver);
		//actions.moveByOffset(100, 0).click().build().perform();
		actions.moveToElement(UploadFile, 800, 20).click().build().perform();
		return this;
	}
	
	public UploadPopupPage clickNext(){
		waitForElementToClickable("div.tab-panel p a.next-button");
		Next.click();
		return this;
	}
	
	@FindBy(css="#uploadFileForm > div.tabs.inside-dialog > div.tab-panel > p > a.link-button.green")
	private WebElement GetFile;
	public UploadPopupPage clickGetFile(){
		waitForElementToClickable(By.cssSelector("#uploadFileForm > div.tabs.inside-dialog > div.tab-panel > p > a.link-button.green"));
		forceWait(1000);
		GetFile.click();
		return this;
	}
	
	@FindBy(css="div#backButtonContainer a#progressNextButton")
	private WebElement NextAfterUpload;
	public BasePage clickNextAfterUpload(boolean noMappingDefined){
		//customWait(30);
		waitForQuickLoad(30);		
		NextAfterUpload.click();
		if(noMappingDefined){
			return PageFactory.initElements(driver, MappingPage.class);		
		}
		else{
			return PageFactory.initElements(driver, DetailsPage.class);
		}
	}
	
	public String getMessage(){
		waitForElementToBeVisible(By.cssSelector("div.upload-result"));
		return driver.findElement(By.cssSelector("div.upload-result")).getText();
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
	
	@FindBy(css="label#lb_Full_File")
	private WebElement FullFile;
	public UploadPopupPage checkFullFile(){
		if(!FullFile.isSelected()){
			FullFile.click();
		}
		
		return this;
	}
	
	public boolean isFullFile(){
		return FullFile.isSelected();
	}
	
	@FindBy(css="lb_ChangeImportSettings")
	private WebElement SetUpColumnMapping;
	public void clickSetUpColumnMapping(){
		if(!SetUpColumnMapping.isEnabled()){
			SetUpColumnMapping.click();
		}
	}
	
	@FindBy(css="div.tab-panel div a.selectBox span.selectBox-arrow")
	private WebElement dropbox;
	
	@FindBy(css="li a[rel='Comma']")
	private WebElement CSV;
	
	@FindBy(css="li a[rel='Tab']")
	private WebElement TXT;
	
	@FindBy(css="li a[rel='Excel']")
	private WebElement Excel;
	
	@FindBy(css="li a[rel='Xml']")
	private WebElement XML;
	
	public UploadPopupPage selectDropBoxOption(UploadType type){
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dropbox.click();
		quickWait();
		if(type == UploadType.CSV) CSV.click();
		else if(type == UploadType.TXT) TXT.click();
		else if(type == UploadType.EXCEL) Excel.click();
		else if(type == UploadType.XML) XML.click();
		
		return this;
	}
	
	@FindBy(css="a.selectBox span.selectBox-label")
	private WebElement selectedFileType;
	
	public UploadType getFileType(){
		String fileType = selectedFileType.getText();
		
		if(fileType.equalsIgnoreCase(UploadType.CSV.toString())) return UploadType.CSV;
		else if(fileType.toUpperCase().contains(UploadType.TXT.toString())) return UploadType.TXT;
		else if(fileType.toUpperCase().contains(UploadType.EXCEL.toString())) return UploadType.EXCEL;
		else if(fileType.toUpperCase().contains(UploadType.XML.toString())) return UploadType.XML;

		return null;
	}
	
	public enum UploadType{
		CSV,
		TXT,
		EXCEL,
		XML		
	}

//	public String getProgress(){
//		int count =0;
//		String status= "";
//		long startTime= System.currentTimeMillis();
//		while(System.currentTimeMillis() - startTime < 40000){
//			if (!status.equals(progress.getText())){
//				status = progress.getText();
//				logger.info(status);
//				if(status.contains("100")) break;
//			}
//			count++;
//			try {
//				Thread.sleep(500);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//		return progress.getText();
//	}
	
	public void waitForProgress(){
		waitForQuickLoad(30);

	}
	
	public UploadPopupPage uploadLocalFileFromFinder() throws InterruptedException{
		return uploadLocalFileFromFinder("big");
	}
	
	public UploadPopupPage uploadLocalFileFromFinder(String BigOrSmall) throws InterruptedException{
		Thread.sleep(1500);
		WebElement fileInput = null;
		try{
			fileInput = driver.findElement(By.cssSelector("input[type='file']"));
			logger.info("found input type file");
		}catch(NoSuchElementException e){
			logger.info("didn't find input type file");
		}
		
		String pathToFile ="";
		if(BigOrSmall.equals("small")){
			pathToFile = System.getProperty("user.dir")  + "/src/test/resources/Catalogs/smallLondon.csv";
			if(isGrid){
				pathToFile = "/home/slave/Documents/Catalogs/smallLondon.csv";
			}

		}else{
			pathToFile = System.getProperty("user.dir")  + "/src/test/resources/Catalogs/London.csv";
			if(isGrid){
				pathToFile = "/home/qe/Documents/Catalogs/London.csv";
//				pathToFile = "/home/qe/Documents/London.csv";

			}
		}
		
		logger.info(pathToFile);
		fileInput.sendKeys(pathToFile);
		
		return this;
	}
	public UploadPopupPage uploadLocalFileFromResource(String fileName) throws InterruptedException{
		forceWait(1500);
		WebElement fileInput = null;
		
		try{
			fileInput = driver.findElement(By.cssSelector("input[type='file']"));
			logger.info("found input type file");
		}catch(NoSuchElementException e){
			logger.info("didn't find input type file");
		}
		
		String pathToFile = System.getProperty("user.dir")  + "/src/test/resources/Catalogs/"+fileName;
		
		if(isGrid){
			pathToFile = "/home/qe/Documents" + fileName;
		}
		
		logger.info(pathToFile);
		fileInput.sendKeys(pathToFile);
		
		//fileInput.sendKeys(Keys.RETURN);
		
		return this;
	}
	

}
