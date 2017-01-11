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
	 * getAllProviders test method
	 */
	GET_ALL_PROVIDERS("getAllProviders"),
	
	/**
	 * createProvider test method
	 */
	CREATE_PROVIDER("createProvider"),
	
	/**
	 * updateProvider test method
	 */
	UPDATE_PROVIDER("updateProvider"),
	
	/**
	 * deleteProvider test method
	 */
	DELETE_PROVIDER("deleteProvider"),
	
	/**
	 * createProviderKoMissedName test method
	 */
	CREATE_PROVIDER_KO_MISSED_NAME("createProviderKoMissedName"),
	
	/**
	 * createProviderMissedPollutionRate test method
	 */
	CREATE_PROVIDER_MISSED_POLLUTION_RATE("createProviderMissedPollutionRate"),
	
	/**
	 * createProviderMissedTime test method
	 */
	CREATE_PROVIDER_MISSED_TIME("createProviderMissedTime"),
	
	/**
	 * initProviderOfUpdateTests test method
	 */
	INIT_PROVIDER_OF_UPDATE_TESTS("initProviderOfUpdateTests"),
	
	/**
	 * initProviderOfUpdateTests test method
	 */
	UPDATE_PROVIDER_MISSED_NEW_NAME("initProviderOfUpdateTests"),
	
	/**
	 * initProviderOfUpdateTests test method
	 */
	UPDATE_PROVIDER_MISSED_PROVIDER_ID("initProviderOfUpdateTests"),
	
	/**
	 * initProviderOfUpdateTests test method
	 */
	CLEAR_PROVIDER_OF_UPDATE_TESTS("initProviderOfUpdateTests")
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
