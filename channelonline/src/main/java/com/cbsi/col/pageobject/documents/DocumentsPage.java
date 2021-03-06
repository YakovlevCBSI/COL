package com.cbsi.col.pageobject.documents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.text.WordUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cbsi.col.pageobject.customers.CurrentAccountTab;
import com.cbsi.col.pageobject.home.ColBasePage;

public class DocumentsPage extends ColBasePage{
	public final Logger logger = LoggerFactory.getLogger(DocumentsPage.class);
	
	public DocumentsPage(WebDriver driver){
		super(driver);
//		waitForPageToLoad(By.cssSelector("div h1"));
//		waitForTextToBeVisible("Documents","h1");
		try{
		waitForTextToBeVisible("Recycle Bin", "ul li.last.pull-right a");
		}catch(Exception e){
			e.printStackTrace();
		}
		waitForTextToBeVisible("Quotes", "ul li a");
	}
	
	@FindBy(css="table.costandard")
	private WebElement Table;
	
	public List<String> getDescriptionTitles(){
		List<WebElement> descriptions = driver.findElements(By.cssSelector("div[id^='lineDescriptionOne']"));
		List<String> descriptionList = new ArrayList<String>();
		for(WebElement description: descriptions){
			descriptionList.add(description.getText());
		}
		
		return descriptionList;
	}
	
	public void searchQuote(){
		
	}
//	public boolean hasQuote(int docNumber){
//		List<WebElement> docNumbers = driver.findElements(By.xpath("//td[2]/a"));
//		for(WebElement singleDoc: docNumbers){
//			if(singleDoc.getText().equals(docNumber+"")){
//				return true;
//			}
//		}
//		return false;
//	}
	
//	@FindBy(css="div.btn-toolbar div a[href*='deleted=true']")
	@FindBy(linkText="Recycle Bin")
	private WebElement RecycleBin;
	
	public RecycleBinPage clickRecycleBin(){
		RecycleBin.click();
		return PageFactory.initElements(driver, RecycleBinPage.class);
	}
	
	public boolean hasQuote(long docNumber){
		return hasDoc(docNumber);
	}
	
	public boolean hasProposal(long docNumber){
		return hasDoc(docNumber, false);

	}
	
	public boolean hasInvoice(long docNumber){
		logger.info("Looking for invoice #" + docNumber);
		return hasDoc(docNumber);
	}

	public boolean hasSalesOrder(long docNumber){
		return hasDoc(docNumber);
	}
	
	public boolean hasRma(long docNumber){
		return hasDoc(docNumber);
	}
	
	public boolean hasDoc(long docNumber){
		setFilterByModifiedToDefault();		
		return findDataRowByName(docNumber, true)==null? false:true;

	}
	
	public boolean hasDoc(long docNumber, boolean secondColumn){
		return findDataRowByName(docNumber, secondColumn)==null? false:true;

	}
	
	public QuotePage goToQuote(long docNumber){
		try{
			goToDocument(docNumber);
			return PageFactory.initElements(driver, QuotePage.class);

		}catch(UnhandledAlertException e){
			logger.debug("handle expired alert");
			acceptAlert();			
		}
		return PageFactory.initElements(driver, QuotePage.class);
	}
	
	public SalesOrderPage goToOrder(long docNumber){
		goToDocument(docNumber);	
		return PageFactory.initElements(driver, SalesOrderPage.class);
	}
	
	public ProposalPage goToProposal(long orderNumber){
		WebElement docLink = findDataRowByName(orderNumber, false);
		docLink.click();
		try{		
			return PageFactory.initElements(driver, ProposalPage.class);
		}catch(Exception e){
			refresh();
		}
		return PageFactory.initElements(driver, ProposalPage.class);
	}
	
	
	public RMAPage goToRma(long docNumber){
		goToDocument(docNumber);		
		return PageFactory.initElements(driver, RMAPage.class);
	}
	
	public void goToDocument(long docNumber){
		logger.info("Go to doc #" + docNumber);
		WebElement docLink = findDataRowByName(docNumber);
		docLink.click();
	}
	
	public <T>T goToDoc(long docNumber, Class clazz){
		logger.info("Go to doc #" + docNumber);
		WebElement docLink = findDataRowByName(docNumber);
		docLink.click();
		
		return (T)PageFactory.initElements(driver, clazz);
	}
	
//	public DocumentsPage deleteQuotesByCompnayName(String companyName){
////		WebElement docLink = findDataRowByName(quoteNumber);
//		forceWait(300);
//		List<WebElement> companyList = driver.findElements(By.cssSelector("table td:nth-child(3) a"));
//		WebElement singleCompany = null;
//		for(WebElement company: companyList){
//			System.out.println("c name: " + company.getText());
//			if(company.getText().toLowerCase().contains(companyName.toLowerCase()) && company.isDisplayed()) {
//				singleCompany = company;
//				break;
//			}
//			
//		}
//		System.out.println("deleting " + singleCompany.getText());
//		singleCompany.findElement(By.xpath("../../td/input[contains(@id,'delete')]")).click();
//	
////		acceptAlert();
//		
//		forceWait(1000);
//		driver.findElement(By.cssSelector("#delete-doc-btn")).click();
//	
//		forceWait(700);
//		return PageFactory.initElements(driver, DocumentsPage.class);
//	}
	
	public DocumentsPage deleteDocumentByCompanyName(String companyName){
//		WebElement docLink = findDataRowByName(quoteNumber);
		forceWait(300);
		WebElement company = findDataRowByName(companyName, 3, false);
		
		if(company == null){
			company = findDataRowByName(companyName, 2, false);
		}
		
		System.out.println("deleting " + company.getText());
		WebElement doc = company.findElement(By.xpath("../../td/input[contains(@id,'delete')]"));
		scrollToView(doc);
		doc.click();
	
//		acceptAlert();
		
		waitForTextToBeVisible("Are you sure", "div form p");
//		forceWait(1000);
		for(WebElement del:driver.findElements(By.cssSelector("button[id^='delete-'][id*='-btn']"))){
			if(del.isDisplayed()){
				scrollToView(del);
				del.click();
				break;
			}
		}
	
		forceWait(700);
		return PageFactory.initElements(driver, DocumentsPage.class);
	}
	
	public DocumentsPage deleteQuoteByDocNumber(long docNumber){
//		findDataRowByName(docNumber).findElement(By.xpath("../../td[14]/input")).click();
		findDataRowByName(docNumber).findElement(By.xpath("../../td/input[contains(@id, 'delete')]")).click();
		
		logger.info("deleting " + docNumber);	

		waitForTextToBeVisible("Are you sure","p");
//		WebElement del = driver.findElement(By.cssSelector("button[id^='delete-'][id*='-btn']"));
		WebElement del = driver.findElement(By.cssSelector("button[id^='delete-doc-btn']"));

		del.click();
	
		forceWait(700);
		
		return PageFactory.initElements(driver, DocumentsPage.class);
	}
	
	public DocumentsPage deleteDocument(long docNumber){
		findDataRowByName(docNumber, false).findElement(By.xpath("../../td/input[contains(@id, 'delete')]")).click();
		logger.info("deleting " + docNumber);	

		waitForTextToBeVisible("Are you sure","p");
		WebElement del = driver.findElement(By.cssSelector("button[id^='delete-']"));

		del.click();
	
		forceWait(700);
		
		return PageFactory.initElements(driver, DocumentsPage.class);
	}
	
	private WebElement ViewQuote;
	
	static int currentPage=0;
	private static List<WebElement> dataColumns;
	
	public WebElement findDataRowByName(long quoteNumber){
		return findDataRowByName(quoteNumber, true);
	}
	
	public WebElement findDataRowByName(long quoteNumber, boolean docNumberSecondcolumn){
		if(docNumberSecondcolumn){
			dataColumns = driver.findElements(By.cssSelector("table.costandard tbody tr td:nth-child(2) a"));
		}else{
			dataColumns = driver.findElements(By.cssSelector("table.costandard tbody tr td:nth-child(1) a"));
		}
		
		for(WebElement dataColumn: dataColumns){
//			System.out.println(dataColumn.getText());
			if(dataColumn.getText().contains(quoteNumber+"")){
				return dataColumn;
			}
		}
		
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

			waitForTextToBeVisible("Documents", "h1");
	
			dataColumns=null;
			return findDataRowByName(quoteNumber, docNumberSecondcolumn);
		}
		
		return null;		
	}
	
	public QuotePage clickViewQuote(int quoteNumber){
		WebElement dataRow = findDataRowByName(quoteNumber);
		ViewQuote = dataRow.findElement(By.xpath("../td[2]/a"));
		ViewQuote.click();
		
		waitForElementToBeInvisible(By.xpath("td/a[contains(@title,'View Customer')]"));
		return PageFactory.initElements(driver, QuotePage.class);
	}
	
// wrong method use goToOrder instead.
//	public SalesOrderPage clickViewSalesOrder(int orderNumber){
//		WebElement dataRow = findDataRowByName(orderNumber);
//		ViewQuote = dataRow.findElement(By.xpath("../td[2]/a"));
//		ViewQuote.click();
//		
//		waitForElementToBeInvisible(By.xpath("td/a[contains(@title,'View Customer')]"));
//		return PageFactory.initElements(driver, SalesOrderPage.class);
//	}
	
	public DocumentsPage switchToTab(DocumentTabs tab){
//		if(tab == DocumentTabs.QUOTES){
//			driver.findElement(By.cssSelector("li a[href*='" + WordUtils.capitalizeFully(tab.toString()) + "/listQuotes'")).click();
//		}else{	
//			driver.findElement(By.cssSelector("li a[href*='" + (tab.toString()))).click();
//		}
		
		try{
			driver.findElement(By.cssSelector("li a[href*='" + (tab.toString()))).click();
			forceWait(500);
		}catch(NoSuchElementException e){
			driver.findElement(By.cssSelector("li a[href*='" + (tab.toStringAlt()))).click();
			forceWait(500);
		}
		try{
			waitForElementToBeInvisible(By.cssSelector("select#time_limit"), 5);
		}catch(TimeoutException e){
			
		}
		waitForElementToBeVisible(By.cssSelector("select#time_limit"));
		
		return PageFactory.initElements(driver, DocumentsPage.class);
	}
	
	@FindBy(css="select#status")
	private WebElement StatusDrpdown;
	public DocumentsPage filterByStatus(Status status){
		StatusDrpdown.click();
		driver.findElement(By.cssSelector("option[value='" + status.toString().toLowerCase().replace("s_", "|") + "'")).click();
//		driver.findElement(By.cssSelector("option[value*='" + status.toString().toLowerCase().replace("_", " ("))).click();

		try{
			waitForElementToBeInvisible(By.cssSelector("select#time_limit"), 5);
		}catch(TimeoutException e){
			
		}
		
		return PageFactory.initElements(driver, DocumentsPage.class);
	}
	
	@FindBy(css="select#saved_by_id")
	private WebElement ModifiedBy;
	
	@FindBy(css="select#contactList")
	private WebElement ContactList;
	
	public DocumentsPage clickContactList(){
		ContactList.click();
		return this;
	}
	
	public List<String> getContactList(){
		List<String> contacts = new ArrayList<String>();
		for(WebElement w: ContactList.findElements(By.xpath("option"))){
			contacts.add(w.getText().toLowerCase());
		}
		contacts.remove(0);
		return contacts;
	}
	
	public DocumentsPage clickModifiedBy(){
		ModifiedBy.click();
		return this;
	}
	
	public List<String> getModifiedByList(){
		List<String> modifiedBysNames = new ArrayList<String>();
		List<WebElement> modifiedBys = ModifiedBy.findElements(By.xpath("option"));
		
		for(int i=1; i<modifiedBys.size(); i++){
			modifiedBysNames.add(modifiedBys.get(i).getText().toLowerCase());
		}
		
		return modifiedBysNames;
	}
	
	public DocumentsPage setFilterByModifiedBy(String person){
		WebElement userOption = null;
		clickModifiedBy();
		List<WebElement> persons = ModifiedBy.findElements(By.xpath("option"));
		
		for(WebElement p: persons){
			if(p.getText().toLowerCase().replaceAll("[, \\s+]", "").contains(person.toLowerCase().replaceAll("[, \\s+]", ""))){
				userOption = p;
				break;
			}
//			if(p.getText().replaceAll(",", "").contains(person.toLowerCase().replaceAll(",", "").split("\\s+")[0])){
//				userOption = p;
//				break;
//			}
		}
		if(userOption != null){
			forceWait(1000);
			userOption.click();

		}
		else
			System.err.println("\"" + person + "\" is not found in Modified dropdown" );
		
		try{
			waitForElementToBeInvisible(By.cssSelector("select#time_limit"), 5);
		}catch(TimeoutException e){
			
		}
		
		return PageFactory.initElements(driver, DocumentsPage.class);	
	}
	
	public String getFilterByModified(){
	
		List<WebElement> users = ModifiedBy.findElements(By.xpath("option"));
		for(WebElement user: users){
			if(user.getAttribute("selected") != null){
				return user.getText();
			}
		}
		
		WebElement defaultUser = ModifiedBy.findElement(By.xpath("option[1]"));
		return defaultUser.getText();
	}
	
	public DocumentsPage setFilterByModifiedToDefault(){
		logger.debug("checking modfiied default");
		if(!ModifiedBy.findElement(By.xpath("option[1]")).isDisplayed()){
			System.out.println("Now set to default...");
			setFilterByModifiedBy("Buyers (All)");
		}
		return this;
	}
	
	public List<LinkedHashMap<String, String>> getTableAsMaps() {
		return this.getTableAsMaps(Table, 0,12);
	}
	
	public List<LinkedHashMap<String, String>> getTableAsMaps(String key, String value) {
		return getTableAsMaps(1,Table, key, value,  0,12);
	}

	
	private static int currentPageNum= 1;
	public DocumentsPage goToNextPage(){
		driver.findElement(By.linkText("Next")).click();
		currentPageNum++;
		
		return PageFactory.initElements(driver, DocumentsPage.class);
	}

	
	public enum Status{
		ALL,
		QUOTES_ALL,
		QUOTES_OPEN,
		QUOTES_LOST,
		QUOTES_HOLD,
		QUOTES_WON,
		
		ORDERS_ALL,
		ORDERS_PENDING,
		ORDERS_SUBMITTED,
		ORDERS_COMPLETE,
		
		INVOICES_DUE
		
		
	}	
	
	public enum DocumentTabs{
		ALLQUOTESANDORDERS{
			public String toString(){
				return "Docs/list";
			}
		},
		QUOTES{
			public String toString(){
				return "Docs/listQuotes";
			}
		},
		PROPOSALS{
			public String toStringAlt(){
				return "Proposals/list";
			}
			
			public String toString(){
				return "Proposals/customerList?";
			}
		},
		ORDERS{
			public String toString(){
				return "Docs/listOrders";
			}
			
		},
		INVOICES{
			public String toString(){
				return "Docs/listInvoices";
						
			}
		},
		RMAS{
			public String toString(){
				return "Rmas/list";
			}
		};

		public String toStringAlt() {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	public static void main(String[] args){
		String s = WordUtils.capitalizeFully("QUOTES");
		System.out.println(s);
	}
	
}
