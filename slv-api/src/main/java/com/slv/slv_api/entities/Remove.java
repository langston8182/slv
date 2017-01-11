package com.slv.slv_api.entities;

/**
 * The remove operation.
 * 
 * @author cmarchive
 */
public class Remove  extends Operation{
	
	/**
	 *  The path contain name of the removed
	 */
	String from ;
	
	/**
	 * Provides Remove object.
	 * @param from The from given.
	 */
	public Remove(String from){
		this.from= from;
	}

	/**
	 * @return {@link Remove#from}
	 */
	public String getFrom() {
		return from;
	}
}
