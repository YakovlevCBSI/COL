package Smoke;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.support.PageFactory;

import com.cbsi.tests.Foundation.BaseTest;
import com.cbsi.tests.Foundation.ParameterFeeder;
import com.cbsi.tests.PageObjects.CatalogsPage;

/**
 * Add this temp test class for https verification once davit fixes the issue.
 * @author alpark
 *
 */
@RunWith(Parameterized.class)
public class HttpsSanity extends BaseTest{

	public HttpsSanity(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}
	
	@Parameterized.Parameters
	public static Collection testParam(){
		return Arrays.asList(
				new ParameterFeeder().configureTestParams("secure")
				);
	}
	
	@Test
	public void CatalogsPageIsLoaded(){
		CatalogsPage cattalogsPage = PageFactory.initElements(driver, CatalogsPage.class);

	}
	
}
