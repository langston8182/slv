package com.slv.slv_api.lamptype;

/*
  * Rest api URLs for a lamp types
 */
public enum LampTypeMethods {

	CREATE_BRAND("assetmanagement/createBrand"),
	UPDATE_BRAND("assetmanagement/updateBrand"),
	DELETE_BRAND("assetmanagement/deleteBrand"),
	GET_ALL_BRANDS("asset/getAllBrands"),
	GET_BRAND_PROPERTY_DESCRIPTORS("assetmanagement/getBrandPropertyDescriptors");
	
	private String url;
	
	private LampTypeMethods(final String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return this.url;
	}
} 