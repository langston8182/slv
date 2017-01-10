package com.slv.slv_api.providers;

import java.io.IOException;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.slv.slv_api.core.AbstractTest;
import com.slv.slv_api.exceptions.SLVTestsException;
import com.slv.slv_api.services.JsonDiffResult;
import com.slv.slv_api.utils.Constantes;

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
	 * Shared string to store the id of the created provider for functional tests
	 */
	private Integer providerId = null;


	/**
	 * Json format test of API Service "asset/getAllProviders"
	 * 
	 * @throws SLVTestsException
	 */
	@Test(groups={"providers-format"})
	public void getAllProviders() throws SLVTestsException {
		// CALL
		JsonDiffResult result = retrieveResult(ProvidersMethods.GET_ALL_PROVIDERS.getUrl());

		// VERIFY
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());		
	}
	
	/**
	 * Creates a Provider and assert its existence and equality.
	 * 
	 * @throws SLVTestsException 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@Test
	public void createProviderKo() throws SLVTestsException, JsonParseException, JsonMappingException, IOException  {
		// INIT
		JsonNode parameters = getInputs().get(ProvidersMethods.CREATE_PROVIDER.getUrl());
		((ObjectNode)parameters).remove(Constantes.CREATE_PROVIDER_INPUT_NAME_KEY);
		
		// CALL
		JsonDiffResult result = retrieveResult(ProvidersMethods.CREATE_PROVIDER.getUrl());		

		// VERIFY
		Assert.assertFalse(result.isEquals(), result.getErrorMessage());

		// Extract the id of the created Provider
		Map<String, Object> map = convert(result.getResponse());
		String errorCode = (String)map.get(Constantes.RESPONSE_ERROR_CODE_KEY);
		String errorMessage = (String)map.get(Constantes.RESPONSE_MESSAGE_KEY);
		
		Assert.assertTrue(Constantes.INTERNAL_ERROR_CODE.equals(errorCode), result.getErrorMessage());
		Assert.assertTrue(errorMessage.contains("streetlight.data.Provider.name"), result.getErrorMessage());
		
		
	}

	/**
	 * Creates a Provider and assert its existence and equality.
	 * 
	 * @throws SLVTestsException 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@Test(groups={"providers-createUpdateDeleteProvider"}) 
	public void createProvider() throws SLVTestsException, JsonParseException, JsonMappingException, IOException  {
		// CALL
		JsonDiffResult result = retrieveResult(ProvidersMethods.CREATE_PROVIDER.getUrl());

		// VERIFY
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());

		// Extract the id of the created Provider
		Map<String, Object> map = convert(result.getResponse());
		providerId = (Integer)map.get(Constantes.CREATE_PROVIDER_OUTPUT_ID_KEY);
	}

	/**
	 * Updates a Provider and assert that the modification has been done.<br/> 
	 * Depends on {@link #createProvider()}
	 * 
	 * @throws SLVTestsException 
	 */
	@Test(groups={"providers-createUpdateDeleteProvider"}, dependsOnMethods={"createProvider"}) 
	public void updateProvider() throws SLVTestsException {
		if (providerId != null) {
			// INIT
			JsonNode parameters = getInputs().get(ProvidersMethods.UPDATE_PROVIDER.getUrl());
			((ObjectNode)parameters).put(Constantes.UPDATE_PROVIDER_INPUT_ID_KEY, providerId);
			
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
	@Test(groups={"providers-createUpdateDeleteProvider"}, dependsOnMethods={"updateProvider"}) 
	public void deleteProvider() throws SLVTestsException {
		if (providerId != null) {
			// INIT
			JsonNode parameters = getInputs().get(ProvidersMethods.DELETE_PROVIDER.getUrl());
			((ObjectNode)parameters).put(Constantes.DELETE_PROVIDER_INPUT_ID_KEY, providerId);
			
			// CALL
			JsonDiffResult result = retrieveResult(ProvidersMethods.DELETE_PROVIDER.getUrl(), parameters);

			// VERIFY
			Assert.assertTrue(result.isEquals(), result.getErrorMessage());
		}
	}

	//	@Test(enabled = true)
	//	public void createProvider() throws JsonProcessingException, IOException {
	//		// INIT
	//		JsonNode parameters = getInputs().get(ProvidersMethods.CREATE_PROVIDER.getUrl());
	//		JsonNode awaitedResponse = getOutputs().get(ProvidersMethods.CREATE_PROVIDER.getUrl());
	//		
	//		// CALL
	//		String realResponse = getRestService().get(ProvidersMethods.CREATE_PROVIDER.getUrl(), convert(parameters));
	//		JsonDiffResult result = JsonDiffService.getInstance().diff(realResponse, awaitedResponse.toString());
	//		
	//		// VERIFY
	//		Assert.assertTrue(result.isEquals(), result.getErrorMessage());
	//	}


	//
	//	@Test(enabled = false)
	//	public void updateProvider() {
	//		JsonNode parameters = getInputs().get(ProvidersMethods.UPDATE_PROVIDER.getUrl());
	//		JsonNode awaitedResponse = getOutputs().get(ProvidersMethods.UPDATE_PROVIDER.getUrl());
	//		System.out.println(getRestService().get(ProvidersMethods.UPDATE_PROVIDER.getUrl(), convert(parameters)));
	//		Assert.assertTrue(true);
	//	}
	//
	//	@Test(enabled = false)
	//	public void deleteProvider() {
	//		JsonNode parameters = getInputs().get(ProvidersMethods.DELETE_PROVIDER.getUrl());
	//		JsonNode awaitedResponse = getOutputs().get(ProvidersMethods.DELETE_PROVIDER.getUrl());
	//		System.out.println(getRestService().get(ProvidersMethods.DELETE_PROVIDER.getUrl(), convert(parameters)));
	//		Assert.assertTrue(true);
	//	}

	//	@AfterTest
	//	public void nettoyage() throws JsonProcessingException, IOException {
	//		// INIT
	//		JsonNode parameters = getInputs().get(ProvidersMethods.DELETE_PROVIDER.getUrl());
	//		getOutputs().get(ProvidersMethods.DELETE_PROVIDER.getUrl());	
	//		// CALL
	//		getRestService().get(ProvidersMethods.DELETE_PROVIDER.getUrl(), convert(parameters));
	//	}

	//	public static void main(String... args) throws JsonProcessingException, IOException, SLVTestsException {
	//		ProvidersTest runner = new ProvidersTest();
	//
	//		runner.beforeTest("http://5.196.91.118:8080/celad/api/", "celad", "Celad20!6");
	//		JsonNode parameters = runner.getInputs().get(ProvidersMethods.GET_ALL_PROVIDERS.getUrl());
	//		JsonNode awaitedResponse = runner.getOutputs().get(ProvidersMethods.GET_ALL_PROVIDERS.getUrl());
	//		String realResponse = runner.getRestService().get(ProvidersMethods.GET_ALL_PROVIDERS.getUrl(), runner.convert(parameters));
	//		
	//		JsonDiffService comparator = JsonDiffService.getInstance();
	//		JsonDiffResult result = comparator.diff(realResponse, awaitedResponse.toString());
	//		
	//		System.out.println(result.isEquals());
	//		System.out.println(result.getErrorMessage());
	//	}

	public static void main(String... args) throws JsonProcessingException, IOException, SLVTestsException {
		ProvidersTest runner = new ProvidersTest();

		runner.beforeTest("http://5.196.91.118:8080/celad/api/", "celad", "Celad20!6");
		// CALL
		JsonDiffResult result = runner.retrieveResult(ProvidersMethods.CREATE_PROVIDER.getUrl());

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
