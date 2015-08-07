package com.cbsi.col.pageobject.sidebar;

import java.util.List;

import org.openqa.selenium.By;
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
	
	public DocumentTemplatesPage clickQuotes(){
		Quotes.click();
//		waitForActive(By.linkText("Quotes"));
		return PageFactory.initElements(driver, DocumentTemplatesPage.class);
	}
	
	public DocumentTemplatesPage clickProposals(){
		Proposals.click();
//		waitForActive(By.linkText("Proposals"));
		return PageFactory.initElements(driver, DocumentTemplatesPage.class);
	}
	
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
	
// work in progress....
//	public DocumentTemplateDesignerPage createNewQuoteTemplate(){
//		Quotes.click();
//		CreateNewTemplate.click();
//	}
	public boolean hasProposalTemplate(String templateName){
		return findDataRowByName(templateName)==null? false:true;
	}
	
	public boolean hasQuoteTemplate(String templateName){
		return findDataRowByName(templateName)==null? false:true;
	}
	
	List<WebElement> dataColumns;
	public WebElement findDataRowByName(String templateName){
		dataColumns = driver.findElements(By.cssSelector("table.costandard tbody tr td:nth-child(2)"));
		for(WebElement dataColumn: dataColumns){
			if(dataColumn.getText().contains(templateName)){
				return dataColumn;
			}
		}
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
	
//	public void waitForActive(By by){
//		String elementState = "";
//		long start = System.currentTimeMillis();
//		while(System.currentTimeMillis() - start <= 5000){
//			elementState = driver.findElement(by).findElement(By.xpath("../")).getAttribute("class");
//			if(elementState.equals("active"))
//				break;
//		}
//		return;	
//	}

}
