package com.snlab.mapleserver.message;

public class Action{

	private int type;//drop, forward
	
	private String value;//tpid|tpid

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
