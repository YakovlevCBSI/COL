package com.cbsi.col.pageobject.sidebar;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.cbsi.col.pageobject.home.HomePage.Admin;
import com.cbsi.col.test.foundation.ColBaseTest;

public class TemplatesPageTest extends ColBaseTest{

	public TemplatesPageTest(String url, String browser) {
		super(url, browser);
		// TODO Auto-generated constructor stub
	}
	public DocumentTemplatesPage dtp;
	public String testVarCopy;
	public String testVar = this.getClass().getSimpleName() + "_" + System.currentTimeMillis();
	private boolean isProposalTest= false;
	
	@Before
	public void startUp(){
		super.startUp();
		dtp = homePage.navigateToSideBar(Admin.Document_Templates, DocumentTemplatesPage.class);
		System.out.println("testvar: " + testVar);
	}
	
	@After
	public void cleanUp(){
		takeScreenshot();
		super.cleanUp();
		super.startUp();
		dtp = homePage.navigateToSideBar(Admin.Document_Templates, DocumentTemplatesPage.class);
		if(testVarCopy!= null){
			if(!isProposalTest)
				dtp.deleteQuoteTemplateByName(testVarCopy);
			else
				dtp.deleteProposalTemplateByName(testVarCopy);
		}
		
		///Current bug where copying doc removes the original template, therefore this part fails.
		///REMOVE THE TRY-CATCH BLOCK ONCE THE BUG IS RESOLVED.
		if(!isProposalTest)
			dtp.deleteQuoteTemplateByName(testVar);
		else
			dtp.deleteProposalTemplateByName(testVar);
	}
	
	@Test
	public void createProposalTemplate(){
		isProposalTest = true;
		
		DocumentTemplateDesignerPage dtdp = dtp.createNewProposalTemplate(testVar);
		dtdp = dtdp.addComponentTop().fromCompany().pickComponents("Certifications", "Company Info", "Disclaimer").clickSave().clickSave();	
		dtp = dtdp.goToHomePage().navigateToSideBar(Admin.Document_Templates, DocumentTemplatesPage.class);
		
		assertTrue(dtp.hasProposalTemplate(testVar));
	}
	
	@Test
	public void createQuoteTemplate(){
		DocumentTemplatesPage dtp = createQuoteTemplateSetup();
		
		assertTrue(dtp.hasQuoteTemplate(testVar));
	}
	
	@Test
	public void copyQuoteTemplate(){
		DocumentTemplatesPage dtp = createQuoteTemplateSetup();
		dtp = dtp.copyTemplateByName(testVar);
		
		assertTrue(dtp.hasQuoteTemplate(getCopyOfTestVar()));
		assertTrue(dtp.hasQuoteTemplate(testVar));
	}
	
	@Test
	public void editQuoteTemplate(){
		DocumentTemplatesPage dtp = createQuoteTemplateSetup();
		DocumentTemplateDesignerPage dtdp = dtp.editTemplateByName(testVar);
		dtp = dtdp.goToHomePage().navigateToSideBar(Admin.Document_Templates, DocumentTemplatesPage.class);
		
		assertTrue(dtp.hasQuoteTemplate(testVar));
	}
	
	@Test
	public void addComponentsOnQuoteTemplate(){
		DocumentTemplateDesignerPage dtdp = dtp.createNewQuoteTemplate(testVar);
		dtdp = dtdp.addComponentTop().fromCompany().pickComponents("Company Info").clickSave();
		dtdp = dtdp.addComponentMidShort(1).fromCustomer().pickComponents("all").clickSave();
		dtdp = dtdp.addComponentMidShort(2).fromDocument().pickComponents("all").clickSave();
		dtdp = dtdp.addComponentMidLong().fromOrder().pickComponents("all").clickSave();
		dtdp = dtdp.addComponentBottom().fromInvoice().pickComponents("all").clickSave();
		dtdp.clickSave();
		dtp = dtdp.goToHomePage().navigateToSideBar(Admin.Document_Templates, DocumentTemplatesPage.class);	
		
		assertTrue(dtp.hasQuoteTemplate(testVar));
	}
	
	public DocumentTemplatesPage createQuoteTemplateSetup(){
		DocumentTemplateDesignerPage dtdp = dtp.createNewQuoteTemplate(testVar);
		dtdp = dtdp.addComponentTop().fromCompany().pickComponents("Certifications", "Company Info", "Disclaimer").clickSave().clickSave();	
		dtp = dtdp.goToHomePage().navigateToSideBar(Admin.Document_Templates, DocumentTemplatesPage.class);
		return dtp;
	}
	public String getCopyOfTestVar(){
		testVarCopy = "Copy of " + testVar;
		return testVarCopy;
	}
	
	
//	@Test
//	public void deleteTestTemplates(){
////		dtp = dtp.clickProposalsTab();
//		while(true)
//		dtp = dtp.deleteQuoteTemplateByName("_");
//	}

}
