package com.snlab.maple.mapleclient.api;

import com.snlab.maple.mapleclinet.core.Action;
import com.snlab.maple.mapleclinet.core.MapleCore;
import com.snlab.maple.mapleclinet.core.MapleSystem;

public abstract class MapleApp {
	
	private MapleCore mc;

	public MapleSystem ms;
	
	abstract public Action onPacket(Packet p);
	
}
