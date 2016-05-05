package com.snlab.maple.mapleclient.api;

public interface MapleSystemAPI {

	public <T> T read(Identifier<T> ref);
	
	public <T> void write(Identifier<T> ref, T value);
	
}
