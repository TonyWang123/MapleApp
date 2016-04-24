package com.snlab.maple.mapleclient.core.odl;

import com.snlab.maple.mapleclient.api.Packet;

public class ODLPacket implements Packet{
	
	String srcMac;
	
	String dstMac;

	public void setSrcMac(String srcMac) {
		this.srcMac = srcMac;
	}

	public void setDstMac(String dstMac) {
		this.dstMac = dstMac;
	}

	@Override
	public String getSrcMac() {
		// TODO Auto-generated method stub
		return srcMac;
	}

	@Override
	public String getDstMac() {
		// TODO Auto-generated method stub
		return dstMac;
	}

}
