package com.slv.slv_api.common;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Helper class with methods to get messages from resource bundle
 * @author atran
 *
 */
public class MessageHelper {

	private static ResourceBundle messagesBundle;
	
	private static ResourceBundle getInstance() {
		if(messagesBundle == null) {
			messagesBundle = ResourceBundle.getBundle("messages");
		}
		return messagesBundle;
	}
	
	/**
	 * Returns a message from the resource bundle. Message can be parameterized.
	 * @param key the key defined in the resource bundle
	 * @param parameters the parameters (optional)
	 * @return the message value
	 */
	public static String getMessage(final String key, final String... parameters) {
		String message = getInstance().getString(key);
		if(parameters != null) {
			message = MessageFormat.format(message, (Object[])parameters);
		}
		return message;
	}
}
