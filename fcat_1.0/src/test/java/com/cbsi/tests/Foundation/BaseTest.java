package com.cbsi.tests.Foundation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.rules.Timeout;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.tests.FCatMongoObject.Product;
import com.cbsi.tests.FCatSqlObject.Catalog;
import com.cbsi.tests.PageObjects.AddCatalogPage;
import com.cbsi.tests.PageObjects.BFPLoginPage;
import com.cbsi.tests.PageObjects.CatalogsPage;
import com.cbsi.tests.PageObjects.EmbedPage;
import com.cbsi.tests.PageObjects.FCatHomePage;
import com.cbsi.tests.PageObjects.FCatLoginPage;
import com.cbsi.tests.PageObjects.MappingPage;
import com.cbsi.tests.PageObjects.ProductsCatalogPage;
import com.cbsi.tests.PageObjects.UploadPopupPage;
import com.cbsi.tests.util.GlobalVar;

public class BaseTest {
	public WebDriver driver;
	
	private String browser;
	private String URL;
	
	private String chromeDriverVersion = System.getProperty("chromedriver-version", "2.18");
	public boolean isGrid = System.getProperty("useGrid", "false").equals("true") ;
	private String username = System.getProperty("user.name");
	public boolean screenShotCreated = false;

	
	public BaseTest(String URL, String browser){
		this.URL = URL;
		this.browser = browser;
	}
	
	/**
	 * passing parameters to sub test classes.
	 * @return
	 */
	
	public String getBrowser() { return browser; }
	public String getURL() { return URL; }
	
	//Need Time out rule here
	@Rule 
	public Timeout globalTimeout = new Timeout(180000);
	
	/**
	@BeforeClass
	public static void configureSetup(){
		try {
			ReadFile.setGlobalVars();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/

	protected CatalogsPage catalogsPage;
	
	@Before
	public void startUp(){
		insertHeader();
		driver = configureDrivers();
		navigateToHomePage();
		maximizeWindow();
//		setDisplayToVm();
	}
	
	public void startUpWithoutLogin(){
		insertHeader();
		driver = configureDrivers();
	}
	
	public void insertHeader(){
		
		String headerText = "testName: " + testInfo.getMethodName() + "\tURL: " + getURL() + "\tBrowser: " + getBrowser();
		String separator = new String(new char[headerText.length()]).replace("\0", "-");
		System.out.println(separator + "\n" + headerText + "\n" + separator);
	}
	
	public WebDriver configureDrivers(){
		WebDriver emptyDriver = null;
		System.out.println("staring conditions");
		if(!isGrid){
			if(getBrowser().contains("chrome")){
				System.out.println("in chromecondition");
				emptyDriver = getChromeDriver();
			}else if(getBrowser().contains("internet explorer")){
				System.out.println("in ie condition");
				emptyDriver = getIEDriver();
			}
			else{
				System.out.println("in firefox conditions");
				try{
					FirefoxProfile profile = new FirefoxProfile();
					emptyDriver = new FirefoxDriver(profile);
				}catch(Exception e){
					System.out.println("Failed to create a firefox driver");
					e.printStackTrace();
				}
				
			}
		}
		else{
			try {
				emptyDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), getBrowser().contains("chrome")?getChromeCapability():getFirefoxCapability());
//				emptyDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), getFirefoxCapability());
//				emptyDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), getChromeCapability());

			
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return emptyDriver;
	}
	
	public void goHome(){
		driver.get(getURL());
		FCatHomePage fcatHomePage = PageFactory.initElements(driver, FCatHomePage.class);
		catalogsPage = fcatHomePage.goToCatalogs();
	}
	//Navigate to Catalogs page(HOMePAGe)
	public void navigateToHomePage(){
		driver.get(getURL());
		if(getURL().contains(embedPath)){
			//do nothing
			catalogsPage = PageFactory.initElements(driver, CatalogsPage.class);
		}
		else if (getURL().contains(BFP) || getURL().contains(BFP.replace("http://", ""))){
			EasyLoginToBFP();
		}
		else{
			EasyLoginToLocal();
		}
	}
	
	//Navigate to Login pagedirectly.  For phx and stage formbased login.

	public void navigateToLoginPage(){
		driver.get(getURL());
		if(getURL().contains(embedPath)){
			//do nothing
		}
		else if (getURL().contains(BFP)){
			EasyLoginToBFP();
		}
		else{
			EasyLoginToLoginPage();
		}
	}
	
	public void navigateToLoginPage(String username, String pw){
		driver.get(getURL());
		if(getURL().contains(embedPath)){
			//do nothing
		}
		else if (getURL().contains(BFP)){
			return;
		}
		else{
			EasyLoginToFcat(username, pw);
		}
	}


	//private String pathToChromeDriver = "/Users/alpark/Documents/workspace/fcat_1.0/src/test/resources/Drivers/chromedriver";

	public WebDriver getChromeDriver(){
		String pathToChromeDriver = System.getProperty("user.dir") + "/src/test/resources/Drivers/Chrome/" + chromeDriverVersion +"/";
		
		//Checking mac or Linux condition here for chromedriver.
		if(System.getProperty("os.name").toLowerCase().contains("mac")){
			pathToChromeDriver = pathToChromeDriver + "chromedriver_mac32";
		}
		else{
			pathToChromeDriver = pathToChromeDriver + "chromedriver_linux32";
			//pathToChromeDriver = "/usr/local/bin/chromedriver_linux32";
		}
		
		System.setProperty("webdriver.chrome.driver", pathToChromeDriver);
		
		// Adding logging caps to chromedriver.  This is required for reading console messages.
		DesiredCapabilities caps = DesiredCapabilities.chrome();
		LoggingPreferences logprefs = new LoggingPreferences();
		logprefs.enable(LogType.BROWSER, Level.ALL);
		caps.setCapability(CapabilityType.LOGGING_PREFS, logprefs);

		return new ChromeDriver(caps);
		
		
		//Capabilities caps = DesiredCapabilities.chrome();
		//return new RemoteWebDriver(caps);
	}
	
	public Capabilities getChromeCapability(){
		DesiredCapabilities caps = DesiredCapabilities.chrome();
		caps.setBrowserName("chrome");
//		caps.setPlatform(Platform.LINUX);
		caps.setCapability("platform", Platform.LINUX);
		caps.setVersion("43");
		
		System.err.println("getting chrome caps..");
		return caps;
	}
	
	public Capabilities getFirefoxCapability(){
		DesiredCapabilities caps = DesiredCapabilities.chrome();
		caps.setBrowserName("firefox");
		caps.setPlatform(Platform.LINUX);
		caps.setVersion("38");
		
		return caps;
	}
	
	public String browserStackHub = "http://" + GlobalVar.BSId + ":" + GlobalVar.BSAccessKey + "@hub.browserstack.com/wd/hub";
	
	//Using IE on browserStack.
	public WebDriver getIEDriver(){
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("browser", "IE");
		caps.setCapability("os", "Windows");
		caps.setCapability("os_version", "7");
		caps.setCapability("browserstack.debug", "true");
		caps.setCapability("browserstack.local", "true");
		//caps.setCapability("browserstack.localIdentifier", "Test123");
		String version = "";
		
		if(getBrowser().contains("11")){	
			version = "11.0";
		}
		else if(getBrowser().contains("10")){
			version = "10.0";
		}
		
		caps.setCapability("browser_version", version);	
		
		//System.out.println("bsh: " + browserStackHub);
		try {
			driver = new RemoteWebDriver(new URL(browserStackHub), caps);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			System.err.println("Failed to connect to browser stack hub...");
			e.printStackTrace();
		}
		
		//Not needed as of now.
		//driver.manage().deleteCookieNamed("JSESSIONID");
		return driver;
	}
	
	@After
	public void cleanUp(){
		
		//screenshot
		//run again for false positive
	}

	private String BFP =  GlobalVar.BFPServer;//need to login using their credentials.
	private String embedPath = GlobalVar.embedPath;
	


	
	public void maximizeWindow(){
		driver.manage().window().maximize();
	}
	
	public void setDisplayToVm(){
		driver.manage().window().setSize(new Dimension(1024, 768));
	}
	
	public void EasyLoginToLocal(){
		FCatLoginPage loginPage = PageFactory.initElements(driver, FCatLoginPage.class);
		FCatHomePage homePage = loginPage.loginToHomePage();
		catalogsPage = homePage.goToCatalogs();
		return;

	}
	
	public FCatHomePage EasyLoginToLoginPage(){
		FCatLoginPage loginPage = PageFactory.initElements(driver, FCatLoginPage.class);
		FCatHomePage homePage = loginPage.loginToHomePage();
		return homePage;
	}
	
	public void EasyLoginToBFP(){
		BFPLoginPage bfpLoginPage = PageFactory.initElements(driver, BFPLoginPage.class);
		catalogsPage = bfpLoginPage.loginToHomePage();
		return;
	}
	
	public FCatLoginPage EasyLoginToFcat(String username, String pw){
		FCatLoginPage loginPage = PageFactory.initElements(driver, FCatLoginPage.class);
		return loginPage.loginToHomePage(username, pw);
		
	}
	
	public EmbedPage GotoEmbedPage(){
		return PageFactory.initElements(driver, EmbedPage.class);
		
	}
	
	public void cleanUpThenDeleteTemp(){
		for(String tempFile: tempFiles){
			System.out.println(tempFile.length());
			if(!tempFiles.isEmpty() || tempFiles != null){
				System.out.println();
				System.out.println("Delete Temp in Actions");
				System.out.println("----------------------------");
	
				//takeScreenshot();
				//driver.quit();
				startUp();
				
				try{
					catalogsPage.deleteTempFile(tempFile);
				} catch(Exception e){
					System.out.println("failed to delete temp file...");
					e.printStackTrace();
				}
			}
		}
	
	}
	
	public void cleanUpThenDeleteTemp(List<String> tempFiles){
		if(!tempFiles.isEmpty() || tempFiles != null){
			for(String tempFile: tempFiles){
					System.out.println();
					System.out.println("Delete Temp in Actions");
					System.out.println("----------------------------");
		
					//takeScreenshot();
					//driver.quit();
					startUp();
					
					try{
						CatalogsPage catalogsPage = PageFactory.initElements(driver, CatalogsPage.class);
						catalogsPage.deleteTempFile(tempFile);
					} catch(Exception e){
						System.out.println("failed to delete temp file...");
						e.printStackTrace();
					}
			}
		}
	
	}
	
	@Rule
	public TestName testInfo = new TestName();
	/**
	@Ignore
	public void takeScreenshot()  {
	       // System.out.println("Taking screenshot");
			String testName= this.getClass().getName();
			String methodName = testInfo.getMethodName();
	
	       // new File("target/surefire-reports/screenshot/").mkdirs(); 
			String filename = "target/surefire-reports/"+testName+"/" + methodName +"." + getURL().replace("/", "_")+ ".png";
	        
	        try {
	        	driver = new Augmenter().augment(driver);
	            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	            FileUtils.copyFile(scrFile, new File(filename), true);
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println("Error capturing screen shot of " + testName + " test failure.");
	        }

	        System.out.println("Saving screenshot for "  + testName + "." + getURL());
	        
	}
*/
	public void takeScreenshot(){
		if(!screenShotCreated){
			String testName= this.getClass().getName();
			String methodName = testInfo.getMethodName();
			String filename = "target/surefire-reports/"+testName+"/" + methodName +"." + getURL().replace("/", "_") + "." + getBrowser()+ ".png";
	
			File srcFile= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	//		driver = new Augmenter().augment(driver);
	//		File srcFile= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	
			try {
				FileUtils.copyFile(srcFile, new File(filename));
			} catch (IOException e) {
				// TODO Auto-generated catch block 
				e.printStackTrace();
			}
			screenShotCreated = true;
		}
	}

	//rerun failed test for false-positive cases.
	@Rule
	public Retry retry = new Retry(isAutoRun()?2:1);
//	public Retry retry = new Retry(2);

//	@Rule
//	public ScreenshotRule screenshotRule = new ScreenshotRule();
	
	//----------------------------Innerclass Rule for fail-try---------------------/
	
	class Retry implements TestRule {
        private int retryCount;
        
        public Retry(int retryCount) {
            this.retryCount = retryCount;
        }

        public Statement apply(Statement base, Description description) {
            return statement(base, description);
        }

        private Statement statement(final Statement base, final Description description) {
            return new Statement() {
                @Override
                public void evaluate() throws Throwable {
                    Throwable caughtThrowable = null;

                    // implement retry logic here
                    for (int i = 0; i < retryCount; i++) {
                        try {
                            base.evaluate();
                            return;
                        } catch (Throwable t) {
                            caughtThrowable = t;
                            System.err.println(description.getDisplayName() + ": run " + (i+1) + " failed");
                            
                            //second try failed. Take a screenshot.
                            if(i+1 ==2){
                            	System.err.println("Taking a screenshot");
                            	takeScreenshot();
                            }
                        } finally{
                        	 if(driver!=null){
                        		 System.out.println("quit the driver");
                             	driver.quit();
                             }  
                        }
                    }
                    if(isAutoRun()){
                    	System.out.println("killed all driver instances");
        				runCommand("killall firefox");
        				runCommand("killall chrome");
        			}
                    System.err.println(description.getDisplayName() + ": giving up after " + retryCount + " failures");
                    throw caughtThrowable;
                }
            };
        }
	}
	
	//--------------------------------Screenshot inner class ------------------------------//

	class ScreenshotRule extends TestWatcher{
		@Override
		protected void failed(Throwable e, Description description){
//			takeScreenshot();
			if(driver != null){
				driver.quit();
			}
			//System.out.println(driver == null);
			
		}
	
		@Override
		protected void finished(Description description){
			if(driver != null){
				driver.quit();
			}
			if(isAutoRun()){
				runCommand("killall firefox");
				runCommand("killall chrome");
			}
		}
	}

	public void runCommand(String command){
		Process process=null;
		try {
			process = Runtime.getRuntime().exec(command);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			process.destroy();
		}
	}
	
	public boolean isAutoRun(){
		if(username.equals("jenkins") || username.contains("slave")) return true;
		return false;
	}
	//------------------------------------------common methods-----------------------------------//
	
	/**
	 * This was for reading console error on firefox browser.  Keep it and see if this works.
	 */
	public boolean hasNoError(){

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Firefox is disable temporarily because javascriptError pacakage needs to be added separately for maven.
		/**
		if(getBrowser().contains("firefox")){
			
			/**List<JavaScriptError> jsErrors = JavaScriptError.readErrors(driver);
			
			if(!jsErrors.isEmpty()){
				for(JavaScriptError e: jsErrors){
					System.out.println("console message: " + e.getConsole());
					System.out.println("js error message:" + e.getErrorMessage());
				}
			
			
				return false;
			}
			String js = (String) ((JavascriptExecutor)driver).executeScript("return window.javascript_errors ");
			System.out.println("message:" + js);
			
		}
		 */
		//For Chrome this also might work w/ firefox. Haven't tested for firefox browser yet, since 
		//our error message catcher catches firefox error that shows up on console.
		if(getBrowser().contains("chrome") || getBrowser().contains("firefox")){
			LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
			for(LogEntry e: logEntries){
				if(e.getLevel().toString().contains("SEVERE") ){
					//System.out.println(e.getLevel());
					System.err.println(e.toString());
					return false;
				}
			}
			
		}
		
		//IE will return true since, there is no method to read console.
		
		return true;
	}
	
	public UploadPopupPage uploadLocalFileOSSpecific(UploadPopupPage uploadPopupPage){
		if(getBrowser().contains("chrome") || getBrowser().contains("firefox")){
			try {
				uploadPopupPage.uploadLocalFileFromFinder();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
				uploadPopupPage.uploadLocalFileFromFinder("C:\\Users\\hello\\Documents\\documents\\text-sample1.txt");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return uploadPopupPage;
	}
	
	public UploadPopupPage uploadLocalFileOSSpecific(UploadPopupPage uploadPopupPage, String fileName){
		if(getBrowser().contains("chrome") || getBrowser().contains("firefox")){
			try {
				uploadPopupPage.uploadLocalFileFromResource(fileName);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
				uploadPopupPage.uploadLocalFileFromFinder("C:\\Users\\hello\\Documents\\documents\\text-sample1.txt");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return uploadPopupPage;
	}
	
	
	public  UploadPopupPage UploadFile(){
		CatalogsPage catalogPage= PageFactory.initElements(driver, CatalogsPage.class);
		catalogPage.setMyCatalogToManualCatalog();
		UploadPopupPage uploadPopupPage = catalogPage.clickUpload();
		uploadPopupPage.clickUploadFile();
		//uploadPopupPage.uploadFile("LondonDrugsTxt");
		
		uploadPopupPage = uploadLocalFileOSSpecific(uploadPopupPage).clickNext();

		
		return uploadPopupPage;
	}
	

	public ProductsCatalogPage navigateToProductsCatalogPage(){
		CatalogsPage catalogsPage = PageFactory.initElements(driver, CatalogsPage.class);
		ProductsCatalogPage productsCatalogPage = catalogsPage.goToCatalogWithSomeNumberOfProducts(30);
		return productsCatalogPage;
	}
	
	public ProductsCatalogPage navigateToProductsCatalogPage(int num1, int num2){
		CatalogsPage catalogsPage = PageFactory.initElements(driver, CatalogsPage.class);
		ProductsCatalogPage productsCatalogPage = catalogsPage.goToCatalogWithSomeNumberOfProducts(num1, num2);
		return productsCatalogPage;
	}
	
	public ProductsCatalogPage navigateToProductsCatalogPageManual(){
		CatalogsPage catalogsPage = PageFactory.initElements(driver, CatalogsPage.class);
		ProductsCatalogPage productsCatalogPage = catalogsPage.setMyCatalogToManualCatalog().goToProductsCatalogPage();
		return productsCatalogPage;
	}
	
	public MappingPage UploadFullFile() throws InterruptedException{
		UploadPopupPage uploadPopupPage = navigateToAddcatalogPage(false).fillInName();
		uploadPopupPage.clickUploadFile();
		uploadPopupPage = uploadLocalFileOSSpecific(uploadPopupPage).clickNext();
		
		MappingPage mappingPage = (MappingPage) uploadPopupPage.clickNextAfterUpload(true);
		return mappingPage;
	}
	
	public MappingPage UploadFullFile(String filename){
		return UploadFullFile(filename,"TXT");
	}
	
	public MappingPage UploadFullFile(String filename, String selectOption){
		UploadPopupPage uploadPopupPage = navigateToAddcatalogPage(false).fillInName();
		uploadPopupPage.selectDropBoxOption(selectOption);
		uploadPopupPage.clickUploadFile();
		uploadPopupPage = uploadLocalFileOSSpecific(uploadPopupPage, filename).clickNext();
		
		MappingPage mappingPage = (MappingPage) uploadPopupPage.clickNextAfterUpload(true);
		return mappingPage;
	}
	
	protected static List<String> tempFiles;
	public String tempFile;
	public AddCatalogPage navigateToAddcatalogPage(boolean isAutomatic){
		tempFiles= new ArrayList<String>();
		//System.out.println("drive rnull? " + driver == null);
		CatalogsPage catalogsPage = PageFactory.initElements(driver, CatalogsPage.class);
		AddCatalogPage addCatalogsPage = catalogsPage.goToAddCatalog();
		tempFile = addCatalogsPage.getTempFileName();
		tempFiles.add(tempFile);
		
		if(isAutomatic){
			addCatalogsPage.switchToAutomatic();
			
		}
		
		return addCatalogsPage;
	}
	
	public <T> boolean twoListsAreEqual(List<T> list1, List<T> list2){
		//System.out.println("is it equal " + list1.equals(list2));
		if(!list1.equals(list2)){ 
			Collection subtractedList1 = CollectionUtils.subtract(list1, list2);
			Collection subtractedList2 = CollectionUtils.subtract(list2, list1);
			
			if(subtractedList1.size() >= 1){
				System.out.println();
				System.out.println("list1: ");
				for(Object c: subtractedList1){
					if(c instanceof Catalog){
						System.out.println("===comparing catalogs object list===");
						Catalog convertC= (Catalog)c;
						System.out.println(convertC.getCatalog_name() + "/ " + convertC.getModified_by() + "/ " + convertC.getParty());
					}
					else if(c instanceof Product){
						System.out.println("===comparing Products object list===");
						Product convertP = (Product)c;
						System.out.println(convertP.toString());
					}
					else{
						System.out.println(c.toString());
					}
				}
				return false;
			}
			
			if(subtractedList2.size() >= 1){
				System.out.println();
				System.out.println("list2: ");
				for(Object c: subtractedList2){
					if(c instanceof Catalog){
						Catalog convertC= (Catalog)c;
						System.out.println(convertC.getCatalog_name() + "/ " + convertC.getModified_by() + "/ " + convertC.getParty());
					}
					else if(c instanceof Product){
						Product convertP = (Product)c;
						System.out.println(convertP.toString());
					}
					else{
						System.out.println(c.toString());
					}
				}
				return false;
			}	
		}	
		return true;
	}
	
	public void skipFirefox(){
		if(getBrowser().toLowerCase().contains("firefox")) return;
	}
	
	public String getRandomNumber(){
		return System.currentTimeMillis() + "";
	}
}
