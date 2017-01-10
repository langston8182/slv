package com.slv.slv_api.userprofile;

/**
 * Enum of Json attributes used in the expected input and ouput of every api service for a UserProfile
 * @author atran
 *
 */
public enum JsonAttributes {
	INPUT_CREATE_PROFIL_NAME("profilName"),
	OUTPUT_CREATE_PROFIL_NAME("name"),
	INPUT_UPDATE_PROFIL_NAME("profilName"),
	OUTPUT_UPDATE_PROFIL_NAME("name"),
	OUTPUT_GEOZONES_PROFIL_NAME("name"),
	OUTPUT_GEOZONES_PROFIL_PROPERTIES("properties"),
	OUTPUT_DELETE_ERROR_CODE("errorCode"),
	OUTPUT_DELETE_STATUS("status"),
	OUTPUT_DELETE_STATUS_ERROR("statusError"),
	OUTPUT_DELETE_STATUS_OK("statusOk"),
	PROPERTY_OBJECT_KEY("key"),
	PROPERTY_OBJECT_VALUE("value"),
	PROPERTY_LOCALE("property.locale"),
	PROPERTY_SKIN("property.skin"),
	PROPERTY_BLOCKED_ACTIONS("property.blockedActions");
	
	/**
	 * The name of the Json attribute
	 */
	private String name;
	
	/**
	 * Default constructor to provide a {@link JsonAttributes} enumeration.
	 * 
	 * @param name The attribute's name
	 */
	private JsonAttributes(final String name) {
		this.name = name;
	}

	/**
	 * @return {@link JsonAttributes#name}
	 */
	public String getName() {
		return this.name;
	}
}
