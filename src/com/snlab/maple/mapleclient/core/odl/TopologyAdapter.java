package com.snlab.maple.mapleclient.core.odl;

import java.util.List;

import com.snlab.mapleserver.message.KeyValueObject;

public class TopologyAdapter implements Adapter{

	@Override
	public Object transform(KeyValueObject topology) {
		ODLTopology result = new ODLTopology();
		List<KeyValueObject> nodes = (List<KeyValueObject>)topology.getObject("nodes");
		List<KeyValueObject> links = (List<KeyValueObject>)topology.getObject("links");
		List<KeyValueObject> ports = (List<KeyValueObject>)topology.getObject("ports");
		
		for(KeyValueObject node: nodes){
			ODLNode odlNode = new ODLNode();
			odlNode.setMac((String)node.getObject("mac"));
			
		}
		return result;
	}

}
