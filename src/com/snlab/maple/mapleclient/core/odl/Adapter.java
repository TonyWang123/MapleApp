package com.snlab.maple.mapleclient.core.odl;

import com.snlab.mapleserver.message.KeyValueObject;

public interface Adapter {

	public Object transform(KeyValueObject object);
}
