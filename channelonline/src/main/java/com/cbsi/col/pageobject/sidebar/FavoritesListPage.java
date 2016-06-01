package com.cbsi.col.pageobject.sidebar;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.home.ColBasePage;

public class FavoritesListPage extends ColBasePage{

	public FavoritesListPage(WebDriver driver){
		super(driver);
		waitForTextToBeVisible("This section offers a way", "p");
	}
	
	@FindBy(css="")
	private WebElement AddFavorite;
	
	@FindBy(css="")
	private WebElement ReorderLines;
	
	
	
//	public AddFavoritePage clickAddFavorite(){
//		AddFavorite.click();
//		
//		return PageFactory.initElements(driver, AddFavoritePage.class);
//	}
//	
	
	public FavoritesAdminPage clickReorderLines(){
		ReorderLines.click();
		
		return PageFactory.initElements(driver, FavoritesAdminPage.class);
	}
	
	@FindBy(css="button[id*='-caret']")
	private WebElement Caret;
	
	@FindBy(css="a[id*='saveAndExit']")
	private WebElement SaveAndExit;
	
	public FavoritesAdminPage clickSaveAndExit(){
		Caret.click();
		SaveAndExit.click();
		
		return PageFactory.initElements(driver, FavoritesAdminPage.class);
	}
	
	
}
