package com.cbsi.tests.PageObjects;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
	
	@FindBy(linkText="Cancel")
	private WebElement Cancel;

	private WebElement Title;

	private WebElement Cross;
	
	private static boolean topbarIsConfigured = false;
	
	public boolean isTitleDisplayed(){
		if(!topbarIsConfigured){
			WebElement dialogBody = driver.findElement(By.cssSelector("div.overlay-upload-catalog-body.dialog"));
			Cross = dialogBody.findElement(By.xpath("../div[contains(@class, 'header-close-button')]"));
			Title = dialogBody.findElement(By.xpath("../div[contains(@class, 'overlay-header dialog')]"));
			
			topbarIsConfigured = true;
		}
		
		return Title.isDisplayed() && !Title.getText().isEmpty();
	}
	
	public boolean isCrossDisplayed(){
		if(!topbarIsConfigured){
			WebElement dialogBody = driver.findElement(By.cssSelector("div.overlay-upload-catalog-body"));
			Cross = dialogBody.findElement(By.xpath("../div[contains(@class, 'header-close-button')]"));
			Title = dialogBody.findElement(By.xpath("../div[contains(@class, 'overlay-header dialog')]"));
			
			topbarIsConfigured = true;
		}
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
		waitForElementToBeVisible(By.cssSelector("div#backButtonContainer a#progressNextButton"));
		NextAfterUpload.click();
		if(noMappingDefined){
			return PageFactory.initElements(driver, MappingPage.class);		
		}
		else{
			return PageFactory.initElements(driver, DetailsPage.class);
		}
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
	
	public UploadPopupPage selectDropBoxOption(String option){
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dropbox.click();
		quickWait();
		if(option.equals("CSV")) CSV.click();
		else if(option.equals("TXT")) TXT.click();
		else if(option.equals("Excel")) Excel.click();
		
		return this;
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
		
		String pathToFile = System.getProperty("user.dir")  + "/src/test/resources/Catalogs/London.csv";
		if(isGrid){
			pathToFile = "/home/slave/Documents/Catalogs/London.csv";
		}
		
		
		System.out.println(pathToFile);
		fileInput.sendKeys(pathToFile);
		
		//fileInput.sendKeys(Keys.RETURN);
		
		return this;
	}
	
	public UploadPopupPage uploadLocalFileFromResource(String fileName) throws InterruptedException{
		Thread.sleep(1500);
		WebElement fileInput = null;
		try{
			fileInput = driver.findElement(By.cssSelector("input[type='file']"));
			System.out.println("found input type file");
		}catch(NoSuchElementException e){
			System.out.println("didn't find input type file");
		}
		
		String pathToFile = System.getProperty("user.dir")  + "/src/test/resources/Catalogs/"+fileName;
		if(isGrid){
			pathToFile = "/home/slave/Documents/Catalogs/" + fileName;
		}
		
		System.out.println(pathToFile);
		fileInput.sendKeys(pathToFile);
		
		//fileInput.sendKeys(Keys.RETURN);
		
		return this;
	}
	
	public UploadPopupPage uploadLocalFileFromFinder(String BigOrSmall) throws InterruptedException{
		Thread.sleep(1500);
		WebElement fileInput = null;
		try{
			fileInput = driver.findElement(By.cssSelector("input[type='file']"));
			System.out.println("found input type file");
		}catch(NoSuchElementException e){
			System.out.println("didn't find input type file");
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
				pathToFile = "/home/slave/Documents/Catalogs/London.csv";
			}
		}
		
		
		System.out.println(pathToFile);
		fileInput.sendKeys(pathToFile);
		
		//fileInput.sendKeys(Keys.RETURN);
		
		return this;
	}
}
