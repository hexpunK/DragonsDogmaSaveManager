package com.woernerj.dragonsdogma.bo.types;

public class Array extends NamedType {

	private String type;
	private Integer count;
	private Object[] children;
	
	public String getType() {
		return type;
	}
	public Integer getCount() {
		return count;
	}
	public Object[] getChildren() {
		return children;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public void setChildren(Object[] children) {
		this.children = children;
	}
}
