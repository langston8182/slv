package com.slv.slvapi.exceptions;

/**
 * Caused by a managed exception in one of the tests.
 * 
 * @author atran
 */
public class SlvTestsException extends Exception {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = -8055101082457791536L;

  /**
   * Exception code.
   */
  private ExceptionCode code;

  /**
   * Exception message.
   */
  private String message;

  /**
   * Provides a SLVTestsException object.
   * 
   * @param code
   *          The error code.
   * @param message
   *          The detail message.
   */
  public SlvTestsException(final ExceptionCode code, final String message) {
    super(message);
    this.code = code;
  }

  /**
   * Provides a SLVTestsException object.
   * 
   * @param code
   *          The error code.
   * @param message
   *          The detail message.
   * @param cause
   *          The throwable cause.
   */
  public SlvTestsException(final ExceptionCode code, final String message, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  /**
   * @return {@link SlvTestsException#code}.
   */
  public ExceptionCode getCode() {
    return code;
  }

  /**
   * Set {@link SlvTestsException#code}}
   * 
   * @param code
   *          code to set.
   */
  public void setCode(ExceptionCode code) {
    this.code = code;
  }

  /**
   * @return {@link SlvTestsException#message}.
   */
  public String getMessage() {
    return message;
  }

  /**
   * Set {@link SlvTestsException#message}.
   * 
   * @param message
   *          message to set.
   */
  public void setMessage(String message) {
    this.message = message;
  }
}
