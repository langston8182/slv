package com.slv.slv_api.users;

/**
 * Rest api URLs for a users
 * @author cmarchive
 *
 */
public enum UsersMethods {
	CREATE_USER("userprofile/createUser"),
	UPDATE_USER("userprofile/updateUser"),
	UPDATE_USER_PROPERTIES("userprofile/updateUserProperty"),
	GET_CURRENT_USER("userprofile/getCurrentUser"),
	GET_ALL_USERS("userprofile/getAllUsers"),
	GET_USERS_GEOZONE("userprofile/getGeoZoneUsers"),
	DELETE_USER("userprofile/deleteUser"),
	UPDATE_PASSWORD("userprofile/changePassword"),
	VERIFY_PASSWORD("userprofile/checkPassword");
	
	private String url;
	
	private UsersMethods(final String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return this.url;
	}
}
