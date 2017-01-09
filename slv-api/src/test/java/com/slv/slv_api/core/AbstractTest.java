package com.slv.slv_api.core;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.slv.slv_api.common.MessageHelper;
import com.slv.slv_api.exceptions.ExceptionCode;
import com.slv.slv_api.exceptions.SLVTestsException;
import com.slv.slv_api.services.JsonDiffResult;
import com.slv.slv_api.services.JsonDiffService;
import com.slv.slv_api.services.RestService;

public abstract class AbstractTest {

	private static final Logger logger = Logger.getLogger(AbstractTest.class);
	
	/**
	 * Rest Client service
	 */
	private RestService restService;
	
	/**
	 * Map of inputs for each API Method
	 */
	private Map<String, JsonNode> inputs;
	
	/**
	 * Map of outputs for each API Method
	 */
	private Map<String, JsonNode> outputs;
	
	@BeforeTest
	@Parameters({ "url", "login", "password" })
	protected void beforeTest(String url, String login, String password) throws SLVTestsException {
		restService = RestService.getInstance(url, login, password);
		inputs = extractJsonStreams(getInputFile());
		outputs = extractJsonStreams(getOutputFile());
	}
	
	/**
	 * <p>
	 * Extract Json Object from a file. This file must be formatted like the
	 * example below : <br/>
	 * {<br>
	 * "key" : {JsonObject1},<br>
	 * "key2" : {JsonObject2}<br>
	 * }<br>
	 * </p>
	 * 
	 * @param inputFile
	 *            the file to
	 * @return
	 * @throws SLVTestsException
	 */
	protected Map<String, JsonNode> extractJsonStreams(String inputFile) throws SLVTestsException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			URL fileURL = this.getClass().getClassLoader().getResource(inputFile);
			if(fileURL == null) {
				logger.error(MessageHelper.getMessage("core.abstract.test.json.files.load.not.exists"));
				throw new SLVTestsException(ExceptionCode.READ_JSON_FILES.toString(), MessageHelper.getMessage("core.abstract.test.json.files.load.error"));
			}
			return mapper.readValue(new File(fileURL.getPath()),
					new TypeReference<Map<String, JsonNode>>() {
					});
		} catch (IOException e) {
			logger.error(MessageHelper.getMessage("core.abstract.test.json.files.load.error"), e);
			throw new SLVTestsException(ExceptionCode.READ_JSON_FILES.toString(), MessageHelper.getMessage("core.abstract.test.json.files.load.error"),
					e);
		}
	}
	
	/**
	 * get result of the test
	 * 
	 * @param method
	 *            The method to apply
	 * @throws SLVTestsException 
	 */
	protected JsonDiffResult retrieveResult(String url) throws SLVTestsException {
		logger.info("Exécution du test " + url);
		
		// INIT
		JsonNode parameters = getInputs().get(url);
		
		return callAndCompare(url, parameters);
	}
	
	/**
	 * get result of the test
	 * 
	 * @param method
	 *            The method to apply
	 * @throws SLVTestsException 
	 */
	protected JsonDiffResult retrieveResult(String url, JsonNode parameters) throws SLVTestsException {
		logger.info("Exécution du test " + url);				
		return callAndCompare(url, parameters);
	}
	
	/**
	 * Call a service and compare its result to a response example
	 * 
	 * @param url
	 * @param parameters
	 * 
	 * @return {@link JsonDiffResult}
	 * 
	 * @throws SLVTestsException 
	 */
	protected JsonDiffResult callAndCompare(String url, JsonNode parameters) throws SLVTestsException {
		// INIT
		JsonNode expectedResponse = getOutputs().get(url);
		// CALL
		String realResponse = call(url, parameters);
		try {
			JsonDiffResult jsonDiffResult = JsonDiffService.getInstance().diff(realResponse, expectedResponse.toString());
			jsonDiffResult.setResponse(realResponse);
			return jsonDiffResult;
		} catch(IOException e) {
			logger.error(MessageHelper.getMessage("core.abstract.test.diff.error"), e);
			throw new SLVTestsException(ExceptionCode.DIFF_METHOD_CALL.toString(), MessageHelper.getMessage("core.abstract.test.diff.error"), e);
		}
	}
	
	/**
	 * Call a Rest Service by its HTTP URL with optional parameters
	 * @param url the HTTP url to call
	 * @param parameters the parameters to send (optionnal)
	 * @return a {@link String} containing the response
	 */
	protected String call(String url, Map<String, Object> parameters) {
		return getRestService().get(url, parameters);
	}

	/**
	 * Call a Rest Service by its HTTP URL with optional parameters
	 * @param url the HTTP url to call
	 * @param parameters the parameters to send (optionnal)
	 * @return a {@link String} containing the response
	 */
	protected String call(String url, JsonNode parameters) {
		return call(url, convert(parameters));
	}

	/**
	 * Convert a {@link JsonNode} to {@link Map}
	 * 
	 * @param value
	 *            the {@link JsonNode}
	 * @return the {@link Map}
	 */
	protected Map<String, Object> convert(JsonNode value) {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.convertValue(value, new TypeReference<Map<String, Object>>() {
		});
	}

	/**
	 * Convert a {@link String} to {@link Map}
	 * 
	 * @param value
	 *            the Json formatted String
	 * @return the {@link Map}
	 * @throws SLVTestsException 
	 */
	protected Map<String, Object> convert(String value) throws SLVTestsException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(value, new TypeReference<Map<String, Object>>() {
			});
		} catch (IOException e) {
			logger.error(MessageHelper.getMessage("core.abstract.test.convert.string.to.jsonnode", value), e);
			throw new SLVTestsException(ExceptionCode.DIFF_METHOD_CALL.toString(), MessageHelper.getMessage("core.abstract.test.diff.error"), e);
		}
	}

	public Map<String, JsonNode> getInputs() {
		return inputs;
	}

	public Map<String, JsonNode> getOutputs() {
		return outputs;
	}

	public RestService getRestService() {
		return restService;
	}

	protected abstract String getInputFile();
	
	protected abstract String getOutputFile();
}
