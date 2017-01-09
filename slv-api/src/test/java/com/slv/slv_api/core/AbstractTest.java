package com.slv.slv_api.core;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.slv.slv_api.common.MessageHelper;
import com.slv.slv_api.exceptions.ExceptionCode;
import com.slv.slv_api.exceptions.SLVTestsException;
import com.slv.slv_api.services.JsonDiffResult;
import com.slv.slv_api.services.JsonDiffService;
import com.slv.slv_api.services.RestService;
import com.slv.slv_api.users.UsersMethods;

public abstract class AbstractTest {

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
				throw new SLVTestsException(ExceptionCode.USER_PROFILE.toString(), MessageHelper.getMessage("core.abstract.test.json.files.load.error"));
			}
			return mapper.readValue(new File(fileURL.getPath()),
					new TypeReference<Map<String, JsonNode>>() {
					});
		} catch (IOException e) {
			// TODO - messages in properties file
			throw new SLVTestsException(ExceptionCode.USER_PROFILE.toString(), MessageHelper.getMessage("core.abstract.test.json.files.load.error"),
					e);
		}
	}
	
	/**
	 * get result of the test
	 * 
	 * @param method
	 *            The method to apply
	 */
	protected JsonDiffResult retrieveResult(String url) throws JsonProcessingException, IOException {
		// INIT
		JsonNode parameters = getInputs().get(url);
		JsonNode awaitedResponse = getOutputs().get(url);

		// CALL
		String realResponse = getRestService().get(url, convert(parameters));
		JsonDiffResult result = JsonDiffService.getInstance().diff(realResponse, awaitedResponse.toString());
		
		return result;
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
