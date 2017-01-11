package com.slv.slvapi.exceptions;

/**
 * Thrown to indicate that the requested operation is not supported.
 * 
 * @author cmarchive
 */
public class UnsupportedOperationException extends SlvTestsException {

  /**
   * serial version UID.
   */
  private static final long serialVersionUID = 4792606186838808168L;

  /**
   * Constructs a new exception with the specified detail message and the error
   * code.
   * 
   * @param code
   *          The error code
   * @param message
   *          The detail message.
   */
  public UnsupportedOperationException(ExceptionCode code, String message) {
    super(code, message);
  }
}
