/*
 * Copyright (c) 2016 SNLAB and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.snlab.maple.mapleclinet.core.tracetree;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class V extends Node {
  public TraceItem.Field field = null;
  public Map<String, Node> subtree = new HashMap<String, Node>();

  @Override
  public void delete(Node node) {
    for (String name : subtree.keySet()) {
      if (subtree.get(name) == node) subtree.remove(name);
    }
  }

  public Node getChild(String value) {
    return subtree.get(value);
  }

  public void augment(List<TraceItem> trace, Action action) {
    if (trace.isEmpty()) {
      return;
    }
    TraceItem next = trace.remove(0);
    field = next.field;
    if (trace.size() == 0) {
      L child = new L(action);
      child.father = this;
      if (subtree.containsKey(next.value)) subtree.remove(next.value);
      subtree.put(next.value, child);
    } else {
      if(getChild(next.value) != null)
        getChild(next.value).augment(trace, action);
      else {
        if (trace.get(0) instanceof TraceItemT ) {
          T child = new T();
          child.father = this;
          subtree.put(next.value, child);
          child.augment(trace, action);
        }
        else if(trace.get(0) instanceof TraceItemV){
            V child = new V();
            child.father = this;
            subtree.put(next.value, child);
            child.augment(trace, action);
          }
        else if(trace.get(0) instanceof TraceItemD){
          D child = new D();
          child.father = this;
          subtree.put(next.value, child);
          child.augment(trace, action);
        }
        else{
        	System.out.println("V error");
        }
      }
    }
  }
}
