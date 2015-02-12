package com.cbsi.tests.PageObjects;

import java.util.ArrayList;
import java.util.List;

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
		try{
			waitForElementToBeVisible(By.cssSelector("div.panel div.catalogs-index"));
		}catch(TimeoutException e){
			System.out.println("Checking if this is addCatalogsPage");
		}
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
	public CatalogsPage clickInfo(){
		WebElement Info = myCatalog.findElement(By.xpath("../../td[6]/a[1]"));
		customWait(3);
		Info.click();
		return this;
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
		WebElement tempElement = driver.findElement(By.linkText(fileToDelete));
		WebElement tempElementDeleteButton = tempElement.findElement(By.xpath("../../td[6]/a[4]"));
		customWait(5);
		tempElementDeleteButton.click();
		
		WebElement Yes = driver.findElement(By.linkText("Yes"));
		customWait(5);
		Yes.click();
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
			if(Integer.parseInt(e.getText().trim()) < num2 && Integer.parseInt(e.getText().trim()) > num1 ){
				elementToUse = e.findElement(By.xpath("../../td[@class='name-column']/a"));
				break;
			}
		}
		elementToUse.click();
		
		return PageFactory.initElements(driver, ProductsCatalogPage.class);
	
	}
	
	@FindBy(id="topbar-party-search")
	private WebElement searchParty;
	
	public PartyPopupPage clickSearchParty(){
		searchParty.click();
		return PageFactory.initElements(driver, PartyPopupPage.class);
	}
	
	

}
