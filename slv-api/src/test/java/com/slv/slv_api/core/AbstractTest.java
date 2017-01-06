package com.slv.slv_api.core;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.slv.slv_api.exceptions.ExceptionCode;
import com.slv.slv_api.exceptions.SLVTestsException;
import com.slv.slv_api.services.RestService;

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
			return mapper.readValue(new File(this.getClass().getClassLoader().getResource(inputFile).getPath()),
					new TypeReference<Map<String, JsonNode>>() {
					});
		} catch (IOException e) {
			// TODO - messages in properties file
			throw new SLVTestsException(ExceptionCode.USER_PROFILE.toString(), "Cannot init input and ouput json files",
					e);
		}
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
