package com.cbsi.col.pageobject.sidebar;

import static org.junit.Assert.assertTrue;

import org.junit.After;
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
	public String testVarCopy;
	public String testVar = this.getClass().getSimpleName() + "_" + System.currentTimeMillis();
	@Before
	public void startUp(){
		super.startUp();
		dtp = homePage.navigateToSideBar(Admin.Document_Templates, DocumentTemplatesPage.class);
		System.out.println("testvar: " + testVar);
	}
	
	@After
	public void cleanUp(){
		super.cleanUp();
		super.startUp();
		dtp = homePage.navigateToSideBar(Admin.Document_Templates, DocumentTemplatesPage.class);
		if(testVarCopy!= null){
			System.out.println("TRYING TO DELETE " + testVarCopy);
			dtp.sortByLastModified().deleteTemplateByName(testVarCopy);
		}
		
		///Current bug where copying doc removes the original template, therefore this part fails.
		///REMOVE THE TRY-CATCH BLOCK ONCE THE BUG IS RESOLVED.
		try{
			dtp.sortByLastModified().deleteTemplateByName(testVar);
		}catch(NullPointerException e){
			
		}

		super.cleanUp();
	}
	
	@Test
	public void createProposalTemplate(){
		DocumentTemplateDesignerPage dtdp = dtp.createNewProposalTemplate(testVar);
		System.out.println(testVar);
		dtdp.addComponentTop().fromCompany().pickComponents("Certifications", "Company Info", "Disclaimer").clickSave().clickSave();	
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
		DocumentTemplateDesignerPage dtdp = dtp.sortByLastModified().copyTemplateByName(testVar);
		dtp = dtdp.goToHomePage().navigateToSideBar(Admin.Document_Templates, DocumentTemplatesPage.class);
		
		assertTrue(dtp.sortByLastModified().hasQuoteTemplate(getCopyOfTestVar()));		
	}
	
	@Test
	public void editQuoteTemplate(){
		DocumentTemplatesPage dtp = createQuoteTemplateSetup();
		DocumentTemplateDesignerPage dtdp = dtp.sortByLastModified().editTemplateByName(testVar);
		dtp = dtdp.goToHomePage().navigateToSideBar(Admin.Document_Templates, DocumentTemplatesPage.class);
		
		assertTrue(dtp.sortByLastModified().hasQuoteTemplate(testVar));
	}
	
	@Test
	public void addComponents(){
		DocumentTemplateDesignerPage dtdp = dtp.createNewQuoteTemplate(testVar);
		dtdp = dtdp.addComponentTop().fromCompany().pickComponents("Company Info").clickSave();
		dtdp = dtdp.addComponentMidShort(1).fromCustomer().pickComponents("all").clickSave();
		dtdp = dtdp.addComponentMidShort(2).fromDocument().pickComponents("all").clickSave();
		dtdp = dtdp.addComponentMidLong().fromOrder().pickComponents("all").clickSave();
		dtdp = dtdp.addComponentBottom().fromInvoice().pickComponents("all").clickSave();
		dtdp.clickSave();
		dtp = dtdp.goToHomePage().navigateToSideBar(Admin.Document_Templates, DocumentTemplatesPage.class);		
	}
	
	public DocumentTemplatesPage createQuoteTemplateSetup(){
		DocumentTemplateDesignerPage dtdp = dtp.createNewQuoteTemplate(testVar);
		System.out.println(testVar);
		dtdp.addComponentTop().fromCompany().pickComponents("Certifications", "Company Info", "Disclaimer").clickSave().clickSave();	
		dtp = dtdp.goToHomePage().navigateToSideBar(Admin.Document_Templates, DocumentTemplatesPage.class);
		return dtp;
	}
	public String getCopyOfTestVar(){
		testVarCopy = "Copy of " + testVar;
		return testVarCopy;
	}

}
