package com.slv.slv_api.userprofile;

/**
 * Rest api URLs for a user profile
 * @author atran
 *
 */
public enum UserProfileMethods {

	GET_GEOZONE_PROFILS("userprofile/getGeoZoneProfils");
	
	private String url;
	
	private UserProfileMethods(final String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return this.url;
	}
} 
