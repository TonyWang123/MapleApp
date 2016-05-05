/*
 * Copyright (c) 2016 SNLAB and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.snlab.maple.mapleclinet.core;


import com.snlab.maple.mapleclient.api.Port;
import com.snlab.maple.mapleclinet.core.tracetree.Action;
import com.snlab.maple.mapleclinet.core.tracetree.Match;
import com.snlab.maple.mapleclinet.core.tracetree.Rule;

import java.util.List;

public interface MapleDataPathAdaptor {
    void sendPacket(byte[] payload, Port ingress, Action action);
    void installPath(Action action, Match match, Integer priority);
    void deletePath(Action action, Match match, Integer priority);

    void installRule(Rule r, Port sw); //Not in use
    void deleteRule(Rule r, Port sw);  //Not in use

    void setMapleCore(MapleCore mapleCore);
}
