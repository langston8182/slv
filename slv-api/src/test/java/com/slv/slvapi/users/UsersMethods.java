package com.slv.slvapi.users;

import com.slv.slvapi.providers.ProvidersMethods;

/**
 * Rest api URLs for a users.
 * 
 * @author cmarchive
 */
public enum UsersMethods {

  /**
   * URN for create user call.
   */
  CREATE_USER("api/userprofile/createUser"),

  /**
   * URN for update user call.
   */
  UPDATE_USER("api/userprofile/updateUser"),

  /**
   * URN for update user property call.
   */
  UPDATE_USER_PROPERTIES("api/userprofile/updateUserProperty"),

  /**
   * URN for get current user call.
   */
  GET_CURRENT_USER("api/userprofile/getCurrentUser"),

  /**
   * URN for get all users call.
   */
  GET_ALL_USERS("api/userprofile/getAllUsers"),

  /**
   * URN for get geozone users call.
   */
  GET_USERS_GEOZONE("api/userprofile/getGeoZoneUsers"),

  /**
   * URN for delete user call.
   */
  DELETE_USER("api/userprofile/deleteUser"),

  /**
   * URN for change password call.
   */
  UPDATE_PASSWORD("api/userprofile/changePassword"),

  /**
   * URN for send reset password request by mail call.
   */
  RECOVER_PASSWORD("public/api/publicconfig/sendResetPasswordRequestByMail"),

  /**
   * URN for check password call.
   */
  VERIFY_PASSWORD("api/userprofile/checkPassword");

  /**
   * The url.
   */
  private String url;

  /**
   * Constructor to provide a UsersMethods enumeration.
   * 
   * @param url
   *          The url
   */
  private UsersMethods(final String url) {
    this.url = url;
  }

  /**
   * {@link ProvidersMethods#url}.
   */
  public String getUrl() {
    return this.url;
  }
}