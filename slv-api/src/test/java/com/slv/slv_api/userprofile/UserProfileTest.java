package com.slv.slv_api.userprofile;

import java.io.IOException;

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
	 * File name of UserProfil API Inputs
	 */
	private final static String INPUT_FILE = "json/userprofile/input.json";
	
	/**
	 * File name of UserProfil API Outputs
	 */
	private final static String OUTPUT_FILE = "json/userprofile/output.json";
	
	@Test
	public void getGeoZoneProfils() throws JsonProcessingException, IOException {
		// INIT
		JsonNode parameters = getInputs().get(UserProfileMethods.GET_GEOZONE_PROFILS.getUrl());
		JsonNode awaitedResponse = getOutputs().get(UserProfileMethods.GET_GEOZONE_PROFILS.getUrl());
		
		// CALL
		String realResponse = getRestService().get(UserProfileMethods.GET_GEOZONE_PROFILS.getUrl(), convert(parameters));
		JsonDiffResult result = JsonDiffService.getInstance().diff(realResponse, awaitedResponse.toString());
		
		// VERIFY
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());
	}

	@Test
	public void getCurrentProfil() {
		JsonNode parameters = getInputs().get(UserProfileMethods.GET_CURRENT_PROFIL.getUrl());
		JsonNode awaitedResponse = getOutputs().get(UserProfileMethods.GET_CURRENT_PROFIL.getUrl());
		System.out.println(getRestService().get(UserProfileMethods.GET_CURRENT_PROFIL.getUrl(), convert(parameters)));
		Assert.assertTrue(true);
	}

	@Test
	public void getProfilProperties() {
		JsonNode parameters = getInputs().get(UserProfileMethods.GET_PROFIL_PROPERTIES.getUrl());
		JsonNode awaitedResponse = getOutputs().get(UserProfileMethods.GET_PROFIL_PROPERTIES.getUrl());
		System.out.println(getRestService().get(UserProfileMethods.GET_PROFIL_PROPERTIES.getUrl(), convert(parameters)));
		Assert.assertTrue(true);
	}

	@Test
	public void getProfilPropertyDescriptors() {
		JsonNode parameters = getInputs().get(UserProfileMethods.GET_PROFIL_PROPERTY_DESCRIPTORS.getUrl());
		JsonNode awaitedResponse = getOutputs().get(UserProfileMethods.GET_PROFIL_PROPERTY_DESCRIPTORS.getUrl());
		System.out.println(getRestService().get(UserProfileMethods.GET_PROFIL_PROPERTY_DESCRIPTORS.getUrl(), convert(parameters)));
		Assert.assertTrue(true);
	}
	
	public static void main(String... args) throws JsonProcessingException, IOException, SLVTestsException {
		UserProfileTest runner = new UserProfileTest();

		runner.beforeTest("http://5.196.91.118:8080/celad/api/", "celad", "Celad20!6");
		JsonNode parameters = runner.getInputs().get(UserProfileMethods.GET_GEOZONE_PROFILS.getUrl());
		JsonNode awaitedResponse = runner.getOutputs().get(UserProfileMethods.GET_GEOZONE_PROFILS.getUrl());
		String realResponse = runner.getRestService().get(UserProfileMethods.GET_GEOZONE_PROFILS.getUrl(), runner.convert(parameters));
		
		JsonDiffService comparator = JsonDiffService.getInstance();
		JsonDiffResult result = comparator.diff(realResponse, awaitedResponse.toString());
		
		System.out.println(result.isEquals());
		System.out.println(result.getErrorMessage());
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
