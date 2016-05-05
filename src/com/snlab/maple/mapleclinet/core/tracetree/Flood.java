/*
 * Copyright (c) 2016 SNLAB and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.snlab.maple.mapleclinet.core.tracetree;

public class Flood extends Action {
  @Override
  public boolean equals(Object other) {
    if (null == other) { return false; }
    if (other instanceof Flood) {
      Flood other2 = (Flood) other;
      return true;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    int result = 1;
    return result;
  }

  @Override
  public String toString() {
    return "Flood; ";
  }
}
