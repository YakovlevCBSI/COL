package com.cbsi.fcat.xmltest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.Assume;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.rules.Timeout;
import org.junit.runners.MethodSorters;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.cbsi.fcat.pageobject.catatlogpage.CatalogsPage;
import com.cbsi.fcat.pageobject.catatlogpage.DetailsPage;
import com.cbsi.fcat.pageobject.catatlogpage.DetailsPage.UploadStatus;
import com.cbsi.fcat.pageobject.catatlogpage.ProductsCatalogPage;
import com.cbsi.fcat.pageobject.foundation.FormBaseTest;
import com.cbsi.fcat.pageobject.foundation.ParameterFeeder;
import com.cbsi.fcat.util.ElementConstants;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class XMLRegressionTest extends FormBaseTest{
	private String ftpUrl = "ftp://C06515:iERUr09w0kN@janus.cnetdata.com/download/ContentCastExport";
	private static List<String> partyCodes = new ArrayList<String>();

	@Rule
	public ExpectedException expect = ExpectedException.none();
	
	public XMLRegressionTest(String URL, String browser){
		super(URL, browser);
	}
	
	@Before
	public void startUp(){
		super.startUp();
		Assume.assumeTrue(getBrowser().contains("chrome"));

	}
	
	@Test
	public void xmltest1_getLenovoFtpFiles() throws InterruptedException{
		driver.get(ftpUrl);
		
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("h1#header")));

		driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
		
		List<WebElement> catalogs = driver.findElements(By.cssSelector("td a.icon.file"));
		for(WebElement e: catalogs){
			System.out.println("name: " + e.getText());
			partyCodes.add("bfp-"+e.getText().split("_")[0]);
		}
		
		//shuffle filenames
		Collections.shuffle(partyCodes, new Random(System.nanoTime()));
	}

	@Test
	public void xmltest2_EditTest(){
		for(int i=0; i<partyCodes.size()/5; i++){
			System.out.println("filename under test: " + partyCodes.get(i));
			ProductsCatalogPage xmlProductCatalogPage = navigateToXmlCatalog(partyCodes.get(i));
			xmlProductCatalogPage.clickEdit().clickCancel();
			
			assertTrue(hasNoError());
			catalogsPage = xmlProductCatalogPage.clickReturnToList();
		}
	}
	
	@Test
	public void xmltest3_AddProductIsDisabled(){
		for(int i=0; i<partyCodes.size()/5; i++){
			ProductsCatalogPage xmlProductCatalogPage = navigateToXmlCatalog(partyCodes.get(i));
			
			expect.expectMessage("InvocationTargetException");
			xmlProductCatalogPage.clickAddProduct();
			
			catalogsPage = xmlProductCatalogPage.clickReturnToList();
		}
	}
	

	@Test
	public void xmltest4_DetailsPageStatusSucessToday(){
		for(int i=0; i<partyCodes.size()/5; i++){
			ProductsCatalogPage xmlProductCatalogPage = navigateToXmlCatalog(partyCodes.get(i));
			DetailsPage detailsPage = xmlProductCatalogPage.clickShowDetails();
			String status = detailsPage.getStatus();
			
			String modifiedDate = detailsPage.getModified().split("\\s+")[0].replaceAll("‑", "");
			String todayDate =  new SimpleDateFormat("yyyyMMdd").format(new Date());
			String yesterdayDate = (Integer.parseInt(todayDate) -1) + "";
	
			assertEquals(UploadStatus.DONE.toString(), status);
			//Check both today and yesterday, incase of the time this test is being ran;
			assertTrue(modifiedDate + " / " + todayDate, todayDate.equals(modifiedDate) || yesterdayDate.equals(modifiedDate));
			
			catalogsPage = detailsPage.clickReturnToList();
		}
	}
	
	public ProductsCatalogPage navigateToXmlCatalog(String partyCode){
		CatalogsPage catalogsPageLenovo = catalogsPage.clickPartyChooserIcon().searchCode(partyCode).pickFromResult();
		ProductsCatalogPage xmlProductCatalogPage = catalogsPageLenovo.gotoCatalogByNameAndSomeNumberOfProducts("Lenovo", 10);
		
		return xmlProductCatalogPage;
	}
	

	

}
