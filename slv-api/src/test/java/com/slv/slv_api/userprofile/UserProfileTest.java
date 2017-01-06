package com.slv.slv_api.userprofile;

import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.slv.slv_api.core.AbstractTest;

@RunWith(BlockJUnit4ClassRunner.class)
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
