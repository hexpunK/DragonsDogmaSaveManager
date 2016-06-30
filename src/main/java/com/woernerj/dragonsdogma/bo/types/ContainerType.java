package com.woernerj.dragonsdogma.bo.types;

import java.util.List;

public abstract class ContainerType extends NamedType {

	private List<?> children;
	
	public List<?> getChildren() {
		return this.children;
	}
	public void setChildren(List<?> children) {
		this.children = children;
	}
}
