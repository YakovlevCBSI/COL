package com.cbsi.fcat.pageobject.catatlogpage;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cbsi.fcat.pageobject.foundation.BasePage;
import com.cbsi.fcat.pageobject.homepage.FCatHomePage;
import com.cbsi.fcat.pageobject.homepage.FCatLoginPage;


public class CatalogsPage extends BasePage{
	public final static Logger logger = LoggerFactory.getLogger(CatalogsPage.class);

	public CatalogsPage(WebDriver driver) {
		super(driver);
		waitForPageToLoad();
	}
	
	//@FindBy(partialLinkText="albert")
	public WebElement myCatalog;
	
	public CatalogsPage setMyCatalogToManualCatalog(){
		List<WebElement> myCatalogs = driver.findElements(By.partialLinkText("albert"));
		
		outerLoop:
			for(WebElement e: myCatalogs){
				if(!e.getText().toLowerCase().contains("ftp")  && !e.findElement(By.xpath("../../td[@class='name-column']/a")).getText().equals("0")&& e.getText().toLowerCase().contains("manual")){
					this.myCatalog = e;
					logger.info(e.getText());
					break outerLoop;
				}
			}
		
		return this;
	}
	
	public String getMyCatalogLastLoaded(){
		return myCatalog.findElement(By.xpath("../../td[@class='date-column']/span[@data-utc-year]")).getText();
	}
	
	public CatalogsPage setMyCatalog(String name){
		this.myCatalog = driver.findElement(By.partialLinkText(name));
		
		
		return this;
	}
	
	public void setMyCatalog(){
		logger.info("looking for my catalog element to use");
		List<WebElement> elements = driver.findElements(By.partialLinkText("albert"));
		WebElement elementToUse = null;
		int count=0;
		
		OuterLoop:
		for(WebElement e: elements){
			if(!e.getText().toLowerCase().contains("ftp")){
				elementToUse = e;
				logger.info("found my catalog element :"+ e.getText() + " \n count:" + count);
				break OuterLoop;
			}
			count++;
		}
		myCatalog = elementToUse;
	}
		
	public String getMyCatalog(){
		return myCatalog.getText();
	}

	/**
	 * switched the css path due to catalogs 
	 */
	@Override
	public void waitForPageToLoad(){
		waitForElementToBeVisible(By.cssSelector("div.catalogs-index"));
	}
	
	@FindBy(css="a.link-button.navy")
	private WebElement AddCatalog;
	public AddCatalogPage goToAddCatalog(){
		waitForElementToClickable("a.link-button.navy");
		//customWait(10);
		AddCatalog.click();
		return PageFactory.initElements(driver, AddCatalogPage.class);
	}
	
	/**
	 * Define action buttons on each column.
	 */
	

	
	public CatalogsPage setMyCatalogToAutomaticCatalog(){
		List<WebElement> list = driver.findElements(By.partialLinkText("albert"));
		
		outerLoop:
		for(WebElement e: list){
			if(e.getText().toLowerCase().contains("ftp")){
				myCatalog = e;
				break;
			}
		}
		
		return this;
		
	}
	//public WebElement myCatalog = driver.findElement(By.linkText("albert-test1"));
	/**
	//@FindBy(xpath="//tr/td[@class='actions']/a[1]")
	public WebElement Info = myCatalog.findElement(By.xpath("../../td[6]/a[1]"));

	
	//@FindBy(xpath="//tr/td[@class='actions']/a[2]")
	private WebElement Edit = myCatalog.findElement(By.xpath("../../td[6]/a[2]"));

	//@FindBy(xpath="//tr/td[@class='actions']/a[3]")
	private WebElement Upload = myCatalog.findElement(By.xpath("../../td[6]/a[3]"));
	
	//@FindBy(xpath="//tr/td[@class='actions']/a[4]")
	private WebElement Delete = myCatalog.findElement(By.xpath("../../td[6]/a[4]"));
	
	//@FindBy(xpath="//tr/td[@class='actions']/a[5]")
	private WebElement CoverageReport = myCatalog.findElement(By.xpath("../../td[7]/a[1]"));
*/
	public ProductsCatalogPage goToProductsCatalogPage(){
		customWait(5);
		myCatalog.click();
		return PageFactory.initElements(driver, ProductsCatalogPage.class);
	}
	
	public CatalogsPage clickMyCatalog(){
		customWait(5);
		myCatalog.click();
		//customWait(30);
		return this;
	}
	
	public static int tdAciton = 7; 

	public DetailsPage clickDetails(){
		WebElement Info = myCatalog.findElement(By.xpath("../../td["+tdAciton+"]/div/a[1]"));
		customWait(3);
		Info.click();
		return PageFactory.initElements(driver, DetailsPage.class);
	}
	
	public AddCatalogPage clickEdit(){
		WebElement Edit = myCatalog.findElement(By.xpath("../../td["+tdAciton+"]/div/a[2]"));
		customWait(3);
		Edit.click();
		return PageFactory.initElements(driver, AddCatalogPage.class);
	}
	
	public UploadPopupPage clickUpload(){
		logger.info((myCatalog == null) + "");
		logger.info("text : " + myCatalog.getText());
		//setMyCatalog();
		WebElement Upload = myCatalog.findElement(By.xpath("../../td["+tdAciton+"]/div/a[3]"));
		//WebElement Upload = myCatalog.findElement(By.xpath("../.."));

		customWait(3);
		Upload.click();
		
		return PageFactory.initElements(driver, UploadPopupPage.class);
	}
	
	public CatalogsPage clickDelete(){
		WebElement Delete = myCatalog.findElement(By.xpath("../../td[" +tdAciton+ "]/div/a[4]"));
		customWait(3);
		Delete.click();
		return this;
	}
	
	public CoverageReportPage clickCoverageReport(){
		WebElement CoverageReport = myCatalog.findElement(By.xpath("../../td[" +tdAciton+ "]/div/a[5]"));
		customWait(3);
		CoverageReport.click();
		return PageFactory.initElements(driver, CoverageReportPage.class);
	}
	
	public List<String> getCatalogNamesAsList(){
		List<WebElement> catalogNameList = driver.findElements(By.cssSelector("tbody[id*='catalog-list'] td.name-column a"));
		List<String> catalogNameAsString = new ArrayList<String>();
		
		for(WebElement e: catalogNameList){
			catalogNameAsString.add(e.getText());
		}
		
		return catalogNameAsString;
	}
	
	public CatalogsPage deleteTempFile(String fileToDelete){
		logger.info("Deleting test catalog...");
		CatalogsPage catalogsPage =PageFactory.initElements(driver, CatalogsPage.class);
		List<WebElement> tempElements = driver.findElements(By.cssSelector("tr[data-id] td.name-column a"));
		WebElement tempElement= null;
		if(tempElements == null) throw new IllegalStateException("No Delete File was found");
		for (WebElement e: tempElements){
			if(e.getText().equals(fileToDelete)){
				tempElement = e;
				logger.info("tempElement: " + tempElement.getText());
				break;
			}
		}
		WebElement tempElementDeleteButton = tempElement.findElement(By.xpath("../../td[" +tdAciton+ "]/div/a[4]"));
//		WebElement tempElementDeleteButton = tempElement.findElement(By.xpath("../../td[@class='actions-column']/div/a[4]"));

//		waitForElementToClickable(By.xpath("../../td[6]/a[4]"));
		tempElementDeleteButton.click();
		customWait(5);
		WebElement Yes = driver.findElement(By.linkText("Yes"));
		customWait(5);
		Yes.click();
		waitForElementToBeInvisible(By.cssSelector("div.overlay-header.splash"));
		return this;
		
	}
	
	public ProductsCatalogPage goToCatalogWithSomeNumberOfProducts(int num){
		List<WebElement> productNumbers = driver.findElements(By.cssSelector("td.number-column span"));
		WebElement elementToUse =getCatalogByNameAndProductNumber("albert", num);
		
		if(elementToUse == null)
			elementToUse = getCatalogByNameAndProductNumber("", num);
		
		elementToUse.click();
		
		return PageFactory.initElements(driver, ProductsCatalogPage.class);
	}
	
	public ProductsCatalogPage goToCatalogWithSomeNumberOfProducts(int num1, int num2){
		List<WebElement> productNumbers = driver.findElements(By.cssSelector("td.number-column span"));
		
		WebElement elementToUse =getCatalogByNameAndProductNumber("albert", num1, num2);
		elementToUse.click();
		
		return PageFactory.initElements(driver, ProductsCatalogPage.class);
	}
	
	public ProductsCatalogPage goToCatalogByName(String catalogPartialText){
		getCatalogByNameAndProductNumber(catalogPartialText, 0).click();
		
		return PageFactory.initElements(driver, ProductsCatalogPage.class); 
	}
	
	public ProductsCatalogPage gotoCatalogByNameAndSomeNumberOfProducts(String catalogPartialText, int productNum){
		getCatalogByNameAndProductNumber(catalogPartialText, productNum).click();
		
		return PageFactory.initElements(driver, ProductsCatalogPage.class); 
	}
	
	public WebElement getCatalogByNameAndProductNumber(){
		return getCatalogByNameAndProductNumber("");
	}
	
	public WebElement getCatalogByNameAndProductNumber(String catalogParitalText){
		return getCatalogByNameAndProductNumber(catalogParitalText, 0);
	}
	
	public WebElement getCatalogByNameAndProductNumber(String catalogParitalText, int num1){
		return getCatalogByNameAndProductNumber(catalogParitalText, num1, 99999999);
	}
	
	public WebElement getCatalogByNameAndProductNumber(String catalogParitalText, int num1, int num2){
		List<WebElement> productNumbers = driver.findElements(By.cssSelector("td.number-column span"));
		
		WebElement elementToUse =null;
		for(WebElement e: productNumbers){
			if(Integer.parseInt(e.getText().trim()) < num2 && Integer.parseInt(e.getText().trim()) > num1 ){
				if(e.findElement(By.xpath("../../td[@class='name-column']/a")).getText().contains(catalogParitalText)){
					elementToUse = e.findElement(By.xpath("../../td[@class='name-column']/a"));
					logger.info("my catalog: " + elementToUse.getText());
					return elementToUse;
				}
			}
		}
		return null;
	}
	
	@FindBy(id="topbar-party-search-click")
	private WebElement searchParty;
	
	@FindBy(linkText="Security")
	private WebElement Security;
	
	@FindBy(linkText="Metadata")
	private WebElement Metadata;
	
	@FindBy(linkText="Manage Catalogs")
	private WebElement ManageCatalogs;
	
	@FindBy(linkText="Catalog Integration Flows")
	private WebElement CatalogIntegrationFlows;
	
	@FindBy(linkText="Workflow Dashboard")
	private WebElement WorkflowDashboard;
	
	@FindBy(css="#topbar-back-click")
	private WebElement Back;
	
	public FCatHomePage clickBackIcon(){
		action = getActions();
		action.moveToElement(Back, 12, 10).click();
		action.build().perform();
		return PageFactory.initElements(driver, FCatHomePage.class);
	}
	
	public PartyPopupPage clickPartyChooserIcon(){		
		action = getActions();
		action.moveToElement(searchParty, 12, 10).click();
		action.build().perform();
		return PageFactory.initElements(driver, PartyPopupPage.class);
	}
	
	public boolean isWorkflowDashboardDisplayed(){
		try{ 
			WorkflowDashboard.isDisplayed();
		}catch(NoSuchElementException e){
			return false;
		}
		
		return true;
	}
	
	public PartyPopupPage clickSearchParty(){
		searchParty.click();
		return PageFactory.initElements(driver, PartyPopupPage.class);
	}
	
	public boolean isDefaultCatalog(){
		String defaultText = myCatalog.findElement(By.xpath("../label")).getText();
		return defaultText.toLowerCase().contains("default");
	}
	
	public List<String> getCatalogNames(){
		List<String> catalogNamesToString = new ArrayList<String>();
		refresh();
		List<WebElement> catalogNames = driver.findElements(By.cssSelector("td.name-column a"));
		for(WebElement catalogName: catalogNames){
			System.out.println(catalogName.getText());
			catalogNamesToString.add(catalogName.getText());
		}
		
		return catalogNamesToString;
	}
	
	public CatalogsPage goToManageCatalogs(){
		ManageCatalogs.click();
		return PageFactory.initElements(driver, CatalogsPage.class);
	}
	
	public FCatHomePage goBack(){
		Back.click();
		return PageFactory.initElements(driver, FCatHomePage.class);
	}
	
	public CatalogsPage cleanUpLeftOverCatalogs(){
		List<String> catalogNamesToString= getCatalogNames();
		for(String catalog: catalogNamesToString){
			if(StringUtils.isNumeric(catalog) || catalog.startsWith(getHostUserName())){
				deleteTempFile(catalog);
			}
		}	
		return this;
	}
	
	@FindBy(css="div#topbar-profile div.x-welcome-text")
	private WebElement userName;
	
	@FindBy(css="a#topbar-logout")
	private WebElement Logout;
	
	public FCatLoginPage goToLogout(){
		waitForElementToClickable(By.cssSelector("div#topbar-profile div.x-welcome-text"));
		getActions().moveToElement(userName).build().perform();
		Logout.click();
		return PageFactory.initElements(driver, FCatLoginPage.class);
	}
	
	public int getProductNumberByCatalog(String catalogName){
		List<WebElement> catalogNames = driver.findElements(By.cssSelector("td.name-column a"));
		for(WebElement c:catalogNames){
			logger.info("looking for " + catalogName);
			if(c.getText().equalsIgnoreCase(catalogName)){
				return Integer.parseInt(c.findElement(By.xpath("../../td[@class ='number-column']/span")).getText());
			}
		}
		
		return -1;
	}
	
	public String getMarketByCatalog(String catalog){
		return myCatalog.findElement(By.xpath("../../td[@class='market-column']/span")).getText();
	}	 
}
