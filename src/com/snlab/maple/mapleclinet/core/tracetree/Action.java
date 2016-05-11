/*
 * Copyright (c) 2016 SNLAB and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.snlab.maple.mapleclinet.core.tracetree;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.snlab.maple.mapleclient.api.Link;
import com.snlab.maple.mapleclient.api.Port;

public class Action {
  private boolean enable = true;

  private static Punt punt = new Punt();
  private static Drop drop = new Drop();
  private static Flood flood = new Flood();
  private static Pass pass = new Pass();

  public static Action Pass() { return pass; }
  public static Action Punt() { return punt; }
  public static Action Route(List<Link> routingPath, Port ncf) { return new Route(routingPath, ncf); }
  public static Action ToPorts(int... portIDs) { return new ToPorts(portIDs); }
  public static Action Drop() { return drop; }
  public static Action Flood() { return flood; }
  
  public Set<String> accessDataSet = new HashSet<String>();

  public void disabled() {
    enable = false;
  }

  @Override
  public String toString() {
    if (this instanceof Punt) return "Punt";
    if (this instanceof Drop) return "Drop";
    if (this instanceof Flood) return "Flood";
    if (this instanceof Pass) return "Pass";
    return "otherwise";
  }
}
