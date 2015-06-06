package com.cbsi.tests.PageObjects;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class CatalogsPage extends BasePage{
	public CatalogsPage(WebDriver driver) {
		super(driver);
		waitForPageToLoad();

		//System.out.println("constructor: "  + myCatalog.getText());

	}
	
	//@FindBy(partialLinkText="albert")
	public WebElement myCatalog;
	
	public CatalogsPage setMyCatalogToManualCatalog(){
		List<WebElement> myCatalogs = driver.findElements(By.partialLinkText("albert"));
		
		outerLoop:
			for(WebElement e: myCatalogs){
				if(!e.getText().toLowerCase().contains("ftp")  && !e.findElement(By.xpath("../../td[@class='name-column']/a")).getText().equals("0")){
					this.myCatalog = e;
					System.out.println(e.getText());
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
		System.out.println("looking for mycatralog eleemtn to use");
		List<WebElement> elements = driver.findElements(By.partialLinkText("albert"));
		WebElement elementToUse = null;
		int count=0;
		
		OuterLoop:
		for(WebElement e: elements){
			if(!e.getText().toLowerCase().contains("ftp")){
				elementToUse = e;
				System.out.println("foudn mycatalog element :"+ e.getText() + " \n count:" + count);
				break OuterLoop;
			}
			count++;
		}
		myCatalog = elementToUse;

		
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
	
	public DetailsPage clickDetails(){
		WebElement Info = myCatalog.findElement(By.xpath("../../td[6]/a[1]"));
		customWait(3);
		Info.click();
		return PageFactory.initElements(driver, DetailsPage.class);
	}
	
	public AddCatalogPage clickEdit(){
		WebElement Edit = myCatalog.findElement(By.xpath("../../td[6]/a[2]"));
		customWait(3);
		Edit.click();
		return PageFactory.initElements(driver, AddCatalogPage.class);
	}
	
	public UploadPopupPage clickUpload(){
		System.out.println(myCatalog == null);
		System.out.println("text : " + myCatalog.getText());
		//setMyCatalog();
		WebElement Upload = myCatalog.findElement(By.xpath("../../td[6]/a[3]"));
		//WebElement Upload = myCatalog.findElement(By.xpath("../.."));

		customWait(3);
		Upload.click();
		
		return PageFactory.initElements(driver, UploadPopupPage.class);
	}
	
	public CatalogsPage clickDelete(){
		WebElement Delete = myCatalog.findElement(By.xpath("../../td[6]/a[4]"));
		customWait(3);
		Delete.click();
		return this;
	}
	
	public CoverageReportPage clickCoverageReport(){
		WebElement CoverageReport = myCatalog.findElement(By.xpath("../../td[6]/a[5]"));
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
		System.out.println("Deleting test catalog...");
		CatalogsPage catalogsPage =PageFactory.initElements(driver, CatalogsPage.class);
		List<WebElement> tempElements = driver.findElements(By.cssSelector("tr[data-id] td.name-column a"));
		WebElement tempElement= null;
		if(tempElements == null) throw new IllegalStateException("No Delete File was found");
		for (WebElement e: tempElements){
			if(e.getText().equals(fileToDelete)){
				tempElement = e;
				System.out.println("tempElement: " + tempElement.getText());
				break;
			}
		}
		WebElement tempElementDeleteButton = tempElement.findElement(By.xpath("../../td[6]/a[4]"));
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
		WebElement elementToUse =null;
		for(WebElement e: productNumbers){
			if(Integer.parseInt(e.getText().trim()) > num ){
				elementToUse = e.findElement(By.xpath("../../td[@class='name-column']/a"));
				break;
			}
		}
		elementToUse.click();
		
		return PageFactory.initElements(driver, ProductsCatalogPage.class);
	
	}
	
	public ProductsCatalogPage goToCatalogWithSomeNumberOfProducts(int num1, int num2){
		List<WebElement> productNumbers = driver.findElements(By.cssSelector("td.number-column span"));
		
		WebElement elementToUse =null;
		for(WebElement e: productNumbers){
			//System.out.println(e.getText());
			if(Integer.parseInt(e.getText().trim()) < num2 && Integer.parseInt(e.getText().trim()) > num1 ){
				elementToUse = e.findElement(By.xpath("../../td[@class='name-column']/a"));
				System.out.println(elementToUse.getText());
				break;
			}
		}
		elementToUse.click();
		
		return PageFactory.initElements(driver, ProductsCatalogPage.class);
	
	}
	
	@FindBy(id="topbar-party-search")
	private WebElement searchParty;
	
	@FindBy(linkText="Security")
	private WebElement Security;
	
	@FindBy(linkText="Metadata")
	private WebElement Metadata;
	
	@FindBy(linkText="Manage Catalogs")
	private WebElement ManageCatalogs;
	
	@FindBy(linkText="Catalog Integration Flows")
	private WebElement CatalogIntegrationFlows;
	
	@FindBy(css="#topbar-back-click")
	private WebElement Back;
	
	public PartyPopupPage clickSearchParty(){
		searchParty.click();
		return PageFactory.initElements(driver, PartyPopupPage.class);
	}
	
	public boolean isDefaultCatalog(){
		String defaultText = myCatalog.findElement(By.xpath("../span")).getText();
		return defaultText.toLowerCase().contains("(default)");
	}
	
	public List<String> getCatalogNames(){
		List<String> catalogNamesToString = new ArrayList<String>();
		List<WebElement> catalogNames = driver.findElements(By.cssSelector("td.name-column a"));
		for(WebElement catalogName: catalogNames){
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
			if(StringUtils.isNumeric(catalog)){
				deleteTempFile(catalog);
			}
		
		}
		
		return this;
	}
	
	@FindBy(css="div#topbar-profile div")
	private WebElement userName;
	
	@FindBy(css="a#topbar-logout")
	private WebElement Logout;
	
	public FCatLoginPage goToLogout(){
		userName.click();
		Logout.click();
		return PageFactory.initElements(driver, FCatLoginPage.class);
	}

}
