package com.slv.slv_api.userprofile;

/**
 * Rest api URLs for a user profile
 * @author atran
 *
 */
public enum UserProfileMethods {

	GET_GEOZONE_PROFILS("userprofile/getGeoZoneProfils"),
	CREATE_PROFIL("userprofile/createProfil"),
	UPDATE_PROFIL("userprofile/updateProfil"),
	DELETE_PROFIL("userprofile/deleteProfil"),
	GET_CURRENT_PROFIL("userprofile/getCurrentProfil"),
	GET_PROFIL_PROPERTIES("asset/getProfilProperties"),
	GET_PROFIL_PROPERTY_DESCRIPTORS("userprofile/getProfilPropertyDescriptors");
	
	private String url;
	
	private UserProfileMethods(final String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return this.url;
	}
} 
