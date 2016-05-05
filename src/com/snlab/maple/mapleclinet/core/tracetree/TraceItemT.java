/*
 * Copyright (c) 2016 SNLAB and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.snlab.maple.mapleclinet.core.tracetree;

import com.snlab.maple.mapleclient.api.Port;

public class TraceItemT extends TraceItem {

  @Override
  public String toString() {
    return "TraceItemT [field=" + field + ", value=" + value + ", Tvalue=" + Boolean.toString(Tvalue) + "] --> ";
  }

  @Override
  public boolean equals(Object other) {
    if (null == other) { return false; }
    TraceItemT other2 = (TraceItemT) other;
    return field==other2.field && value==other2.value && Tvalue==other2.Tvalue;
  }

  @Override
  public int hashCode() {
    final int prime = 5557;
    int result = 1;
    result = prime * result + field.ordinal();
    result = prime * result + value.hashCode();
    if (Tvalue)
      result += 1;
    return result;
  }

  public static TraceItemT ethSrcIs(long addr, long exp) {
    TraceItemT item = new TraceItemT();
    item.field = Field.ETH_SRC;
    item.value = String.valueOf(exp);
    item.Tvalue = (addr == exp);
    return item;
  }

  public static TraceItemT ethDstIs(long addr, long exp) {
    TraceItemT item = new TraceItemT();
    item.field = Field.ETH_DST;
    item.value = String.valueOf(exp);
    item.Tvalue = (addr == exp);
    return item;
  }

  public static TraceItemT ethTypeIs(int typ, int exp) {
    TraceItemT item = new TraceItemT();
    item.field = Field.ETH_TYPE;
    item.value = String.valueOf(exp);
    item.Tvalue = (typ == exp);
    return item;
  }

  public static TraceItemT inPortIs(Port port, Port exp) {
    TraceItemT item = new TraceItemT();
    item.field = Field.IN_PORT;
    item.value = exp.toString();
    item.Tvalue = (port.toString() == exp.toString());
    return item;
  }

  //There should no public static TraceItemT topology()
}
