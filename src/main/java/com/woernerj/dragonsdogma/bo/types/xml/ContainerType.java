package com.woernerj.dragonsdogma.bo.types.xml;

import java.util.List;

public abstract class ContainerType<T> extends NamedType {

	private List<T> children;
	
	public List<T> getChildren() {
		return this.children;
	}
	public void setChildren(List<T> children) {
		this.children = children;
	}
}
