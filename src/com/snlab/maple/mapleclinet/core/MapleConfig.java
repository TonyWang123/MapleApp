package com.snlab.maple.mapleclinet.core;

public class MapleConfig {

	private String controllerAddress;
	
	private int port;

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getControllerAddress() {
		return controllerAddress;
	}

	public void setControllerAddress(String controllerAddress) {
		this.controllerAddress = controllerAddress;
	}
}
