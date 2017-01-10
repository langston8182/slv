package com.slv.slv_api.entities;

/**
 * 
 * @author cmarchive
 */
public class Remove  extends Operation{
	
	/**
	 * 
	 */
	String from ;
	
	/**
	 * 
	 * @param from
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
