package com.snlab.mapleserver.message;

import java.util.HashMap;
import java.util.Map;

public class KeyValueObject {
	
	String type;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, Object> getContent() {
		return content;
	}

	public void setContent(Map<String, Object> content) {
		this.content = content;
	}

	Map<String, Object> content = new HashMap<String, Object>();
	
	public void addObject(String key, Object object){
		content.put(key, object);
	}
	
	public Object getObject(String key){
		return content.get(key);
	}
}
