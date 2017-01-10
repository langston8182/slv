package com.slv.slv_api.providers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.slv.slv_api.core.AbstractTest;
import com.slv.slv_api.exceptions.SLVTestsException;
import com.slv.slv_api.services.JsonDiffResult;
import com.slv.slv_api.utils.Constantes;

/**
 * Test class of the rest api URLs for a provider.
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
	 * The key is the method name and the value is the identifier
	 */
	private Map<String, Integer> createdProviderIdMap = new HashMap<String, Integer>();


	/**
	 * Json format test of API Service "asset/getAllProviders"
	 * 
	 * @throws SLVTestsException
	 */
	@Test(groups={"providers-format"}, priority = 1)
	public void getAllProviders() throws SLVTestsException {
		// CALL
		JsonDiffResult result = retrieveResult(ProvidersMethods.GET_ALL_PROVIDERS.getUrl());

		// VERIFY
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());		
	}

	/**
	 * Fails to create a Provider without specifying a name.
	 * 
	 * @throws SLVTestsException 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@Test(groups={"providers-createProviderWithMissingAttributes"}, priority = 2) 
	public void createProviderKoMissedName() throws SLVTestsException, JsonParseException, JsonMappingException, IOException  {
		// INIT
		JsonNode parameters = getInputs().get(ProvidersMethods.CREATE_PROVIDER.getUrl());
		// Remove the name from the request input
		((ObjectNode)parameters).remove(Constantes.CREATE_PROVIDER_INPUT_NAME_KEY);

		// CALL
		JsonDiffResult result = retrieveResult(ProvidersMethods.CREATE_PROVIDER.getUrl());	

		reloadInputs();

		// Extract the error message from the response
		Map<String, Object> map = convert(result.getResponse());
		String errorCode = (String)map.get(Constantes.RESPONSE_ERROR_CODE_KEY);
		String errorMessage = (String)map.get(Constantes.RESPONSE_MESSAGE_KEY);

		// Verify that the error belongs to the name
		Assert.assertTrue(Constantes.INTERNAL_ERROR_CODE.equals(errorCode), result.getErrorMessage());
		Assert.assertTrue(errorMessage.contains("streetlight.data.Provider.name"), result.getErrorMessage());				
	}

	/**
	 * Creates a Provider without specifying a pollution rate.
	 * 
	 * @throws SLVTestsException 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@Test(groups={"providers-createProviderWithMissingAttributes"}, priority = 2) 
	public void createProviderMissedPollutionRate() throws SLVTestsException, JsonParseException, JsonMappingException, IOException  {
		// INIT
		JsonNode parameters = getInputs().get(ProvidersMethods.CREATE_PROVIDER.getUrl());
		// Remove the pollution rate from the request input
		((ObjectNode)parameters).remove(Constantes.CREATE_PROVIDER_INPUT_POLLUTIONRATE_KEY);

		// CALL
		JsonDiffResult result = retrieveResult(ProvidersMethods.CREATE_PROVIDER.getUrl());	

		reloadInputs();

		// Verify that the provider is created 
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());		

		// Extract the id of the created Provider
		Map<String, Object> map = convert(result.getResponse());
		createdProviderIdMap.put("createProviderMissedPollutionRate", (Integer)map.get(Constantes.CREATE_PROVIDER_OUTPUT_ID_KEY));
		deleteCreatedProviderWithMissingAttributes("createProviderMissedPollutionRate");
	}

	/**
	 * Creates a Provider without specifying a time.
	 * 
	 * @throws SLVTestsException 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@Test(groups={"providers-createProviderWithMissingAttributes"}, priority = 2) 
	public void createProviderMissedTime() throws SLVTestsException, JsonParseException, JsonMappingException, IOException  {
		// INIT
		JsonNode parameters = getInputs().get(ProvidersMethods.CREATE_PROVIDER.getUrl());
		// Remove the time from the request input
		((ObjectNode)parameters).remove(Constantes.CREATE_PROVIDER_INPUT_TIME_KEY);

		// CALL
		JsonDiffResult result = retrieveResult(ProvidersMethods.CREATE_PROVIDER.getUrl());		

		// Verify that the provider is created 
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());	

		// Extract the id of the created Provider
		Map<String, Object> map = convert(result.getResponse());
		createdProviderIdMap.put("createProviderMissedTime", (Integer)map.get(Constantes.CREATE_PROVIDER_OUTPUT_ID_KEY));
		deleteCreatedProviderWithMissingAttributes("createProviderMissedTime");
	}

	/**
	 * Deletes a created provider.
	 * 
	 * @param methodName
	 * @throws SLVTestsException
	 */
	public void deleteCreatedProviderWithMissingAttributes(String methodName) throws SLVTestsException {
		if (createdProviderIdMap.get(methodName) != null) {
			JsonNode parameters = getInputs().get(ProvidersMethods.DELETE_PROVIDER.getUrl());
			((ObjectNode)parameters).put(Constantes.DELETE_PROVIDER_INPUT_ID_KEY, createdProviderIdMap.get(methodName));			
			call(ProvidersMethods.DELETE_PROVIDER.getUrl(), parameters);			
		}
	}
	

	/**
	 * Creates a Provider and assert its existence and equality.
	 * 
	 * @throws SLVTestsException 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@Test(groups={"providers-createUpdateDeleteProvider"}, priority = 4) 
	public void createProvider() throws SLVTestsException, JsonParseException, JsonMappingException, IOException  {
		// CALL
		JsonDiffResult result = retrieveResult(ProvidersMethods.CREATE_PROVIDER.getUrl());

		// VERIFY
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());

		// Extract the id of the created Provider
		Map<String, Object> map = convert(result.getResponse());
		createdProviderIdMap.put("createProvider", (Integer)map.get(Constantes.CREATE_PROVIDER_OUTPUT_ID_KEY));
	}

	/**
	 * Updates a Provider and assert that the modification has been done.<br/> 
	 * Depends on {@link #createProvider()}
	 * 
	 * @throws SLVTestsException 
	 */
	@Test(groups={"providers-createUpdateDeleteProvider"}, dependsOnMethods={"createProvider"}, priority = 4) 
	public void updateProvider() throws SLVTestsException {
		if (!createdProviderIdMap.isEmpty()) {
			// INIT
			JsonNode parameters = getInputs().get(ProvidersMethods.UPDATE_PROVIDER.getUrl());
			((ObjectNode)parameters).put(Constantes.UPDATE_PROVIDER_INPUT_ID_KEY, createdProviderIdMap.get("createProvider"));

			// CALL
			JsonDiffResult result = retrieveResult(ProvidersMethods.UPDATE_PROVIDER.getUrl(), parameters);

			// VERIFY
			Assert.assertTrue(result.isEquals(), result.getErrorMessage());
		}		
	}

	/**
	 * Deletes a Provider and assert that it does not exist anymore.<br/> 
	 * Depends on {@link #updateProvider()}
	 * 
	 * @throws SLVTestsException 
	 */
	@Test(groups={"providers-createUpdateDeleteProvider"}, dependsOnMethods={"updateProvider"}, priority = 4) 
	public void deleteProvider() throws SLVTestsException {
		if (!createdProviderIdMap.isEmpty()) {
			// INIT
			JsonNode parameters = getInputs().get(ProvidersMethods.DELETE_PROVIDER.getUrl());
			((ObjectNode)parameters).put(Constantes.DELETE_PROVIDER_INPUT_ID_KEY, createdProviderIdMap.get("createProvider"));

			// CALL
			JsonDiffResult result = retrieveResult(ProvidersMethods.DELETE_PROVIDER.getUrl(), parameters);

			// VERIFY
			Assert.assertTrue(result.isEquals(), result.getErrorMessage());
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
