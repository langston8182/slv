package com.slv.slv_api.providers;

/**
 * Rest api URLs for a user profile
 * 
 * @author atran
 */
public enum ProvidersMethods {

	/**
	 * URN for get all providers call.
	 */
	GET_ALL_PROVIDERS("api/asset/getAllProviders"),

	/**
	 * URN for create provider call.
	 */
	CREATE_PROVIDER("api/assetmanagement/createProvider"),

	/**
	 * URN for update provider call.
	 */
	UPDATE_PROVIDER("api/assetmanagement/updateProvider"),

	/**
	 * URN for delete provider call.
	 */
	DELETE_PROVIDER("api/assetmanagement/deleteProvider");

	/**
	 * The url
	 */
	private String url;

	/**
	 * Default constructor to provide a ProvidersMethods enumeration.
	 * 
	 * @param url The url
	 */
	private ProvidersMethods(final String url) {
		this.url = url;
	}

	/**
	 * {@link ProvidersMethods#url}
	 */
	public String getUrl() {
		return this.url;
	}
} 
