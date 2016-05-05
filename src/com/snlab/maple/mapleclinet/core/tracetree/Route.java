/*
 * Copyright (c) 2016 SNLAB and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.snlab.maple.mapleclinet.core.tracetree;

import java.util.ArrayList;
import java.util.List;

import com.snlab.maple.mapleclient.api.Link;
import com.snlab.maple.mapleclient.api.Port;

public class Route extends Action{
  public List<Link> routingPath;
  public Port lastNCR;

  Route() {
    this.routingPath = new ArrayList<Link>();
  }

  Route(List<Link> ports, Port lastNCR) {
    this.routingPath = ports;
    this.lastNCR = lastNCR;
  }

  // TODO: This is quite inefficient; it would be better to use
  // StringBuilder here.
  @Override
  public String toString() {

    String start = "Route: [";
    String end = "]";
    String middle = "";

    for (int i = 0; i < routingPath.size(); i++) {
      middle += routingPath.get(i);
      if (i < routingPath.size() - 1) { middle += ", "; }
    }
    middle += ", " + lastNCR.toString();

    return start + middle + end;
  }

  @Override
  public boolean equals(Object other) {
    if (null == other) { return false; }
    Route other2 = (Route) other;
    return routingPath.equals(other2.routingPath);
  }

  @Override
  public int hashCode() {
    final int prime = 5557;
    int result = 1;
    for (Link p : routingPath) {
      result = prime * result + p.hashCode();
    }
    result = prime * result + lastNCR.hashCode();
    return result;
  }
}
