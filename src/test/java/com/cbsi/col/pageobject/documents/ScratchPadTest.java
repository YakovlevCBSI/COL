package com.cbsi.col.pageobject.documents;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.documents.DocumentsBasePage.LineActions;
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
		
		
		
		List<HashMap<String, String>> afterProducts = scratchPad.getTableAsMaps();
		
		assertTrue(afterProducts.size()==2);
	}
}
