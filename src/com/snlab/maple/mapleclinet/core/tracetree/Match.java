/*
 * Copyright (c) 2016 SNLAB and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.snlab.maple.mapleclinet.core.tracetree;

import java.util.HashSet;

public class Match {
  public HashSet<TraceItem> fieldValues;
  public Match() {
	  fieldValues = new HashSet<TraceItem>();
  }
  public Match(HashSet<TraceItem> fieldValues) {
    this.fieldValues = fieldValues;
  }

  public static Match matchAny() {
    return new Match(new HashSet<TraceItem>());
  }

  public Match add(TraceItem item) {
    fieldValues.add(item);
    return this;
  }

  @Override
  public String toString() {
    String str = "Matches: ";
    for (TraceItem item : fieldValues) {
      str+=item.toString();
      str+="; ";
    }
    return str.substring(0,str.length()-2);
  }

  @Override
  public boolean equals(Object other) {
    if (null == other) { return false; }
    Match other2 = (Match) other;
    return fieldValues.equals(other2.fieldValues);
  }
}
