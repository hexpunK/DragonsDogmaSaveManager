package com.woernerj.dragonsdogma.bo.types.xml;

public class Array extends ContainerType {

	private String type;
	private Integer count;
	
	public String getType() {
		return type;
	}
	public Integer getCount() {
		return count;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
}
