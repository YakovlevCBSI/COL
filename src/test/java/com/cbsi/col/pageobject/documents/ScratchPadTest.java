package com.cbsi.col.pageobject.documents;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.documents.DocumentsBasePage.LineActions;
import com.cbsi.col.pageobject.documents.DocumentsBasePage.SendPage;
import com.cbsi.col.pageobject.products.ProductsPage;
import com.cbsi.col.pageobject.products.ProductsPage.Action;
import com.cbsi.col.test.foundation.DocumentsBasePageTest;

public class ScratchPadTest extends DocumentsBasePageTest{

	public ScratchPadTest(String url, String browser) {
		super(url, browser);
		// TODO Auto-generated constructor stub
	}

	public ScratchPadPage scratchPad;
	
	@Test
	public void canAddThenDeleteProduct(){
		scratchPad = homePage.goToScratchPadPage(); 
		
		scratchPad = (ScratchPadPage) scratchPad.removeAllProducts();
			
		ProductsPage productsPage = scratchPad.searchProduct("Lenovo");
		productsPage.checkCompareBoxes(1,2).selectAction(Action.AddToQuote);
		
		scratchPad = PageFactory.initElements(driver, ScratchPadPage.class);
		
		assertTrue(scratchPad.getTableAsMaps().size()==2);
	}
	
	@Test
	public void emptyDocCleansProducts(){
		scratchPad = homePage.goToScratchPadPage(); 		
		addProductIfEmpty();

		scratchPad = scratchPad.clickEmptyDoc();
		
		assertTrue(scratchPad.getTableAsMaps().size() == 0);
	}
	
//	@Test
//	public void copyToCustomerError(){
//		scratchPad = homePage.goToScratchPadPage(); 
//		addProductIfEmpty();
//		
//		exception.expect(org.openqa.selenium.UnhandledAlertException.class);
//		scratchPad.clickCopyToCustomer();
//	}
//	
	@Test
	public void copyToCustomer(){
		scratchPad = homePage.goToScratchPadPage(); 
		addProductIfEmpty();
		
		int productNumBefore = scratchPad.getTableAsMaps().size();
		QuotePage quotePage = scratchPad.clickCopyToCustomer();
		
		assertTrue(productNumBefore == quotePage.getTableAsMaps().size());
	}
	
	@Test
	public void previewWithoutError(){
		scratchPad = homePage.goToScratchPadPage(); 
		addProductIfEmpty();
		
		scratchPad.clickSend();
	}
	
	public void addProductIfEmpty(){
		if(scratchPad.getTableAsMaps().size() ==0){
			ProductsPage productsPage = scratchPad.searchProduct("Lenovo");
			productsPage.checkCompareBoxes(1,2).selectAction(Action.AddToQuote);
			
			scratchPad = PageFactory.initElements(driver, ScratchPadPage.class);		
		}
	}
}
