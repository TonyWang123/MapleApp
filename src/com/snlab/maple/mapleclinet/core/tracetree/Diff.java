/*
 * Copyright (c) 2016 SNLAB and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.snlab.maple.mapleclinet.core.tracetree;

import java.util.LinkedList;

public class Diff {
  public LinkedList<Rule> removed;  // = old - new
  public LinkedList<Rule> added;    // = new - old

  public Diff (LinkedList<Rule> removed, LinkedList<Rule> added) {
    this.removed = removed;
    this.added   = added;
  }

  public static Diff diff(LinkedList<Rule> oldRules, LinkedList<Rule> newRules) {
    LinkedList<Rule> removed = new LinkedList<Rule>();
    for (Rule oldRule : oldRules) {
      boolean inNewRules = false;
      for (Rule newRule : newRules) {
        if (oldRule.equals(newRule))
          inNewRules = true;
      }
      if (!inNewRules) removed.add(oldRule);
    }

    LinkedList<Rule> added = new LinkedList<Rule>();
    for (Rule newRule : newRules) {
      boolean inOldRules = false;
      for (Rule oldRule : oldRules) {
        if (newRule.equals(oldRule))
          inOldRules = true;
      }
      if (!inOldRules) added.add(newRule);
    }

    return new Diff(removed, added);
  }

  @Override
  public boolean equals(Object other) {
    if (null == other) { return false; }
    if (other instanceof Diff) {
      Diff other2 = (Diff) other;
      return
          removed.equals(other2.removed) &&
          added.equals(other2.added);
    } else {
      return false;
    }
  }

}
