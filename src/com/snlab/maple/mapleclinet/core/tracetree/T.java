/*
 * Copyright (c) 2016 SNLAB and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.snlab.maple.mapleclinet.core.tracetree;

import java.util.List;

public class T extends Node {
  public TraceItemT.Field field = null;
  public String value;
  public final static int POS_BRANCH = 1;   // (val == exp);
  public final static int NEG_BRANCH = 0;   // (val != exp);
  public Node[] subtree = new Node[2];

  @Override
  public void delete(Node node) {
    for (int i = 0; i < 2; i++)
      if (subtree[i] == node) subtree[i] = null;
  }

  public Node getChild(boolean value) {
    if (value)
      return subtree[POS_BRANCH];
    else
      return subtree[NEG_BRANCH];
  }

  public void augment(List<TraceItem> trace, Action action) {
    if (trace.isEmpty()) {
      return;
    }
    TraceItem next = trace.remove(0);
    field = next.field;
    value = next.value;

    if (trace.size()==0) {  // Add leaf node.
      L child = new L(action);
      child.father = this;
      if (next.Tvalue == true)
        subtree[POS_BRANCH] = child;
      else {
        subtree[NEG_BRANCH] = child;
      }
    }
    else { // Augment trace recursively.
      if(getChild(next.Tvalue) != null)
        getChild(next.Tvalue).augment(trace, action);
      else { // Child is empty
        if (trace.get(0) instanceof TraceItemT) {
          T child = new T();
          child.father = this;
          subtree[next.Tvalue ? POS_BRANCH : NEG_BRANCH] = child;
          child.augment(trace, action);
        }
        else if (trace.get(0) instanceof TraceItemV) {
          V child = new V();
          child.father = this;
          subtree[next.Tvalue ? POS_BRANCH : NEG_BRANCH] = child;
          child.augment(trace, action);
        }
        else if(trace.get(0) instanceof TraceItemD){
        	D child = new D();
        	child.father = this;
        	subtree[next.Tvalue ? POS_BRANCH : NEG_BRANCH] = child;
        	child.augment(trace, action);
        }
        else {
          //FIXME:TODO: Replace println with a uniform LOG module.
          System.out.println("Error in T.augment: Unknown type of TraceItem.");
        }
      } // End of child is empty
    } // End of Augment trace recursively
  }
}
