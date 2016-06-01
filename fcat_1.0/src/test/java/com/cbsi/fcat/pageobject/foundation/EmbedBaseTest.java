package com.cbsi.fcat.pageobject.foundation;

import java.util.Arrays;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.cbsi.fcat.util.GlobalVar.Env;

@RunWith(Parameterized.class)
public class EmbedBaseTest extends BaseTest{

	public EmbedBaseTest(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}
	
	@Parameterized.Parameters
	public static Collection testParam(){
		return Arrays.asList(
				new ParameterFeeder().configureTestParams(Env.EMBED)
				);
	}

}
