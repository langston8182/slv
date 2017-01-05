package com.slv.slv_api.services;

/**
 * Used to compare two JSON {@link String}.
 * Interface to make a comparison of two items according to their data format.
 * 
 * @author cmarchive
 * 
 * @return 
 */
public class JsonDiffService  {

	/**
	 * Instance of {@link JsonDiffService}
	 */
	private JsonDiffService service = null;
	
	/**
	 * @return instance of {@link JsonDiffService} as a Singleton
	 */
	public JsonDiffService getInstance() {
		if(service == null) {
			service = new JsonDiffService();
		}
		return service;
	}
	
	/**
	 * Method that verifies if two {@link String} representing two Json streams are equals in terms of attributes
	 * @return {@link JsonDiffResult} an object that indicates the result of the comparison and an error message when needed
	 */
	public JsonDiffResult diff(String toVerify, String target) {
		//TODO - Implement this method
		return null;
	}

}
