package com.slv.slv_api.entities;

/**
 * The Add operation.
 * 
 * @author cmarchive
 */
public class Add extends Operation {
	
	/**
	 * The path
	 */
	String path;

	/**
	 * Provides Add object
	 * 
	 * @param path The path given
	 */
	public Add(String path) {
		this.path = path;
	}

	/**
	 * @return {@link Add#path}
	 */
	public String getPath() {
		return path;
	}
}
