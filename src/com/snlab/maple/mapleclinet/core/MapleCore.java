package com.snlab.maple.mapleclinet.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;


import com.snlab.maple.mapleclient.api.MapleApp;
import com.snlab.maple.mapleclient.api.Port;
import com.snlab.maple.mapleclient.api.Topology;
import com.snlab.maple.mapleclinet.core.packet.Ethernet;
import com.snlab.maple.mapleclinet.core.tracetree.Action;
import com.snlab.maple.mapleclinet.core.tracetree.Diff;
import com.snlab.maple.mapleclinet.core.tracetree.MaplePacket;
import com.snlab.maple.mapleclinet.core.tracetree.Node;
import com.snlab.maple.mapleclinet.core.tracetree.Rule;
import com.snlab.maple.mapleclinet.core.tracetree.TraceItem;
import com.snlab.maple.mapleclinet.core.tracetree.TraceItemV;
import com.snlab.maple.mapleclinet.core.tracetree.TraceTree;

public class MapleCore {

	static ArrayList<MapleCore> mapleCores = new ArrayList<MapleCore>();
    static MapleCore allocateMapleCore() {
        return mapleCores.get(0); //TODO: missing allocator
    }

    private MapleApp userApp;
    private TraceTree traceTree;
    private HashSet<Node> data2Node;
    private MapleDataPathAdaptor mapleDataPathAdaptor;
    private MapleDataStoreAdaptor mapleDataStoreAdaptor;

    public MapleCore(MapleDataPathAdaptor mdpa, MapleDataStoreAdaptor mdsa) {
        if (mdpa == null || mdsa == null)
            throw new IllegalArgumentException("mapleAdaptor passed to " +
                                               "MapleSystem must be non-null");

        mapleDataPathAdaptor = mdpa;
        mapleDataStoreAdaptor = mdsa;

        mdpa.setMapleCore( this );
        mdsa.setMapleCore( this );

        mapleCores.add( this );

        traceTree = new TraceTree( this );
        oldRules = new LinkedList<Rule>();
        data2Node = new HashSet<Node>();
    }

    public void setMapleApp(MapleApp newApp) {
        if (userApp == newApp) return;

        userApp = newApp;
        traceTree = new TraceTree( this );
        data2Node = new HashSet<Node>();
    }

    public void mapNode2DataClass(Node node, Class<?> clazz) {
    	if (!data2Node.contains(node)) data2Node.add(node);
    	/*
    	System.out.println("mapNode2DataClass" + ((node instanceof V) ? "V" : "T"));

        List<Node> list = data2Node.get(clazz);
        if (list == null) {
            list = new ArrayList<Node>();
            list.add(node);
            data2Node.put(clazz, list);
        } else {
        	for (Node t : list)
        		if (t == node) {
        			System.out.println(node.toString() + "mulitcast");
        			return;
        		}
            list.add(node);
            assert(data2Node.containsValue(list));
        }
        */
    }

    private boolean omitFlag = true;

    public void onDataChanged(Object data) {
    	if (omitFlag) {
    		omitFlag = false;
    		return;
    	}

        if (data == null) return;

        if(data instanceof Topology){
        	System.out.println("NOTE: Topology has been changed.");
        }

        /*
        LinkedList<Rule> newRules = traceTree.compile();

        System.out.println("data2Node size:" + String.valueOf(data2Node.size()));
        for (Node node : data2Node)
        	if (node != null) {
        		System.out.println("node deleted");
        		node.invalidate();
        		data2Node.remove(node);
        	}
        */

        //traceTree.rootPointer.subtree[0].invalidate();
        //recompileTraceTree();
        /////////////////////////////////////////////////
        this.traceTree = new TraceTree(this);

        /*
        List<Node> nodes = data2Node.get(Topology.class);

        System.out.println("Get nodes delete: " + String.valueOf(nodes.size()));

        if (nodes != null) {
        	for (Node node : nodes)
        		if (node != null) node.invalidate();
        	System.out.println("Delete Nodes :" + String.valueOf(nodes.size()));
        	data2Node.remove(data.getClass());
        	recompileTraceTree();
        }
		*/

        userApp.onDataChanged(data);
    }

    public void writeData(String xpath, Object data) {
        mapleDataStoreAdaptor.writeData(xpath, data); //TODO
    }


    private LinkedList<Rule> oldRules;
    private List<TraceItem> traceItems;

    public void traceAdd(TraceItem traceItem) {
        traceItems.add(traceItem);
    }

    public Object readData(String xpath) {
        if (xpath.equals("/root/network-topology/topology")) {
            Object data = mapleDataStoreAdaptor.readData(xpath);
            traceItems.add(TraceItemV.topology()); //TODO: add to data2Node
            return data;
        }

        return null; //Should never reach here
    }

    public void onPacket(byte[] payload, Port ingressPort) {
        if (userApp == null) {
            return; //TODO
        }

        Ethernet frame = new Ethernet();
        frame.deserialize(payload, 0, payload.length);

        traceItems = new ArrayList<TraceItem>();
        MaplePacket maplePacket = new MaplePacket(frame, ingressPort, this);
        Action action = userApp.onPacket(maplePacket);

        if (action != null) {
            traceTree.augment(traceItems, action);

            mapleDataStoreAdaptor.writeTraceTree(traceTree);

            data2Node = new HashSet<Node>();
            recompileTraceTree();

            mapleDataPathAdaptor.sendPacket(payload, ingressPort, action);
        }
    }

    private void recompileTraceTree() {
        LinkedList<Rule> newRules = traceTree.compile();
        mapleDataStoreAdaptor.writeTraceTree(traceTree);

        Diff diff = Diff.diff(oldRules, newRules);
        oldRules = newRules;
        for (Rule r : diff.removed) mapleDataPathAdaptor.deletePath(r.action, r.match, r.priority);
        for (Rule r : diff.added) mapleDataPathAdaptor.installPath(r.action, r.match, r.priority);
    }
}
