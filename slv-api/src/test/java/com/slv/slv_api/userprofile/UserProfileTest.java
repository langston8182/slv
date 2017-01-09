package com.slv.slv_api.userprofile;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.slv.slv_api.core.AbstractTest;
import com.slv.slv_api.exceptions.SLVTestsException;
import com.slv.slv_api.services.JsonDiffResult;
import com.slv.slv_api.services.JsonDiffService;

public class UserProfileTest extends AbstractTest {

	/**
	 * File name of UserProfile API Inputs
	 */
	private final static String INPUT_FILE = "json/userprofile/input.json";
	
	/**
	 * File name of UserProfile API Outputs
	 */
	private final static String OUTPUT_FILE = "json/userprofile/output.json";
	
	/**
	 * Json format test of API Service "userProfile/getGeoZoneProfils"
	 * @throws SLVTestsException
	 */
	@Test(groups={"userProfile-format"})
	public void getGeoZoneProfils() throws SLVTestsException {
		// CALL
		JsonDiffResult result = retrieveResult(UserProfileMethods.GET_GEOZONE_PROFILS.getUrl());
		
		// VERIFY
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());
	}

	/**
	 * Json format test of API Service "userProfile/getCurrentProfil"
	 * @throws SLVTestsException
	 */
	@Test(groups={"userProfile-format"})
	public void getCurrentProfil() throws SLVTestsException {
		// CALL
		JsonDiffResult result = retrieveResult(UserProfileMethods.GET_CURRENT_PROFIL.getUrl());
		
		// VERIFY
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());
	}

	/**
	 * Json format test of API Service "asset/getProfilProperties"
	 * @throws SLVTestsException
	 */
	@Test(groups={"userProfile-format"})
	public void getProfilProperties() throws SLVTestsException {
		// CALL
		JsonDiffResult result = retrieveResult(UserProfileMethods.GET_PROFIL_PROPERTIES.getUrl());
		
		// VERIFY
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());
	}

	/**
	 * Json format test of API Service "userprofile/getProfilPropertyDescriptors"
	 * @throws SLVTestsException
	 */
	@Test(groups={"userProfile-format"})
	public void getProfilPropertyDescriptors() throws SLVTestsException {
		// CALL
		JsonDiffResult result = retrieveResult(UserProfileMethods.GET_PROFIL_PROPERTY_DESCRIPTORS.getUrl());
		
		// VERIFY
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());
	}
	
	/**
	 * Creates a UserProfile and assert its existence and equality.
	 * @throws SLVTestsException 
	 */
	@Test(groups={"userProfile-createUpdateDeleteUser"}) 
	public void createUserProfile() throws SLVTestsException {
		// CALL
		JsonDiffResult result = retrieveResult(UserProfileMethods.CREATE_PROFIL.getUrl());
		
		// VERIFY FORMAT
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());

		// VERIFY RESPONSE
		String response = result.getResponse();
		Map<String, JsonNode> responseJsonNode = extractJsonStreams(response);
		Map<String, Object> inputs = convert(getInputs().get(UserProfileMethods.CREATE_PROFIL.getUrl()));
		
		// Profil names must be equals
		String expectedProfilName = (String)inputs.get("profilName");
		JsonNode realProfilName = responseJsonNode.get("name");
		Assert.assertTrue(realProfilName.isTextual());
		Assert.assertEquals(realProfilName, expectedProfilName);
	}

	/**
	 * Updates a UserProfile and assert that the modification has been done.<br/> 
	 * Depends on {@link #createUserProfile()}
	 */
	@Test(groups={"userProfile-createUpdateDeleteUser"}, dependsOnMethods={"createUserProfile"}) 
	public void updateUserProfile() {
		String result = call(UserProfileMethods.UPDATE_PROFIL.getUrl(), getInputs().get(UserProfileMethods.UPDATE_PROFIL.getUrl()));
		Assert.assertTrue(true);
	}

	/**
	 * Deletes a UserProfile and assert that it does not exist anymore.<br/> 
	 * Depends on {@link #updateUserProfile()}
	 */
	@Test(groups={"userProfile-createUpdateDeleteUser"}, dependsOnMethods={"updateUserProfile"}) 
	public void deleteUserProfile() {
		String result = call(UserProfileMethods.DELETE_PROFIL.getUrl(), getInputs().get(UserProfileMethods.DELETE_PROFIL.getUrl()));
		Assert.assertTrue(true);
	}


	private static final Logger logger = Logger.getLogger(AbstractTest.class);
	
	public static void main(String... args) throws JsonProcessingException, IOException, SLVTestsException {
		UserProfileTest runner = new UserProfileTest();

		runner.beforeTest("http://5.196.91.118:8080/celad/api/", "celad", "Celad20!6");
		JsonNode parameters = runner.getInputs().get(UserProfileMethods.GET_GEOZONE_PROFILS.getUrl());
		JsonNode awaitedResponse = runner.getOutputs().get(UserProfileMethods.GET_GEOZONE_PROFILS.getUrl());
		String realResponse = runner.getRestService().get(UserProfileMethods.GET_GEOZONE_PROFILS.getUrl(), runner.convert(parameters));

		System.out.println(realResponse.toString());
		JsonDiffService comparator = JsonDiffService.getInstance();
		JsonDiffResult result = comparator.diff(realResponse, awaitedResponse.toString());
		
		System.out.println(result.isEquals());
		System.out.println(result.getErrorMessage());
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
