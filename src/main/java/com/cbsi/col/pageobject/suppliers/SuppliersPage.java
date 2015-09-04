package com.cbsi.col.pageobject.suppliers;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.home.ColBasePage;
import com.cbsi.col.test.util.StringUtil;

public class SuppliersPage extends ColBasePage{

	public SuppliersPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForTextToBeVisible("Suppliers", "h1");
	}
	
	@FindBy(css="a[title='Create New Supplier']")
	private WebElement CreateNewSupplier;
	
	@FindBy(css="a[href *='deleted_suppliers']")
	private WebElement RecycleBin;
	
	public CreateSupplierPage clickCreateNewSupplier(){
		CreateNewSupplier.click();
		return PageFactory.initElements(driver, CreateSupplierPage.class);
	}
	
	public SuppliersPage switchToTab(SupplierTabs tab){
		driver.findElement(By.linkText(StringUtil.cleanElementName(tab.toString()))).click();
		
		try{
			waitForElementToBeInvisible(By.cssSelector("p.lead"));
		}catch(TimeoutException e){
			
		}
		
		return PageFactory.initElements(driver, SuppliersPage.class);
	}
	
	public enum SupplierTabs{
		Recent_Supplier,
		Supplier_List
	}
	
	@FindBy(css="table.costandard")
	private WebElement SupplierTable;
	
	public boolean hasSupplier(String supplierName){
		return findDataRowByName(supplierName)==null?false:true;
	}
	
	public SuppliersPage deleteSupplierByName(String supplierName){
		WebElement supplier = findDataRowByName(supplierName);
		if(supplier!=null){
			supplier.findElement(By.xpath("../../td/a[contains(@href,'deleted')]")).click();
			acceptAlert();
		}
	
		return PageFactory.initElements(driver, SuppliersPage.class);
	}
	
	public List<HashMap<String, String>> getTableAsMaps(){
		return getTableAsMaps(SupplierTable, 0, 1, 9);
	}
	
	static int currentPage=0;
	private static List<WebElement> dataColumns;
	
	public WebElement findDataRowByName(String supplierName){
		dataColumns = driver.findElements(By.cssSelector("table.costandard tbody tr td:nth-child(4) font"));
	
		
		for(WebElement dataColumn: dataColumns){
//			System.out.println(dataColumn.getText());
			if(dataColumn.getText().contains(supplierName+"")){
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
			pageList.get(0).click();
			waitForTextToBeVisible("Documents", "h1");
			dataColumns=null;
			return findDataRowByName(supplierName);
		}
		
		return null;		
	}

}
