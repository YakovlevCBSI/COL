package com.cbsi.col.pageobject.sidebar;

import java.util.LinkedHashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.home.ColBasePage;

public class FavoritesAdminPage extends ColBasePage{

	public FavoritesAdminPage(WebDriver driver){
		super(driver);
		waitForTextToBeVisible("Favorites Admin", "h1");
	}
	
//	@FindBy(css="")
//	private WebElement AddFavorite;
//	
//	@FindBy(css="")
//	private WebElement ReorderLines;
//	
	
	@FindBy(css="a[title='Create Favorites List']")
	private WebElement CreateNewFavoritesList;
	
	@FindBy(css="input[value='Copy Favorites']")
	private WebElement Copy;
	
	public FavoritesListPage clickCreateNewFavoritesList(){
		CreateNewFavoritesList.click();
		return PageFactory.initElements(driver, FavoritesListPage.class);
	}
	
	@FindBy(css="button#copy-favoriteslist-btn")
	private WebElement CopyConfirm;
	public FavoritesListPage clickCopyByName(String name){
		findDataRowByName(name).findElement(By.xpath("../td/input[@title= 'Copy Favorites']")).click();
		waitForTextToBeVisible("Are you sure", "p");
		CopyConfirm.click();
		
		forceWait(500);
		return PageFactory.initElements(driver, FavoritesListPage.class);
	}

	public FavoritesListPage clickEditByName(String name){
		findDataRowByName(name).findElement(By.xpath("../td/a[@title= 'Update Page']")).click();
		
		return PageFactory.initElements(driver, FavoritesListPage.class);
	}
	
	@FindBy(css="button#delete-favoriteslist-btn")
	private WebElement DelConfirm;
	
	public FavoritesAdminPage clickDeleteByName(String name){
		findDataRowByName(name).findElement(By.xpath("../td/input[@title= 'Delete Favorites']")).click();
		waitForTextToBeVisible("Are you sure", "p");
		DelConfirm.click();
		forceWait(500);
		
		return PageFactory.initElements(driver, FavoritesAdminPage.class);

	}
	
	public WebElement findDataRowByName(String name){
		return findDataRowByName(name, 3, "", false);
	}
	
	@FindBy(css="table.costandard")
	private WebElement favoriteTable;
	
	public List<LinkedHashMap<String, String>> getTableAsMaps(){
		return super.getTableAsMaps(1,favoriteTable, 0,1,7,8);  //only get MfrPart, margin, total datas.
	}
	
	public boolean hasFavorites(String name){
		return findDataRowByName(name) != null ? true:false;
	}
	
	
}
