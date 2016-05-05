/*
 * Copyright (c) 2016 SNLAB and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.snlab.maple.mapleclinet.core.tracetree;


import java.util.ArrayList;
import java.util.List;

import com.snlab.maple.mapleclient.api.TpId;

public class Punt extends Action {
  public List<TpId> routingPath;

  Punt() {
    this.routingPath = new ArrayList<TpId>();
  }

  Punt(List<TpId> ports) {
    this.routingPath = ports;
  }

  @Override
  public boolean equals(Object other) {
    if (null == other) { return false; }
    if (other instanceof Punt) {
      Punt other2 = (Punt) other;
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
    return "Punt; ";
  }
}
