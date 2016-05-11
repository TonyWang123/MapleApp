package com.snlab.mapleserver.message;

import java.util.Map;

public class Message{
	
	private int type; //message type
	
	private byte[] data; //packet payload
	
	private String ingressPort; //ingress port id: nodeId|portId
	
	private Action action;
	
	private Map<Integer, String> match;

	public Map<Integer, String> getMatch() {
		return match;
	}

	public void setMatch(Map<Integer, String> match) {
		this.match = match;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getIngressPort() {
		return ingressPort;
	}

	public void setIngressPort(String ingressPort) {
		this.ingressPort = ingressPort;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}
}
