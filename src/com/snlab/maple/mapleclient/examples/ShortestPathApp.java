package com.snlab.maple.mapleclient.examples;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.snlab.maple.mapleclient.api.Identifier;
import com.snlab.maple.mapleclient.api.Link;
import com.snlab.maple.mapleclient.api.MapleApp;
import com.snlab.maple.mapleclient.api.Node;
import com.snlab.maple.mapleclient.api.Packet;
import com.snlab.maple.mapleclient.api.Topology;
import com.snlab.maple.mapleclient.core.odl.ODLTopologyIdentifier;
import com.snlab.maple.mapleclinet.core.Action;
import com.snlab.maple.mapleclinet.core.MapleClient;
import com.snlab.maple.mapleclinet.core.MapleConfig;

import edu.columbia.cs.psl.phosphor.runtime.MultiTainter;

public class ShortestPathApp extends MapleApp {

	private List<Link> bfs(Node root, String target, Map<String, List<Link>> myTopo) {
		System.out.println("start bfs");
		List<Link> path = new LinkedList<Link>();
		System.out.println("1");
		List<Node> q = new LinkedList<Node>();
		Set<Node> alreadySeenNodes = new HashSet<Node>();
		System.out.println("2");
		Map<Node, Link> reversedMap = new HashMap<Node, Link>();
		System.out.println("3");
		
		System.out.println(root.getMac() + " " + target);
		if (root.getMac().equals(target))
			return null;
		alreadySeenNodes.add(root);
		q.add(root);
		boolean found = false;
		Node targetNode = null;
		System.out.println("start while");
		while (!q.isEmpty()) {
			Node first = q.get(0);
			q.remove(0);
			System.out.println("first node: " + first.getMac());
			List<Link> nextLinks = myTopo.get(first.getMac());
			for (Link link : nextLinks) {
				Node nextNode = link.getDstNode();
				System.out.println("next node: " + nextNode.getMac());
				reversedMap.put(nextNode, link);
				if (!alreadySeenNodes.contains(nextNode)) {
					System.out.println("enter not alreadySeenNodes");
					alreadySeenNodes.add(nextNode);
					if (nextNode.getMac().equals(target)) {
						found = true;
						targetNode = nextNode;
						System.out.println("found");
						break;
					}
					q.add(nextNode);
				}
			}
			if (found)
				break;
		}
		System.out.println("reversedMap size: " + reversedMap.size());
		if(reversedMap.get(targetNode).getSrcNode().equals(root)){
			Link tempLink = reversedMap.get(targetNode);
			path.add(tempLink);
		}
		while (!reversedMap.get(targetNode).getSrcNode().equals(root)) {
			System.out.println("checking reversedMap");
			Link tempLink = reversedMap.get(targetNode);
			path.add(tempLink);
			targetNode = tempLink.getSrcNode();
		}
		return path;
	}

	// macAddress
	private List<Link> findShortestPath(String src, String dst, Topology topo) {
		List<Link> path = null;
		Map<String, List<Link>> myTopo = new HashMap<String, List<Link>>();
		Node rootNode = null;
		for (Link link : topo.getLinks()) {
			Node srcNode = link.getSrcNode();
			String macAddress = srcNode.getMac();
			if (macAddress.equals(src))
				rootNode = srcNode;
			if (myTopo.containsKey(macAddress)) {
				myTopo.get(macAddress).add(link);
				System.out.println("add entry in myTopo");
			} else {
				List<Link> temp = new LinkedList<Link>();
				temp.add(link);
				myTopo.put(macAddress, temp);
				System.out.println("generate and add entry in myTopo");
			}
		}
		System.out.println("myTopo: get taint: " + MultiTainter.getTaint(myTopo));
		path = bfs(rootNode, dst, myTopo);
		return path;
	}

	@Override
	public Action onPacket(Packet p) {
		String src = p.getSrcMac();
		String dst = p.getDstMac();
		
		Identifier<Topology> topoRef = new ODLTopologyIdentifier();
		Topology topo = ms.read(topoRef);
		System.out.println("topo: get taint: " + MultiTainter.getTaint(topo));
		List<Link> path = findShortestPath(src, dst, topo);
		Action action = new Action();
		action.setPath(path);
		return action;
	}

	public static void main(String[] args) {
		ShortestPathApp app = new ShortestPathApp();
		MapleClient client = new MapleClient();
		MapleConfig conf = new MapleConfig();
		conf.setControllerAddress("localhost");
		conf.setPort(10000);
		client.setup(conf);
		client.addMapleApp(app);
		client.register();
	}
}
