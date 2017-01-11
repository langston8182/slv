package com.slv.slvapi.providers;

/**
 * Rest api URLs for the Providers
 * 
 * @author yromdhane
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
	 * Constructor to provide a ProvidersMethods enumeration.
	 * 
	 * @param url The url
	 */
	private ProvidersMethods(final String url) {
		this.url = url;
	}

	/**
	 * @return {@link ProvidersMethods#url}
	 */
	public String getUrl() {
		return this.url;
	}
} 
