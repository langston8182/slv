package com.slv.slv_api.userprofile;

/**
 * Rest api URLs for a user profile
 * @author atran
 *
 */
public enum UserProfileMethods {

	GET_GEOZONE_PROFILS("api/userprofile/getGeoZoneProfils"),
	CREATE_PROFIL("api/userprofile/createProfil"),
	UPDATE_PROFIL("api/userprofile/updateProfilProperties"),
	DELETE_PROFIL("api/userprofile/deleteProfil"),
	GET_CURRENT_PROFIL("api/userprofile/getCurrentProfil"),
	GET_PROFIL_PROPERTIES("api/asset/getProfilProperties"),
	GET_PROFIL_PROPERTY_DESCRIPTORS("api/userprofile/getProfilPropertyDescriptors");
	
	private String url;
	
	private UserProfileMethods(final String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return this.url;
	}
} 
