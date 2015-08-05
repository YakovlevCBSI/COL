package com.cbsi.col.test.foundation;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Level;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.HomePage;
import com.cbsi.col.pageobject.LoginPage;
import com.cbsi.col.test.pageobject.customers.CreateAccountPage;
import com.cbsi.col.test.pageobject.customers.AccountsPage;
import com.cbsi.col.test.pageobject.customers.RecentAccountsTab;

@RunWith(Parameterized.class)
public class ColBaseTest {
	protected WebDriver driver;
	
	private String url;
	private String browser;
	
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
	public Timeout globalTimeout = new Timeout(180000);
	
	@Rule
	public TestName testInfo = new TestName();
	
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
	}
	
	@After
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
	
	
	//******* common methods ******///
	protected AccountsPage customersPage;
	
	public void navigateToCustomersPage(){
		HomePage homePage = navigatetoLoginPage();
		customersPage = homePage.goToAccountsPage();
	}
	
	public HomePage navigatetoLoginPage(){
		HomePage homePage = null;
		LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
		return loginPage.loginToHomePage();
	}
	
	public static final String companyName = "QaCustomer_ " +System.currentTimeMillis();
	public static final String address = "444 Oceancrest Dr";
	public static final String city = "Irvine";
	public static final String zip = "90019";
	
	public RecentAccountsTab createAccount(){
		System.out.println(companyName);
		CreateAccountPage createNewCustomerPage = customersPage.clickCreateNewCustomer("customer");
		createNewCustomerPage.setCompanyName(companyName);
		createNewCustomerPage.setAddress(address);
		createNewCustomerPage.setCity(city);
		createNewCustomerPage.setZip(zip);
		createNewCustomerPage.clickFinish();
		
		RecentAccountsTab recentCustomersPage = createNewCustomerPage.goToAccountsPage().goToRecentCustomersTab();
		return recentCustomersPage;
	}
}
