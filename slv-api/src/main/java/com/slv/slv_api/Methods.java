package com.slv.slv_api;

public enum Methods {

	RETRIEVE_CURRENT_PROFILE("userprofile/getCurrentProfil"),
	RETRIEVE_CURRENT_USER("userprofile/getCurrentUser");
	
	private String name = "";
	
	Methods(String name) {
		this.name = name;
	}
	
	public String toString() {
		return this.name;
	}
	
}
