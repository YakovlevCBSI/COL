package com.cbsi.tests.Foundation;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.tests.PageObjects.BFPLoginPage;
import com.cbsi.tests.PageObjects.CatalogsPage;
import com.cbsi.tests.PageObjects.EmbedPage;
import com.cbsi.tests.PageObjects.FCatHomePage;
import com.cbsi.tests.PageObjects.FCatLoginPage;
import com.cbsi.tests.util.GlobalVar;

@RunWith(Parameterized.class)
public class BaseTest {
	public WebDriver driver = null;
	
	private String browser;
	private String URL;
	
	public BaseTest(){
		
	}
	
	public BaseTest(String URL, String browser){
		this.URL = URL;
		this.browser = browser;
	}
	
	@Parameterized.Parameters
	public static Collection testParam(){
		return Arrays.asList(
				new ParameterFeeder().configureTestParams()
				);
	}
	
	public String getBrowser() { return browser; }
	public String getURL() { return URL; }
	
	//Need Time out rule here
	@Rule 
	public Timeout globalTimeout = new Timeout(120000);
	
	@Before
	public void startUp(){
	
		//Test page header goes here
		System.out.println("\nURL: " + getURL() +  "\t\tBrowser: " + getBrowser());
		System.out.println("------------------------------------------");
		
		
		driver = configureDrivers();
		driver.manage().window().setPosition(new Point(1200, 0));
		navigateToHomePage();
		
		
		
		maximizeWindow();
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
	private String pathToChromeDriver = System.getProperty("user.dir") + "/src/test/resources/Drivers/chromedriver";

	public WebDriver getChromeDriver(){
		System.setProperty("webdriver.chrome.driver", pathToChromeDriver);
		return new ChromeDriver();
		
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
		String filename = "target/surefire-reports/"+testName+"/" + methodName +"." + getURL().replace("/", "_")+ ".png";

		File srcFile= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(srcFile, new File(filename));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
