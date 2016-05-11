/*
 * Copyright (c) 2016 SNLAB and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.snlab.maple.mapleclinet.core.tracetree;

import java.util.List;

public class L extends Node {

  public Action action;

  @Override
  public void delete(Node node) { }

  public L(Action action) {
    this.action = action;
  }

  public void augment(List<TraceItem> trace, Action action) {
    //Should NEVER be used
  }
  
  public boolean dependOn(String dataType){
	  if(action.accessDataSet.contains(dataType))return true;
	  else return false;
  }
}
