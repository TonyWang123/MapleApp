package com.snlab.maple.mapleclient.core.odl;

import java.util.LinkedList;
import java.util.List;

import com.snlab.maple.mapleclient.api.Link;
import com.snlab.maple.mapleclient.api.Node;
import com.snlab.maple.mapleclient.api.Port;
import com.snlab.maple.mapleclient.api.Topology;

import edu.columbia.cs.psl.phosphor.runtime.MultiTainter;
import edu.columbia.cs.psl.phosphor.runtime.Taint;
import edu.columbia.cs.psl.phosphor.runtime.Tainter;

public class ODLTopology implements Topology{
	
	List<Link> links;
	
	List<Node> nodes;
	
	List<Port> ports;
	
	

	@Override
	public List<Link> getLinks() {
		ODLNode n1 = new ODLNode();
		n1.setMac(new String(MultiTainter.taintedCharArray("m1".toCharArray(), "t-m1")));
		ODLNode n2 = new ODLNode();
		n2.setMac(new String(MultiTainter.taintedCharArray("m2".toCharArray(), "t-m2")));
		ODLNode n3 = new ODLNode();
		n3.setMac(new String(MultiTainter.taintedCharArray("m3".toCharArray(), "t-m3")));
		ODLNode n4 = new ODLNode();
		n4.setMac(new String(MultiTainter.taintedCharArray("m4".toCharArray(), "t-m4")));
		ODLNode n5 = new ODLNode();
		n5.setMac(new String(MultiTainter.taintedCharArray("m5".toCharArray(), "t-m5")));
		ODLNode n6 = new ODLNode();
		n6.setMac(new String(MultiTainter.taintedCharArray("m6".toCharArray(), "t-m6")));
		ODLNode n7 = new ODLNode();
		n7.setMac(new String(MultiTainter.taintedCharArray("m7".toCharArray(), "t-m7")));
		
		ODLLink l1 = new ODLLink();
		l1.setSrc(n1);
		l1.setDst(n2);
		l1.bandwidth = MultiTainter.taintedInt(10, "t-l1");
		ODLLink l2 = new ODLLink();
		l2.setSrc(n1);
		l2.setDst(n3);
		l2.bandwidth = MultiTainter.taintedInt(12, "t-l2");
		ODLLink l3 = new ODLLink();
		l3.setSrc(n2);
		l3.setDst(n4);
		l3.bandwidth = MultiTainter.taintedInt(14, "t-l3");
		ODLLink l4 = new ODLLink();
		l4.setSrc(n4);
		l4.setDst(n5);
		l4.bandwidth = MultiTainter.taintedInt(16, "t-l4");
		ODLLink l5 = new ODLLink();
		l5.setSrc(n5);
		l5.setDst(n6);
		l5.bandwidth = MultiTainter.taintedInt(18, "t-l5");
		ODLLink l6 = new ODLLink();
		l6.setSrc(n6);
		l6.setDst(n7);
		l6.bandwidth = MultiTainter.taintedInt(20, "t-l6");
		List<Link> links = new LinkedList<Link>();
		links.add(l1);
		links.add(l2);
		links.add(l3);
		links.add(l4);
		System.out.println("links: get taint: " + MultiTainter.getTaint(links));
		System.out.println("l1: get taint: " + MultiTainter.getTaint(links.get(0)));
		return links;
	}

	@Override
	public List<Node> getNodes() {
		return null;
	}

}
