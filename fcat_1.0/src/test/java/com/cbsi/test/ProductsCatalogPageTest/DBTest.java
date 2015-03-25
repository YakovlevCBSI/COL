package com.cbsi.test.ProductsCatalogPageTest;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.tests.FCatMongoObject.Product;
import com.cbsi.tests.FCatSqlObject.Catalog;
import com.cbsi.tests.FcatDB.MongoConnector;
import com.cbsi.tests.FcatDB.MySQLConnector;
import com.cbsi.tests.Foundation.StageBaseTest;
import com.cbsi.tests.PageObjects.CatalogsPage;
import com.cbsi.tests.PageObjects.DetailsPage;
import com.cbsi.tests.PageObjects.EditProductPopupPage;
import com.cbsi.tests.PageObjects.MappingPage;
import com.cbsi.tests.PageObjects.ProductsCatalogPage;
import com.cbsi.tests.util.CatalogReader;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DBTest extends StageBaseTest{
	
	@Rule 
	public Timeout globalTimeout = new Timeout(300000);
	
	public DBTest(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}
	/**
	 * How this goes down.....
	 * 
	 * create a new catalog
	 * run query on my sql to find out partyid and catid.
	 * run mongo query to find out products information.
	 * open the catalog on fcat.
	 * matche these validations
	 * @throws  
	 */
	
	private static boolean pass= true;
	@After
	public  void cleanup(){

	}
	
	private static String thisTempFile = "";
	
	@Test
	public void a_addACatalog() throws InterruptedException{
	
		try{
			MappingPage mappingPage = UploadFullFile();
			thisTempFile = tempFile;
			System.out.println("file: " +  thisTempFile);
			DetailsPage detailsPage = mappingPage.automap();
				
				//mappingPage.forceWait(5);

			System.out.println("catName: " + thisTempFile);
			pass = true;
			
			return;
			
		}catch(Exception e){
			System.out.println("failed on adding a catalog...");
			System.out.println("deleting a catalog...");
			e.printStackTrace();
			System.out.println(" end of stacktrace.");
			cleanUpThenDeleteTemp();
			
		}
		
		Assert.fail();
	}
	
	private static String catalogId = "";
	private static String partyId = "";
	
	@Test             
	public void b_runQueryonMysql(){
		if(!pass) Assert.fail("a_addCatalog Failed. Rest tests are aborted.");
		
		System.out.println(getQuery());
		MySQLConnector sql = new MySQLConnector().connectToFcatDB();
		List<Catalog> catalogObject = sql.runQuery(getQuery(), Catalog.class, true);
		sql.quit();
		
		catalogId = catalogObject.get(0).getId();
		partyId = catalogObject.get(0).getParty() + "";
		
		System.out.println(catalogId + " / " + partyId);
		assertTrue(!catalogId.isEmpty() && !partyId.isEmpty());
	}
	
	private static List<Product> productsFromMongo;
	
	@Test
	public void c_runQueryOnMongo(){
		if(!pass) Assert.fail("a_addCatalog Failed. Rest tests are aborted.");

		MongoConnector mongo = new MongoConnector().connectToMongo();
		mongo.queryFor("item", new String[]{"catId: " + catalogId, "partyId: " + partyId}, 10);
		productsFromMongo = mongo.turnQueryIntoProductList();

		assertTrue(!productsFromMongo.isEmpty());
	}
	
	private static List<Product> productsFromFile;
	
	@Test
	public void d_fileMatchesMongo() throws Exception{
		if(!pass) Assert.fail("a_addCatalog Failed. Rest tests are aborted.");
		MongoConnector mongo = new MongoConnector().connectToMongo();
		mongo.queryFor("item", new String[]{"catId: " + catalogId, "partyId: " + partyId}, 10);
		productsFromMongo = mongo.turnQueryIntoProductList();
		
		//==========Delete above--->
		
		StringBuilder sb = new CatalogReader().readFrom("src/test/resources/Catalogs/London.csv");
		productsFromFile = new CatalogReader().convertRawDataToProducts(sb, true);
		
		assertTrue(twoListsAreEqual(cleanFieldsNotFoundInFile(productsFromMongo), (productsFromFile)));
	}
	
	@Test
	public void e_mongoMatchesProductsOnFront() throws Exception{
		if(!pass) Assert.fail("a_addCatalog Failed. Rest tests are aborted.");
		
		MongoConnector mongo = new MongoConnector().connectToMongo();
		mongo.queryFor("item", new String[]{"catId: " + catalogId, "partyId: " + partyId}, 10);
		productsFromMongo = mongo.turnQueryIntoProductList();
		
		//==========Delete above--->
		CatalogsPage catalogsPage = PageFactory.initElements(driver, CatalogsPage.class);
		catalogsPage.setMyCatalog(thisTempFile);
		ProductsCatalogPage productPage = catalogsPage.goToProductsCatalogPage();
		List<Product> productsFromWeb = turnProductsTableIntoObject(productPage);
		
		System.out.println(productsFromMongo.size()+ " / "   +productsFromWeb.size() );
		
		assertTrue(twoListsAreEqual(cleanFiledsNotFoundInWebView(productsFromMongo), productsFromWeb));
	}
	
	@Test
	public void f_mongoMatchesContentsAttributesOnFront() throws InterruptedException{
		if(!pass) Assert.fail("a_addCatalog Failed. Rest tests are aborted.");
		
		MongoConnector mongo = new MongoConnector().connectToMongo();
		mongo.queryFor("item", new String[]{"catId: " + catalogId, "partyId: " + partyId}, 10);
		productsFromMongo = mongo.turnQueryIntoProductList();
		
		//---mongo work above ----//
		System.out.println("tempfile: " + thisTempFile);
		Thread.sleep(3000);
		ProductsCatalogPage productCatalog = catalogsPage.setMyCatalog(thisTempFile).goToProductsCatalogPage();
		List<WebElement> productList = driver.findElements(By.cssSelector("tbody#product-table-body tr td[class^='product-id']"));
		
		List<Product> productsFromFront = new ArrayList<Product>();
		for(WebElement e: productList){
			System.out.println(e.getText());
			Product product = new Product();
			EditProductPopupPage editPopup = (EditProductPopupPage) productCatalog.setProductToUse(e.getText()).clickAction("edit");
			editPopup.setData();

			product.setId(editPopup.getProductId());
			product.setMf(editPopup.getManufacturerName());
			product.setMfpn(editPopup.getManufacturerPartNumber());
			product.setCentSkuId(editPopup.getCnetSkuId());
			//product.setProductUrl(editPopup.getProductURL());
			product.setInventory(editPopup.getInventory());
			product.setPrice(editPopup.getPrice());
			product.setMsrp(editPopup.getMsrp());
			product.setUpcEan(editPopup.getUpcEan());
			
			productsFromFront.add(product);
			
			editPopup.clickCancel();
			Thread.sleep(500);
			
		}
		assertTrue(twoListsAreEqual(cleanFiledsNotFoundInEditPopup(productsFromMongo), productsFromFront));
	}
	
	@Test
	public void g_deleteTemp(){
		if(!pass) Assert.fail("a_addCatalog Failed. Rest tests are aborted.");
		cleanUpThenDeleteTemp();
	}
	
	public String getQuery(){
		return "select * from catalog where catalog_name =" + thisTempFile;
	}
	
	public List<Product>  cleanFieldsNotFoundInFile(List<Product> mongo){

			for(Product p:mongo){
				p.setMap(0);
				p.setLocale("");
				p.setContentType("");

			}
			
			return mongo;
		
	}
	
	public List<Product> cleanFiledsNotFoundInWebView(List<Product> mongo){
		for(Product p:mongo){
			p.setLocale("");
			p.setContentType("");
			p.setMsrp("");
			p.setUpcEan("");
			p.setPrice("");
			p.setProductUrl("");
			p.setInventory("");
			p.setCentSkuId("");
			p.setUpcEan("");
		}
		return mongo;
	}
	
	public List<Product> cleanFiledsNotFoundInEditPopup(List<Product> mongo){
		for(Product p:mongo){
			p.setMap(0);
			p.setLocale("");
			p.setContentType("");
		}
		return mongo;
	}
	
	public List<Product> sortList(){
		return null;
	}
	
	public List<Product> turnProductsTableIntoObject(ProductsCatalogPage productPage){
		List<WebElement> dataRows = productPage.getDataRows();
		System.out.println("dataRow size: " + dataRows.size());
		List<Product> products = new ArrayList<Product>();
		for(WebElement dr: dataRows){
			Product product = new Product();
			Map<String,String> map = productPage.dataRowToProductObject(dr);
			
			for(Map.Entry<String, String> entry: map.entrySet()){
				if(entry.getKey().equals("product id")){
					product.setId(entry.getValue());
				}
				else if(entry.getKey().equals("manufacturer name")){
					product.setMf(entry.getValue());
				}
				else if(entry.getKey().equals("part number")){
					product.setMfpn(entry.getValue());
				}
				else if(entry.getKey().equals("map")){
					if(entry.getValue().toLowerCase().equals("mapped")){
						product.setMap(1);
					}
					else{
						product.setMap(0);
					}
					
				}
			}
			
			products.add(product);
			
		}
		return products;
	}
}
