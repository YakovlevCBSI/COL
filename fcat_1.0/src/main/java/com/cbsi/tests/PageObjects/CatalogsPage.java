package com.cbsi.tests.PageObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CatalogsPage extends BasePage{
	public CatalogsPage(WebDriver driver) {
		super(driver);
		waitForPageToLoad();
		// TODO Auto-generated constructor stub
		//driver.manage().window().maximize();
		
		//driver.findElement(By.cssSelector("div.header h1"));
		//customWait(20);
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
			new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.panel div.catalogs-index")));
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
	@FindBy(partialLinkText="albert")
	public WebElement myCatalog;

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
	
	public CatalogsPage clickEdit(){
		WebElement Edit = myCatalog.findElement(By.xpath("../../td[6]/a[2]"));
		customWait(3);
		Edit.click();
		return this;
	}
	
	public UploadPopupPage clickUpload(){
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
	
	public CatalogsPage clickCoverageReport(){
		WebElement CoverageReport = myCatalog.findElement(By.xpath("../../td[7]/a[1]"));
		customWait(3);
		CoverageReport.click();
		return this;
	}
	
	protected String tempFileName = System.currentTimeMillis() + "";
	public String getTempFileName(){
		return tempFileName;
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
	
	@FindBy(id="topbar-party-search")
	private WebElement searchParty;
	
	public PartyPopupPage clickSearchParty(){
		searchParty.click();
		return PageFactory.initElements(driver, PartyPopupPage.class);
	}
	
	

}
