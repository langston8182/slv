package com.slv.slv_api.userprofile;

import java.util.Iterator;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.slv.slv_api.common.MessageHelper;
import com.slv.slv_api.core.AbstractTest;
import com.slv.slv_api.exceptions.SLVTestsException;
import com.slv.slv_api.services.JsonDiffResult;

public class UserProfileTest extends AbstractTest {

	/**
	 * Value "0" is returned for "errorCode" if object has been deleted
	 */
	private static final String DELETE_ERROR_CODE_OK_VALUE = "0";

	/**
	 * Value "OK" is returned for "status" if object has been deleted
	 */
	private static final String DELETE_STATUS_OK_VALUE = "OK";

	/**
	 * Property value used to define user profile blocked actions
	 */
	private static final String PROPERTY_VALUE_BLOCKED_ACTIONS = "blockedActions";

	/**
	 * Property value used to define user profile skin
	 */
	private static final String PROPERTY_VALUE_SKIN = "skin";

	/**
	 * Property value used to define user profile locale
	 */
	private static final String PROPERTY_VALUE_LOCALE = "locale";

	/**
	 * Contenu de la réponse du service updateProfil dans le cas passant : "OK"
	 */
	private static final String UPDATE_PROFIL_OK_RESPONSE = "\"OK\"";

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
		String expectedProfilName = (String)inputs.get(JsonAttributes.INPUT_CREATE_PROFIL_NAME.getKey());
		String realProfilName = (String)responseJsonNode.get(JsonAttributes.OUTPUT_CREATE_PROFIL_NAME.getKey());
		Assert.assertNotNull(realProfilName, MessageHelper.getMessage("user.profile.create.response.name.not.found"));
		Assert.assertEquals(realProfilName, expectedProfilName, MessageHelper.getMessage("user.profile.create.response.name.not.expected", expectedProfilName, realProfilName));

		// Verify existence by calling "getGeoZoneProfils"
		response = call(UserProfileMethods.GET_GEOZONE_PROFILS.getUrl(), getInputs().get(UserProfileMethods.GET_GEOZONE_PROFILS.getUrl()));
		Assert.assertNotNull(response, MessageHelper.getMessage("user.profile.geozoneprofils.response.null"));
		
		JsonNode jsonNode = convertToJsonNode(response);
		Assert.assertTrue(jsonNode.isArray(), MessageHelper.getMessage("user.profile.geozoneprofils.response.not.array", response));
		
		boolean profilFound = false;
		for (Iterator<JsonNode> iterator = jsonNode.elements(); iterator.hasNext();) {
			JsonNode node = (JsonNode) iterator.next();
			
			// Get the name and compare with expectedName
			JsonNode profilName = node.get(JsonAttributes.OUTPUT_GEOZONES_PROFIL_NAME.getKey());
			profilFound = profilName != null && expectedProfilName.equals(profilName.asText());
			if(profilFound) {
				break;
			}
		}
		
		Assert.assertTrue(profilFound, MessageHelper.getMessage("user.profile.create.profil.not.found.in.list", expectedProfilName));
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
		Assert.assertEquals(response, UPDATE_PROFIL_OK_RESPONSE, MessageHelper.getMessage("user.profile.update.response.not.ok", response));

		// Verify properties by calling "getGeoZoneProfils"
		response = call(UserProfileMethods.GET_GEOZONE_PROFILS.getUrl(), getInputs().get(UserProfileMethods.GET_GEOZONE_PROFILS.getUrl()));
		Assert.assertNotNull(response, MessageHelper.getMessage("user.profile.geozoneprofils.response.null"));
		
		JsonNode jsonNode = convertToJsonNode(response);
		Assert.assertTrue(jsonNode.isArray(), MessageHelper.getMessage("user.profile.geozoneprofils.response.not.array", response));

		// Get the profile properties
		Map<String, Object> inputs = convert(getInputs().get(UserProfileMethods.UPDATE_PROFIL.getUrl()));
		String expectedProfilName = (String)inputs.get(JsonAttributes.INPUT_UPDATE_PROFIL_NAME.getKey());
		JsonNode properties = null;
		for (Iterator<JsonNode> iterator = jsonNode.elements(); iterator.hasNext();) {
			JsonNode node = (JsonNode) iterator.next();
			
			// Get the name and compare with expectedName
			JsonNode profilName = node.get(JsonAttributes.OUTPUT_GEOZONES_PROFIL_NAME.getKey());
			if(expectedProfilName.equals(profilName.asText())) {
				properties = node.get(JsonAttributes.OUTPUT_GEOZONES_PROFIL_PROPERTIES.getKey());
				break;
			}
		}
		
		Assert.assertNotNull(properties, MessageHelper.getMessage("user.profile.geozoneprofils.no.properties"));
		Assert.assertTrue(properties.isArray(), MessageHelper.getMessage("user.profile.geozoneprofils.properties.not.array", response));
		
		// Verify that updated properties are equals to what we sent to updateProfil service
		assertUpdatedPropertiesPresence(inputs, properties);
	}

	/**
	 * Verify that the properties contained in the input {@link Map} are present in the property list defined by the properties parameters
	 * @param inputs a {@link Map} which contains the values sent to the updateProfil service
	 * @param properties a {@link JsonNode} which represents the property list 
	 */
	private void assertUpdatedPropertiesPresence(Map<String, Object> inputs, JsonNode properties) {
		boolean localeCheck = false;
		boolean skinCheck = false;
		boolean blockedActionChecked = false;
		
		for (Iterator<JsonNode> iterator = properties.iterator(); iterator.hasNext();) {
			JsonNode property = (JsonNode) iterator.next();
			// Value of the "key" attribute
			String key = property.get(JsonAttributes.PROPERTY_OBJECT_KEY.getKey()) != null ? property.get(JsonAttributes.PROPERTY_OBJECT_KEY.getKey()).asText() : null;
			// Value of the "value" attribute
			String value = property.get(JsonAttributes.PROPERTY_OBJECT_VALUE.getKey()) != null ? property.get(JsonAttributes.PROPERTY_OBJECT_VALUE.getKey()).asText() : null;
			
			// Search for expected values
			if(PROPERTY_VALUE_LOCALE.equals(key) 
					&& inputs.get(JsonAttributes.PROPERTY_LOCALE.getKey()).equals(value)) {
				localeCheck = true;
			} else if(PROPERTY_VALUE_SKIN.equals(key) 
					&& inputs.get(JsonAttributes.PROPERTY_SKIN.getKey()).equals(value)) {
				skinCheck = true;
			} else if(PROPERTY_VALUE_BLOCKED_ACTIONS.equals(key) && 
					inputs.get(JsonAttributes.PROPERTY_BLOCKED_ACTIONS.getKey()).equals(value)) {
				blockedActionChecked = true;
			}
		}
		
		Assert.assertTrue(localeCheck && skinCheck && blockedActionChecked, MessageHelper.getMessage("user.profile.update.properties.not.updated"));
	}

	/**
	 * Deletes a UserProfile and assert that it does not exist anymore.<br/> 
	 * Depends on {@link #updateUserProfile()}
	 * @throws SLVTestsException 
	 */
	@Test(groups={"userProfile-createUpdateDeleteUser"}, dependsOnMethods={"createUserProfile"}, priority = 3) 
	public void deleteUserProfile() throws SLVTestsException {
		// CALL
		JsonDiffResult result = retrieveResult(UserProfileMethods.DELETE_PROFIL.getUrl());

		// VERIFY FORMAT
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());

		// VERIFY RESPONSE
		String response = result.getResponse();
		Map<String, Object> responseJsonNode = convert(response);
		
		// "errorCode" must be present and equals to "0"
		Assert.assertTrue(responseJsonNode.containsKey(JsonAttributes.OUTPUT_DELETE_ERROR_CODE.getKey()));
		Assert.assertEquals(responseJsonNode.get(JsonAttributes.OUTPUT_DELETE_ERROR_CODE.getKey()), DELETE_ERROR_CODE_OK_VALUE);

		// "status" must be present and equals to "OK"
		Assert.assertTrue(responseJsonNode.containsKey(JsonAttributes.OUTPUT_DELETE_STATUS.getKey()));
		Assert.assertEquals(responseJsonNode.get(JsonAttributes.OUTPUT_DELETE_STATUS.getKey()), DELETE_STATUS_OK_VALUE);

		// "statusOk" must be present and equals to true
		Assert.assertTrue(responseJsonNode.containsKey(JsonAttributes.OUTPUT_DELETE_STATUS_OK.getKey()));
		Assert.assertEquals(responseJsonNode.get(JsonAttributes.OUTPUT_DELETE_STATUS_OK.getKey()), true);

		// "statusError" must be present and equals to false
		Assert.assertTrue(responseJsonNode.containsKey(JsonAttributes.OUTPUT_DELETE_STATUS_ERROR.getKey()));
		Assert.assertEquals(responseJsonNode.get(JsonAttributes.OUTPUT_DELETE_STATUS_ERROR.getKey()), false);
	}
	
	public static void main(String... args) throws SLVTestsException {
		UserProfileTest runner = new UserProfileTest();
		runner.beforeTest("http://5.196.91.118:8080/celad/api/", "celad", "Celad20!6");
		runner.createUserProfile();
		runner.updateUserProfile();
		runner.deleteUserProfile();
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
