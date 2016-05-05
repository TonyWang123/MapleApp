/*
 * Copyright (c) 2016 SNLAB and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.snlab.maple.mapleclinet.core.tracetree;

import com.snlab.maple.mapleclient.api.Port;

public class TraceItemV extends TraceItem {

  @Override
  public String toString() {
    return "TraceItemV [field=" + field + ", value=" + value + "] --> ";
  }

  @Override
  public boolean equals(Object other) {
    if (null == other) { return false; }
    TraceItem other2 = (TraceItem) other;
    return field==other2.field && value==other2.value;
  }

  @Override
  public int hashCode() {
    final int prime = 5557;
    int result = 1;
    result = prime * result + field.ordinal();
    result = prime * result + value.hashCode();
    return result;
  }

  public static TraceItemV ethSrc(long addr) {
    TraceItemV item = new TraceItemV();
    item.field = Field.ETH_SRC;
    item.value = String.valueOf(addr);
    return item;
  }

  public static TraceItemV ethDst(long addr) {
    TraceItemV item = new TraceItemV();
    item.field = Field.ETH_DST;
    item.value = String.valueOf(addr);
    return item;
  }

  public static TraceItemV ethType(int typ) {
    TraceItemV item = new TraceItemV();
    item.field = Field.ETH_TYPE;
    item.value = String.valueOf(typ);
    return item;
  }

  public static TraceItemV IP4Src(int addr) {
	  TraceItemV item = new TraceItemV();
	  item.field = Field.IPv4_SRC;
	  item.value = String.valueOf(addr);
	  return item;
  }

  public static TraceItemV IP4Dst(int addr) {
	  TraceItemV item = new TraceItemV();
	  item.field = Field.IPv4_DST;
	  item.value = String.valueOf(addr);
	  return item;
  }

  public static TraceItemV TCP_SRC_PORT(int port) {
	  TraceItemV item = new TraceItemV();
	  item.field = Field.TCP_SRC_PORT;
	  item.value = String.valueOf(port);
	  return item;
  }

  public static TraceItemV TCP_DST_PORT(int port) {
	  TraceItemV item = new TraceItemV();
	  item.field = Field.TCP_DST_PORT;
	  item.value = String.valueOf(port);
	  return item;
  }

  public static TraceItemV inPort(Port port) {
    TraceItemV item = new TraceItemV();
    item.field = Field.IN_PORT;
    item.value = port.toString();
    return item;
  }

  public static TraceItemV topology() {
    TraceItemV item = new TraceItemV();
    item.field = Field.TOPOLOGY;
    item.value = "topology";
    return item;
  }
}
