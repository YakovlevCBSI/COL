package com.cbsi.col.pageobject.sidebar;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.cbsi.col.pageobject.HomePage.Admin;
import com.cbsi.col.test.foundation.ColBaseTest;

public class TemplatesPageTest extends ColBaseTest{

	public TemplatesPageTest(String url, String browser) {
		super(url, browser);
		// TODO Auto-generated constructor stub
	}
	public DocumentTemplatesPage dtp;
	public String testVar = this.getClass().getSimpleName() + "_" + System.currentTimeMillis();
	@Before
	public void startUp(){
		super.startUp();
		dtp = homePage.navigateToSideBar(Admin.Document_Templates, DocumentTemplatesPage.class);
	}
	
	@Test
	public void createProposalTemplate(){
		DocumentTemplateDesignerPage dtdp = dtp.createNewProposalTemplate(testVar);
		dtdp.addComponentTop().fromCompany().pickComponents("all").clickSave().clickSave();	
		dtp = (DocumentTemplatesPage)dtdp.goToHomePage().navigateToSideBar(Admin.Document_Templates, DocumentTemplatesPage.class);
		
		assertTrue(dtp.hasProposalTemplate(testVar));
	}
	
	@Test
	public void createQuoteTemplate(){
		
	}
}
