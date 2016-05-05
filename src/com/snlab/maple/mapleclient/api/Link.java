package com.snlab.maple.mapleclient.api;

public interface Link {

	public int getID();
	
	public Node getSrcNode();
	
	public Node getDstNode();
	
	public Port getSrcPort();
	
	public Port getDstPort();
	
	public int getBandwidth();
}
