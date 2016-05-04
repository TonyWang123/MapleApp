package com.snlab.maple.mapleclinet.core;

import com.snlab.maple.mapleclient.api.Identifier;
import com.snlab.maple.mapleclient.core.odl.ODLTopology;
import com.snlab.maple.mapleclient.core.odl.ODLTopologyIdentifier;

public class MapleSystem{
	
	public MapleSystem(MapleCore mc){
		
	}

	public <T> T read(Identifier<T> ref){
		if(ref instanceof ODLTopologyIdentifier){
			return (T) new ODLTopology();
		}
		return null;
	}
	
	public <T> void write(Identifier<T> ref, T value){
		
	}
}
