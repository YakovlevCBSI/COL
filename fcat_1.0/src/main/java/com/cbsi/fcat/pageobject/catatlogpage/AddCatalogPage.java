package com.cbsi.fcat.pageobject.catatlogpage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cbsi.fcat.pageobject.foundation.BasePage;


public class AddCatalogPage extends BasePage {
	public final static Logger logger = LoggerFactory.getLogger(AddCatalogPage.class);

	public AddCatalogPage(WebDriver driver){
		super(driver);
		waitForPageToLoad();
//		waitForTextToBeVisible("Properties", "h1");
		logger.info("Done loading addCatalogs page.");
	}
	
	@Override
	public void waitForPageToLoad(){
		waitForPageToLoad(By.cssSelector("#editCatalogForm > div.content.catalog > div.clearfix > div:nth-child(2) > div:nth-child(2)"));
//		customWait(10);
//		waitForElementToBeVisible(By.cssSelector("div.editor-field-indent input"));
//		waitForElementToBeVisible(By.cssSelector("a.x-open-schedule"));
	}

	@FindBy(css="div.logo.en-us a.logo-url")
	private WebElement ContentSolutionsHeader;
	
	public boolean isHeaderDisplayed(){
		return driver.findElements(By.cssSelector("div.logo.en-us a.logo-url")).size() != 0;
	}

	@FindBy(css="input#Name") 
	private WebElement name;
	
	@FindBy(linkText="Next") 
	private WebElement Next;
	
	@FindBy(css="select[name='CountryMarket']") 
	private WebElement Market;
	
	@FindBy(css="label[for='FullFile']")
	private WebElement FullFile;
	
	public AddCatalogPage setFullFile(){
		FullFile.click();
		return this;
	}
	
	public UploadPopupPage clickNext(){
		Next.click();
		return PageFactory.initElements(driver, UploadPopupPage.class);

	}
	
	public AddCatalogPage clickNextFail(){
		forceWait(500);
		Next.click();
		return this;
	}
	
	public UploadPopupPage fillInName(){
		logger.info("filling out catalog name. Next...");
		//customWait(20);
		logger.info("file created: " +tempFileName);
		name.sendKeys(tempFileName);
		customWait(20);
		Next.click();
		return PageFactory.initElements(driver, UploadPopupPage.class);
	}
	
	public void fillInName(String title){
		logger.info("filling out catalog name. Next...");
		//customWait(20);
		name.sendKeys(title);
		customWait(5);
		Next.click();
		
		return;
	}
	
	public AddCatalogPage setMarket(String country){
		forceWait(500);
		WebElement dropdown = Market.findElement(By.xpath("../a"));
		dropdown.click();
		
		WebElement countryFromDropdown = driver.findElement(By.cssSelector("ul.selectBox-dropdown-menu li a[rel='" + getCodeByCountry(country) + "']"));
		
		waitForElementToBeVisible(countryFromDropdown);
		countryFromDropdown.click();
		return this;
	}
	
	public List<String> getMarkets(){
		List<WebElement> list = Market.findElements(By.cssSelector("option"));
		List<String> listToString = new ArrayList<String>();
		
		for(WebElement e: list){
			listToString.add(e.getText());
		}
		
		return listToString;	
	}
	
	public boolean fieldValidationErrorIsDisplayed(){
		By errorSpanPath = By.cssSelector("span.field-validation-error");
		
		waitForElementToBeVisible(errorSpanPath);
		WebElement errorSpan = driver.findElement(errorSpanPath);
		return errorSpan.isDisplayed();	
	}
	
	public String[] countries = new String[] {"Belgium (Dutch)", "El Salvador", "Switzerland (English)", "United Kingdom", "World Wide"};
	public String[] code = new String[] {"NL-BEL", "SLV", "EN-CHE","GBR", "WW"};
	
	public String pickRandomMarket(){
		int randomNumber = new Random().nextInt(countries.length);
		return countries[randomNumber];
	}
	
	public String getCodeByCountry(String country){
		int index=-1;
		for(int i=0; i<countries.length; i++){
			if(countries[i].equals(country)){
				index = i;
				break;
			}
		}
		
		return code[index];
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
		scrollToView(Automatic);
		Automatic.click();
		return this;
	}
	
	
	@FindBy(css="input#FileLocation_FileUrl")
	private WebElement FileLocation;
	
	@FindBy(css="input#FileLocation_Login")
	private WebElement Username;
	
	@FindBy(css="input#FileLocation_Password")
	private WebElement Password;
	
	public AddCatalogPage setFileLocation(String fileLocal){
		waitForElementToBeVisible(FileLocation);
		FileLocation.clear();
		FileLocation.sendKeys(fileLocal);
		return this;
	}
	public AddCatalogPage setUserName(String username){
		waitForElementToBeVisible(Username);
		Username.sendKeys(username);
		return this;
	}
	
	public AddCatalogPage setPassword(String password){
		Password.sendKeys(password);
		return this;
	}
	
	@FindBy(linkText="Set Schedule")
	private WebElement SetSchedule;
	
	public SchedulePopup clickSetSchedule(){
		scrollToView(SetSchedule);
		SetSchedule.click();
		return PageFactory.initElements(driver, SchedulePopup.class);
	}

	@FindBy(css="a.schedule-result-text")
	private WebElement scheduleResult;
	
	public String getScheduleResult(){
		while(scheduleResult.getText().isEmpty()){
			forceWait(500);
			logger.info("waiting for message...");
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
		forceWait(300);
		logger.info("Before condition check.");
		//WebElement tempDefault = driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/form/div[1]/div[1]/div[1]/div[9]/div[1]/label[1]"));
		scrollToView(Default);
		if(!Default.getAttribute("class").contains("checked")){
			logger.info("Not checked...");
			Default.click();
		}
		logger.debug("out of loop.");
		
		return this;
	}
	public AddCatalogPage setFileAndUserInfoAll(String fileLocation, String username, String password){
		return setFileLocation(fileLocation).setUserName(username).setPassword(password);
		
	}
	
	@FindBy(css="div.standalone-radio label[id*='_DoNoUseSchedule']")
	private WebElement DoNotUseSchedule;
	public boolean doNotUseScheduleIsChecked(){
		return DoNotUseSchedule.getAttribute("class").contains("checked");
	}
	
	public AddCatalogPage clickDoNotUseSchedule(){
		forceWait(500);
		DoNotUseSchedule.click();
		return this;
	}
	
	@FindBy(css="span[data-valmsg-for='Name'][class^='field-validation'] span")
	private WebElement CatalogNameIsRequired;
	public boolean displaysCatalogNameError(){
		return CatalogNameIsRequired.isDisplayed();
	}
	
	@FindBy(css="span[data-valmsg-for='Code'][class^='field-validation'] span")
	private WebElement CatalogCodeIsRequired;
	public boolean displaysCatalogCodeError(){
		return CatalogCodeIsRequired.isDisplayed();
	}
	
	@FindBy(css="span[data-valmsg-for='FileLocationFileUrl'][class^='field-validation'] span")
	private WebElement FtpLocationIsRequired;
	public boolean displaysFtpLocationError(){
		return FtpLocationIsRequired.isDisplayed();
	}

	@FindBy(css="span[data-valmsg-for='FileLocationLogin'][class^='field-validation'] span")
	private WebElement FtpUserNameIsRequired;
	public boolean displaysFtpUserNameError(){
		return FtpUserNameIsRequired.isDisplayed();
	}
	
	@FindBy(css="span[data-valmsg-for='FileLocationPassword'][class^='field-validation'] span")
	private WebElement FtpPasswordIsRequired;
	public boolean displaysFtpPasswordError(){
		return FtpPasswordIsRequired.isDisplayed();
	}
}
