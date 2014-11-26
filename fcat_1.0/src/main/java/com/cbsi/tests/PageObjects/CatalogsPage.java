package com.cbsi.tests.PageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
	
	@Override
	public void waitForPageToLoad(){
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.panel div.header")));
	}
	@FindBy(css="a.link-button.navy")
	private WebElement AddCatalog;
	public AddCatalogPage goToAddCatalog(){
		//customWait(20);
		AddCatalog.click();
		
		return PageFactory.initElements(driver, AddCatalogPage.class);
	}
	
	/**
	 * Define action buttons on each column.
	 */
	@FindBy(partialLinkText="albert")
	private WebElement myCatalog;
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
		WebElement Upload = myCatalog.findElement(By.xpath("../../td[6]/a[3]"));
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
	
	public CatalogsPage deleteTempFile(String fileToDelete){
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
	
	
	
}
