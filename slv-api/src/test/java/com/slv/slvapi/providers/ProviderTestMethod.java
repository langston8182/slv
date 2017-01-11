package com.slv.slvapi.providers;

import com.slv.slvapi.userprofile.JsonAttributes;

/**
 * Tests methods names of provider module to hold created providers 
 * identifiers in a map in order to delete them.
 * 
 * @author yromdhane
 *
 */
public enum ProviderTestMethod {
	
  /**
   * 
   */
	GET_ALL_PROVIDERS("getAllProviders"),
	
	
	createProvider("createProvider"),
	updateProvider("updateProvider"),
	deleteProvider("deleteProvider"),
	createProviderKoMissedName("createProviderKoMissedName"),
	createProviderMissedPollutionRate("createProviderMissedPollutionRate"),
	createProviderMissedTime("createProviderMissedTime"),
	initProviderOfUpdateTests("initProviderOfUpdateTests"),
	updateProviderMissedNewName("initProviderOfUpdateTests"),
	updateProviderMissedProviderId("initProviderOfUpdateTests"),
	clearProviderOfUpdateTests("initProviderOfUpdateTests")
	;
	
	
	/**
	 * The name of the test method
	 */
	private String name;
	
	/**
	 * Constructor to provide a {@link ProviderTestMethod} enumeration.
	 * 
	 * @param name The method's name
	 */
	private ProviderTestMethod(final String name) {
		this.name = name;
	}

	/**
	 * @return {@link JsonAttributes#name}
	 */
	public String getName() {
		return this.name;
	}
}
