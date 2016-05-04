package com.snlab.maple.mapleclient.core.odl;

import com.snlab.maple.mapleclient.api.Packet;
import com.snlab.maple.mapleclinet.core.MapleCore;

public class ODLPacket implements Packet{
	
	private MapleCore mc;/////////////generate trace tree
	
	public MapleCore getMc() {
		return mc;
	}

	public void setMc(MapleCore mc) {
		this.mc = mc;
	}

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
