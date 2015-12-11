package com.cbsi.fcat.pageobject.foundation;

import org.junit.Test;

/**
 * http://junit.10954.n7.nabble.com/Mixing-parameterized-and-non-parameterized-tests-in-the-same-test-class-td4237.html
 * https://github.com/orfjackal/jumi/blob/709c5b75abc613f7673dfdd8fa94ad3aaf0e58ed/end-to-end-tests/src/test/java/fi/jumi/test/PartiallyParameterized.java
 * 
 * @author alpark
 *
 */
public class SubBaseTestWithParam extends BaseTest{

	public SubBaseTestWithParam(String URL, String browser) {
		super(URL, browser);
		// TODO Auto-generated constructor stub
	}
	/**
	public BaseTest(String URL, String browser){
		this.URL = URL;
		this.browser = browser;
	}
	*/


}
