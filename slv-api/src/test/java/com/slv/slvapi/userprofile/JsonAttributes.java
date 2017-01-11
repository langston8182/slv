package com.slv.slvapi.userprofile;

/**
 * Enum of Json attributes used in the expected input and ouput of every api service for a UserProfile
 * @author atran
 *
 */
public enum JsonAttributes {
	/**
	 * Input attribute create profil name.
	 */
	INPUT_CREATE_PROFIL_NAME("profilName"),
	
	/**
	 * Output attribute create profil name.
	 */
	OUTPUT_CREATE_PROFIL_NAME("name"),
	
	/**
	 * Inpout attribute update profil name
	 */
	INPUT_UPDATE_PROFIL_NAME("profilName"),
	
	/**
	 * Output attribute update profil name.
	 */
	OUTPUT_UPDATE_PROFIL_NAME("name"),
	
	/**
	 * Ouput attribute geozones profil name.
	 */
	OUTPUT_GEOZONES_PROFIL_NAME("name"),
	
	/**
	 * Output attribute geozones profil properties.
	 */
	OUTPUT_GEOZONES_PROFIL_PROPERTIES("properties"),
	
	/**
	 * Output attribute delete error code.
	 */
	OUTPUT_DELETE_ERROR_CODE("errorCode"),
	
	/**
	 * Output attribute delete status.
	 */
	OUTPUT_DELETE_STATUS("status"),
	
	/**
	 * Output attribute delete status error.
	 */
	OUTPUT_DELETE_STATUS_ERROR("statusError"),
	
	/**
	 * Output attribute delete status ok.
	 */
	OUTPUT_DELETE_STATUS_OK("statusOk"),
	
	/**
	 * Porperty object key.
	 */
	PROPERTY_OBJECT_KEY("key"),
	
	/**
	 * Property object value.
	 */
	PROPERTY_OBJECT_VALUE("value"),
	
	/**
	 * Property locale.
	 */
	PROPERTY_LOCALE("property.locale"),
	
	/**
	 * Property skin.
	 */
	PROPERTY_SKIN("property.skin"),
	
	/**
	 * Property blocked actions.
	 */
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
