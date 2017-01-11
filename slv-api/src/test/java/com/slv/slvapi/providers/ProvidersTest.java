package com.slv.slvapi.providers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.slv.slvapi.core.AbstractTest;
import com.slv.slvapi.utils.Constantes;
import com.slv.slvapi.common.MessageHelper;
import com.slv.slvapi.exceptions.SlvTestsException;
import com.slv.slvapi.services.JsonDiffResult;

/**
 * Test class for the rest api URLs of a provider.
 * 
 * @author yromdhane
 *
 */
public class ProvidersTest extends AbstractTest {

	/**
	 * File name of Providers API Inputs
	 */
	private final static String INPUT_FILE = "json/providers/input.json";

	/**
	 * File name of Providers API Outputs
	 */
	private final static String OUTPUT_FILE = "json/providers/output.json";

	/**
	 * Map to store the identifiers of the created providers in order to delete them at the end of the tests
	 * The key is the method name and the value is the provider identifier
	 */
	private Map<String, Integer> createdProviderIdMap = new HashMap<String, Integer>();


	/**
	 * Json format test of API Service "asset/getAllProviders"
	 * 
	 * @throws SlvTestsException
	 */
	@Test(groups={"providers-format"}, priority = 1)
	public void getAllProviders() throws SlvTestsException {
		// CALL
		JsonDiffResult result = retrieveResult(ProvidersMethods.GET_ALL_PROVIDERS.getUrl());

		// VERIFY
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());		
	}

	//----------- Begin Functional test : Create - Update - Delete -------------
	/**
	 * Creates a Provider and assert its existence and equality.
	 * 
	 * @throws SlvTestsException 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@Test(groups={"providers-createUpdateDeleteProvider"}, priority = 2) 
	public void createProvider() throws SlvTestsException, JsonParseException, JsonMappingException, IOException  {
		// CALL
		JsonDiffResult result = retrieveResult(ProvidersMethods.CREATE_PROVIDER.getUrl());

		// Verify that the provider is created 
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());

		// Extract the id of the created Provider
		Map<String, Object> map = convert(result.getResponse());
		createdProviderIdMap.put(ProviderTestMethod.createProvider.getName(), (Integer)map.get(Constantes.CREATE_PROVIDER_OUTPUT_ID_KEY));
	}

	/**
	 * Updates a Provider and assert that the modification has been done.<br/> 
	 * Depends on {@link #createProvider()}
	 * 
	 * @throws SlvTestsException 
	 */
	@Test(groups={"providers-createUpdateDeleteProvider"}, dependsOnMethods={"createProvider"}, priority = 2) 
	public void updateProvider() throws SlvTestsException {
		// INIT
		JsonNode parameters = getInputs().get(ProvidersMethods.UPDATE_PROVIDER.getUrl()).deepCopy();
		((ObjectNode)parameters).put(Constantes.UPDATE_PROVIDER_INPUT_ID_KEY, createdProviderIdMap.get(ProviderTestMethod.createProvider.getName()));

		// CALL
		JsonDiffResult result = retrieveResult(ProvidersMethods.UPDATE_PROVIDER.getUrl(), parameters);

		// Verify that the provider is updated 
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());	
	}

	/**
	 * Deletes a Provider and assert that it does not exist anymore.<br/> 
	 * Depends on {@link #updateProvider()}
	 * 
	 * @throws SlvTestsException 
	 */
	@Test(groups={"providers-createUpdateDeleteProvider"}, dependsOnMethods={"updateProvider"}, priority = 2) 
	public void deleteProvider() throws SlvTestsException {
		// INIT
		JsonNode parameters = getInputs().get(ProvidersMethods.DELETE_PROVIDER.getUrl()).deepCopy();
		((ObjectNode)parameters).put(Constantes.DELETE_PROVIDER_INPUT_ID_KEY, createdProviderIdMap.get(ProviderTestMethod.createProvider.getName()));

		// CALL
		JsonDiffResult result = retrieveResult(ProvidersMethods.DELETE_PROVIDER.getUrl(), parameters);

		// VERIFY
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());
	}

	//----------- End Functional test : Create - Update - Delete -------------

	/**
	 * Fails to create a Provider without specifying a name.
	 * 
	 * @throws SlvTestsException 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@Test(groups={"providers-createProviderWithMissingAttributes"}, dependsOnMethods={"deleteProvider"}, priority = 3) 
	public void createProviderKoMissedName() throws SlvTestsException, JsonParseException, JsonMappingException, IOException  {
		// INIT
		JsonNode parameters = getInputs().get(ProvidersMethods.CREATE_PROVIDER.getUrl()).deepCopy();
		// Remove the name from the request input
		((ObjectNode)parameters).remove(Constantes.CREATE_PROVIDER_INPUT_NAME_KEY);

		// CALL
		String response = call(ProvidersMethods.CREATE_PROVIDER.getUrl(), parameters);	

		// Extract the error message from the response
		Map<String, Object> map = convert(response);
		String errorCode = (String)map.get(Constantes.RESPONSE_ERROR_CODE_KEY);
		String errorMessage = (String)map.get(Constantes.RESPONSE_MESSAGE_KEY);

		// Verify that the error belongs to the name
		Assert.assertTrue(Constantes.INTERNAL_ERROR_CODE.equals(errorCode), MessageHelper.getMessage("providers.error.code.not.recognized"));
		Assert.assertTrue(errorMessage.contains("streetlight.data.Provider.name"), MessageHelper.getMessage("providers.error.message.not.recognized"));	
	}

	/**
	 * Creates a Provider without specifying a pollution rate.
	 * 
	 * @throws SlvTestsException 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@Test(groups={"providers-createProviderWithMissingAttributes"}, dependsOnMethods={"deleteProvider"}, priority = 3) 
	public void createProviderMissedPollutionRate() throws SlvTestsException, JsonParseException, JsonMappingException, IOException  {
		// INIT
		JsonNode parameters = getInputs().get(ProvidersMethods.CREATE_PROVIDER.getUrl()).deepCopy();
		// Remove the pollution rate from the request input
		((ObjectNode)parameters).remove(Constantes.CREATE_PROVIDER_INPUT_POLLUTIONRATE_KEY);

		// CALL
		JsonDiffResult result = retrieveResult(ProvidersMethods.CREATE_PROVIDER.getUrl(), parameters);

		// Verify that the provider is created 
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());		

		// Extract the id of the created Provider
		Map<String, Object> map = convert(result.getResponse());
		createdProviderIdMap.put(ProviderTestMethod.createProviderMissedPollutionRate.getName(), (Integer)map.get(Constantes.CREATE_PROVIDER_OUTPUT_ID_KEY));
		deleteCreatedProviderWithMissingAttributes(ProviderTestMethod.createProviderMissedPollutionRate.getName());
	}

	/**
	 * Creates a Provider without specifying a time.
	 * 
	 * @throws SlvTestsException 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@Test(groups={"providers-createProviderWithMissingAttributes"}, dependsOnMethods={"deleteProvider"}, priority = 3) 
	public void createProviderMissedTime() throws SlvTestsException, JsonParseException, JsonMappingException, IOException  {
		// INIT
		JsonNode parameters = getInputs().get(ProvidersMethods.CREATE_PROVIDER.getUrl()).deepCopy();
		// Remove the time from the request input
		((ObjectNode)parameters).remove(Constantes.CREATE_PROVIDER_INPUT_TIME_KEY);

		// CALL
		JsonDiffResult result = retrieveResult(ProvidersMethods.CREATE_PROVIDER.getUrl(), parameters);		

		// Verify that the provider is created 
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());	

		// Extract the id of the created Provider
		Map<String, Object> map = convert(result.getResponse());
		createdProviderIdMap.put(ProviderTestMethod.createProviderMissedTime.getName(), (Integer)map.get(Constantes.CREATE_PROVIDER_OUTPUT_ID_KEY));
		deleteCreatedProviderWithMissingAttributes(ProviderTestMethod.createProviderMissedTime.getName());
	}


	/**
	 * Initialize a provider for update and delete operations test
	 * 
	 * @throws SlvTestsException
	 */
	@Test(groups={"providers-updateProviderWithMissingAttributes"}, dependsOnGroups = { "providers-createProviderWithMissingAttributes" }, priority = 3)
	public void initProviderOfUpdateTests() throws SlvTestsException {
		// Create a provider
		String response = call(ProvidersMethods.CREATE_PROVIDER.getUrl(), getInputs().get(ProvidersMethods.CREATE_PROVIDER.getUrl()));
		// Extract the id of the created Provider
		Map<String, Object> map = convert(response);
		createdProviderIdMap.put(ProviderTestMethod.initProviderOfUpdateTests.getName(), (Integer)map.get(Constantes.CREATE_PROVIDER_OUTPUT_ID_KEY));
	}

	/**
	 * Fails to update a Provider without specifying a new name.
	 * 
	 * @throws SlvTestsException 
	 */
	@Test(groups={"providers-updateProviderWithMissingAttributes"}, dependsOnMethods = { "initProviderOfUpdateTests" }, priority = 3)
	public void updateProviderMissedNewName() throws SlvTestsException {
		// INIT
		JsonNode parameters = getInputs().get(ProvidersMethods.UPDATE_PROVIDER.getUrl()).deepCopy();
		// Remove the name from the request input
		((ObjectNode)parameters).remove(Constantes.UPDATE_PROVIDER_INPUT_NEW_NAME_KEY);
		((ObjectNode)parameters).put(Constantes.UPDATE_PROVIDER_INPUT_ID_KEY, createdProviderIdMap.get(ProviderTestMethod.initProviderOfUpdateTests.getName()));			

		// CALL
		JsonDiffResult result = retrieveResult(ProvidersMethods.UPDATE_PROVIDER.getUrl(), parameters);

		// VERIFY
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());	
	}

	/**
	 * Fails to update a Provider without specifying a new name.
	 * 
	 * @throws SlvTestsException 
	 */
	@Test(groups={"providers-updateProviderWithMissingAttributes"}, dependsOnMethods = { "initProviderOfUpdateTests" }, priority = 3) 
	public void updateProviderMissedProviderId() throws SlvTestsException {
		// INIT
		JsonNode parameters = getInputs().get(ProvidersMethods.UPDATE_PROVIDER.getUrl()).deepCopy();
		// Remove the provider id from the request input
		((ObjectNode)parameters).remove(Constantes.UPDATE_PROVIDER_INPUT_ID_KEY);

		// CALL
		String response = call(ProvidersMethods.UPDATE_PROVIDER.getUrl(), parameters);			

		// Extract the error code from the response
		Map<String, Object> map = convert(response);
		String errorCode = (String)map.get(Constantes.RESPONSE_ERROR_CODE_KEY);

		// Verify that the error belongs to the name
		Assert.assertTrue(Constantes.ITEM_NOT_FOUND_ERROR_CODE.equals(errorCode), MessageHelper.getMessage("providers.error.code.not.recognized"));				
	}

	/**
	 * Initialize a provider for update and delete operations test
	 * 
	 * @throws SlvTestsException
	 */
	@Test(groups={"providers-updateProviderWithMissingAttributes"}, dependsOnMethods = { "updateProviderMissedNewName", "updateProviderMissedProviderId" }, priority = 3)
	public void clearProviderOfUpdateTests() throws SlvTestsException {	
		deleteCreatedProviderWithMissingAttributes("initProviderOfUpdateTests");		
	}

	/**
	 * Deletes a created provider.
	 * 
	 * @param methodName
	 * 
	 * @throws SlvTestsException
	 */
	public void deleteCreatedProviderWithMissingAttributes(String methodName) throws SlvTestsException {
		if (createdProviderIdMap.get(methodName) != null) {
			JsonNode parameters = getInputs().get(ProvidersMethods.DELETE_PROVIDER.getUrl());
			((ObjectNode)parameters).put(Constantes.DELETE_PROVIDER_INPUT_ID_KEY, createdProviderIdMap.get(methodName));			
			call(ProvidersMethods.DELETE_PROVIDER.getUrl(), parameters);			
		}
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