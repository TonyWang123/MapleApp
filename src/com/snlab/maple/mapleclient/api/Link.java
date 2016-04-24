package com.snlab.maple.mapleclient.api;

public interface Link {

	public int getID();
	
	public Node getSrc();
	
	public Node getDst();
	
	public int getBandwidth();
}
