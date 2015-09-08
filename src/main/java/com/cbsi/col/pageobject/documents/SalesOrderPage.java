package com.cbsi.col.pageobject.documents;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.home.ColBasePage;
import com.cbsi.col.pageobject.purchaseorders.PurchaseOrdersTab;

public class SalesOrderPage extends DocumentsBasePage{

	public SalesOrderPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
//		waitForPageToLoad(By.cssSelector("form div p"));
		try{
			waitForTextToBeVisible(5000, "Addresses", "b");
		}catch(Exception e){
			
		}
	}
	
	@FindBy(css="#billing_FirstName")
	private WebElement FirstName;
	
	@FindBy(css="#billing_LastName")
	private WebElement LastName;
	
	@FindBy(css="#billing_Email")
	private WebElement Email;
	
	@FindBy(css="#billing_Address1")
	private WebElement Address;
	
	@FindBy(css="#billing_City")
	private WebElement City;
	
	@FindBy(css="#billing_Zip")
	private WebElement Zip;

	public void setFirstName(String firstName) {
		FirstName.sendKeys(firstName);
	}

	public void setLastName(String  lastName) {
		LastName.sendKeys(lastName);
	}

	public void setEmail(String email) {
		Email.sendKeys(email);
	}

	public void setAddress(String address) {
		Address.sendKeys(address);
	}

	public void setCity(String city) {
		City.sendKeys(city);
	}

	public void setZip(String zip) {
		Zip.sendKeys(zip);;
	}
	
	@FindBy(css="input[name='copy_b_to_s']")
	private WebElement CopyToShipping;
	
	public SalesOrderPage clickCopyToShipping(){
		CopyToShipping.click();
		return this;
	}
	
	
	//-----------------------Bottom bar options---------------------//
	@FindBy(linkText="Save")
	private WebElement Save;

	@FindBy(linkText="Cancel")
	private WebElement Cancel;
	
	
	//There is an addToOrder(?) page. work on it whenever needed.
	public SalesOrderPage clickSave(){
		
		try{
			Save.click();
			waitForElementToBeVisible(By.cssSelector("input[name='ponumber']"), 10);
		}catch(TimeoutException e){
			waitForTextToBeVisible("Sales Order (Submitted)", "span");
		}catch(UnhandledAlertException e){
			acceptAlert();
		}
//		return this;
		return PageFactory.initElements(driver, SalesOrderPage.class);
	}

	public SalesOrderPage clickCancel(){
		Cancel.click();
		return this;
	}
	
	//-----------------------Sales order payment page---------------------//
	@FindBy(css="input[name='ponumber']")
	private WebElement PONumber;
	
	@FindBy(css="input[type='radio'][value='po']")
	private WebElement Terms;
	
	@FindBy(css="input[type='radio'][value='money_order']")
	private WebElement MoneyOrder;
	
	@FindBy(css="input[type='radio'][value='cod']")
	private WebElement COD;
	
	@FindBy(css="select[name='delivery']")
	private WebElement DeliveryMethod;
//	@FindBy(css="input[type='radio][value='money_order']")
//	private WebElement Leasing;
	
	public SalesOrderPage setPaymentMethod(Payment payment){
		switch(payment){
		case Terms:
			Terms.click();
			break;
		case MoneyOrder:
			MoneyOrder.click();
			break;
		case COD:
			COD.click();
			break;

		default:
			break;
		}
		return this;

	}
	
	public SalesOrderPage setPoNumber(int num){
		PONumber.sendKeys(num+"");
		return this;
	}
	
	public SalesOrderPage setPoNumberAndPaymentMethod(int num, Payment payment){
		return setPoNumber(num).setPaymentMethod(payment);
	}
	
	public SalesOrderPage selectDelieveryMethod(Delivery option){
		DeliveryMethod.click();
		List<WebElement> options = driver.findElements(By.cssSelector("select[name='delivery'] option"));
		
		switch(option){
			case UPSNextDayAir:
					options.get(0).click();
				break;
			case UPS2ndDayAir:
					options.get(1).click();
					break;
			default:
		}
		return this;
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
		CreateRMA
	}
	public enum Payment{
		Terms,
		MoneyOrder,
		COD,
		Leasing
	}
	
	public enum Delivery{
		UPSNextDayAir,
		UPS2ndDayAir,
		UPSGround,
		UPS3DaySelect,
		UPS2ndDayAirAM,
		UPSNExtDayAirSaver,
		UPS3DaySelectResidential
	}
	
	public static class CreatePoPopup extends ColBasePage{

		public CreatePoPopup(WebDriver driver) {
			super(driver);
			// TODO Auto-generated constructor stub
			switchFrame(By.cssSelector("iframe"));
			waitForTextToBeVisible("Create POs", "h3");
		}
		@FindBy(css="span.btn-save")
		private WebElement CreatePos;
		
		public PurchaseOrdersTab clickCreatePos(){
			CreatePos.click();
			switchBack();
			return PageFactory.initElements(driver, PurchaseOrdersTab.class);
		}
	}
}
