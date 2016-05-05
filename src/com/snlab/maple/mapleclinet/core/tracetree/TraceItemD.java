/*
 * Copyright (c) 2016 SNLAB and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.snlab.maple.mapleclinet.core.tracetree;

public class TraceItemD extends TraceItem{

	  @Override
	  public String toString() {
	    return "TraceItemD [field=" + field + ", value=" + value + "] --> ";
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

	  public static TraceItemD topology() {
	    TraceItemD item = new TraceItemD();
	    item.field = Field.TOPOLOGY;
	    item.value = "topology";
	    return item;
	  }
}
