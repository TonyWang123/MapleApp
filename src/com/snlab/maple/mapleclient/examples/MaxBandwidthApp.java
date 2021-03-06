package com.snlab.maple.mapleclient.examples;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import com.snlab.maple.mapleclient.api.Identifier;
import com.snlab.maple.mapleclient.api.Link;
import com.snlab.maple.mapleclient.api.MapleApp;
import com.snlab.maple.mapleclient.api.Topology;
import com.snlab.maple.mapleclient.core.odl.ODLTopologyIdentifier;
import com.snlab.maple.mapleclinet.core.MapleClient;
import com.snlab.maple.mapleclinet.core.MapleConfig;
import com.snlab.maple.mapleclinet.core.tracetree.Action;
import com.snlab.maple.mapleclinet.core.tracetree.MaplePacket;


public class MaxBandwidthApp extends MapleApp{

	@Override
	public Action onPacket(MaplePacket p) {
		Identifier<Topology> topoRef = new ODLTopologyIdentifier();
		Topology topo = ms.read(topoRef);
		Queue<Link> queue = new PriorityQueue<Link>(10, new Comparator<Link>() {
            @Override
            public int compare(Link x, Link y) {
                int bwx = x.getBandwidth();
                int bwy = y.getBandwidth();
                return (bwx > bwy ? -1 : 1);
            }
        });
		System.out.println("topo links size: " + topo.getLinks().size());
		for(Link link: topo.getLinks())queue.add(link);
		System.out.println("queue size: " + queue.size());
		Action action = new Action();
		List<Link> path = new LinkedList<Link>();
		path.add(queue.poll());
		return action;
	}
	
	public static void main(String[] args) {
		MaxBandwidthApp app = new MaxBandwidthApp();
		MapleClient client = new MapleClient();
		MapleConfig conf = new MapleConfig();
		conf.setControllerAddress("localhost");
		conf.setPort(10000);
		client.setup(conf);
		client.addMapleApp(app);
		client.register();
	}

	@Override
	public void onDataChanged(Object data) {
		// TODO Auto-generated method stub
		
	}
}
