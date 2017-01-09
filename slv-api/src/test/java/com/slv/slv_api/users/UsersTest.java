package com.slv.slv_api.users;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.slv.slv_api.core.AbstractTest;
import com.slv.slv_api.exceptions.SLVTestsException;
import com.slv.slv_api.services.JsonDiffResult;
import com.slv.slv_api.services.JsonDiffService;

/**
 * @author cmarchive
 * 
 *         Test cases for users part
 */
public class UsersTest extends AbstractTest {

	/**
	 * File name of UserProfil API Inputs
	 */
	private final static String INPUT_FILE = "json/users/input.json";

	/**
	 * File name of UserProfil API Outputs
	 */
	private final static String OUTPUT_FILE = "json/users/output.json";

	@Test
	public void retrieveCurrentUser() throws JsonProcessingException, IOException {
		// INIT
		JsonNode parameters = getInputs().get(UsersMethods.GET_CURRENT_USER.getUrl());
		JsonNode awaitedResponse = getOutputs().get(UsersMethods.GET_CURRENT_USER.getUrl());

		// CALL
		String realResponse = getRestService().get(UsersMethods.GET_CURRENT_USER.getUrl(),
				convert(parameters));
		JsonDiffResult result = JsonDiffService.getInstance().diff(realResponse, awaitedResponse.toString());

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

	public static void main(String[] args) {
		UsersTest test = new UsersTest();
		try {
			test.beforeTest("http://5.196.91.118:8080/celad/api/", "celad", "Celad20!6");
			test.retrieveCurrentUser();
		} catch (SLVTestsException e) {
			e.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
}
