package com.slv.slv_api.others;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.slv.slv_api.core.AbstractTest;
import com.slv.slv_api.exceptions.SLVTestsException;
import com.slv.slv_api.services.JsonDiffResult;

public class OthersTest extends AbstractTest {

	/**
	 * File name of UserProfil API Inputs
	 */
	private final static String INPUT_FILE = "json/others/input.json";

	/**
	 * File name of UserProfil API Outputs
	 */
	private final static String OUTPUT_FILE = "json/others/output.json";
	
	@Test
	public void recoverPassword() throws SLVTestsException {
		//super.beforeTest("http://5.196.91.118:8080/celad/public/api/", null, null);
		JsonDiffResult result = retrieveResult(OthersMethod.RECOVER_PASSWORD.getUrl());

		// VERIFY
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());
	}

	@Override
	protected String getInputFile() {
		return INPUT_FILE;
	}

	@Override
	protected String getOutputFile() {
		return OUTPUT_FILE;
	}
	
}
