/*
 * Copyright (c) 2016 SNLAB and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.snlab.maple.mapleclinet.core.tracetree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Set;


import com.snlab.maple.mapleclient.api.Topology;
import com.snlab.maple.mapleclinet.core.MapleCore;

public class TraceTree {

  private MapleCore mapleCore;

  public T rootPointer = new T();
  public int priority = 0;
  public LinkedList<Rule> rules;

  public TraceTree(MapleCore mapleCore) {
    this.mapleCore = mapleCore;
  }

  public void augment(List<TraceItem> trace, Action action) {

	    if(rootPointer.subtree[0] == null) {
      rootPointer.subtree[0] = trace2tree(trace, action);
      rootPointer.subtree[0].father = rootPointer;
    } else {
      rootPointer.subtree[0].augment(trace, action);
    }
  }

  public LinkedList<Rule> compile() {
    rules = new LinkedList<Rule>();
    priority = 0;
    build(rootPointer.subtree[0], Match.matchAny());
    return rules;
  }

  //TODO: This is a very naive version of generating rules from TT.
  //      The first step towards optimizations is described in Maple paper Section 4.3.
  private void build(Node t, Match match) { //TODO :: map data to nodes
    if (t == null) {  // empty child of T nodes
      Rule rule = new Rule(priority, match, Action.Punt());
      rules.add(rule);
      priority += 1;
    }
    else if (t instanceof L) {
      L leaf = (L) t;
      Rule rule = new Rule(priority, match, leaf.action);
      rules.add(rule);
      priority += 1;
    }
    else if (t instanceof V) {
      //System.out.println("V Node: " + ((V) t).field.toString());

      /*if (((V) t).field == TraceItem.Field.TOPOLOGY) {
        mapleCore.mapNode2DataClass(t, Topology.class);
      }*/

      Set<String> keys = ((V) t).subtree.keySet();
      Iterator<String> iterator = keys.iterator();
      while(iterator.hasNext()) {
        TraceItemV item = new TraceItemV();
        item.field = ((V) t).field;
        item.value = iterator.next();
        Match m = Match.matchAny();
        for(TraceItem item2 : match.fieldValues) {
          m.add(item2);
        }
        m.add(item);
        build(((V) t).getChild(item.value), m);
      }
    }
    else if (t instanceof T) { //TODO: if only contain NegativeBranch , add a virtual Rule

      /*if (((T) t).field == TraceItem.Field.TOPOLOGY) {
        mapleCore.mapNode2DataClass(t, Topology.class);
      }*/

      Match m_neg = Match.matchAny();
      for (TraceItem item2 : match.fieldValues) {
        m_neg.add(item2);
      }
      build(((T) t).subtree[T.NEG_BRANCH], m_neg);

      TraceItemT item = new TraceItemT();
      item.field = ((T) t).field;
      item.value = ((T) t).value;
      item.Tvalue = true;

      Match m = Match.matchAny();
      for (TraceItem item2 : match.fieldValues) {
        m.add(item2);
      }
      if (((T) t).subtree[T.POS_BRANCH] == null) {
        m.add(item);
        Rule rule = new Rule(priority, m, Action.Punt()); // barrier rule
        rules.add(rule);
        priority += 1;
      } else {
        m.add(item);
        build(((T) t).subtree[T.POS_BRANCH], m);
      }
    }
    else if(t instanceof D){
        build(((D) t).subtree, match);
    }
  }

  private Node trace2tree(List<TraceItem> trace, Action action) {
    if (trace.isEmpty()) {
      return new L(action);
    }
    if (trace.get(0) instanceof TraceItemT) {
      T treeRoot = new T();
      treeRoot.augment(trace, action);
      return treeRoot;
    }
    else if(trace.get(0) instanceof TraceItemV){
      V treeRoot = new V();
      treeRoot.augment(trace, action); //trace.get(0) instanceof TraceItemV
      return treeRoot;
    }else{
      D treeRoot = new D();
      treeRoot.augment(trace, action);
      return treeRoot;
    }
  }
}
