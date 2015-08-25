package com.cbsi.col.test.foundation;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
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
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.customers.AccountsPage;
import com.cbsi.col.pageobject.customers.CreateAccountPage;
import com.cbsi.col.pageobject.customers.CurrentAccountTab;
import com.cbsi.col.pageobject.customers.RecentAccountsTab;
import com.cbsi.col.pageobject.customers.AccountsPage.AccountType;
import com.cbsi.col.pageobject.home.HomePage;
import com.cbsi.col.pageobject.home.LoginPage;

@RunWith(Parameterized.class)
public class ColBaseTest {
	protected WebDriver driver;
	
	private String url;
	private String browser;
	private String username = System.getProperty("user.name");	
	private String chromeDriverVersion = System.getProperty("chromedriver-version", "2.16");

	
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
	
	@Rule
	public Timeout globalTimeout = new Timeout(280000);
	
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
		driver = configureDrivers();
		driver.get(url);
//		driver.manage().window().maximize();
		driver.manage().window().setSize(new Dimension(1600, 700));
		navigatetoLoginPage();
	}
	
	public void cleanUp(){
		driver.quit();
	}
	
	public WebDriver configureDrivers(){
		WebDriver emptyDriver = null;
		System.out.println("staring conditions");

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
				System.out.println("Failed to create a firefox driver");
				e.printStackTrace();
			}
			
		}

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
		System.out.println(separator + "\n" + headerText + "\n" + separator);
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
	
	public boolean isAutoRun(){
		if(username.equals("jenkins") || username.contains("slave")) return true;
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
	
	public void navigatetoLoginPage(){
		LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
		homePage =  loginPage.loginToHomePage();
		return;
	}
	
	public static final String companyName = "Qa_customer_"+System.currentTimeMillis();
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
			createNewCustomerPage.setZip(zip);
			createNewCustomerPage = createNewCustomerPage.clickNext();
			
			//---------------------------------------------------------------------------
			//WORK AROUND FOR IFrame loading error without Contact info #5431
			createNewCustomerPage.setContactInfo_FirstName(companyName.split("_")[0]);
			createNewCustomerPage.setContactInfo_LastName(companyName.split("_")[1]);
			createNewCustomerPage = createNewCustomerPage.clickNext();
			//----------------------------------------------------------------------------
			
			recentCustomersPage = createNewCustomerPage.goToAccountsPage().goToRecentCustomersTab();
		}else{
			createNewCustomerPage.setFirstName(companyName.split("_")[0]);
			createNewCustomerPage.setLastName(companyName.split("_")[1]);
			createNewCustomerPage.setEmail(companyName+"@email.com");
			createNewCustomerPage.setContactInfo_CompanyName(companyName);
			CurrentAccountTab currentAccountTab = createNewCustomerPage.clickSaveButton();

			recentCustomersPage = currentAccountTab.goToAccountsPage().goToRecentCustomersTab();
		}
		
		return recentCustomersPage;
	}
	

	
}
