package com.slv.slv_api.exceptions;

/**
 * Exception codes
 * 
 * @author atran
 */
public enum ExceptionCode {
	
	/**
	 * Error during json read.
	 */
	READ_JSON_FILES,
	
	/**
	 * Error during diff method call.
	 */
	DIFF_METHOD_CALL,
	
	/**
	 * Error during diff method execution.
	 */
	DIFF_METHOD_EXECUTION,
	
	/**
	 * Error during the conversion of string to json.
	 */
	CONVERT_STRING_TO_JSON;
}
