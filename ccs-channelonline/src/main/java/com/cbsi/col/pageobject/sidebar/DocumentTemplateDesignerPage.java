package com.cbsi.col.pageobject.sidebar;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.ColBasePage;

public class DocumentTemplateDesignerPage extends ColBasePage{

	public DocumentTemplateDesignerPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForTextToBeVisible("Document Template Designer:", "span");
	}
	
	@FindBy(css="button[title='Save']")
	private WebElement Save;
	
	@FindBy(css="button[titl='Cancel']")
	private WebElement Cancel;
	
	@FindBy(css="a#addcomponentbtn-r1c1")
	private WebElement topComponent;
	
	@FindBy(css="a#addcomponentbtn-r2c1")
	private WebElement midComponent1;
	
	@FindBy(css="a#addcomponentbtn-r2c2")
	private WebElement midComponent2;
	
	@FindBy(css="a#addcomponentbtn-r2c3")
	private WebElement midComponent3;
	
	@FindBy(css="a#addcomponentbtn-r3c1")
	private WebElement midComponentLong;
	
	@FindBy(css="a#addcomponentbtn-r3c1")
	private WebElement bottomComponent;
	
	public AddComponentPopup addComponentTop(){
		topComponent.click();
		return PageFactory.initElements(driver, AddComponentPopup.class);	
	}
	
	public DocumentTemplateDesignerPage clickSave(){
		Save.click();
		return this;
	}
	
	public DocumentTemplateDesignerPage clickCancel(){
		Cancel.click();
		return this;
	}
	
	public AddComponentPopup addComponentMidShort(int...location){
		for(int n:location){
			if(n == 1)
				midComponent1.click();
			else if(n==2)
				midComponent2.click();
			else if (n==3)
				midComponent3.click();
		}
		return PageFactory.initElements(driver, AddComponentPopup.class);	
	}
	
	public AddComponentPopup addComponentMidLong(){
		midComponentLong.click();
		return PageFactory.initElements(driver, AddComponentPopup.class);	
	}
	
	public AddComponentPopup addComponentBottom(){
		bottomComponent.click();
		return PageFactory.initElements(driver, AddComponentPopup.class);	
	}
	
	//---------------------------- inner popup class----------------------------//
	public static class AddComponentPopup extends ColBasePage{

		public AddComponentPopup(WebDriver driver) {
			super(driver);
			// TODO Auto-generated constructor stub
			waitForTextToBeVisible("Add Components", "h3");
		}
		
		@FindBy(css="span.btn-save")
		private WebElement Save;
		
		@FindBy(linkText="Company")
		private WebElement CompanyTab;
		
		@FindBy(linkText="Customer")
		private WebElement CustomerTab;
		
		@FindBy(linkText="Document")
		private WebElement DocumentTab;
		
		@FindBy(linkText="Order")
		private WebElement OrderTab;
		
		@FindBy(linkText="Proposal")
		private WebElement ProposalTab;
		
		@FindBy(css="div.tbl-head div input")
		private WebElement checkAll;
		
		public AddComponentPopup fromCompany(){
			CompanyTab.click();
			return this;
		}
		
		public AddComponentPopup fromCustomer(){
			CustomerTab.click();
			return this;
		}
		
		public AddComponentPopup fromDocument(){
			DocumentTab.click();
			return this;
		}
		
		public AddComponentPopup fromOrder(){
			OrderTab.click();
			return this;
		}
		
		public AddComponentPopup fromProposal(){
			ProposalTab.click();
			return this;
		}
		

		
		public AddComponentPopup pickComponents(String...options){
			List<WebElement> spans = driver.findElements(By.cssSelector("div span"));

			if(options[0].toLowerCase().equals("all")){
				List<WebElement> checkBoxAlls= driver.findElements(By.cssSelector("div.tbl-head div input"));
				for(WebElement checkBoxAll: checkBoxAlls){
					if(checkBoxAll.isDisplayed()) checkBoxAll.click();
					break;
				}
			}else{
				for(String s: options){
					for (WebElement span:spans){
						if(s.toLowerCase().equals(span.getText().toLowerCase())){
							WebElement checkBox = span.findElement(By.xpath("../../div/input"));
							checkBox.click();
						}
					}
				}
			}
			
			return this;
		}
		
		public DocumentTemplateDesignerPage clickSave(){
			Save.click();
			return PageFactory.initElements(driver, DocumentTemplateDesignerPage.class);
		}
	}
	
	

}
