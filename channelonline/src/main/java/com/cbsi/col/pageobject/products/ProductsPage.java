package com.cbsi.col.pageobject.products;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.col.pageobject.documents.DocumentsBasePage;
import com.cbsi.col.pageobject.documents.DocumentsPage;
import com.cbsi.col.pageobject.documents.QuotePage;
import com.cbsi.col.pageobject.home.ColBasePage;

public class ProductsPage extends ColBasePage{
	public ProductsPage(WebDriver driver){
		super(driver);
		waitForTextToBeVisible("Search By:", "ul li span.nav-title");
	}
	
	@FindBy(css="a[title href*='item_add']")
	private WebElement AddToQuote;
	
	@FindBy(css="input#check_all")
	private WebElement CheckAll;
	
	public void addProductsToQuote(int...nThCheckBoxes){
		checkCompareBoxes(nThCheckBoxes);
		AddToQuote.click();
		
//		return (T)PageFactory.initElements(driver, this.getClass());
		return;
	}
	
	public ProductsPage checkCompareBoxes(int...nThCheckBoxes){
		List<WebElement> checkBoxes = driver.findElements(By.cssSelector("input[id^='checkmultiple']"));
		
		for(int n: nThCheckBoxes){
			checkBoxes.get(n-1).click();
		}
		
		return this;
	}
	
	public ProductsPage clickCheckAll(){
		CheckAll.click();
		return this;
	}
	
	public String[] getProductNames(int...nthProduct){
		String[] foundTitles = new String[nthProduct.length];
		List<WebElement> descriptionTitles = driver.findElements(By.cssSelector("font.BodyActionText a"));
		
		int count=0;
		for(int n: nthProduct){
			foundTitles[count] = descriptionTitles.get(n).getText();
		}
		
		return foundTitles;
	}
	
	public void goToLinkText(String linkText){
		driver.findElement(By.linkText(linkText)).click();
		forceWait(500);
		waitForTextToBeVisible("Search By:", "ul li span.nav-title");
	}
	
	@FindBy(css="select#action")
	private WebElement SelectOne;
	
	@FindBy(css="option[value='compare']")
	private WebElement CompareOption;
	
	@FindBy(css="option[value='add_to_doc']")
	private WebElement AddToQuoteOption;
	
	@FindBy(css="option[value='add_to_catalog']")
	private WebElement AddToCatalogOption;
	
	@FindBy(css="option[value='add_to_fav']")
	private WebElement AddToFavoritesOption;
	
	@FindBy(css="input[name='Go']")
	private WebElement Go;
	
	public void selectAction(Action action){

		scrollToView(SelectOne);
		SelectOne.click();
		forceWait(1000);
//		Cheeck if dropdown is open. If not, open it again.		
		if(action == Action.Compare){
			CompareOption.click();
		}
		else if(action == Action.AddToQuote){
			AddToQuoteOption.click();
		}
		else if(action == Action.AddToCatalogs){
			AddToCatalogOption.click();

		}
		else if(action == Action.AddToFavorites){
			AddToFavoritesOption.click();

		}
		
		forceWait(1000); //clicking Go too fast, does not register the dropdown selection.
		
		Go.click();
		
//		return (T)PageFactory.initElements(driver, this.getClass());
		return;

	}
	
	public enum Action{
		Compare,
		AddToQuote, //also use this for add to cart in storesites.
		AddToCatalogs,
		AddToFavorites, 
	}
}
