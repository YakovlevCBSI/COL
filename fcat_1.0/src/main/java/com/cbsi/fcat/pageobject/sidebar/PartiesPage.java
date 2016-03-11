package com.cbsi.fcat.pageobject.sidebar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.fcat.pageobject.foundation.BasePage;

public class PartiesPage extends BasePage{

	public PartiesPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForTextToBeVisible("List all Partys", "span");
	}

	@FindBy(css="a[href*='partys?form']")
	private WebElement CreateNewParty;
	
	@FindBy(css="table")
	private WebElement Table;
	
	@FindBy(css="a[title='First Page']")
	private WebElement FirstPage;
	
	@FindBy(css="a[title='Previous Page']")
	private WebElement PreviousPage;

	@FindBy(css="a[title='Next Page']")
	private WebElement NextPage;
	
	@FindBy(css="a[title='Last Page']")
	private WebElement LastPage;
	
	
	public static final String showPartyXPath = "td[@class='utilbox']/a[@title='Show Party']";
	public static final String updatePartyXPath = "td[@class='utilbox']/a[@title='Update Party']";
	public static final String deletePartyXPath = "td[@class='utilbox']/form/input[@value='DELETE']";
	
	public PartiesPage clickFirstPage(){
		FirstPage.click();
		forceWait(500);
		return PageFactory.initElements(driver, PartiesPage.class);
	}
	
	public PartiesPage clickPreviousPage(){
		PreviousPage.click();
		forceWait(500);
		return PageFactory.initElements(driver, PartiesPage.class);
	}
	
	public PartiesPage clickNextPage(){
		NextPage.click();
		forceWait(500);
		return PageFactory.initElements(driver, PartiesPage.class);
	}
	
	public PartiesPage clickLastPage(){
		LastPage.click();
		forceWait(500);
		return PageFactory.initElements(driver, PartiesPage.class);
	}
	
	public UpdatePartyPage clickCreateNewParty(){
		CreateNewParty.click();
		forceWait(500);
		return PageFactory.initElements(driver, UpdatePartyPage.class);
	}
	
	public ShowPartyPage showParty(String partyName){
		findPartyRowByName(partyName).findElement(By.xpath(showPartyXPath)).click();
		return PageFactory.initElements(driver, ShowPartyPage.class);
	}
	
	public UpdatePartyPage updateParty(String partyName){
		findPartyRowByName(partyName).findElement(By.xpath(updatePartyXPath)).click();
		return PageFactory.initElements(driver, UpdatePartyPage.class);
	}
	
	public PartiesPage deleteParty(String partyName){
		findPartyRowByName(partyName).findElement(By.xpath(deletePartyXPath)).click();
		acceptAlert();
		
		return PageFactory.initElements(driver, PartiesPage.class);

	}
	
	public static boolean isOnLastPage = false;
	
	public WebElement findPartyRowByName(String partyName){
		WebElement tr = null;
		for(WebElement td: Table.findElements(By.xpath("td[1])"))){
			if(td.getText().equalsIgnoreCase(partyName)){
				tr =  td.findElement(By.xpath(".."));
				return tr;
			}
		}
		
		if(tr == null){
			if(!isOnLastPage){
				clickLastPage();
				isOnLastPage = true;
			}else{
				clickPreviousPage();
			}
			findPartyRowByName(partyName);
		}
		
		return null;
	}
	
	//---------------- show party page elements -------------------//
	public static class ShowPartyPage extends BasePage{
		public ShowPartyPage(WebDriver driver){
			super(driver);
			waitForTextToBeVisible("Show Party", "span");
		}
		
		@FindBy(css="div[id*='Party_partyName_id'] div")
		private WebElement PartyName;
		
		@FindBy(css="div[id*='Party_description_id'] div")
		private WebElement Description;
		
		@FindBy(css="div[id*='Party_lastModified_id'] div")
		private WebElement LastModified;
		
		public String getPartyName(){
			return PartyName.getText();
		}
		
		public String getDescription(){
			return Description.getText();
		}
		
		public String getLastModified(){
			return LastModified.getText();
		}
	}
	//---------------- update party page elements -------------------//

	public static class UpdatePartyPage extends BasePage{
		public UpdatePartyPage(WebDriver driver) {
			super(driver);
			// TODO Auto-generated constructor stub
			waitForTextToBeVisible(" Party", "span");
		}

		@FindBy(css="input#_partyName_id")
		private WebElement PartyName;
		
		@FindBy(css="input#_description_id")
		private WebElement Description;
		
		@FindBy(css="input[value='Save']")
		private WebElement Save;
		
		@FindBy(css="a[title='List all Partys']")
		private WebElement ListAllPartys;
		
		public void setPartyName(String name){
			PartyName.clear();
			PartyName.sendKeys(name);
		}
		
		public String getPartyName(){
			return PartyName.getAttribute("value");
		}
		
		public void setDescription(String description){
			Description.clear();
			Description.sendKeys(description);
		}
		
		public String getDescription(){
			return Description.getAttribute("value");
		}
		
		public PartiesPage clickSave(){
			Save.click();
			waitForTextToBeVisible("Show Party", "span");
			
			return clickListAllPartys();
		}
		
		public PartiesPage clickListAllPartys(){
			ListAllPartys.click();
			return PageFactory.initElements(driver, PartiesPage.class);
		}
		
	}
}
