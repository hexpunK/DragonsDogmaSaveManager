package com.woernerj.dragonsdogma.bo.types.xml;

public abstract class NamedType {

	private java.lang.String name;
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	public abstract java.lang.String getXmlString();
}
