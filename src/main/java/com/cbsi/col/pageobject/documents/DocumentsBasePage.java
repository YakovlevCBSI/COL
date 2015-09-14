package com.cbsi.col.pageobject.documents;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.documents.QuotePage.CopyToNewQuotePage;
import com.cbsi.col.pageobject.documents.SalesOrderPage.CreatePoPopup;
import com.cbsi.col.pageobject.home.ColBasePage;
import com.cbsi.col.pageobject.products.ProductsPage;
import com.cbsi.col.pageobject.purchaseorders.PurchaseOrdersTab;
import com.cbsi.col.test.util.StringUtil;

public class DocumentsBasePage<T> extends ColBasePage{

	public DocumentsBasePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public int getDocNumber(){
		int quoteInt;
		try{
			String quoteNumber= driver.findElement(By.cssSelector("div.control-column div.readonly-text")).getText();
			quoteNumber = quoteNumber.split("-")[0].trim();
			quoteInt = Integer.parseInt(quoteNumber);
		}catch(NumberFormatException e){			
			String quoteNumber= driver.findElement(By.cssSelector("input#quoteNumber")).getAttribute("value");
			quoteInt = Integer.parseInt(quoteNumber);
		}
		
		System.out.println(quoteInt);
		return quoteInt;
	}
	//--------------------------  Bottom bar---------------------------//
	@FindBy(css="button#next-action_save")
	private WebElement Save;
	
	@FindBy(css="li a[id*='action_saveNewRevision']")
	private WebElement SaveAsNewRevision;
	
	@FindBy(css="button#next-action_copyToQuote")
	private WebElement CopyToNewQuote;
	
//	@FindBy(css="button#ld-nextaction-caret")
	private WebElement Caret;
	
	@FindBy(css="button[id^='next-action_order']")
	private WebElement ConvertToOrder;
	
	@FindBy(css="button#next-action_send")
	private WebElement Send;
	
	@FindBy(css="div.btn-group.dropup button#next-action_cancelOrder")
	private WebElement CancelThisOrder;
	
	@FindBy(css="div button[id *= '_convertToInvoice']")
	private WebElement ConvertToInvoice;
	
	@FindBy(css="li a[id *= '_completeInvoice']")
	private WebElement CompletInvoice;
	
	@FindBy(css="button[id*='live_cost_']")
	private WebElement LiveCost;
	
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

		return copyToNewQuotePage.clickCreate();
	}
	
	public SalesOrderPage clickConvertToOrder(){

		ConvertToOrder.click();
		return PageFactory.initElements(driver, SalesOrderPage.class);
	}	
	
	public SendPage clickSend(){
		Send.click();
		return PageFactory.initElements(driver, SendPage.class);
	}

	@FindBy(css="span.btn-save")
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
	//----------------------- inner page object: calculator  -----------------------//
	public PriceCalculator priceCalculator;
	
	public void initializePriceCalculator(){
		 priceCalculator = PageFactory.initElements(driver, PriceCalculator.class);
	}
	
	public PriceCalculator getPriceCalculator(){
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

		public double getTaxOn() {
			return Double.parseDouble(TaxOn.getAttribute("value"));
		}

		public void setTaxOn(double taxOn) {
			cleanInput(TaxOn);
			TaxOn.sendKeys(taxOn+"");
			TaxOn.sendKeys(Keys.TAB);
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
			return round(getTaxableItems() + (getTaxableItems() * (getTaxOn()*0.01)),2);
		}
		
		public double getExpectedNontaxableSubTotal(){
			return round(getNontaxableItems() + getShippingAmount() + getMisc(), 2);
		}
		
		public double getExpectedTotal(){
			return getExpectedTaxedSubTotal() + getExpectedNontaxableSubTotal();
		}
		
		public static double round(double value, int places) {
		    if (places < 0) throw new IllegalArgumentException();

		    BigDecimal bd = new BigDecimal(value);
		    bd = bd.setScale(places, RoundingMode.HALF_UP);
		    return bd.doubleValue();
		}
	}
	
	//----------------------- inner page object: sendPage  -----------------------//
	
	public static class SendPage extends ColBasePage{

		public SendPage(WebDriver driver) {
			super(driver);
			// TODO Auto-generated constructor stub
			waitForTextToBeVisible("Select a view", "label");
			switchFrame(By.cssSelector("iframe#pdfPreviewIframe"));
			switchBack();
		}
		
		@FindBy(css="button#print-btn")
		private WebElement Print;
		
		@FindBy(css="button#email-btn")
		private WebElement Email;
		
		@FindBy(css="button#save-btn")
		private WebElement PDF;
		
		public void clickPrint(){

			Print.click();
		
			switchToNextWindow();			
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
		
		public SendPage clickEmail(){
			Email.click();
			return this;
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
			
			public void clickCancel(){
				Cancel.click();
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
		AddProductSearchBox.sendKeys(productName);
		Search.click();
		
		return PageFactory.initElements(driver, ProductsPage.class);
	}
			
	//--------------------------------  product table-----------------------------------//
	@FindBy(css="table.line-item-reorder")
	private WebElement productTable;
	public T selectProductFromTable(int...n){
		for(int nth: n){
			productTable.findElement(By.xpath("tbody/tr[@data-itemtype='product'][" + nth + "]/td/label/input")).click();
		}
		return (T)this;
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
		WebElement input = productTable.findElement(By.xpath("tbody/tr[@data-itemtype='product'][" + nth + "]/td/div/div/input[contains(@id, 'quantity')]"));
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
		}
		
		return (T)PageFactory.initElements(driver, this.getClass());
	}
	
	@FindBy(css="button#addImportUpdate")
	private WebElement AddImportUpdateDropdown;
	public T selectFromAddImportUpdate(AddImportUpdates add){
		AddImportUpdateDropdown.click();
		
		AddImportUpdateDropdown.findElement(By.xpath("../ul/li/a[contains(@title, '" + StringUtil.cleanElementName(add.toString()) + "')]")).click();
		waitForQuickLoad();

		return (T) PageFactory.initElements(driver, QuickAddProductPopup.class);	
	}
	
	public enum LineActions{
		Delete,
		Compare,
		Price_History,
		Add_to_Catalogs,
		Convert_to_Bundle {public String toString(){
								return "Convert to bundle";
							}
						},
		Unbundle,
		Insert_Header,
		Insert_Subtotal
	}
	
	public enum AddImportUpdates{
		Quick_Add_Product,
		UpdateMarginMarkup,
		AddManualLineItem,
		MergeIntoThisDocument,
		ImportConfig,
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
	
	public T clickSaveBundle(){
		WebElement save = bundleHeader.findElement(By.xpath("../../../../td/a[@title='Done']"));
		save.click();
		waitForQuickLoad();
		
		return (T)PageFactory.initElements(driver, this.getClass());
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
	
	//----------------------------- RMA popup for SO and PO-----------------------------//
	public static class CreateRmaPopup extends ColBasePage{

		public CreateRmaPopup(WebDriver driver) {
			super(driver);
			// TODO Auto-generated constructor stub
			switchFrame(By.cssSelector("iframe"));
			waitForTextToBeVisible("RMA", "h3");
		}
		
		@FindBy(css="span.btn-save")
		private WebElement CreateRMA;
		
		public RMAPage clickCreateRMA(){
			CreateRMA.click();
			switchBack();
			return PageFactory.initElements(driver, RMAPage.class);
		}
	}
	
	public enum DocStatus{
		Submitted;
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
	
}
