package com.woernerj.dragonsdogma.bo.types;

public class Class extends NamedType {

	private String type;
	private Object[] children;
	
	public String getType() {
		return this.type;
	}
	public Object[] getChildren() {
		return this.children;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setChildren(Object[] children) {
		this.children = children;
	}
}
