package com.slv.slvapi.userprofile;

/**
 * Rest api URLs for a user profile.
 * 
 * @author atran
 */
public enum UserProfileMethods {

  /**
   * URN to get all user profiles.
   */
  GET_GEOZONE_PROFILS("api/userprofile/getGeoZoneProfils"),

  /**
   * URN to create a user profile.
   */
  CREATE_PROFIL("api/userprofile/createProfil"),

  /**
   * URN to update a user profile.
   */
  UPDATE_PROFIL("api/userprofile/updateProfilProperties"),

  /**
   * URN to delete a user profile.
   */
  DELETE_PROFIL("api/userprofile/deleteProfil"),

  /**
   * URN to get the current logged in user profile.
   */
  GET_CURRENT_PROFIL("api/userprofile/getCurrentProfil"),

  /**
   * URN to get a profile property list.
   */
  GET_PROFIL_PROPERTIES("api/asset/getProfilProperties"),

  /**
   * URN to get the descriptions of the properties for a user profile.
   */
  GET_PROFIL_PROPERTY_DESCRIPTORS("api/userprofile/getProfilPropertyDescriptors");

  /**
   * The url.
   */
  private String url;

  /**
   * Default constructor to provide a {@link UserProfileMethods} enumeration.
   * 
   * @param url The url.
   */
  private UserProfileMethods(final String url) {
    this.url = url;
  }

  /**
   * @return {@link UserProfileMethods#url}.
   */
  public String getUrl() {
    return this.url;
  }
}
