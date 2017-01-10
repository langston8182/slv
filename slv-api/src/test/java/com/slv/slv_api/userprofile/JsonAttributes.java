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
	 * The key of the Json attribute
	 */
	private String key;
	
	private JsonAttributes(final String key) {
		this.key = key;
	}
	
	public String getKey() {
		return this.key;
	}
}
