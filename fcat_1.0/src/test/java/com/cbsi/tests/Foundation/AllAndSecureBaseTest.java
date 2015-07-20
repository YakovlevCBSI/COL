package com.cbsi.tests.Foundation;

import java.util.Arrays;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class AllAndSecureBaseTest extends BaseTest{
	public AllAndSecureBaseTest(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}

	@Parameterized.Parameters
	public static Collection testParam(){
		return Arrays.asList(
				
				new ParameterFeeder().configureTestParams("allAndSecure")
				);
	}
	
}