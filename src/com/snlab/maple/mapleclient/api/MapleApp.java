package com.snlab.maple.mapleclient.api;


import com.snlab.maple.mapleclinet.core.MapleCore;
import com.snlab.maple.mapleclinet.core.MapleSystem;
import com.snlab.maple.mapleclinet.core.tracetree.Action;
import com.snlab.maple.mapleclinet.core.tracetree.MaplePacket;

public abstract class MapleApp {

	public MapleSystemAPI ms;
	
	public abstract void onDataChanged(Object data);

    public abstract Action onPacket(MaplePacket pkt);
	
}
