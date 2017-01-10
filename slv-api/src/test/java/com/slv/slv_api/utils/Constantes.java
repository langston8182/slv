package com.slv.slv_api.utils;


/**
 * Constants class
 * 
 * @author yromdhane
 *
 */
public final class Constantes {

	/**
	 * Provider id output key for create provider operation
	 */
	public static final String CREATE_PROVIDER_OUTPUT_ID_KEY = "id";

	/**
	 * Provider name input key for create provider operation
	 */
	public static final String CREATE_PROVIDER_INPUT_NAME_KEY = "name";

	/**
	 * Pollution rate input key for create provider operation
	 */
	public static final String CREATE_PROVIDER_INPUT_POLLUTIONRATE_KEY = "pollutionRate";

	/**
	 * Time input key for create provider operation
	 */
	public static final String CREATE_PROVIDER_INPUT_TIME_KEY = "time";

	/**
	 * Provider id input key for update provider operation
	 */
	public static final String UPDATE_PROVIDER_INPUT_ID_KEY = "providerId";
	
	/**
	 * Provider newName input key for update provider operation
	 */
	public static final String UPDATE_PROVIDER_INPUT_NEW_NAME_KEY = "newName";


	/**
	 * Provider id input key for delete provider operation
	 */
	public static final String DELETE_PROVIDER_INPUT_ID_KEY = "id";

	/**
	 * Error response code key
	 */
	public static final String RESPONSE_ERROR_CODE_KEY = "errorCode";

	/**
	 * Error response message key
	 */
	public static final String RESPONSE_MESSAGE_KEY = "message";

	/**
	 * Server internal error code
	 */
	public static final String INTERNAL_ERROR_CODE = "500";

	/**
	 * Item not found error code
	 */
	public static final String ITEM_NOT_FOUND_ERROR_CODE = "613";

	/**
	 * Private constructor to prevent instantiation of this class
	 */
	private Constantes() {}

}