package com.woernerj.dragonsdogma.bo.types;

import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;

public abstract class ContainerType extends NamedType {

	private List<?> children;
	
	public List<?> getChildren() {
		return this.children;
	}
	@XmlAnyElement
	public void setChildren(List<?> children) {
		this.children = children;
	}
}
