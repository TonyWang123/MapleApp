/*
 * Copyright (c) 2016 SNLAB and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.snlab.maple.mapleclinet.core.tracetree;

import java.util.List;

public abstract class Node {
  public Node father;

  public abstract void delete(Node node);

  public void invalidate() {
    father.delete( this );
  }
  public abstract void augment(List<TraceItem> trace, Action action);
}
