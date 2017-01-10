package com.slv.slv_api.exceptions;

/**
 * Caused by a managed exception in one of the tests
 * @author atran
 *
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
	
	public SLVTestsException(final ExceptionCode code, final String message) {
		super(message);
		this.code = code;
	}
	
	public SLVTestsException(final ExceptionCode code, final String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public ExceptionCode getCode() {
		return code;
	}

	public void setCode(ExceptionCode code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
