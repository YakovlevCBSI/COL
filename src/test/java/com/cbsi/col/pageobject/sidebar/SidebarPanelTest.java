package com.cbsi.col.pageobject.sidebar;

import static org.junit.Assert.assertTrue;

import com.cbsi.col.test.foundation.ColBaseTest;

import org.junit.Assert;
import org.junit.Test;

public class SidebarPanelTest extends ColBaseTest{

	public SidebarPanelTest(String url, String browser) {
		super(url, browser);
		// TODO Auto-generated constructor stub
	}
	
	@Test
	public void collapseControlPanel(){
		if(!homePage.isControlPaneExpanded()){
			homePage.expandControlPanel();
		}
		
		homePage.collapeControlPanel();
		
		Assert.assertFalse(homePage.isControlPaneExpanded());		
	}
	
	@Test
	public void expandControlPanel(){
		if(homePage.isControlPaneExpanded()){
			homePage.collapeControlPanel();
		}
		
		homePage.expandControlPanel();
		assertTrue(homePage.isControlPaneExpanded());
	}
	
	@Test
	public void iconsDisplayWhenCollpased(){
		homePage.collapeControlPanel();
		assertTrue(homePage.areIconsDisplayed());
	}
	
}
