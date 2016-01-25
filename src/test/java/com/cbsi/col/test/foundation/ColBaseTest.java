package com.cbsi.col.test.foundation;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.model.Statement;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cbsi.col.pageobject.customers.AccountsPage;
import com.cbsi.col.pageobject.customers.CreateAccountPage;
import com.cbsi.col.pageobject.customers.CurrentAccountTab;
import com.cbsi.col.pageobject.customers.RecentAccountsTab;
import com.cbsi.col.pageobject.customers.AccountsPage.AccountType;
import com.cbsi.col.pageobject.documents.DocumentsBasePage;
import com.cbsi.col.pageobject.documents.DocumentsPage;
import com.cbsi.col.pageobject.documents.QuotePage;
import com.cbsi.col.pageobject.documents.DocumentsPage.DocumentTabs;
import com.cbsi.col.pageobject.home.HomePage;
import com.cbsi.col.pageobject.home.LoginPage;
import com.cbsi.col.test.util.GlobalProperty;
import com.cbsi.col.test.util.LoginProperty;
import com.cbsi.col.test.util.LoginUtil;
import com.cbsi.col.test.util.StringUtil;

@RunWith(Parameterized.class)
public class ColBaseTest {
	public final Logger logger = LoggerFactory.getLogger(ColBaseTest.class);

	protected WebDriver driver;
	
	private String url;
	private String browser;
	private String username = System.getProperty("user.name");
	private String chromeDriverVersion = System.getProperty("chromedriver-version", "2.18");
	public boolean isGrid = GlobalProperty.GRID!=null?true:false;
//	public boolean isGrid = true;

	
	public ColBaseTest(String url, String browser){
		this.url = url;
		this.browser = browser;

	}
	
	public String getUrl(){
		return url;
	}
	
	public void setUrl(String url){
		this.url = url;
	}
	
	public String getBrowser(){
		return browser;
	}
	
	public void setBrowser(String browser){
		this.browser = browser;
	}
//	

	@Rule
	public Timeout globalTimeout = new Timeout(300000);
	
	@Rule
	public TestName testInfo = new TestName();
	
	@Rule
	public Retry retry = new Retry(isAutoRun()?2:1);
	
	@Parameterized.Parameters
	public static Collection testParam(){
		return Arrays.asList(			
				new ParameterFeeder().configureTestParams()
				);
	}
	
	@Before
	public void startUp(){
		insertHeader();
		logger.debug(getUsername() + "/ " +  getPassword());
		driver = configureDrivers();
		driver.get(url);
		driver.manage().window().maximize();
//		driver.manage().window().setSize(new Dimension(1200, 700));
		navigatetoLoginPage();

	}
	
	public void cleanUp(){
     	if(isGrid && userCheckedOut == true){
     		userCheckedOut= false;
     		login.checkInUser();
     	}
		driver.quit();
	}
	
	public WebDriver configureDrivers(){
		WebDriver emptyDriver = null;
		logger.info("configure driver for browser");
		if(!isGrid){
			if(getBrowser().contains("chrome")){
				emptyDriver = getChromeDriver();
			}else if(getBrowser().contains("internet explorer")){
				emptyDriver = getIEDriver();
			}
			else{
				try{
					FirefoxProfile profile = new FirefoxProfile();
					emptyDriver = new FirefoxDriver(profile);
				}catch(Exception e){
					logger.info("Failed to create a firefox driver");
					e.printStackTrace();
				}
				
			}
		}else{
			try {
				emptyDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), getBrowser().contains("chrome")?getChromeCapability():getFirefoxCapability());
//				emptyDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), getFirefoxCapability());
//				emptyDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), getChromeCapability());

			
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//		try {
//			emptyDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), getBrowser().contains("chrome")?getChromeCapability():getFirefoxCapability());
//		} catch (MalformedURLException e) {
//			10// TODO Auto-generated catch block
//			e.printStackTrace();
//		}


		return emptyDriver;
	}
	
	public WebDriver getChromeDriver(){
		String pathToChromeDriver = System.getProperty("user.dir") + "/src/test/resources/Drivers/Chrome/" + chromeDriverVersion +"/";
		String osName = System.getProperty("os.name");
		//Checking mac or Linux condition here for chromedriver.
		if(osName.toLowerCase().contains("mac")){
			pathToChromeDriver = pathToChromeDriver + "chromedriver_mac32";
		}
		else if(osName.toLowerCase().contains("windows")){
			pathToChromeDriver = pathToChromeDriver + "chromedriver.exe";
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
	
	public Capabilities getFirefoxCapability(){
		DesiredCapabilities caps = DesiredCapabilities.chrome();
		caps.setBrowserName("firefox");
		caps.setPlatform(Platform.LINUX);
		caps.setVersion("38");
		
		return caps;
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
		/**
		//System.out.println("bsh: " + browserStackHub);
		try {
			driver = new RemoteWebDriver(new URL(browserStackHub), caps);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			System.err.println("Failed to connect to browser stack hub...");
			e.printStackTrace();
		}
		*/
		//Not needed as of now.
		//driver.manage().deleteCookieNamed("JSESSIONID");
		return driver;
	}
	
	public void insertHeader(){		
		String headerText = "TestName: " + testInfo.getMethodName() + "\nURL: " + getUrl() + "\nBrowser: " + getBrowser();
		String separator = new String(new char[headerText.length()]).replace("\0", "-");

		System.out.println(separator + "\n" + headerText + "\n" + separator + "\n");
	}
	
	//************************************** inner retry class *****************************************//
	public boolean screenShotCreated = false;
	
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
                            	logger.info("Taking a screenshot");
                            	takeScreenshot();
                            }
                        } finally{
                        	 if(driver!=null){
                        		 logger.info("quit the driver");
                             	driver.quit();
                             	if(isGrid && userCheckedOut == true){
                             		login.checkInUser();
                             	}

                             }  
                        }
                    }
                    if(isAutoRun()){
                    	logger.info("killed all driver instances");
        				runCommand("killall firefox");
        				runCommand("killall chrome");
        			}
                    System.err.println(description.getDisplayName() + ": giving up after " + retryCount + " failures");
                    throw caughtThrowable;
                }
            };
        }
	}
	
	public boolean isAutoRun(){
		if(username.equals("jenkins") || username.contains("slave") || username.contains("qe")) return true;
		return false;
	}
	
	public void takeScreenshot(){
		if(!screenShotCreated){
			String testName= this.getClass().getName();
			String methodName = testInfo.getMethodName();
			String filename = "target/surefire-reports/"+testName+"/" + methodName +"." + getUrl().replace("/", "_") + "." + getBrowser()+ ".png";
	
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
	
	//************************************** common methods ************************************///
	protected AccountsPage customersPage;
	protected HomePage homePage;
	
	public void navigateToCustomersPage(){
//		navigatetoLoginPage();
		customersPage = homePage.goToAccountsPage();

	}
	
	LoginUtil login;
	public static boolean userCheckedOut = false;
	public void navigatetoLoginPage(){
		LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
		if(isGrid){
//			userCheckedOut=true;
//			login = new LoginUtil();
//			homePage =  loginPage.loginToHomePage(login.checkOutUser(), LoginProperty.testPassword);
			homePage = loginPage.loginToHomePage(getUsername(),getPassword());

		}
		else{		
			homePage = loginPage.loginToHomePage(getUsername(),getPassword());
//			homePage = loginPage.loginToHomePage();
			logger.info("logged in as a user " + getUsername());
		}
		return;
	}
	
	public static final String companyName = "QaCustomer"+System.currentTimeMillis();
	public static final String companyNameCommon = "QaCustomer";
	public static final String address = "444 Oceancrest Dr";
	public static final String city = "Irvine";
	public static final String zip = "90019";
	
	public RecentAccountsTab createAccount(){
		return createAccount(AccountType.CUSTOMER);
	}
	
	public RecentAccountsTab createAccount(AccountType accountType){
		System.out.println(companyName);
		RecentAccountsTab recentCustomersPage = null;
		CreateAccountPage createNewCustomerPage = customersPage.clickCreateNewAccount(accountType);
		if(accountType != AccountType.LEAD){ 
			createNewCustomerPage.setCompanyName(companyName);
			createNewCustomerPage.setAddress(address);
			createNewCustomerPage.setCity(city);
			createNewCustomerPage.setState("ca");
			createNewCustomerPage.setZip(zip);
			
			createNewCustomerPage.setDefaultPaymentToCod();
			
			createNewCustomerPage = (CreateAccountPage) createNewCustomerPage.clickNext();
			
//			
//			//---------------------------------------------------------------------------
			//WORK AROUND FOR IFrame loading error withouta Contact info #5431
			createNewCustomerPage.setContactInfo_FirstName("Qa");
			createNewCustomerPage.setContactInfo_LastName("customer");
			createNewCustomerPage.setDefaultContact();
			createNewCustomerPage.setState("ca");
			
			createNewCustomerPage.setEmail("cbsiqa@gmail.com");
//			createNewCustomerPage = createNewCustomerPage.clickNext();
//			//----------------------------------------------------------------------------

			createNewCustomerPage = (CreateAccountPage) createNewCustomerPage.clickNext();
			
			createNewCustomerPage.setContactInfo_FirstName("Qa");
			createNewCustomerPage.setContactInfo_LastName("customers");
			createNewCustomerPage.setState("ca");
			
			createNewCustomerPage.clickCopy().clickFinish();
//
			if(createNewCustomerPage.isAlertPresent()){
				createNewCustomerPage.acceptAlert();
			}
		
			recentCustomersPage = createNewCustomerPage.goToAccountsPage().goToRecentCustomersTab();
		}else{
			createNewCustomerPage.setFirstName("Qa");
			createNewCustomerPage.setLastName("customers");
			createNewCustomerPage.setEmail(companyName+"@email.com");
			createNewCustomerPage.setContactInfo_CompanyName(companyName);
			CurrentAccountTab currentAccountTab = createNewCustomerPage.clickSaveButton();
//			createNewCustomerPage.clickFinish();
			
			recentCustomersPage = currentAccountTab.goToAccountsPage().goToRecentCustomersTab();
		}
		
		return recentCustomersPage;
	}
	
	public static String getHostname(){
		String hostName="";
		try{
		    InetAddress addr;
		    addr = InetAddress.getLocalHost();
		    hostName = addr.getHostName();
		}
		catch (UnknownHostException ex){
		    System.out.println("Hostname can not be resolved");
		}
		
		return hostName;
	}
	
	public String getHostUserName(){
		return System.getProperty("user.name");
	}
	
	public String getUsername(){
		if(!GlobalProperty.isProd){
			if(getHostUserName().startsWith("slave1")) return LoginProperty.testUser1;
			else if (getHostUserName().startsWith("slave2")) return LoginProperty.testUser2;
//			else if (getHostname().endsWith("3")) return LoginProperty.testUser3;
			else if (getHostUserName().startsWith("slave3")) return LoginProperty.testUser3;
//			else if (getHostname().startsWith("slave1")) return LoginProperty.testUser4;
			
			return LoginProperty.testUser_manual;
		}
		else{
			if(getHostUserName().startsWith("slave1")) return LoginProperty.testUser1_prod;
			else if (getHostUserName().startsWith("slave2")) return LoginProperty.testUser2_prod;
//			else if (getHostname().endsWith("3")) return LoginProperty.testUser3_prod;
			else if (getHostUserName().startsWith("slave3")) return LoginProperty.testUser3_prod;
			
//			return LoginProperty.testUser4_prod;
			return LoginProperty.testUserManual_prod;

		}
		
	}
	
	public String getPassword(){
		if(!GlobalProperty.isProd) return LoginProperty.testPassword;
		
		return LoginProperty.testPassword_prod;

	}
	
	public QuotePage goToFirstOpenQuote(){
		DocumentsPage documentsPage = homePage.goToDocumentsPage().switchToTab(DocumentTabs.QUOTES).setFilterByModifiedBy("All");
		Long quoteNumber = null;

		for(LinkedHashMap<String, String> map: documentsPage.getTableAsMaps()){
			if(map.get("status").toLowerCase().equals("open")){
				quoteNumber = Long.parseLong(documentsPage.getTableAsMaps().get(0).get("doc#"));
				break;
			}
		}
		
		if(quoteNumber == null){
			throw new NullPointerException("There is not quote document to use for test");
		}
		
		return documentsPage.goToQuote(quoteNumber);	
	}
}
