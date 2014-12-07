package com.cbsi.tests.Foundation;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.junit.After;
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
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.tests.PageObjects.BFPLoginPage;
import com.cbsi.tests.PageObjects.CatalogsPage;
import com.cbsi.tests.PageObjects.EmbedPage;
import com.cbsi.tests.PageObjects.FCatHomePage;
import com.cbsi.tests.PageObjects.FCatLoginPage;
import com.cbsi.tests.util.GlobalVar;
import com.cbsi.tests.util.ReadFile;
import com.thoughtworks.selenium.SeleneseTestBase;

public class BaseTest {
	public WebDriver driver = null;
	
	private String browser;
	private String URL;
	
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
	@Before
	public void startUp(){
		
		//Test page header goes here
		insertHeader();
		driver = configureDrivers();
		//driver.manage().window().setPosition(new Point(1200, 0));
		navigateToHomePage();
		maximizeWindow();
	}
	
	public void insertHeader(){
		
		String headerText = "testName: " + testInfo.getMethodName() + "\tURL: " + getURL() + "\tBrowser: " + getBrowser();
		String separator = new String(new char[headerText.length()]).replace("\0", "-");
		System.out.println(separator + "\n" + headerText + "\n" + separator);
	}
	
	public WebDriver configureDrivers(){
		WebDriver emptyDriver = null;
		System.out.println("staring conditions");
		if(getBrowser().contains("chrome")){
			System.out.println("in chromecondition");
			emptyDriver = getChromeDriver();
		}else{
			emptyDriver = new FirefoxDriver();
			System.out.println("in firefox conditions");
		}
		
		return emptyDriver;
	}
	
	public void navigateToHomePage(){
		driver.get(getURL());
		if(getURL().contains(embedPath)){
			//do nothing
		}
		else if (getURL().contains(BFP)){
			EasyLoginToBFP();
		}
		else{
			EasyLoginToLocal();
		}
	}
	
	//private String pathToChromeDriver = "/Users/alpark/Documents/workspace/fcat_1.0/src/test/resources/Drivers/chromedriver";
	private double chromeDriverVersion = 2.12;

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
		
		return new ChromeDriver();
		
		
		//Capabilities caps = DesiredCapabilities.chrome();
		//return new RemoteWebDriver(caps);
	}
	
	@After
	public void cleanUp(){
		
		takeScreenshot();
		driver.quit();
		//screenshot
		//run again for false positive
	}

	private String BFP =  GlobalVar.BFPServer;//need to login using their credentials.
	private String embedPath = GlobalVar.embedPath;
	


	
	public void maximizeWindow(){
		driver.manage().window().maximize();
	}
	
	public FCatHomePage EasyLoginToLocal(){
		FCatLoginPage loginPage = PageFactory.initElements(driver, FCatLoginPage.class);
		FCatHomePage homePage = loginPage.loginToHomePage();
		homePage.goToCatalogs();
		return homePage;
	}
	
	public CatalogsPage EasyLoginToBFP(){
		BFPLoginPage bfpLoginPage = PageFactory.initElements(driver, BFPLoginPage.class);
		CatalogsPage catalogsPage = bfpLoginPage.loginToHomePage();
		return catalogsPage;
	}
	
	public EmbedPage GotoEmbedPage(){
		return PageFactory.initElements(driver, EmbedPage.class);
		
	}
	
	public void cleanUpThenDeleteTemp(String tempFile){
		if(tempFile.length() != 0){
			System.out.println();
			System.out.println("Delete Temp in Actions");
			System.out.println("----------------------------");

			takeScreenshot();
			driver.quit();
			startUp();
			
			try{
				CatalogsPage catalogsPage = PageFactory.initElements(driver, CatalogsPage.class);
				catalogsPage.deleteTempFile(tempFile);
			} catch(Exception e){
				System.out.println("failed to delete temp file...");
				e.printStackTrace();
			}
		}
		
		driver.quit();
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
		String testName= this.getClass().getName();
		String methodName = testInfo.getMethodName();
		String filename = "target/surefire-reports/"+testName+"/" + methodName +"." + getURL().replace("/", "_") + "." + getBrowser()+ ".png";

		File srcFile= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(srcFile, new File(filename));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//rerun failed test for false-positive cases.
	@Rule
	public Retry retry = new Retry(1);
	
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
                        }
                    }
                    System.err.println(description.getDisplayName() + ": giving up after " + retryCount + " failures");
                    throw caughtThrowable;
                }
            };
        }
	}
}
