package com.cbsi.tests.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class AddCatalogPage extends BasePage {

	public AddCatalogPage(WebDriver driver){
		super(driver);
		waitForPageToLoad();
	}
	
	@Override
	public void waitForPageToLoad(){
		try{
			waitForElementToBeVisible(By.cssSelector("form#editCatalogForm"));
			waitForElementToBeVisible(By.cssSelector("a.x-open-schedule"));
		}catch(TimeoutException e){
			System.out.println("redirect to AddCatalogPage");
		}
	}

	@FindBy(css="div.logo.en-us a.logo-url")
	private WebElement ContentSolutionsHeader;
	
	public boolean isHeaderDisplayed(){
		return driver.findElements(By.cssSelector("div.logo.en-us a.logo-url")).size() != 0;
	}

	@FindBy(css="input#Name") private WebElement name;
	@FindBy(linkText="Next") private WebElement Next;
	
	public UploadPopupPage clickNext(){
		Next.click();
		return PageFactory.initElements(driver, UploadPopupPage.class);

	}
	
	public UploadPopupPage fillInName(){
		System.out.println("filling out catalog name. Next...");
		//customWait(20);
		System.out.println("file created: " +tempFileName);
		name.sendKeys(tempFileName);
		customWait(5);
		Next.click();
		return PageFactory.initElements(driver, UploadPopupPage.class);
	}
	
	public void fillInName(String title){
		System.out.println("filling out catalog name. Next...");
		//customWait(20);
		name.sendKeys(title);
		customWait(5);
		Next.click();
		
		return;
	}
	
	public boolean fieldValidationErrorIsDisplayed(){
		By errorSpanPath = By.cssSelector("span.field-validation-error");
		
		waitForElementToBeVisible(errorSpanPath);
		WebElement errorSpan = driver.findElement(errorSpanPath);
		return errorSpan.isDisplayed();	
	}
	
	//=================================== Automatic components ==========================================//
	
	@FindBy(css="label#lb_UploadMode_Manual")
	private WebElement ManualUpload;
	
	public AddCatalogPage switchToManual(){
		ManualUpload.click();
		return this;
	}
	@FindBy(css="label#lb_UploadMode_Automatic")
	private WebElement Automatic;
	
	public AddCatalogPage switchToAutomatic(){
		Automatic.click();
		return this;
	}
	
	
	@FindBy(css="input#FileLocation_FileUrl")
	private WebElement FileLocation;
	
	@FindBy(css="input#FileLocation_Login")
	private WebElement Username;
	
	@FindBy(css="input#FileLocation_Password")
	private WebElement Password;
	
	public AddCatalogPage typeFileLocation(String fileLocal){
		FileLocation.sendKeys(fileLocal);
		return this;
	}
	public AddCatalogPage typeUserName(String username){
		Username.sendKeys(username);
		return this;
	}
	
	public AddCatalogPage typePassword(String password){
		Password.sendKeys(password);
		return this;
	}
	
	@FindBy(linkText="Set Schedule")
	private WebElement SetSchedule;
	
	public SchedulePopup clickSetSchedule(){
		SetSchedule.click();
		return PageFactory.initElements(driver, SchedulePopup.class);
	}

	@FindBy(css="a.schedule-result-text")
	private WebElement scheduleResult;
	
	public String getScheduleResult(){
		while(scheduleResult.getText().isEmpty()){
			forceWait(500);
			System.out.println("waiting for message...");
		}
		return scheduleResult.getText();
	}
	
	@FindBy(linkText="Save")
	private WebElement Save;
	
	public CatalogsPage clickSave(){
		Save.click();
		return PageFactory.initElements(driver, CatalogsPage.class);
	}
	
	public void clickSaveNone(){
		Save.click();
	}

	@FindBy(css="label#lb_IsDefault")
	private WebElement Default;
	
	public AddCatalogPage clickSetAsDefault(){
		if(!Default.getAttribute("class").contains("checked")){
			Default.click();
		}
		
		return this;
	}
	public AddCatalogPage typeFileAndUserInfoAll(String fileLocation, String username, String password){
		return typeFileLocation(fileLocation).typeUserName(username).typePassword(password);
		
	}
	
	@FindBy(css="div.standalone-radio label[id*='_DoNoUseSchedule']")
	private WebElement DoNotUseSchedule;
	public boolean doNotUseScheduleIsChecked(){
		return DoNotUseSchedule.getAttribute("class").contains("checked");
	}
	
	public AddCatalogPage clickDoNotUseSchedule(){
		DoNotUseSchedule.click();
		return this;
	}
	


}
