package com.snlab.maple.mapleclient.api;

import com.snlab.maple.mapleclinet.core.Action;
import com.snlab.maple.mapleclinet.core.MapleSystem;

public interface MapleApp {
	
	public static MapleSystem ms = new MapleSystem();

	public Action onPacket(Packet p);
}
