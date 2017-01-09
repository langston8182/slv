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
	@Test(groups={"userProfile-format"}, priority = 1)
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
	@Test(groups={"userProfile-format"}, priority = 1)
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
	@Test(groups={"userProfile-format"}, priority = 1)
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
	@Test(groups={"userProfile-format"}, priority = 1)
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
	@Test(groups={"userProfile-createUpdateDeleteUser"}, priority = 2) 
	public void createUserProfile() throws SLVTestsException {
		// CALL
		JsonDiffResult result = retrieveResult(UserProfileMethods.CREATE_PROFIL.getUrl());
		
		// VERIFY FORMAT
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());

		// VERIFY RESPONSE
		String response = result.getResponse();
		Map<String, Object> responseJsonNode = convert(response);
		Map<String, Object> inputs = convert(getInputs().get(UserProfileMethods.CREATE_PROFIL.getUrl()));
		
		// Profil names must be equals
		String expectedProfilName = (String)inputs.get("profilName");
		String realProfilName = (String)responseJsonNode.get("name");
		Assert.assertNotNull(realProfilName);
		Assert.assertEquals(realProfilName, expectedProfilName);

		// TODO - Vérifier existence avec appel getGeoZoneProfil?
	}

	/**
	 * Updates a UserProfile and assert that the modification has been done.<br/> 
	 * Depends on {@link #createUserProfile()}
	 * @throws SLVTestsException 
	 */
	@Test(groups={"userProfile-createUpdateDeleteUser"}, dependsOnMethods={"createUserProfile"}, priority = 2) 
	public void updateUserProfile() throws SLVTestsException {
		// CALL
		JsonDiffResult result = retrieveResult(UserProfileMethods.UPDATE_PROFIL.getUrl());
		
		// VERIFY FORMAT
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());
		
		// VERIFY RESPONSE
		String response = result.getResponse();
		
		// Response must be "\"OK\""
		Assert.assertEquals(response, "\"OK\"");

		// TODO - Mettre à jour plus de données et tester que ces données ont été MAJ
	}

	/**
	 * Deletes a UserProfile and assert that it does not exist anymore.<br/> 
	 * Depends on {@link #updateUserProfile()}
	 * @throws SLVTestsException 
	 */
	@Test(groups={"userProfile-createUpdateDeleteUser"}, dependsOnMethods={"updateUserProfile"}, priority = 2) 
	public void deleteUserProfile() throws SLVTestsException {
		// CALL
		JsonDiffResult result = retrieveResult(UserProfileMethods.DELETE_PROFIL.getUrl());

		// VERIFY FORMAT
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());

		// VERIFY RESPONSE
		String response = result.getResponse();
		Map<String, Object> responseJsonNode = convert(response);
		
		// "errorCode" must be present and equals to "0"
		Assert.assertTrue(responseJsonNode.containsKey("errorCode"));
		Assert.assertEquals(responseJsonNode.get("errorCode"), "0");

		// "status" must be present and equals to "OK"
		Assert.assertTrue(responseJsonNode.containsKey("status"));
		Assert.assertEquals(responseJsonNode.get("status"), "OK");
	}
	
	public static void main(String... args) throws JsonProcessingException, IOException, SLVTestsException {
		UserProfileTest runner = new UserProfileTest();

		runner.beforeTest("http://5.196.91.118:8080/celad/api/", "celad", "Celad20!6");

		// CALL
		JsonDiffResult result = runner.retrieveResult(UserProfileMethods.UPDATE_PROFIL.getUrl());
		System.out.println(result.isEquals());
		System.out.println(result.getErrorMessage());
		System.out.println(result.getResponse());

		Map<String, Object> responseJsonNode = runner.convert(result.getResponse());
		
		System.out.println(responseJsonNode);
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
