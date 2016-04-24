package com.snlab.maple.mapleclinet.core;

import java.util.List;

import com.snlab.maple.mapleclient.api.Link;

public class Action {

	int port;
	
	List<Link> path;
	
	public void setPort(int p){
		port = p;
	}
	
	public void setPath(List<Link> path){
		this.path = path;
	}
}
