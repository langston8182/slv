package com.slv.slv_api.providers;

/**
 * Rest api URLs for a user profile
 * @author atran
 *
 */
public enum ProvidersMethods {
	
	GET_ALL_PROVIDERS("asset/getAllProviders"),
	CREATE_PROVIDER("assetmanagement/createProvider"),
	UPDATE_PROVIDER("assetmanagement/updateProvider"),
	DELETE_PROVIDER("assetmanagement/deleteProvider");
	
	private String url;
	
	private ProvidersMethods(final String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return this.url;
	}
} 
