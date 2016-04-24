package com.snlab.maple.mapleclient.examples;

import java.util.List;

import com.snlab.maple.mapleclient.api.Identifier;
import com.snlab.maple.mapleclient.api.Link;
import com.snlab.maple.mapleclient.api.MapleApp;
import com.snlab.maple.mapleclient.api.Packet;
import com.snlab.maple.mapleclient.api.Topology;
import com.snlab.maple.mapleclient.core.odl.ODLTopologyIdentifier;
import com.snlab.maple.mapleclinet.core.Action;
import com.snlab.maple.mapleclinet.core.MapleClient;
import com.snlab.maple.mapleclinet.core.MapleConfig;

public class SimpleApp implements MapleApp{

	@Override
	public Action onPacket(Packet p) {
		System.out.println("start onPacket");
		String srcMac = p.getSrcMac();
		String dstMac = p.getDstMac();
		if(srcMac.equals(dstMac))return null;
		System.out.println("sm != dm");
		Identifier<Topology> topoRef = new ODLTopologyIdentifier();
		Topology topo = MapleApp.ms.read(topoRef);
		System.out.println("get topology");
		List<Link> links = topo.getLinks();
		Link l1 = links.get(0);
		Action action = new Action();
		action.setPort(l1.getID());
		return action;
	}

	public static void main(String[] args){
		SimpleApp app = new SimpleApp();
		MapleClient client = new MapleClient();
		MapleConfig conf = new MapleConfig();
		conf.setControllerAddress("localhost");
		client.setup(conf);
		client.addMapleApp(app);
		client.register();
	}
}
