package com.woernerj.dragonsdogma.bo.types.xml;

import java.util.List;

public abstract class ContainerType extends NamedType {

	private List<? extends NamedType> children;
	
	public List<? extends NamedType> getChildren() {
		return this.children;
	}
	public void setChildren(List<? extends NamedType> children) {
		this.children = children;
	}
}
