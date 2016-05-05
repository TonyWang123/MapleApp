/*
 * Copyright (c) 2016 SNLAB and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.snlab.maple.mapleclinet.core;

import com.snlab.maple.mapleclinet.core.tracetree.TraceTree;

public interface MapleDataStoreAdaptor {
    Object readData(String xpath);
    void writeData(String xpath, Object data); //TODO
    void writeTraceTree(TraceTree traceTree);
    void setMapleCore(MapleCore ms);
}
