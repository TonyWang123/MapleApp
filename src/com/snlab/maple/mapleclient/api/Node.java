package com.snlab.maple.mapleclient.api;

import java.util.List;

public interface Node {

	public int getID();
	
	public String getMac();
	
	public List<Port> getPorts();
}
