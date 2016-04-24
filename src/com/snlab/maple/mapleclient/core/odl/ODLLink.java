package com.snlab.maple.mapleclient.core.odl;

import com.snlab.maple.mapleclient.api.Link;
import com.snlab.maple.mapleclient.api.Node;

public class ODLLink implements Link{
	
	Node src;
	
	Node dst;
	
	int id;
	
	int bandwidth;

	@Override
	public int getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(int bandwidth) {
		this.bandwidth = bandwidth;
	}

	public void setSrc(Node src) {
		this.src = src;
	}

	public void setDst(Node dst) {
		this.dst = dst;
	}
	
	public void setID(int id){
		this.id = id;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public Node getSrc() {
		// TODO Auto-generated method stub
		return src;
	}

	@Override
	public Node getDst() {
		// TODO Auto-generated method stub
		return dst;
	}

}
