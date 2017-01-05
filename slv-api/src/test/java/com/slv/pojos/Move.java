package com.slv.pojos;

public class Move extends Operation{
	private String from ;
	
	
	public Move(String op,String path,String from){
		super.Op=op;
		super.Path = path;
		this.from = from;
	}
	

	
	
	

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}
	
	

}
