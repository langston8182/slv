package com.slv.slv_api.userprofile;

import java.io.IOException;
import java.util.Iterator;
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

		// Verify existence by calling "getGeoZoneProfils"
		response = call(UserProfileMethods.GET_GEOZONE_PROFILS.getUrl(), getInputs().get(UserProfileMethods.GET_GEOZONE_PROFILS.getUrl()));
		Assert.assertNotNull(response);
		
		JsonNode jsonNode = convertToJsonNode(response);
		Assert.assertTrue(jsonNode.isArray());
		
		boolean profilFound = false;
		for (Iterator<JsonNode> iterator = jsonNode.elements(); iterator.hasNext();) {
			JsonNode node = (JsonNode) iterator.next();
			
			// Get the name and compare with expectedName
			JsonNode profilName = node.get("name");
			profilFound = expectedProfilName.equals(profilName.asText());
			if(profilFound) {
				break;
			}
		}
		
		Assert.assertTrue(profilFound);
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

		// Verify properties by calling "getGeoZoneProfils"
		response = call(UserProfileMethods.GET_GEOZONE_PROFILS.getUrl(), getInputs().get(UserProfileMethods.GET_GEOZONE_PROFILS.getUrl()));
		Assert.assertNotNull(response);
		
		JsonNode jsonNode = convertToJsonNode(response);
		Assert.assertTrue(jsonNode.isArray());

		// Get the profile properties
		Map<String, Object> inputs = convert(getInputs().get(UserProfileMethods.UPDATE_PROFIL.getUrl()));
		String expectedProfilName = (String)inputs.get("profilName");
		JsonNode properties = null;
		for (Iterator<JsonNode> iterator = jsonNode.elements(); iterator.hasNext();) {
			JsonNode node = (JsonNode) iterator.next();
			
			// Get the name and compare with expectedName
			JsonNode profilName = node.get("name");
			if(expectedProfilName.equals(profilName.asText())) {
				properties = node.get("properties");
				break;
			}
		}
		
		Assert.assertNotNull(properties);
		Assert.assertTrue(properties.isArray());
		
		// Verify that updated properties are equals to what have been sent to the service
		boolean localeCheck = false;
		boolean skinCheck = false;
		boolean blockedActionChecked = false;
		
		for (Iterator<JsonNode> iterator = properties.iterator(); iterator.hasNext();) {
			JsonNode property = (JsonNode) iterator.next();
			
			if("locale".equals(property.get("key").asText()) && inputs.get("property.locale").equals(property.get("value").asText())) {
				localeCheck = true;
			} else if("skin".equals(property.get("key").asText()) && inputs.get("property.skin").equals(property.get("value").asText())) {
				skinCheck = true;
			} else if("blockedActions".equals(property.get("key").asText()) && inputs.get("property.blockedActions").equals(property.get("value").asText())) {
				blockedActionChecked = true;
			}
		}
		
		Assert.assertTrue(localeCheck && skinCheck && blockedActionChecked);
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
	
	@Override
	protected String getInputFile() {
		return INPUT_FILE;
	}

	@Override
	protected String getOutputFile() {
		return OUTPUT_FILE;
	}
	
	

}
