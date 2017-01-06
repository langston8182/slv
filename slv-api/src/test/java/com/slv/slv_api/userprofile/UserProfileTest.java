package com.slv.slv_api.userprofile;

import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.slv.slv_api.core.AbstractTest;

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
	public void getGeoZoneProfils() {
		JsonNode parameters = getInputs().get(UserProfileMethods.GET_GEOZONE_PROFILS.getUrl());
		JsonNode awaitedResponse = getOutputs().get(UserProfileMethods.GET_GEOZONE_PROFILS.getUrl());
		System.out.println(getRestService().get(UserProfileMethods.GET_GEOZONE_PROFILS.getUrl(), convert(parameters)));
		Assert.assertTrue(true);
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

	@Override
	protected String getInputFile() {
		return INPUT_FILE;
	}

	@Override
	protected String getOutputFile() {
		return OUTPUT_FILE;
	}
	
	

}
