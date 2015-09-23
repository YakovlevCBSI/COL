package com.cbsi.col.pageobject.sidebar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.home.ColBasePage;

public class StoreSiteAdminPage extends ColBasePage{

	public StoreSiteAdminPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForTextToBeVisible("StoreSite Admin", "h1");
	}
	
	@FindBy(css="a[title='Create New Store']")
	private WebElement CreateNewStore;
	
	@FindBy(css="input#storesite_url")
	private WebElement Shortcut;
	
	@FindBy(css="input#storesite_name")
	private WebElement Name;
	
	@FindBy(css="input#active")
	private WebElement Enabled;
	
	@FindBy(css="input[value='private']")
	private WebElement Private;
	
	@FindBy(css="input[value='public']")
	private WebElement Public;
	
	@FindBy(css="p.view_btnSaveShow")
	private WebElement Save;
	
	public StoreSiteAdminPage createNewStore(String shortcut, String name){
		return createNewStore(true, true, shortcut, name);
	}
	
	public StoreSiteAdminPage createNewStore(boolean enabled, boolean isPrivate, String shortcut, String name){
		CreateNewStore.click();
		
		waitForTextToBeVisible("Create New Store", "h1");
		
		if(!enabled){
			Enabled.click();
		}
		
		if(!isPrivate){
			Public.click();
		}
		
		Shortcut.clear();
		Shortcut.sendKeys(shortcut);
		
		Name.clear();
		Name.sendKeys(name);
		
		forceWait(5000);
		Save.click();
//		acceptAlert();
		forceWait(1000);
		
		return PageFactory.initElements(driver, StoreSiteAdminPage.class);
	}
	
	public boolean hasStore(String storeName){
		return findDataRowByName(storeName, 2, "")==null?false:true;
	}
	
	public StoreSiteAdminPage copyStore(String storeName){
		WebElement dataRow = findDataRowByName(storeName, 2, "");
		WebElement copy = dataRow.findElement(By.xpath("../td[8]/a"));
		copy.click();
		
		return PageFactory.initElements(driver, StoreSiteAdminPage.class);
	}
	public StoreSiteAdminPage deleteStore(String storeName){
		WebElement dataRow = findDataRowByName(storeName, 2, "");
		WebElement delete = dataRow.findElement(By.xpath("../td[9]/a"));
		
		delete.click();
		
		acceptAlert();
		
		return PageFactory.initElements(driver, StoreSiteAdminPage.class);
	}

	
}
