package com.snlab.maple.mapleclient.examples;

import com.snlab.maple.mapleclient.api.Identifier;
import com.snlab.maple.mapleclient.api.MapleApp;
import com.snlab.maple.mapleclient.api.Topology;
import com.snlab.maple.mapleclient.core.odl.ODLTopologyIdentifier;
import com.snlab.maple.mapleclinet.core.MapleClient;
import com.snlab.maple.mapleclinet.core.MapleConfig;
import com.snlab.maple.mapleclinet.core.tracetree.Action;
import com.snlab.maple.mapleclinet.core.tracetree.MaplePacket;

public class TestInvalidationApp extends MapleApp{

	@Override
	public void onDataChanged(Object data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Action onPacket(MaplePacket pkt) {
		// TODO Auto-generated method stub
		long src = pkt.ethSrc();
		Identifier<Topology> topoRef = new ODLTopologyIdentifier();
		Topology topo = ms.read(topoRef);
		
		return Action.Pass();
	}
	
	public static void main(String[] args){
		TestInvalidationApp app = new TestInvalidationApp();
		MapleClient client = new MapleClient();
		MapleConfig conf = new MapleConfig();
		conf.setControllerAddress("localhost");
		client.setup(conf);
		client.addMapleApp(app);
		client.test();
	}

}
