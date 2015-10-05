package Smoke;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;

import com.cbsi.tests.Foundation.FormBaseTest;
import com.cbsi.tests.PageObjects.DashboardPage;
import com.cbsi.tests.PageObjects.MapProductsDialog;
import com.cbsi.tests.PageObjects.PartyPopupPage;
import com.cbsi.tests.PageObjects.ProductsCatalogPage;
import com.cbsi.tests.PageObjects.DashboardPage.STATUS;
import com.cbsi.tests.util.ElementConstants;
import com.cbsi.tests.util.GlobalVar;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RunWorkFlowManuallyTest extends FormBaseTest{

	public RunWorkFlowManuallyTest(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}
	
	private final static String PRODUCT_CPN = "N82E16811146088";
	private final static String PARTY_NAME = "qa_test";
	private final static String CATALOG_NAME = "execute_workflow1";
	private final static long catId = 54641;
	private final static String bloblFileName = "remap_test.txt";
	private static long workflowId;
	@Test
	public void wf1_unmapOneProductInFront() throws InterruptedException{
		PartyPopupPage partyPopup = catalogsPage.clickPartyChooserIcon();
		catalogsPage = partyPopup.searchParty(PARTY_NAME).pickFromResult();
		ProductsCatalogPage productPage = catalogsPage.goToCatalogByName(CATALOG_NAME);
		productPage.setProductToUse(PRODUCT_CPN);
		
	if(productPage.isProductRowMapped()){
			 MapProductsDialog mapDialog = (MapProductsDialog) productPage.clickAction(ElementConstants.MAP);	
			productPage = mapDialog.clickUnmap().clickSave();
		}
				
		Map<String, String> productAsMap = productPage.getProductAsMap(PRODUCT_CPN);
				
		assertTrue(productAsMap.get("map").equals("false"));
	}
	
	@Test
	public void wf2_triggerWorkFlowOnBack(){
		workflowId = runRemapJob(catId, bloblFileName);
		assertNotNull(workflowId);
	}
	
	@Test
	public void wf3_numProcessOnDashBoard(){
		DashboardPage dashboard = catalogsPage.goBack().goToDashboard();
		dashboard.clickExpandButton(workflowId+"");
		List<Map<String, String>> workflowInfo = dashboard.getWorkflowInfoById(workflowId);
		
		
		for(Map<String, String> map: workflowInfo){
			if(map.get("name").equals("inboundConnector")) {
				assertEquals(map.get("status"),"COMPLETED");
				assertEquals(map.get("numprocessed"), "1");
			}
			else if(map.get("name").equals("parseRemapFileService")){
				assertEquals(map.get("status"), "COMPLETED");
				assertEquals(map.get("numprocessed"), "10");
			}
			else if(map.get("name").equals("downloadRemapCatalogService")){
				assertEquals(map.get("status"), "COMPLETED");
				assertEquals(map.get("numprocessed"), "10");
			}
			else if(map.get("name").equals("fastmapFcatService")){
				assertEquals(map.get("status"), "COMPLETED");
				assertEquals(map.get("numprocessed"), "10");
			}
			else if(map.get("name").equals("updateMappingService")){
				assertEquals(map.get("status"), "COMPLETED");
				assertEquals(map.get("numprocessed"), "1");
			}
		}	
	}
	
	@Test
	public void wf4_productIsMappedOnFront(){
		PartyPopupPage partyPopup = catalogsPage.clickPartyChooserIcon();
		catalogsPage = partyPopup.searchParty(PARTY_NAME).pickFromResult();
		ProductsCatalogPage productPage = catalogsPage.goToCatalogByName(CATALOG_NAME);
		productPage.setProductToUse(PRODUCT_CPN);
		
		assertTrue(productPage.isProductMapped(PRODUCT_CPN));
	}
	
	public long runRemapJob(long catId, String blobFileName){
		String backUrl = GlobalVar.stageServer.replace("6600/fcat/", "6800/fcat-back/") + "services/workflow/runWorkflow?catalogid=" + catId+ "&flowid=1&filePath=" + blobFileName;
		System.out.println(backUrl);
		driver.get(backUrl);
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String workflowId =  driver.findElement(By.cssSelector("body pre")).getText();
			
		return Long.parseLong(workflowId);
	}
}
