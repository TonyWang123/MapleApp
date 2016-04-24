package com.snlab.maple.mapleclient.api;

import java.util.List;

public interface Topology {

	public List<Link> getLinks();

    public List<Node> getNodes();
}
