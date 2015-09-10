package com.cbsi.tests.PageObjects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.tests.util.ElementConstants;


public class ProductsCatalogPage extends BasePage{
	public ProductsCatalogPage(WebDriver driver){
		super(driver);
		waitForPageToLoad();

	}
	
	@Override
	public void waitForPageToLoad(){
		waitForPageToLoad(By.cssSelector("table[class*='catalog-product-list']"));
	}
	@FindBy(css="a.nav-button.next")
	private WebElement goRight;
	
	@FindBy(css="div#page-number-container-low")
	private WebElement currentPage;
	public ProductsCatalogPage clickGoRight(){
		String pageNum = currentPage.getText();
		goRight.click();
		
		long startTime = System.currentTimeMillis();
		while(System.currentTimeMillis() - startTime < 45000){
			if(!pageNum.equals(refreshStaleElement(By.cssSelector("div#page-number-container-low")).getText())){
				break;
			}
		}
		
		if(pageNum.equals(refreshStaleElement(By.cssSelector("div#page-number-container-low")).getText())){
			throw new NoSuchElementException("Page was still loading...");
		}
		
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return PageFactory.initElements(driver, ProductsCatalogPage.class);
	}
	
	
	@FindBy(css="div.page-button-bar div.link-button-bar a")
	private WebElement ReturnToList;
	
	public CatalogsPage clickReturnToList(){
		waitForElementToClickable("a.link-button.gray");
		ReturnToList.click();
		return PageFactory.initElements(driver, CatalogsPage.class);
	}
	
	@FindBy(css="div[title='Not mapped']")
	private WebElement NotMappedIcon;
	
	@FindBy(css="div[title='Mapped']")
	private WebElement MappedIcon;
	
	@FindBy(css="a#download-catalog span")
	private WebElement Download;
	
	@FindBy(css="a#show-details")
	private WebElement ShowDetails;
	
	@FindBy(css="div.edit")
	private WebElement Edit;
	
	public DetailsPage clickShowDetails(){
		ShowDetails.click();
		return PageFactory.initElements(driver, DetailsPage.class);
	}
	
	private static WebElement productRow;
	public ProductsCatalogPage setProductToUse(String productName){
//		this.productRow = driver.findElement(By.cssSelector("tbody#product-table-body tr[data-id='" + escapeHtml(productName.toUpperCase()) + "']"));
		productRow = null;
		List<WebElement> productRows = driver.findElements(By.cssSelector("tbody#product-table-body tr td[class='product-id-column']"));
		for(WebElement singleRow: productRows){
			if(singleRow.getText().toLowerCase().equals(productName.toLowerCase())){
				productRow = singleRow.findElement(By.xpath(".."));
				break;
			}
		}
		
		if(productRow == null){
			System.out.println("navigate to next page.");
			clickGoRight();
			setProductToUse(productName);
		}
		
		if(productRow == null){
			throw new NullPointerException("Unable to find the product by pid " + productName);
		}
		return this;
	}
	
	public boolean isProductRowMapped(){
		//for compound xpath class name, make sure to use @contains!!! otherwise it never finds the element.
		forceWait(3000);
		return this.productRow.findElement(By.xpath("td[contains(@class,'actions')]/a/div")).getAttribute("title").toLowerCase().equals("mapped");
	}
	
	public BasePage clickAction(String actionButtonName){
		if(actionButtonName.toLowerCase().equals(ElementConstants.MAP)){
			productRow.findElement(By.xpath("td[contains(@class,'state actions')]/a[1]")).click();
			return PageFactory.initElements(driver, MapProductsDialog.class);
		}
		else if(actionButtonName.toLowerCase().equals(ElementConstants.EDIT)){
			productRow.findElement(By.xpath("td[contains(@class,'state actions')]/a[2]")).click();
			return PageFactory.initElements(driver, EditProductPopupPage.class);
		}
		else if(actionButtonName.toLowerCase().equals(ElementConstants.DELETE)){
			productRow.findElement(By.xpath("td[contains(@class,'state actions')]/a[3]")).click();
			
		}
		else{
			System.err.println("parameter takes " + "\"map\" or \"edit\" or \"delete\".");
		}
		
		return this;
	}
	
	
	
	//----------------------stats, pageRowSelector, stickyButtons and textbox elements-------------------//

	@FindBy(xpath="//table[@class='statistics-table']/tbody/tr[1]/td[1]")
	public WebElement TotalProducts;
	
	@FindBy(xpath="//table[@class='statistics-table']/tbody/tr[1]/td[2]")
	public WebElement TotalProductsValue;
	
	@FindBy(xpath="//table[@class='statistics-table']/tbody/tr[2]/td[1]")
	public WebElement Mapped;
	
	@FindBy(xpath="//table[@class='statistics-table']/tbody/tr[2]/td[2]")
	public WebElement MappedValue;
	
	@FindBy(xpath="//table[@class='statistics-table']/tbody/tr[3]/td[1]")
	public WebElement NotMapped;
	
	@FindBy(xpath="//table[@class='statistics-table']/tbody/tr[3]/td[2]")
	public WebElement NotMappedValue;
	
	
	@FindBy(css="input#product-id")
	public WebElement ProductIDInput;
	
	@FindBy(css="input#manufacturer-name")
	public WebElement ManufacturerNameInput;
	
	@FindBy(css="input#manufacturer-pn")
	public WebElement ManufacturerParNumberInput;
	
	@FindBy(css="input#upcean")
	public WebElement UpcEanInput;
	
	
	@FindBy(css="div.page-rows-selector a span.selectBox-label")
	public WebElement pageSelector;
	
	@FindBy(css="div#page-number-container-up.page-number-container")
	public WebElement pageNumber;
	
	
	@FindBy(css="span.icon.download")
	public WebElement DownloadIcon;
	
	@FindBy(css="span.icon.upload")
	public WebElement UploadIcon;
	
	@FindBy(css="a#add-product")
	public WebElement AddProductIcon;
	
	@FindBy(css="span.icon.info")
	public WebElement ShowDetailsIcon;
	
	@FindBy(css="span.icon.edit")
	public WebElement EditFileIcon;
	
	
	//----------------------------- Table Header-------------------------//
	
	@FindBy(css="th.product-id-column")
	public WebElement productIdColumn;
	
	@FindBy(css="th.manufacturer-name-column")
	public WebElement manufacturerColumn;
	
	@FindBy(css="th.part-number-column")
	public WebElement partNumberColumn;
	
	@FindBy(css="th.upcean-column")
	public WebElement upcEanColumn;
	
	@FindBy(css="th.mapped-column.actions")
	public WebElement mappedColumn;
	
	public AddProductPopup clickAddProduct(){
		AddProductIcon.click();
		return PageFactory.initElements(driver, AddProductPopup.class);
	}
	
	//---------------------Search fields-----------------------------//

	public ProductsCatalogPage searchFor(String whichType, String searchText){
		if(whichType.toLowerCase().equals("pid")){
			ProductIDInput.sendKeys(searchText);
		}
		else if(whichType.toLowerCase().equals("mf")){
			ManufacturerNameInput.sendKeys(searchText);
		}
		else if(whichType.toLowerCase().equals("mfpn")){
			ManufacturerParNumberInput.sendKeys(searchText);
		}
		else{
			UpcEanInput.sendKeys(searchText);
		}
		
		return this;
	}
	
	public void waitForSearch() throws Exception{
		waitForSearch(15000);
	}
	
	public void waitForSearch(long timeInMilli) throws Exception{
		By splashImage = By.cssSelector("div.splash-image");
		
		long start = System.currentTimeMillis();
		
		//incase the search is quick, and splash image never comes up.
		try{
			waitForElementToBeVisible(splashImage);
		}catch(TimeoutException e){
			
		}
		
		while((System.currentTimeMillis() - start) <= 15000){
			waitForElementToBeInvisible(splashImage, 1000);
			
			if(!driver.findElement(splashImage).isDisplayed()) 
				break;
		}
		
		if(driver.findElement(splashImage).isDisplayed()){
			throw new Exception("Search time is taking too long");
		}
		
		return;
	}
	
	///---------------------Table row to object---------------------//
	@FindBy(css="tbody#product-table-body")
	private WebElement productTBody;
	
	public List<WebElement> getDataRows(){
		return productTBody.findElements(By.xpath("tr"));
	}
	
	public Map<String, String> dataRowToProductObject(WebElement dataTr){
		Map<String, String> map = new HashMap<String, String>();
		map.put("product id", dataTr.findElement(By.xpath("td[starts-with(@class, 'product-id')]")).getText());
		map.put("manufacturer name", dataTr.findElement(By.xpath("td[starts-with(@class, 'manufacturer-name')]")).getText());
		map.put("part number", dataTr.findElement(By.xpath("td[starts-with(@class, 'part-number')]")).getText());
		map.put("manufacturer name", dataTr.findElement(By.xpath("td[starts-with(@class, 'manufacturer-name')]")).getText());
		map.put("map", dataTr.findElement(By.xpath("td[@class='state actions']/a[1]/div")).getAttribute("title"));
		
		return map;
	}
	
	public Map<String, String> getProductValue(int nLine){
		WebElement nRow = getDataRows().get(nLine-1);
		return dataRowToProductObject(nRow);
	}
	
	public EditProductPopupPage clickEdit(){
		Edit.click();
		return PageFactory.initElements(driver, EditProductPopupPage.class);
	}
	
	public UploadPopupPage clickUploadFile(){
		UploadIcon.click();
		return PageFactory.initElements(driver, UploadPopupPage.class);
	}
	
	public ProductsCatalogPage clickDownload(){
		Download.click();
		return this;
	}
	private String rowThatWasMapped;
	public MapProductsDialog clickNotMappedOrMappedIcon(){
		try{
			/*
			 * **/
			rowThatWasMapped = MappedIcon.findElement(By.xpath("../../../td[@class='product-id-column']")).getText();
			MappedIcon.click();
		}catch(Exception e){
			System.out.println("couldn't find mapped icon, finding not mapped instead.");
			rowThatWasMapped = NotMappedIcon.findElement(By.xpath("../../../td[@class='product-id-column']")).getText();
			System.out.println("icon: " + rowThatWasMapped);
			NotMappedIcon.click();
		}
		return PageFactory.initElements(driver, MapProductsDialog.class);
	}
	
	public ProductsCatalogPage hoverOverMappedOrNotMappedIcon(){
		try{
			Actions action = new Actions(driver);
			action.moveToElement(MappedIcon).build().perform();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			MappedIcon = driver.findElement(By.cssSelector("div[title='Mapped']"));
			System.out.println(MappedIcon.getCssValue("color"));
		}catch(NoSuchElementException e){
			e.printStackTrace();
		}
		
		return this;
	}
	
	public String getRowThatWasMapped(){
		//System.out.println(row);
		return rowThatWasMapped;
	}
	
	public boolean isRowMapped(By rowThatWasMapped){
		return driver.findElement(rowThatWasMapped).getAttribute("title").toLowerCase().equals("mapped");
	}
	public ProductsCatalogPage mapUnmappedItem(String searchText, int nthResult){
		MapProductsDialog mapProductsDialog = clickNotMappedOrMappedIcon();
		mapProductsDialog.searchName(searchText);
		mapProductsDialog.selectAnItemFromResult(nthResult);
		System.out.println("item selected");
		mapProductsDialog.clickSave();

		return this;
		
	}
	
	public ProductsCatalogPage exitWithoutSaveOnMapppingDialog(){
		MapProductsDialog mapProductsDialog = clickNotMappedOrMappedIcon();
		mapProductsDialog.clickSave();
		return this;
	}
	
	
	public boolean isTableCorrectlyRendered(){
		try{
			driver.findElement(By.cssSelector("table.catalog-product-list tbody tr td"));
		}catch(NoSuchElementException e){
			return false;
		}
		return true;
	}
	
	public boolean actionIconsRenderCorrectly(){
		List<WebElement> iconList = driver.findElements(By.cssSelector("tr td.actions"));
		String gpicon= "gp-icon ";
		
		int count=0;
		for(WebElement e: iconList){
			
			String checkOrPlus = e.findElement(By.xpath("a[1]/div")).getAttribute("class");
			if(!(checkOrPlus.equals(gpicon + "check") || checkOrPlus.equals(gpicon + "plus"))){
				System.out.println("check " + checkOrPlus  + "/ " + count);
				return false;
			}
			
			String edit = e.findElement(By.xpath("a[2]/div")).getAttribute("class");
			if(!(edit.equals(gpicon +"edit"))){
				System.out.println("edit: " + edit + "/ " + count);
				return false;
			}
			
			String trash = e.findElement(By.xpath("a[3]/div")).getAttribute("class");
			if(!(trash.equals(gpicon + "trash"))){
				System.out.println("trash; " + trash + "/ " + count);
				return false;
			}
			
			count++;
		}
		
		return true;
	}
	
	//-----------------------------------Delete Catalog dialog-----------------------------------------//
	
	@FindBy(linkText="Yes")
	private WebElement Yes;
	public void clickYes(){
		waitForElementToBeVisible(By.linkText("Yes"));
		Yes.click();
	}
	
	
}
