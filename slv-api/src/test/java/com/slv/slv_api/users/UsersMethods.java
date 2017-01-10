package com.slv.slv_api.users;

/**
 * Rest api URLs for a users
 * 
 * @author cmarchive
 */
public enum UsersMethods {
	CREATE_USER("api/userprofile/createUser"),
	UPDATE_USER("api/userprofile/updateUser"),
	UPDATE_USER_PROPERTIES("api/userprofile/updateUserProperty"),
	GET_CURRENT_USER("api/userprofile/getCurrentUser"),
	GET_ALL_USERS("api/userprofile/getAllUsers"),
	GET_USERS_GEOZONE("api/userprofile/getGeoZoneUsers"),
	DELETE_USER("api/userprofile/deleteUser"),
	UPDATE_PASSWORD("api/userprofile/changePassword"),
	RECOVER_PASSWORD("public/api/publicconfig/sendResetPasswordRequestByMail"),
	VERIFY_PASSWORD("api/userprofile/checkPassword");
	
	private String url;
	
	private UsersMethods(final String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return this.url;
	}
}
