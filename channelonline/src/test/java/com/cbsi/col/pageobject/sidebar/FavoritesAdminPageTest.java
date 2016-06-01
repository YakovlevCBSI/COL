package com.cbsi.col.pageobject.sidebar;

import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.cbsi.col.pageobject.home.HomePage.Admin;
import com.cbsi.col.test.foundation.ColBaseTest;

public class FavoritesAdminPageTest extends ColBaseTest{

	public FavoritesAdminPageTest(String url, String browser) {
		super(url, browser);
		// TODO Auto-generated constructor stub
	}
	
	public FavoritesAdminPage favoritesAdminPage;
	
	@Before
	public void startUp(){
		super.startUp();
		favoritesAdminPage = homePage.navigateToSideBar(Admin.Favorites_Admin, FavoritesAdminPage.class);
	}

	@Test
	public void copyFavorite(){
		List<LinkedHashMap<String, String>> maps = favoritesAdminPage.getTableAsMaps();
		String favoriteName = maps.get(0).get("name");
		
		FavoritesListPage favoriteslistPage = favoritesAdminPage.clickCopyByName(favoriteName);
		favoritesAdminPage = favoriteslistPage.clickSaveAndExit();
		
		assertTrue(favoritesAdminPage.hasFavorites("Copy of "+favoriteName));
		
		favoritesAdminPage.clickDeleteByName("Copy of "+favoriteName);
	}
}
