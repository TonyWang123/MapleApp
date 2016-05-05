/*
 * Copyright (c) 2016 SNLAB and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.snlab.maple.mapleclinet.core.tracetree;

public class Rule {

  public int priority;
  public Match match;
  public Action action;

  public Rule(int p, Match m, Action action) {
    this.priority = p;
    this.match = m;
    this.action = action;
  }

  @Override
  public boolean equals(Object other) {
    if (null == other) { return false; }
    Rule other2 = (Rule) other;
    return
        priority==other2.priority &&
        match.equals(other2.match) &&
        action.equals(other2.action);
  }

  @Override
  public String toString() {
    String str = "Rule [ priority: ";
    str+= priority;
    str+=", match: ";
    str+=match.toString();
    str+=", action: ";
    str+=action.toString();
    str+="]";
    return str;
  }

}
