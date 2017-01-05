package com.slv.slv_api.services;

/**
 * Factory object for vending comparison service
 * 
 * @author cmarchive
 */
public class DiffServiceFactory {

	/**
	 * Create a DiffService with the specified type
	 * 
	 * @param type Type of data format to use
	 * 
	 * @return the DiffService object
	 */
	public static DiffService getDiffService(DiffType type) {
		DiffService instance = null;
		switch (type) {
		case JSON_TYPE:
			instance = new JsonDiffService();

		case XML_TYPE:
			instance = new XmlDiffService();

		default:
			break;
		}
		
		return instance;
	}
}
