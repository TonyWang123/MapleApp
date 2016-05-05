package com.snlab.maple.mapleclinet.core;

import com.snlab.maple.mapleclient.api.Identifier;
import com.snlab.maple.mapleclient.api.MapleSystemAPI;
import com.snlab.maple.mapleclient.core.odl.ODLTopology;
import com.snlab.maple.mapleclient.core.odl.ODLTopologyIdentifier;

public class MapleSystem implements MapleSystemAPI{
	
	MapleCore mc;
	
	public MapleSystem(MapleCore mc){
		this.mc = mc;
	}

	@Override
	public <T> T read(Identifier<T> ref){
		if(ref instanceof ODLTopologyIdentifier){
			return (T) new ODLTopology();
		}
		return null;
	}
	
	@Override
	public <T> void write(Identifier<T> ref, T value){
		
	}
}
