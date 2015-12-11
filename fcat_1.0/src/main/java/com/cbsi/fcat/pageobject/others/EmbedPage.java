package com.cbsi.fcat.pageobject.others;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.fcat.pageobject.foundation.BasePage;
import com.cbsi.fcat.pageobject.homepage.FCatLoginPage;
import com.cbsi.fcat.util.GlobalVar;

public class EmbedPage extends BasePage{
	public EmbedPage(WebDriver driver){
		super(driver);
		waitForPageToLoad();
	}
	

	public FCatLoginPage goToLoginPage(){
		String currentURL = driver.getCurrentUrl();
		
		int indexOfEmbed = currentURL.indexOf(GlobalVar.embedPath); 
		if(indexOfEmbed != -1){
			driver.get(currentURL.substring(0, indexOfEmbed));
		}
		
		return PageFactory.initElements(driver, FCatLoginPage.class);
	}
	
	
	
	public static void main(String[] args){
		String URL =GlobalVar.stageServer;
		System.out.println(URL.indexOf("embedPagePath"));
	}
	
	
}
