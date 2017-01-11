package com.slv.slvapi.lamptype;

/**
 * Enumerates Rest API lamp type methods.
 * 
 * @author cmarchive
 */
public enum LampTypeMethods {

  /**
   * URN for creat brand call.
   */
  CREATE_BRAND("assetmanagement/createBrand"),
  
  /**
   * URN for update brand call.
   */
  UPDATE_BRAND("assetmanagement/updateBrand"),
  
  /**
   * URN for delete brand call.
   */
  DELETE_BRAND("assetmanagement/deleteBrand"),
  
  /**
   * URN for get all brands call.
   */
  GET_ALL_BRANDS("asset/getAllBrands"),
  
  /**
   * URN for get brand property description call.
   */
  GET_BRAND_PROPERTY_DESCRIPTORS("assetmanagement/getBrandPropertyDescriptors");

  /**
   * The url.
   */
  private String url;

  /**
   * Constructor to provide a LampTypeMethods enumeration.
   * 
   * @param url
   *          The url
   */
  private LampTypeMethods(final String url) {
    this.url = url;
  }

  /**
   * @return {@link LampTypeMethods#url}.
   */
  public String getUrl() {
    return this.url;
  }
}