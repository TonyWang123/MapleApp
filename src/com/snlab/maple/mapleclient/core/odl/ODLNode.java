package com.snlab.maple.mapleclient.core.odl;

import java.util.List;

import com.snlab.maple.mapleclient.api.Node;
import com.snlab.maple.mapleclient.api.Port;

public class ODLNode implements Node{
	
	String mac;
	
	public void setMac(String mac){
		this.mac = mac;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getMac() {
		// TODO Auto-generated method stub
		return mac;
	}

	@Override
	public List<Port> getPorts() {
		// TODO Auto-generated method stub
		return null;
	}

}
