package com.cbsi.col.pageobject.sidebar;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.ColBasePage;

public class DocumentTemplatesPage extends ColBasePage{

	public DocumentTemplatesPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForTextToBeVisible("Document Templates");
	}
	
	@FindBy(linkText="Create New Template")
	private WebElement CreateNewTemplate;
	
	@FindBy(linkText="Quotes")
	private WebElement Quotes;
	
	@FindBy(linkText="Proposals")
	private WebElement Proposals;
	
	public DocumentTemplateDesignerPage createNewProposalTemplate(String name){
		return createNewProposalTemplate(name, "");
	}
	
	public DocumentTemplateDesignerPage createNewProposalTemplate(String name, String desc){
		driver.findElement(By.linkText("Proposals")).click();
		waitForElementToBeInvisible(By.linkText("Proposals"));
		waitForElementToBeVisible(By.linkText("Proposals"));
		CreateNewTemplate.click();
		CreateTemplatePopup ctp = PageFactory.initElements(driver, CreateTemplatePopup.class);
		ctp.setName(name);
		ctp.setDescription(desc);
		ctp.clickOK();
		
		return PageFactory.initElements(driver, DocumentTemplateDesignerPage.class);
	}
	
	public DocumentTemplateDesignerPage createNewQuoteTemplate(String name){
		return createNewQuoteTemplate(name, "");
	}
	
	public DocumentTemplateDesignerPage createNewQuoteTemplate(String name, String desc){
		driver.findElement(By.linkText("Quotes")).click();
//		waitForElementToBeInvisible(By.linkText("Quotes"));
		forceWait(500);
		waitForElementToBeVisible(By.linkText("Quotes"));
		CreateNewTemplate.click();
		CreateTemplatePopup ctp = PageFactory.initElements(driver, CreateTemplatePopup.class);
		ctp.setName(name);
		ctp.setDescription(desc);
		ctp.clickOK();
		
		return PageFactory.initElements(driver, DocumentTemplateDesignerPage.class);
	}
	
	public DocumentTemplatesPage clickProposalsTab(){
		Proposals.click();
		waitForActive(By.linkText("Proposals"));
		return this;
	}
	
	public DocumentTemplatesPage clickQuotesTab(){
		Quotes.click();
		waitForActive(By.linkText("Quotes"));
		return this;
	}
	
	public boolean hasProposalTemplate(String templateName){
		return clickProposalsTab().sortByLastModified().findDataRowByName(templateName)==null? false:true;
	}
	
	public boolean hasQuoteTemplate(String templateName){
		return clickQuotesTab().sortByLastModified().findDataRowByName(templateName)==null? false:true;
	}
	
	public DocumentTemplatesPage deleteQuoteTemplateByName(String templateName){
		WebElement Delete = clickQuotesTab().sortByLastModified().findDataRowByName(templateName).findElement(By.xpath("../td/input[contains(@id, 'delete')]"));
		Delete.click();
		
		forceWait(500);
		driver.switchTo().alert().accept();
		driver.switchTo().defaultContent();
		forceWait(500);

		return PageFactory.initElements(driver, DocumentTemplatesPage.class);
		
	}
	
	public DocumentTemplatesPage deleteProposalTemplateByName(String templateName){
		WebElement Delete = clickProposalsTab().sortByLastModified().findDataRowByName(templateName).findElement(By.xpath("../td/input[contains(@id, 'delete')]"));
		Delete.click();
		
		forceWait(500);
		driver.switchTo().alert().accept();
		driver.switchTo().defaultContent();
		forceWait(500);

		return PageFactory.initElements(driver, DocumentTemplatesPage.class);
		
	}
	
	public DocumentTemplateDesignerPage editTemplateByName(String templateName){
		WebElement edit = findDataRowByName(templateName).findElement(By.xpath("../td/a[@title='Update Page']"));
		edit.click();		
		return PageFactory.initElements(driver, DocumentTemplateDesignerPage.class);		
	}
	
	public DocumentTemplateDesignerPage copyTemplateByName(String templateName){
		WebElement copy = findDataRowByName(templateName).findElement(By.xpath("../td/a[@title='Copy Page']"));
		copy.click();
		return PageFactory.initElements(driver, DocumentTemplateDesignerPage.class);
	}
	
	private static List<WebElement> dataColumns;
	static int currentPage=0;
//	public WebElement findDataRowByName(String templateName){
//		dataColumns = driver.findElements(By.cssSelector("table.costandard tbody tr td:nth-child(2)"));
//		for(WebElement dataColumn: dataColumns){
////			System.out.println("what i see: " + dataColumn.getText());
//			if(dataColumn.getText().contains(templateName)){
//				return dataColumn;
//			}
//		}
//		
//		//navigate to next page if data not found.
//		List<WebElement> pageList = driver.findElements(By.cssSelector("tr.footer td a"));
//		if(currentPage-1 >=0){
//			int removePage = currentPage;
//			while(removePage >0){
//				removePage--;
//				pageList.remove(removePage);
//			}
//		}
//		if(pageList.size() >=1){
//			currentPage++;
//			pageList.get(0).click();
//			waitForTextToBeVisible("Document Templates");
//			dataColumns=null;
//			findDataRowByName(templateName);
//			
//		}
//		
//		return null;		
//	}
	public WebElement findDataRowByName(String templateName){
		dataColumns = driver.findElements(By.cssSelector("table.costandard tbody tr td:nth-child(2)"));
		for(WebElement dataColumn: dataColumns){
//			System.out.println("what i see: " + dataColumn.getText());
			if(dataColumn.getText().contains(templateName)){
				currentPage = 0;
				return dataColumn;
			}
		}
		
		//navigate to next page if data not found.
		List<WebElement> pageList = driver.findElements(By.cssSelector("tr.footer td a"));
		if(currentPage-1 >=0){
			int removePage = currentPage;
			while(removePage >0){
				removePage--;
				pageList.remove(removePage);
			}
		}
		if(pageList.size() >=1){
			currentPage++;
			pageList.get(0).click();
			waitForTextToBeVisible("Document Templates");
			dataColumns=null;
			findDataRowByName(templateName);
			
		}
		
		currentPage = 0;
		return null;		
	}
	
	public static class CreateTemplatePopup extends ColBasePage{

		public CreateTemplatePopup(WebDriver driver) {
			super(driver);
			// TODO Auto-generated constructor stub
			waitForTextToBeVisible("Create Template", "h3");
		}

		
		@FindBy(css="input#pageSchemaName[required='true']")
		private WebElement Name;
		
		@FindBy(css="input#pageSchemaDesc")
		private WebElement Description;
		
		@FindBy(css="button#save-tpl-btn")
		private WebElement OK;
		
		@FindBy(css="button.close-modal")
		private WebElement Cancel;
		
		public CreateTemplatePopup setName(String name){
			Name.sendKeys(name);
			return this;
		}
		
		public CreateTemplatePopup setDescription(String desc){
			Description.sendKeys(desc);
			return this;
		}
		
		public void clickOK(){
			OK.click();
		}
	}
	
	public void waitForActive(By by){
		String elementState = "";
		waitForElementToBeVisible(by);
		long start = System.currentTimeMillis();
		while(System.currentTimeMillis() - start <= 10000){
			try{
			elementState = driver.findElement(by).findElement(By.xpath("..")).getAttribute("class");
			if(elementState.equals("active"))
				break;
			}catch(StaleElementReferenceException e){
				
			}
			forceWait(300);
		}
		return;	
	}
	
	
	//----------------------------------------------- sort table---------------------------------------------//
	@FindBy(linkText="Last Modified")
	private WebElement LastModified;
	
	public DocumentTemplatesPage sortByLastModified(){
		LastModified.click();
		waitForElementToBeInvisible(By.linkText("Last Modified"));
		waitForElementToBeVisible(By.linkText("Last Modified"));
		
		//----  temporary workaround for wrong pagination issue--//
		List<WebElement> pageList = driver.findElements(By.cssSelector("tr.footer td a"));
		if(pageList.size() >=1){
			pageList.get(1).click();
			forceWait(1000);
			List<WebElement> pageListNew = driver.findElements(By.cssSelector("tr.footer td a"));
			pageListNew.get(0).click();
		}
		
		return PageFactory.initElements(driver, DocumentTemplatesPage.class);

	}

}
