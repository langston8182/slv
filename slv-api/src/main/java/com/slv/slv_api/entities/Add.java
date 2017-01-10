package com.slv.slv_api.entities;


public class Add extends Operation {
	String path;

	public Add(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
}
