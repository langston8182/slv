package com.slv.slv_api.exceptions;

/**
 * Caused by a managed exception in one of the tests
 * 
 * @author atran
 */
public class SLVTestsException extends Exception {
	
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -8055101082457791536L;

	/**
	 * Exception code
	 */
	private ExceptionCode code;
	
	/**
	 * Exception message
	 */
	private String message;
	
	/**
	 * Provides a SLVTestsException object.
	 * 
	 * @param code The error code.
	 * @param message The detail message.
	 */
	public SLVTestsException(final ExceptionCode code, final String message) {
		super(message);
		this.code = code;
	}
	
	/**
	 * Provides a SLVTestsException object.
	 * 
	 * @param code The error code.
	 * @param message The detail message.
	 * @param cause The throwable cause.
	 */
	public SLVTestsException(final ExceptionCode code, final String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	/**
	 * @return {@link SLVTestsException#code}
	 */
	public ExceptionCode getCode() {
		return code;
	}

	/**
	 * Set {@link SLVTestsException#code}}
	 * 
	 * @param code code to set.
	 */
	public void setCode(ExceptionCode code) {
		this.code = code;
	}

	/**
	 * @return {@link SLVTestsException#message}
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Set {@link SLVTestsException#message}
	 * 
	 * @param message message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
