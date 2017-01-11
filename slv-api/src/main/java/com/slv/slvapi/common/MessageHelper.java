package com.slv.slvapi.common;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Helper class with methods to get messages from resource bundle.
 * 
 * @author atran
 */
public class MessageHelper {

  /**
   * Unique instance of ResourceBundle.
   */
  private static ResourceBundle INSTANCE;

  /**
   * Get the unique instance of ResourceBundle.
   * 
   * @return The unique instance of ResourceBundle.
   */
  private static ResourceBundle getInstance() {
    if (INSTANCE == null) {
      INSTANCE = ResourceBundle.getBundle("messages");
    }
    return INSTANCE;
  }

  /**
   * Returns a message from the resource bundle. Message can be parameterized.
   * 
   * @param key
   *          the key defined in the resource bundle
   * @param parameters
   *          the parameters (optional)
   * @return the message value
   */
  public static String getMessage(final String key, final String... parameters) {
    String message = getInstance().getString(key);
    if (parameters != null) {
      message = MessageFormat.format(message, (Object[]) parameters);
    }
    return message;
  }
}
