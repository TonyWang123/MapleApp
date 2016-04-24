package com.snlab.maple.mapleclient.core.odl;

import com.snlab.maple.mapleclient.api.Node;

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

}
