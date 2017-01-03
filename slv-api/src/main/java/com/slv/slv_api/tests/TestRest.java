package com.slv.slv_api.tests;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.slv.slv_api.Methods;
import com.slv.slv_api.RestService;

public class TestRest {

	private RestService restService;
	
	@BeforeTest(groups={"user"})
	@Parameters({"url", "login", "password"})
	public void beforeTest(String url, String login, String password) {
		restService = RestService.getInstance(url, login, password);
	}
	
	@Test(groups = {"user"})
	public void testRetrieveCurrentProfil() throws Exception {
		Reporter.log(restService.get(Methods.RETRIEVE_CURRENT_PROFILE));
		Assert.assertTrue(true);
	}
	
	@Test(groups = {"user"})
	public void testRetrieveCurrentUser() throws Exception {
		Reporter.log(restService.get(Methods.RETRIEVE_CURRENT_USER));
		Assert.assertTrue(true);
	}

}
