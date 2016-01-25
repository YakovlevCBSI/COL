package com.cbsi.col.pageobject.documents;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cbsi.col.pageobject.customers.CurrentAccountTab;
import com.cbsi.col.pageobject.documents.OrderOptionsPage.Payment;
import com.cbsi.col.pageobject.documents.QuotePage.CopyToNewQuotePage;
import com.cbsi.col.pageobject.documents.SalesOrderPage.CreatePoPopup;
import com.cbsi.col.pageobject.home.ColBasePage;
import com.cbsi.col.pageobject.home.OrganizerPopup;
import com.cbsi.col.pageobject.products.AddToCatalogsPage;
import com.cbsi.col.pageobject.products.ProductsPage;
import com.cbsi.col.pageobject.purchaseorders.PurchaseOrdersTab;
import com.cbsi.col.test.util.GlobalProperty;
import com.cbsi.col.test.util.StringUtil;

public class DocumentsBasePage<T> extends ColBasePage{
	public final Logger logger = LoggerFactory.getLogger(DocumentsBasePage.class);

	public DocumentsBasePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		logger.debug("invoking documentsBasePage");
	}

	public long getDocNumber(){
		long quoteInt;
		try{
			String quoteNumber= driver.findElement(By.cssSelector("div.control-column div.readonly-text")).getText();
			quoteNumber = quoteNumber.split("-")[0].trim();
			quoteInt = Long.parseLong(quoteNumber);
		}catch(NumberFormatException e){			
			String quoteNumber= driver.findElement(By.cssSelector("input#quoteNumber")).getAttribute("value");
			quoteInt = Long.parseLong(quoteNumber);
		}
		
		logger.info(quoteInt+"");
		return quoteInt;
	}
	//--------------------------  Info ---------------------------//
	@FindBy(css="div#company-name span a")
	private WebElement CompanyName;
	
	public CurrentAccountTab clickCompanyLink(){
		waitForQuickLoad();
		CompanyName.click();
		forceWait(500);
		return PageFactory.initElements(driver, CurrentAccountTab.class);
	}
	
	public String getCompanyName(){
		return CompanyName.getText();
	}
	
	@FindBy(css="div#contact-name")
	private WebElement Contact;
	
	@FindBy(css="a#contact-edit")
	private WebElement ContactEdit;
	
	@FindBy(css="input#description")
	private WebElement Description;

	public T setContact(){
		if(getContact() == null) ContactEdit.click();
		
		return (T)PageFactory.initElements(driver, this.getClass());
	}
	
	public String getContact(){
		if(Contact.getText().toLowerCase().contains("n/a")) return null;
		
		return Contact.getText();
	}
	
	public String getDescription(){
		return Description.getAttribute("value");
	}
	
	public T setDescription(String desc){
		Description.clear();
		Description.sendKeys(desc);
		
		return (T)this;
	}
	
	//--------------------------  Bottom bar---------------------------//
	@FindBy(css="button#next-action_save")
	private WebElement Save;
	
	@FindBy(css="li a[id*='action_saveNewRevision']")
	private WebElement SaveAsNewRevision;
	
	@FindBy(css="li a[id*='action_saveAndClose']")
	private WebElement SaveAndClose;
	
	@FindBy(css="button#next-action_copyToQuote")
	private WebElement CopyToNewQuote;
	
//	@FindBy(css="button#ld-nextaction-caret")
	private WebElement Caret;
	
	@FindBy(css="button[id^='next-action_order']")
	private WebElement ConvertToOrder;
	
	@FindBy(css="button#next-action_send")
	private WebElement Send;
	
	@FindBy(css="a#next-action_esign")
	private WebElement ElectronicSignature;
	
	@FindBy(css="div.btn-group.dropup button#next-action_cancelOrder")
	private WebElement CancelThisOrder;
	
	@FindBy(css="div button[id *= '_convertToInvoice']")
	private WebElement ConvertToInvoice;
	
	@FindBy(css="li a[id *= '_completeInvoice']")
	private WebElement CompletInvoice;
	
	@FindBy(css="button[id*='live_cost_']")
	private WebElement LiveCost;
	
	@FindBy(css="")
	private WebElement CustomButtonTest;
	
	@FindBy(css="Exp")
	private WebElement ExportToAutoTask;
	
	public T clickLiveCost(){
		LiveCost.click();
		waitForQuickLoad();
		
		return (T)PageFactory.initElements(driver, this.getClass());
	}
	
	public T clickSave(){
		Save.click();
		
		waitForQuickLoad();
		return (T)this;
	}
	
	public T clickSaveAndClose(){
		Caret = Save.findElement(By.xpath("../button[contains(@id,'-caret')]"));
		Caret.click();
		quickWait();
		SaveAndClose.click();
		quickWait();
		forceWait(500);
		
		if(isAlertPresent()){
			acceptAlert();
		}
		
		return (T)this;
	}
	
	public T clickSaveAsNewRevision(String description){
		Caret = Save.findElement(By.xpath("../button[contains(@id,'-caret')]"));
		Caret.click();
		quickWait();
		SaveAsNewRevision.click();
		
		waitForAlert();	//wait for firrst alert to input descrption.
		Alert alert = driver.switchTo().alert();
		alert.sendKeys(description);
		alert.accept();
		
		waitForAlert(); //wait for second alert to confirmation.
		acceptAlert();
		return (T)this;
	}
	public QuotePage clickCopyToNewQuote(){
		CopyToNewQuote.click();
		CopyToNewQuotePage copyToNewQuotePage = PageFactory.initElements(driver, CopyToNewQuotePage.class);

		waitForQuickLoad();
		return copyToNewQuotePage.clickCreate();
	}
	
	public AddressPage clickConvertToOrder(){

		ConvertToOrder.click();
		return PageFactory.initElements(driver, AddressPage.class);
	}	
	
	public OrderOptionsPage clickConvertToOrderThenPayment(){

		ConvertToOrder.click();
		forceWait(1000);
		return PageFactory.initElements(driver, OrderOptionsPage.class);
	}
	
	public SendPage clickSend(){
		Send.click();
		waitForQuickLoad();
		forceWait(500);
		return PageFactory.initElements(driver, SendPage.class);
	}
	
	public T clickElectronicSignature(){
		Caret = Send.findElement(By.xpath("../button[@id='ld-nextaction-caret']"));
		Caret.click();
		forceWait(500);

		ElectronicSignature.click();
		
//		forceWait(5000);//delete
		if(isAlertPresent()){
			waitForAlert();
			acceptAlert();
		}	
		
		try{
			waitForQuickLoad(10);
		}catch(Exception e){
			logger.debug("quickLoad wait method is skipped.");
		}
		waitForAlert();

		if(isAlertPresent()){
			acceptAlert();
		}	

		OrderOptionsPage orderOptions = PageFactory.initElements(driver, OrderOptionsPage.class);
		orderOptions.setPoNumber(123);
		orderOptions.setPaymentMethod(Payment.COD);
		
		SendPage sendPage  = (SendPage) orderOptions.clickSave(SendPage.class);
		sendPage.clickSendEsignLink("cbsiqa@gmail.com");		
		
		return (T)PageFactory.initElements(driver, this.getClass());
	}

	@FindBy(css="a.btn.btn-primary")
	private WebElement CreateInvoice;
	
	public InvoicePage clickConvertToInvoice(){
//			CancelThisOrder.findElement(By.xpath("../button[@id='ld-nextaction-caret']")).click();
		try{
			ConvertToInvoice.click(); //this is legacy.
		}catch(NoSuchElementException e){
			selectCreateDoc(Doc.CreateInvoiceAll);
			waitForQuickLoad();
			switchFrame();
			waitForTextToBeVisible("Create Invoice", "h3");
			CreateInvoice.click();
			switchBack();
				
		}
		return PageFactory.initElements(driver, InvoicePage.class);	
	}
	
	@FindBy(css="a#next-action_hotlist")
	private WebElement AddToHotList;
	public T clickAddToHotList(){
		Caret = CopyToNewQuote.findElement(By.xpath("../button[contains(@id,'-caret')]"));
		Caret.click();
		forceWait(700);
		AddToHotList.click();
		waitForQuickLoad();

		return (T) PageFactory.initElements(driver, this.getClass());
	}
	//----------------------- inner page object: calculator  -----------------------//
	public PriceCalculator priceCalculator;
	
	public void initializePriceCalculator(){
		 priceCalculator = PageFactory.initElements(driver, PriceCalculator.class);
	}
	
	public PriceCalculator getPriceCalculator(){
		initializePriceCalculator();
		return priceCalculator;
	}
	
	public static class PriceCalculator extends ColBasePage{
		public PriceCalculator(WebDriver driver) {
			super(driver);
			// TODO Auto-generated constructor stub
		}

		@FindBy(css="input#taxableTotal")
		private WebElement TaxableItems;
		
		@FindBy(css="input#taxRate")
		private WebElement TaxOn;
		
		@FindBy(css="input#totalTaxAmount")
		private WebElement TotalTaxAmount;
		
		@FindBy(css="input#taxedTotal")
		private WebElement TaxedSubTotal;
		
		@FindBy(css="input#nonTaxableSubtotal")
		private WebElement NontaxableItems;
		
		@FindBy(css="input#shippingAmount")
		private WebElement ShippingAmount;
		
		@FindBy(css="input#miscAmount")
		private WebElement Misc;
		
		@FindBy(css="input#nonTaxableTotal")
		private WebElement NonTaxableSubTotal;
		
		@FindBy(css="input#total-credit")
		private WebElement Total;
		
		@FindBy(css="select#shippingType")
		private WebElement ShippingType;

		@FindBy(css="span#subtotal")
		private WebElement SubTotal;
		
		public double getTaxOn() {
			return Double.parseDouble(TaxOn.getAttribute("value"));
		}

		public void setTaxOn(double taxOn) {
			cleanInput(TaxOn);
			TaxOn.sendKeys(taxOn+"");
			TaxOn.sendKeys(Keys.TAB);
			forceWait(200);
		}

		public double getShippingAmount() {
			return Double.parseDouble(StringUtil.cleanCurrency(ShippingAmount.getAttribute("value")));
		}

		public void setShippingAmount(double shippingAmount) {
			cleanInput(ShippingAmount);
			ShippingAmount.sendKeys(shippingAmount+"");
			ShippingAmount.sendKeys(Keys.TAB);
		}

		public double getMisc() {
			return Double.parseDouble(Misc.getAttribute("value"));
		}

		public void setMisc(double misc) {
			cleanInput(Misc);
			Misc.sendKeys(misc+"");
			Misc.sendKeys(Keys.TAB);
		}

		public double getTaxableItems() {
			return Double.parseDouble(StringUtil.cleanCurrency(TaxableItems.getAttribute("value")));
		}

		public double getTotalTaxAmount() {
			return Double.parseDouble(StringUtil.cleanCurrency(TotalTaxAmount.getAttribute("value")));
		}

		public double getTaxedSubTotal() {
			forceWait(300);
			return Double.parseDouble(StringUtil.cleanCurrency(TaxedSubTotal.getAttribute("value")));
		}

		public double getNontaxableItems() {
			return Double.parseDouble(StringUtil.cleanCurrency(NontaxableItems.getAttribute("value")));
		}

		public double getNonTaxableSubTotal() {
			forceWait(300);
			return Double.parseDouble(StringUtil.cleanCurrency(NonTaxableSubTotal.getAttribute("value")));
		}

		public double getTotal() {
			forceWait(300);
			return Double.parseDouble(StringUtil.cleanCurrency(Total.getAttribute("value")));
		}
		
		public double getExpectedTaxedSubTotal(){
			if(GlobalProperty.isProd)
				return round(getTaxableItems() + ((getTaxableItems()+getShippingAmount()) * (getTaxOn()*0.01)),2);
			
			return round(getTaxableItems() + (getTaxableItems() * (getTaxOn()*0.01)),2);
		}
		
		public double getExpectedNontaxableSubTotal(){
			return round(getNontaxableItems() + getShippingAmount() + getMisc(), 2);
		}
		
		public double getExpectedTotal(){
			return getExpectedTaxedSubTotal() + getExpectedNontaxableSubTotal();
		}
		
		public String getShippingType(){
			return ShippingType.findElement(By.xpath("option[@selected='selected']")).getText();
		}
		
		public double getSubtotal(){
			System.out.println("SubTotal: " + SubTotal.getText());
			return Double.parseDouble(StringUtil.cleanCurrency(SubTotal.getText()));
		}
		
		public void setShipingType(ShippingTypes type){
			waitForElementToBeClickable(By.cssSelector("select#shippingType"));
			scrollToView(ShippingType);
//			ShippingType.click();
			getActions().moveToElement(ShippingType).click().build().perform();
				
			for(WebElement e:ShippingType.findElements(By.xpath("option"))){
				if(e.getText().equals(type.toString())) {
					logger.info("found " + e.getText());
					e.click();
					break;
				}
			}
		}
		public static double round(double value, int places) {
		    if (places < 0) throw new IllegalArgumentException();

		    BigDecimal bd = new BigDecimal(value);
		    bd = bd.setScale(places, RoundingMode.HALF_UP);
		    return bd.doubleValue();
		}
		
		public enum ShippingTypes{
			Global,
			Customer,
			Manual
		}
	}
	
	//----------------------- inner page object: sendPage  -----------------------//

	public static class SendPage extends ColBasePage{

		public SendPage(WebDriver driver) {
			super(driver);
			waitForLoad();
			
		}
		
		public void waitForLoad(){
			waitForQuickLoad();
			logger.debug("looking for update button");
			
			if(isEsign()) {
				logger.debug("[This is Esign page. Frame switched]");
				switchFrame();
				waitForTextToBeVisible("Update Preview", "button");

			}else if(isProposalPreview()){
				waitForTextToBeVisible("PDF", "button");
			}else{
				waitForTextToBeVisible("Update Preview", "button");
			}
			
			logger.debug("looking for iframe");
			forceWait(500);
			switchFrame(By.cssSelector("iframe#pdfPreviewIframe"), false);
//			switchFrame();
			logger.debug("found iframe");
			
			switchBack();
		}
		
		public boolean isEsign(){
			try{
				WebElement esignDiv = driver.findElement(By.cssSelector("div#esign-send-modal"));
				return true;
			}catch(NoSuchElementException e){
			
			}
			
			return false;
		}
		
		public boolean isProposalPreview(){
			try{
				WebElement esignDiv = driver.findElement(By.cssSelector("div#send-proposal-preview"));
				return true;
			}catch(NoSuchElementException e){
			
			}
			
			return false;
		}
		
		@FindBy(css="button#print-btn")
		private WebElement Print;
		
		@FindBy(css="button#email-btn")
		private WebElement Email;
		
		@FindBy(css="button#save-btn")
		private WebElement PDF;
		
		@FindBy(css="button#esign-btn")
		private WebElement SendEsignLink;
		
		public void clickPrint(){

			Print.click();
		
			switchToNextWindow();			
		}
		
		public SendPage clickEmail(){
			Email.click();
			waitForElementToBeVisible(By.cssSelector("div#email-options"));
			return this;
		}
		
		public SendPage clickSendEsignLink(String email){
			switchFrame();
			SendEsignLink.click();
			waitForTextToBeVisible("Subject", "label");
			
			setTo(email);
			
			clickSendEmail();
			
			if(isAlertPresent())
				acceptAlert();
			
			switchBack();
			return this;
		}

		
		@FindBy(css="html#print-preview")
		private WebElement printWindow;
		public boolean isPrintPrviewOpen(){
			try{
				waitForPageToLoad(By.cssSelector("html#print-preview"), 10);
			}catch(TimeoutException e){
				
			}
			if(driver.findElements(By.cssSelector("html#print-preview")).size()==0){
				switchToNextWindow();
			}
			return driver.findElements(By.cssSelector("html#print-preview")).size()>=1 ? true:false;
		}
		

		public boolean isEmailBoxOpen(){
			waitForTextToBeVisible("Attachment Format", "legend");
			return true;
		}
		
		public SendPage clickPDF(){
			PDF.click();
			return this;
		}
		
		public boolean isFileDownloaded(){
			//do some io stuff here to cehck file. Then remove the file after.
			return true;
		}
		//--------------EmailBox component-------------//
		
		@FindBy(css="a#submitEmail")
		private WebElement SendEmail;
		
		@FindBy(css="a#cancel-email-btn")
		private WebElement Cancel;
	
		@FindBy(css="a#add-attachment-btn")
		private WebElement AddAttachment;
		
		@FindBy(css=ToPath)
		private WebElement To;
		private static final String ToPath= "div[id*='PageExport_mailto'] div div";

		
		public void clickSendEmail(){
			SendEmail.click();
		}
		
		public void clickCancel(){
			Cancel.click();
		}
		
		public void setTo(String email){
			waitForElementToBeVisible(ToPath);
			getActions().moveToElement(To).click().build().perform();
			forceWait(500);
			logger.debug("Mail set to: " + email);
			To.findElement(By.xpath("div/div/div/input")).sendKeys(email);
			
			getActions().sendKeys(Keys.RETURN).build().perform();
			
			forceWait(500);			
		}
		
		//--------------document content-------------//

		@FindBy(css="tr.lineItemNoteRow td.lineItemNote span")
		private WebElement LineItemNote;
		
		public String getLineItemNote(){
			switchFrame();
			return LineItemNote.getText().replace("Note:", "").trim();
		}
	}
	
	
	//-------------------------------- email page-----------------------------------//
	public static class EmailPopup{
		
	}

	//-------------------------------- search func-----------------------------------//
	@FindBy(css="input#addProductKeyword")
	private WebElement AddProductSearchBox;	
	
	@FindBy(css="button#add-product-submit")
	private WebElement Search;
	
	public ProductsPage searchProduct(String productName){
		waitForElementToBeVisible(By.cssSelector("input#addProductKeyword"));
		forceWait(500); // Scratchpad input does not recognize there is text without this wait.

		AddProductSearchBox.sendKeys(productName);
		Search.click();
		try{
			waitForQuickLoad(10);
		}catch(Exception e){
			
		}
		return PageFactory.initElements(driver, ProductsPage.class);
	}
	
	public T searchExactProduct(String productName){
		AddProductSearchBox.sendKeys(productName);
		Search.click();
		
		forceWait(1000);
		waitForQuickLoad();
		return (T)PageFactory.initElements(driver, this.getClass());
	}
			
	//--------------------------------  product table-----------------------------------//
	@FindBy(css="table.line-item-reorder")
	private WebElement productTable;
	public <T>T selectProductFromTable(int...n){
		for(int nth: n){
//			productTable.findElement(By.xpath("tbody/tr[@data-itemtype='product'][" + nth + "]/td/label/input")).click();
			waitForElementToBeClickable(By.xpath("//tbody/tr[@data-itemtype][\"+ nth + \"]/td/label/input"));
			WebElement input = productTable.findElement(By.xpath("tbody/tr[@data-itemtype]["+ nth + "]/td/label/input"));

			forceWait(500);
			boolean clickable = false;
			int retry = 0;
			
			while(!clickable && retry <=5){
				try{
					
					input.click();
					clickable = true;
				}catch(Exception e){
					forceWait(500);
					retry++;
				}
			}

		}
		return (T)this;
	}
	
	public EditProductPage editProductFromTable(int nth){
		waitForElementToBeClickable(By.xpath("//tbody/tr[@data-itemtype][\"+ nth + \"]/td/label/input"));
		WebElement edit = productTable.findElement(By.xpath("tbody/tr[@data-itemtype]["+ nth + "]/td[contains(@class,'-edit-cell')]/a"));
		edit.click();
		
		waitForQuickLoad();
		return PageFactory.initElements(driver, EditProductPage.class);
	}
	
	public String getItemNoteFromTable(int nth){
		waitForElementToBeClickable(By.xpath("//tbody/tr[@data-itemtype][\"+ nth + \"]/td/label/input"));
		WebElement itemNote = productTable.findElement(By.xpath("tbody/tr["+ (nth +1)+ "]/td/div[@class='lineitem-notes']/div/div/div/div/div/textarea[contains(@class,'note-text-area')]"));
		return itemNote.getText();
	}
	
	public List<String> getProductNamesFromTable(int...n){
		List<String> products = new ArrayList<String>();	
		for(int nth: n){
			WebElement productNames = productTable.findElement(By.xpath("tbody/tr[@data-itemtype='product'][" + nth + "]/td/div[contains(@id,'lineDescriptionOne-')]"));
			String productName = productNames.getText();
			products.add(productName);
		}
		return products;
	}
	
	public List<String> getMfPnsFromTable(int...n){
		List<String> products = new ArrayList<String>();	
		
		for(int nth: n){
			WebElement mfPns = productTable.findElement(By.xpath("tbody/tr[@data-itemtype='product'][" + nth + "]/td[6]"));
			String mfPn = mfPns.getText();
			products.add(mfPn);
		}
		
		return products;
	}
	
	
	public double getUnitPriceFromTable(int nth){
		WebElement unitPrice = productTable.findElement(By.xpath("tbody/tr[@data-itemtype='product'][" + nth + "]/td/div/div/input[contains(@id, 'customerPrice')]"));
		return Double.parseDouble(StringUtil.cleanCurrency(unitPrice.getAttribute("value")));
	}
	
	public void setUnitPriceInTable(int nth, double price){
		WebElement input = productTable.findElement(By.xpath("tbody/tr[@data-itemtype='product'][" + nth + "]/td/div/div/input[contains(@id, 'customerPrice-')]"));
		input.clear();
		input.sendKeys(price+"");
		input.sendKeys(Keys.TAB);
		forceWait(500);
	}
	
	public void setQtyInTable(int nth, int qty){
//		WebElement input = productTable.findElement(By.xpath("tbody/tr[@data-itemtype='product'][" + nth + "]/td/div/div/input[contains(@id, 'quantity')]"));
		WebElement input = productTable.findElement(By.xpath("tbody/tr[@data-itemtype][" + nth + "]/td/div/div/input[contains(@id, 'quantity')]"));

		input.clear();
		input.sendKeys(qty+"");
		input.sendKeys(Keys.TAB);
		forceWait(500);
	}

	public double getTotalPriceFromTable(int nth){
		WebElement unitPrice = productTable.findElement(By.xpath("tbody/tr[@data-itemtype='product'][" + nth + "]/td[contains(@id,'extendedPrice-')]"));
		return Double.parseDouble(StringUtil.cleanCurrency(unitPrice.getText()));
	}
	
	@FindBy(css="div#rma-operations-toolbar div div button#lineActions")
	private WebElement LineActionsDropdown;
	
	@FindBy(css="button#reorderLines")
	private WebElement ReorderLines;
	
	@FindBy(css="button#addImportUpdate")
	private WebElement AddImportUpdateDropdown;
	
	public boolean isLineActionsDropdownDisplayed(){
		try{
			return LineActionsDropdown.isDisplayed();
		}catch(NoSuchElementException e){
			
		}
		return false;
	}
	
	public boolean isReorderLinesDisplayed(){
		try{
			return ReorderLines.isDisplayed();
		}catch(NoSuchElementException e){
			
		}
		return false;
	}
	
	public boolean isAddImportUpdateDropdownDisplayed(){
		try{
			return AddImportUpdateDropdown.isDisplayed();
		}catch(NoSuchElementException e){
			
		}
		return false;
	}
	
	public boolean isLiveCost(){
		try{
			return LiveCost.isDisplayed();
		}catch(NoSuchElementException e){
			
		}
		return false;
	}
	
	public T selectFromLineActions(LineActions lAction){
		LineActionsDropdown.click();
		
		
		try{
			LineActionsDropdown.findElement(By.xpath("../ul/li/a[@title='" + StringUtil.cleanElementName(lAction.toString()) + "']")).click();
		}catch(NoSuchElementException e){
			LineActionsDropdown.findElement(By.xpath("../ul/li/a[@title='" + StringUtil.cleanElementName(lAction.toString()).toLowerCase() + "']")).click();
		}
		
		waitForQuickLoad();
		
		if(lAction == LineActions.Compare){
			return (T) PageFactory.initElements(driver, ComparisonPage.class);
		}else if(lAction == LineActions.Add_to_Catalogs){
			return (T) PageFactory.initElements(driver, AddToCatalogsPage.class);
		}
		
		forceWait(500);
		
		return (T)PageFactory.initElements(driver, this.getClass());
	}
	
	public boolean tableColumnsAreDisplayed(){
		String[] expectedHeaders = new String[]{"Edit", "Description"/**, "Link"*/, "Mfr Part #", "List Price", "Qty", "Tax", "Price", "Total"};
		List<String> headersToString = new ArrayList<String>();
		List<WebElement> headers = productTable.findElements(By.xpath("thead/tr/th"));
		for(WebElement e: headers){
			if(!e.getText().isEmpty() && e.getText().length() >=1){
				headersToString.add(e.getText());
			}
		}
		 String[] actualHeaders = headersToString.toArray(new String[headersToString.size()]);
		 
		 logger.debug("actual headers:");
		 for(String s: actualHeaders){
			 logger.debug(s + " / ");
		 }
		
//		return expectedHeaders.equals(actualHeaders);
		return Arrays.asList(actualHeaders).containsAll(Arrays.asList(expectedHeaders));
	
	}

	public T selectFromAddImportUpdate(AddImportUpdates add){
		AddImportUpdateDropdown.click();
		
		AddImportUpdateDropdown.findElement(By.xpath("../ul/li/a[contains(@title, '" + StringUtil.cleanElementName(add.toString()) + "')]")).click();
		waitForQuickLoad();
		if(add == AddImportUpdates.Merge_into_this_Document) return (T) PageFactory.initElements(driver, MergePopup.class);
		else if(add == AddImportUpdates.Quick_Add_Product) return (T) PageFactory.initElements(driver, QuickAddProductPopup.class);
		else if (add == AddImportUpdates.Import_Config) return (T) PageFactory.initElements(driver, ImportConfigPopup.class);
		
		return (T) PageFactory.initElements(driver, this.getClass());	
	}

	public enum LineActions{
		Delete,
		Compare,
		Price_History,
		Add_to_Catalogs{
			public String toString(){
				return "Add to Catalog(s)";
			}
		},
		Convert_to_Bundle {
						public String toString(){
								return "Convert to bundle";
							}
						},
		Unbundle,
		Insert_Header,
		Insert_Subtotal_Header,
		Insert_Subtotal,
		Add_Subtotal, 
		Add_Blank_Line,
		Add_Horizontal_Line,
		Copy_Line{
				public String toString(){
					return "Copy_Line(s)";
				}
		},
		Link_Lines,
		Unlink_Lines,
		Insert_COL_Toll,
		Print_Selected
	}
	
	public enum AddImportUpdates{
		Quick_Add_Product,
		UpdateMarginMarkup,
		AddManualLineItem,
		Merge_into_this_Document,
		Import_Config,
		ImportFromAutoAsk
	}
	
	WebElement bundleHeader;
	public T setBundleHeader(String header){
//		bundleHeader = productTable.findElement(By.xpath("tbody/tr/td/div/div/input[contains(@id,'lineDescriptionOne-')][contains(@class,'valid')]"));
		bundleHeader = productTable.findElement(By.xpath("tbody/tr/td/div/div/input[contains(@id,'lineDescriptionOne-')]"));

		bundleHeader.sendKeys(header);
		return (T)this;
	}
	
	public T setBundleDesc(String desc){
		WebElement bundleDesc = bundleHeader.findElement(By.xpath("../../../div/div/textarea[contains(@id,'lineDescriptionTwo-')]"));
		bundleDesc.sendKeys(desc);		
		return (T)this;
	}
	
	WebElement literaHeaderText ;
	public String getBundleHeader(){
		literaHeaderText = productTable.findElement(By.xpath("tbody/tr/td/span[@class='edit-document-lineitem-inline']"));
		WebElement headerSpan = literaHeaderText.findElement(By.xpath("../b/span"));
		return headerSpan.getText();
	}
	
	public String getBundleDesc(){
		WebElement descSpan = literaHeaderText.findElement(By.xpath("../span[contains(@class, 'description-two-not-product')]"));
		return descSpan.getText();
	}
	
	public T clickSaveLineItem(){
		WebElement save = bundleHeader.findElement(By.xpath("../../../../td/a[@title='Done']"));
		save.click();
		waitForQuickLoad();
		
		return (T)PageFactory.initElements(driver, this.getClass());
	}
	
	public String getSubTotalHeader(){
		literaHeaderText = productTable.findElement(By.xpath("tbody/tr/td/span[@class='edit-document-lineitem-inline']"));
		return literaHeaderText.getText();
	}
	
	@FindBy(css="button#docActions")
	private WebElement CreateDoc;
	public <T> T selectCreateDoc(Doc doc){
		CreateDoc.click();
		List<WebElement> docActions = driver.findElements(By.cssSelector("ul.dropdown-menu li a"));
		for(WebElement e: docActions){
			if(e.getAttribute("title").replaceAll("[ \\s+ ( )]", "").equals(doc.toString())){
				e.click();
			}
		}
		
		waitForQuickLoad();
		
		if(doc == Doc.CreatePO || doc == Doc.CreatePOAll) {
			CreatePoPopup po = PageFactory.initElements(driver, CreatePoPopup.class);
			PurchaseOrdersTab purchaseOrderPage = po.clickCreatePos();
			return (T)purchaseOrderPage;
		}
		else if (doc == Doc.CreateRMA) {
			CreateRmaPopup crp = PageFactory.initElements(driver, CreateRmaPopup.class);
			RMAPage rmaPage = crp.clickCreateRMA();
			return (T)rmaPage;
		}
		
		return null;
	}
	
	public enum Doc{
		CreatePO,
		CreatePOAll,
		CreateRMA,
		CreateInvoice, 
		CreateInvoiceAll
	}
	
	public List<LinkedHashMap<String, String>> getTableAsMaps(){
		return super.getTableAsMaps(1,productTable, 0,1,2,3,6,8,9);  //only get MfrPart, margin, total datas.
	}
	
	
	public <T>T removeAllProducts(){
		List<LinkedHashMap<String, String>> beforeProducts = getTableAsMaps();

		if(beforeProducts == null || beforeProducts.size() ==0){
			return (T)this;
		}
		else{
			for(int i = 0; i < beforeProducts.size(); i++){
				selectProductFromTable(i+1);
			}
		}
		
		selectFromLineActions(LineActions.Delete);

		return (T)PageFactory.initElements(driver, this.getClass());
	}
	
	@FindBy(css="button[id*='serialstracking-tbl-btn']")
	private WebElement ShowSerialShipping;
	
	public <T>T clickShowSerialShipping(){
		ShowSerialShipping.click();
		forceWait(500);
		
		return (T)PageFactory.initElements(driver, this.getClass());
	}
	
	@FindBy(css="table.source.shiptrack")
	private WebElement shipTrackTable;
	
	public boolean isShipTrackTableDisplayed(){
		return shipTrackTable.isDisplayed();
	}
	
	public <T>T clickReorderLine(){
		ReorderLines.click();
		forceWait(500);
		
		while(!driver.findElement(By.cssSelector("button#reorderLines")).getText().contains("Return to")){
			forceWait(100);
		}
		
		return (T)PageFactory.initElements(driver, this.getClass());
	}
	
	public <T>T reorder(int before, int after){
		WebElement from = productTable.findElement(By.xpath("tbody/tr[" + before + "]/tr/td/div"));
		WebElement to = productTable.findElement(By.xpath("tbody/tr[" + after + "]/tr/td/div"));

		to.click();

		getActions().clickAndHold(from).build().perform();
		forceWait(500);
		getActions().moveToElement(to, 0, 150).build().perform();
		forceWait(500);
		getActions().release().build().perform();
		
		forceWait(5000);
		ReorderLines.click();
		
		forceWait(1000);
		return (T)PageFactory.initElements(driver, this.getClass());
	}
	//----------------------------- RMA popup for SO and PO-----------------------------//
	public static class CreateRmaPopup extends ColBasePage{

		public CreateRmaPopup(WebDriver driver) {
			super(driver);
			// TODO Auto-generated constructor stub
			switchFrame(By.cssSelector("iframe"));
			waitForTextToBeVisible("RMA", "h3");
		}
		
		@FindBy(css="a[href*='createRma();']")
		private WebElement CreateRMA;
		
		public RMAPage clickCreateRMA(){
			CreateRMA.click();
			switchBack();
			return PageFactory.initElements(driver, RMAPage.class);
		}
	}
	
	public enum DocStatus{
		Submitted, 
		OutForESign{
			public String toString(){
				return "Out For E-Sign";
			}
		},
		Open
	}
	
	//----------------------------- QuickAddProduct-----------------------------//
	public static class QuickAddProductPopup extends ColBasePage{

		public QuickAddProductPopup(WebDriver driver) {
			super(driver);
			// TODO Auto-generated constructor stub
			waitForTextToBeVisible("Quick Add Product", "h3");
		}
		
		public ProductsPage search(String text){
			WebElement searchInput = driver.findElements(By.cssSelector("input#addProductKeyword")).get(1);
			searchInput.sendKeys(text);
			searchInput.findElement(By.xpath("../button")).click();
			
			return PageFactory.initElements(driver, ProductsPage.class);
		}
		
	}
	//----------------------------- BillToShipToDiv-----------------------------//
	public static class MergePopup extends ColBasePage{

		public MergePopup(WebDriver driver) {
			super(driver);
			// TODO Auto-generated constructor stub
			switchFrame();
			waitForTextToBeVisible("Merge into", "h3");
			
		}
		
		@FindBy(css="input#doc_search_optiondoclist")
		private WebElement List;
		
		@FindBy(css="a[title='Search'][href*='merge_quotes']")
		private WebElement Search;
		
		@FindBy(css="select[name='doclist']")
		private WebElement searchList;
		
		@FindBy(css="input[value='Copy into selected Quote']")
		private WebElement copyIntoSelectedQuote;
		public MergePopup selectFromList(DocList type){
			List.click();
			searchList.click();
			searchList.findElement(By.xpath("option[@value='" + type.toString().toLowerCase() + "']")).click();
			return this;
		}
		
		public MergePopup clickSearch(){
			Search.click();
			switchBack();
			return 	PageFactory.initElements(driver, MergePopup.class);
		}
		
		public MergePopup selectOneDoc(){
			driver.findElement(By.cssSelector("input[name='quote_id_selected']")).click();
			return this;
			
		}
		
		public void clickCopyIntoSelectedQuote(){
			copyIntoSelectedQuote.click();
			switchBack();
		}
		
		public enum DocList{
			Recent,
			Hotlist,
			Modifiedbyme
		}
	}
	
	//----------------------------- EditProduct page-----------------------------//
	
	public static class EditProductPage extends ColBasePage{

		public EditProductPage(WebDriver driver) {
			super(driver);
			// TODO Auto-generated constructor stub
			logger.debug("invoking editProductPage");
			switchFrame();
			waitForElementToBeVisible(By.cssSelector("div.popup-content table.popupbody"));
		}
		
		@FindBy(css="textarea#note")
		private WebElement ItemNote;
		
		public EditProductPage addItemNote(String comment){
			ItemNote.sendKeys(comment);
			return this;
		}
		
		@FindBy(css="button#lineitem-modal-save")
		private WebElement Save;
		
		@FindBy(css="div.modal-header a.close-modal")
		private WebElement Close;
		public void clickSaveThenExit(){
			clickSave();
			
			switchBack();
			this.Close.click();
			
			forceWait(500);
			waitForElementToBeInvisible(By.cssSelector("div.popup-content table.popupbody"));
		}
		
		public EditProductPage clickSave(){
			switchBack();
			this.Save.click();
			forceWait(500);
			
			return PageFactory.initElements(driver, EditProductPage.class);
		}
	}

	//----------------------------- BillToShipToDiv-----------------------------//
	
	WebElement BillingAndShippingDiv;
	public void setBillingAndShippingDiv(){
//		BillingAndShippingDiv = driver.findElement(By.xpath("div[@class='span4']/label/a[contains(@href,'/billingshipping?')]"));
//		if(BillingAndShippingDiv == null){
			BillingAndShippingDiv = driver.findElement(By.cssSelector("div.span4 label a[href*='/billingshipping?']"));
			BillingAndShippingDiv = BillingAndShippingDiv.findElement(By.xpath("../../.."));
//		}
		
	}
	public String getBillTo(){
		setBillingAndShippingDiv();
		return BillingAndShippingDiv.findElement(By.xpath("div[1]")).getText();
	}
	
	public AddressPage clickBillTo(){
		setBillingAndShippingDiv();
		BillingAndShippingDiv.findElement(By.xpath("div[1]/label/a")).click();
		return PageFactory.initElements(driver, AddressPage.class);
	}
	
	public boolean isBillToEnabled(){		
		try{
			setBillingAndShippingDiv();
			return BillingAndShippingDiv.findElement(By.xpath("div[1]/label/a")).isDisplayed();
		}catch(NoSuchElementException e){
			
		}
		return false;
	}
	
	public String getShipTo(){
		setBillingAndShippingDiv();
		return BillingAndShippingDiv.findElement(By.xpath("div[2]")).getText();
	}
	
	public AddressPage clickShipTo(){
		setBillingAndShippingDiv();
		BillingAndShippingDiv.findElement(By.xpath("div[2]/label/a")).click();
		return PageFactory.initElements(driver, AddressPage.class);
	}
	
	public boolean isShipToEnabled(){		
		try{		
			setBillingAndShippingDiv();
			return BillingAndShippingDiv.findElement(By.xpath("div[2]/label/a")).isDisplayed();
		}catch(NoSuchElementException e){
			
		}
		return false;
	}
	
	
	public String getOrderOptions(){
		setBillingAndShippingDiv();
		return BillingAndShippingDiv.findElement(By.xpath("div[3]")).getText();
	}
	
	public OrderOptionsPage clickOrderOptions(){
		setBillingAndShippingDiv();
		BillingAndShippingDiv.findElement(By.xpath("div[3]/label/a")).click();
		
		if(isAlertPresent())
			acceptAlert();

		return PageFactory.initElements(driver, OrderOptionsPage.class);
	}
	
	public boolean isOrderOptionsEnabled(){		
		try{
			setBillingAndShippingDiv();
			BillingAndShippingDiv.findElement(By.xpath("div[3]/label/a"));
			return true;
		}catch(NoSuchElementException e){
			
		}
		return false;
	}
	
	//----------------------------- Organizer - task -----------------------------//
	@FindBy(css="div#company-name span a.add-notetask-btn")
	private WebElement AddTaskButton;
	
	@FindBy(css="div#company-name span a.view-notetask-btn")
	private WebElement TaskNum;
	
	public T addTask(String subject, String content){
		AddTaskButton.click();
		
		OrganizerPopup organizerPopup = PageFactory.initElements(driver, OrganizerPopup.class);
		organizerPopup.setSubject(subject);
		organizerPopup.setContent(content);

		logger.info("Click SAve");
		
		organizerPopup.clickSave();
		forceWait(1000);
		organizerPopup.quit();
		
		forceWait(500);
		
		return (T) PageFactory.initElements(driver, this.getClass());
	}
	
	public int getTasks(){
		return Integer.parseInt(TaskNum.getText());
	}
	
	//----------------------------- Document state -----------------------------//

	public enum DocumentState{
		Pending,
		Authorized,
		Approved,
		Submitted,
		Complete,
		Document_Declined {
			public String toString(){
				return "Document Declined";
			}
		},
		Open, 
		Out_For_E_Sign {
			public String toString(){
				return "Out For E-Sign";
			}
		}
	}
	
	@FindBy(css="h1[class*='page-title'] span")
	private WebElement PageTitle;
	
	public String getDocumentState(){
		return StringUtil.cleanPageTitle(PageTitle.getText());
	}
	
	//----------------------------- Document notes -----------------------------//
	@FindBy(css="textarea#external-notes")
	private WebElement DocumentNotes;
	
	@FindBy(css="textarea#internal-notes")
	private WebElement InternalDocumentNotes;
	
	public void setDocumentNotes(String text){
		DocumentNotes.sendKeys(text);
	}
	
	public void setInternalDocumentNotes(String text){
		InternalDocumentNotes.sendKeys(text);
	}	
	
	//----------------------------- Import config -----------------------------//
	public static class ImportConfigPopup<T> extends ColBasePage{
		public ImportConfigPopup(WebDriver driver){
			super(driver);
			switchFrame();
			waitForQuickLoad();
			waitForTextToBeVisible("Please browse to the file you want to upload as your config file.", "td");
		}
		
		@FindBy(css="input[value='generic']")
		private WebElement GenericImport;
		
		@FindBy(css="input[id='image_file'][name='upload_file']")
		private WebElement ChooseFile;
		
		public ImportConfigPopup clickGenericImport(){
			GenericImport.click();
			return this;
		}
		
		public ImportConfigPopup clickChooseFile(String fileName){
			ChooseFile.click();
			inputFilePath(fileName);
			getActions().sendKeys(Keys.RETURN);

			return this;
		}

		public T clickSave(){
			ChooseFile.findElement(By.xpath("../../../tr/td/a[contains(@href, 'check_and_submit()')]")).click();
			
			forceWait(500);
			
			waitForTextToBeVisible("Importing Config", "h1");
			switchBack();
			waitForElementToBeInvisible(By.cssSelector("div.popup-content table tbody tr td h1"));
			
			return (T) PageFactory.initElements(driver, QuotePage.class);
		}
		
		public void inputFilePath(String fileName){
			String pathToFile = System.getProperty("user.dir") + "/src/test/resources/Files/";
			WebElement fileInput = driver.findElement(By.cssSelector("input[type='file']"));
			
			logger.debug("using file path: " + pathToFile + fileName);
			
			fileInput.sendKeys(pathToFile + fileName);
		}
		
		
		
	}

}
