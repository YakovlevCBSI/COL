package com.cbsi.test.MapProductsDialogTest;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.cbsi.tests.Foundation.AllBaseTest;
import com.cbsi.tests.util.GlobalVar;

public class SmokeTest extends AllBaseTest{

	public SmokeTest(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}


	/**
	@Test
	public void browserStackInAction() throws MalformedURLException{
		
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("browser", "IE");
		caps.setCapability("browser_version", "9.0");
		caps.setCapability("os_version", "xp");
		caps.setCapability("browserstack.debug", "true");
		
		driver = new RemoteWebDriver(new URL(URL), caps);
		driver.get(GlobalVar.BFPServer);
		String home = System.getProperty("user.home");
		System.out.println(home);
	    File file = new File(home);
	   File[] somelist =  file.listFiles();
	   
	   for(File f: somelist){
		   System.out.println(f.getAbsolutePath());
	   }
	}
	*/
	
	@Test
	public void failTest(){
		assertTrue(false);
	}
	
	@Test
	public void pass(){
		assertTrue(true);
	}

}
