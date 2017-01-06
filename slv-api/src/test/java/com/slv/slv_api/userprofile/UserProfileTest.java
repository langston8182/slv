package com.slv.slv_api.userprofile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.slv.slv_api.Methods;
import com.slv.slv_api.exceptions.ExceptionCode;
import com.slv.slv_api.exceptions.SLVTestsException;
import com.slv.slv_api.services.RestService;

@RunWith(BlockJUnit4ClassRunner.class)
public class UserProfileTest {

	/**
	 * File name of UserProfil API Inputs
	 */
	private final static String INPUT_FILE = "json/userprofile/input.json";
	
	/**
	 * File name of UserProfil API Outputs
	 */
	private final static String OUTPUT_FILE = "json/userprofile/output.json";
	
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

	@BeforeTest(groups = { "userProfile" })
	@Parameters({ "url", "login", "password" })
	public void beforeTest(String url, String login, String password) throws SLVTestsException {
		restService = RestService.getInstance(url, login, password);
		inputs = extractJsonStreams(INPUT_FILE);
		outputs = extractJsonStreams(OUTPUT_FILE);
	}
	
	@Test(groups= {"userProfile"})
	public void getGeoZoneProfils() {
		JsonNode parameters = inputs.get(UserProfileMethods.GET_GEOZONE_PROFILS.getUrl());
		JsonNode awaitedResponse = outputs.get(UserProfileMethods.GET_GEOZONE_PROFILS.getUrl());
		System.out.println(restService.get(UserProfileMethods.GET_GEOZONE_PROFILS.getUrl(), convert(parameters)));
	}
	
	/**
	 * <p>Extract Json Object from a file. This file must be formatted like the example below : <br/>
	 * {<br>
	 * 	"key" : {JsonObject1},<br>
	 *  "key2" : {JsonObject2}<br>
	 * }<br>
	 * </p>
	 * @param inputFile the file to 
	 * @return
	 * @throws SLVTestsException 
	 */
	private Map<String, JsonNode> extractJsonStreams(String inputFile) throws SLVTestsException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(new File(this.getClass().getClassLoader().getResource(inputFile).getPath()),
					new TypeReference<Map<String, JsonNode>>() {
					});
		} catch (IOException e) {
			// TODO - messages in properties file
			throw new SLVTestsException(ExceptionCode.USER_PROFILE.toString(),
					"Cannot init input and ouput json files", e);
		}
	}
	
	/**
	 * Convert a {@link JsonNode} to {@link Map}
	 * @param value the {@link JsonNode}
	 * @return the {@link Map}
	 */
	private Map<String, Object> convert(JsonNode value) {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.convertValue(value,
				new TypeReference<Map<String, Object>>() {
				});
	}

}
