package com.cbsi.fcat.pageobject.catatlogpage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.fcat.pageobject.foundation.BasePage;
import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;

public class MappingPage extends BasePage{
	public MappingPage(WebDriver driver) {
		super(driver);
		waitForPageToLoad();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void waitForPageToLoad(){
		waitForPageToLoad(By.cssSelector("div.panel div.content"));
	}
	
	//---------------------Define mapping dropdown ---------------//
	
	public DetailsPage automap(){
		return automap(false, false);
	}
	
	//---------------------Automap w/ multi params=-------//
	public DetailsPage automap(boolean isUpcEanMappingOnly, boolean isSkuIdMappingOnly){
		forceWait(1500);
		List<WebElement> headers = collectHeaders();
		int scrollCount = 1;
		for(WebElement e: headers){
			//System.out.println("header: " + e.getText());
			String selectThisOption ="";
			try {
				selectThisOption = getMatchingCNETFields(e.getText().trim(), isUpcEanMappingOnly, isSkuIdMappingOnly);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				//System.out.println("auto map failed at automap method...");
				e1.printStackTrace();
			}
			//System.out.println(e.getText() + " matches -> selected option: " + selectThisOption);
				
			WebElement dropdown = e.findElement(By.xpath("../td[contains(@class,'fields-column')]/a/span[@class='selectBox-label']"));
			dropdown.click();
			customWait(5);
			
			List<WebElement> dropdownSelections = driver.findElements(By.cssSelector("ul.selectBox-dropdown-menu li"));
			//System.out.println("dropdownselections size: " + dropdownSelections.size());
			boolean matchIsFound = false;
			
			int yAxis =200;
			for(WebElement matchingElement: dropdownSelections){
				if(matchingElement.isDisplayed()){
					WebElement aElement = matchingElement.findElement(By.xpath("a"));
					if(aElement.getText().toLowerCase().equals(selectThisOption)){
						//System.out.println(selectThisOption);
						try{
							aElement.click();
						}catch(WebDriverException ew){
							System.out.println("element failed to click:" + selectThisOption);
							System.out.println("scrolling x-axis to gain view.");
							
							scrollToView(dropdown);
							dropdown.click();
							forceWait(500);
							customWait(5);
							aElement.click();
						}
						
						matchIsFound = true;
						//System.out.println(aElement.getText() + " was clicked.");
						//System.out.println(50*scrollCount);
						
						break;
						
					}
				}
				yAxis+=200;
			}
			if(!matchIsFound){
					dropdown.click();
				}		
		}
		
		scrollToView(Save);
		clickSave();
		
		return PageFactory.initElements(driver, DetailsPage.class);

	}
	//------------------------------------------------------------------------------------//
	
	public List<WebElement> collectHeaders(){
		List<WebElement> headers = driver.findElements(By.cssSelector("tr.highlighted td.file-headers-column"));
		System.out.println("header size; " + headers.size());
		return headers;
	}
	
	public List<WebElement> collectDataPreviews(){
		List<WebElement> dataPreviews = driver.findElements(By.cssSelector("tr.highlighted td.data-preview-column"));
		return dataPreviews;
	}
	
	private static String[] id = {"product id", "id", "customerpn", "partnumber", "part number", "pn"};
	private static String[] mfpn = {"manufacturer part number", "mfrpn", "part number", "manufacturer", "mfpn"};
	private static String[] mf = {"manufacturer name", "mf", "name", "manufacturer", "mfn", "mfrname"};
	private static String[] cnetSkuId={"cnet sku id", "cnetSkuId", "cnetproductid"}; 
	private static String[] upcean = {"upc/ean", "upcean", "upc", "ean"};
	private static String[] msrp = {"msrp", "msr"};
	private static String[] price = {"price", "sell price"};
	private static String[] inventory = {"inventory"};
	//private static String[] productURL = {"product url", "product", "url"};
	
	static List<String[]> headerMap = new ArrayList<String[]>(Arrays.asList(id, mfpn, mf, cnetSkuId, upcean, msrp, price, inventory/*, productURL*/));
	
	public static String getMatchingCNETFields(String clientHeader, boolean isUpcEanMappingOnly, boolean isSkuMappingOnly) throws Exception{
		boolean hasMatch = false;
		String matchword ="";
		
		for(String[] sArray: headerMap){
			for(String s:sArray){
				if(clientHeader.toLowerCase().equals(s)){
					matchword = sArray[0];
					hasMatch=true;
				}
			}
		}
		if(!hasMatch){
			for(String[] sArray: headerMap){
				int numOfMatch = 0;
				for(String s:sArray){
					if(clientHeader.toLowerCase().contains(s)){
						numOfMatch++;
					}
				}
				
				if((numOfMatch/(double)sArray.length) >= 0.5){
					matchword = sArray[0];
					hasMatch=true;
				}
			//	System.out.println(sArray[0] + " / " + numOfMatch);
			}
		}
		if(!hasMatch){
			//throw new Exception("no matching word was found.  Auto Mapping will fail now....");
			System.out.println("no automapping found for " + clientHeader);
		}
		
		if(isUpcEanMappingOnly){
			if(!matchword.isEmpty() && (matchword.equals(mfpn[0]) || matchword.equals(mf[0]) || matchword.equals(cnetSkuId[0]))){
				matchword="";
			}
		}
		if(isSkuMappingOnly){
			if(!matchword.isEmpty() && (matchword.equals(mfpn[0]) || matchword.equals(mf[0]) || matchword.equals(upcean[0]))){
				matchword="";
			}
		}
		
		return matchword;
	}
	
	public MappingPage setCnetField(CNetFields value, int n){
		WebElement dropdown = driver.findElement(By.cssSelector("table.catalog-mapping-table tr:nth-child(" + n + ") td a"));
		scrollToView(dropdown);
		dropdown.click();
		
		forceWait(500);
		List<WebElement> dropdownSelections = driver.findElements(By.cssSelector("ul.selectBox-dropdown-menu li a[rel='" + value.toString() + "']"));
		for(WebElement dropdownSelection: dropdownSelections){
			if(dropdownSelection.isDisplayed()) {
				dropdownSelection.click(); 
				break;
			}
		}
		
		
		return this;
	}
	
	public enum CNetFields{
		ProductId{
			public String toString(){
				return "id";
			}
		},
		ManufacturerName{
			public String toString(){
				return "mf";
			}
		},
		ManufacturerPartNumber{
			public String toString(){
				return "mfPn";
			}
		},
		Price{
			public String toString(){
				return "price";
			}
		},
		ProductNameOrDescription{
			public String toString(){
				return "productName";
			}
		},
		AddToCartURL{
			public String toString(){
				return "addToCartUrl";
			}
		}	
	}
	
	public static void main(String[] args){
		
		String match="";
		try {
			 match = getMatchingCNETFields("cnetproductid", false, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(match);
		
		

	}
	
	@FindBy(css="a#save_mappings_button")
	private WebElement Save;
	public MappingPage clickSave(){
		waitForElementToBeVisible("a#save_mappings_button");
		Save.click();
		return this;
	}
	
	public boolean isSaveDisabled(){
		System.out.println("save value; "+ Save.getAttribute("href").trim().toLowerCase());
		return Save.getAttribute("class").trim().toLowerCase().contains("disabled");
	}
	
	@FindBy(css="td.cnet-fields-encoding a span.selectBox-label")
	private WebElement DefaultFileEncoding;
	
	/**
	 * All elements that are in dropdown box of fileencoding has rel value assigned.
	 * Get these elements and return as List.
	 * @return
	 */
	public List<WebElement> getFileEncodingElements(){
		List<WebElement> fileEncodingTexts = new ArrayList<WebElement>();
		fileEncodingTexts.add(DefaultFileEncoding);
		DefaultFileEncoding.click();
		
		List<WebElement> dropdownTexts = driver.findElements(By.cssSelector("ul li a"));

		for(WebElement e: dropdownTexts){
			if(!e.findElement(By.xpath("../a")).getText().isEmpty()){
				fileEncodingTexts.add(e);
			}
		}
	
		return fileEncodingTexts;
		
	}
	
	/**
	 * If list contains elements with styles that are different from one anther
	 * return false;
	 * @param elements
	 * @return
	 */
	public boolean areElementsSameStyles(List<WebElement> elements){
		//String color ="";
		String fontWeight ="";
		String fontFamily ="";
		String fontSize = "";
		int count = 0;
		for(WebElement e: elements){
		
			if(		(//e.getCssValue("color") != color||
					!(e.getCssValue("font-weight").equals(fontWeight) ||
					e.getCssValue("font-family").equals(fontFamily) ||
					e.getCssValue("font-size").equals(fontSize))) &&
					count >= 1
				)
					  
			{
				System.out.println("=====================");
				System.out.println(e.getCssValue("color") );
				System.out.println(e.getCssValue("font-weight"));
				System.out.println(e.getCssValue("font-family"));
				System.out.println(e.getCssValue("font-size") );
				System.out.println("=====================");
				return false;
			}
			
			System.out.println(count + " / " + e.getText() );
			System.out.println(e.getCssValue("color") );
			System.out.println(e.getCssValue("font-weight"));
			System.out.println(e.getCssValue("font-family"));
			System.out.println(e.getCssValue("font-size") );
			//color = e.getCssValue("color");
			fontWeight = e.getCssValue("font-weight");
			fontFamily = e.getCssValue("font-family");
			fontSize = e.getCssValue("font-size");
			count++;  
		}
		
		
		return true;
	}
	
	public List<String> getHeaders(){
		List<String> headersAsString = new ArrayList<String>();
		
		for(WebElement header:collectHeaders()){
			headersAsString.add(header.getText());
			System.out.println("header: " + header.getText());
		}
		
		return headersAsString;
	}
	
	public List<String> getDataPreviews(){
		List<String> dataPreviews = new ArrayList<String>();
		
		for(WebElement e: collectDataPreviews()){
			dataPreviews.add(e.getText());
		}
		
		return dataPreviews;
	}
	
	
}