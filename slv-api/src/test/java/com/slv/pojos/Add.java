package com.slv.pojos;

public class Add extends Operation {
	
	private String value ;
	
	public Add(String op,String path,String value){
		super.Op=op;
		super.Path = path;
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	


	
	
	
	
	
	

}
