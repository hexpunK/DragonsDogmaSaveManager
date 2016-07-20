package com.woernerj.dragonsdogma.bo.types.xml;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({Array.class, Class.class, ClassRef.class})
public abstract class ContainerType<T extends NamedType> extends NamedType {

	private List<? extends NamedType> children = new LinkedList<>();

	/**
	 * Get the child nodes of this {@link ContainerType} node.
	 * 
	 * @return A {@link List} of objects extended from {@link NamedType} that 
	 * represents the children of this item in the XML structure.
	 */
	@XmlElementRef
	public List<? extends NamedType> getChildren() {
		return this.children;
	}
	/**
	 * Set the child nodes of this {@link ContainerType} node.
	 * 
	 * @param children The {@link List} of child nodes to insert as children 
	 * of the XML node this object represents.
	 */
	public void setChildren(List<? extends NamedType> children) {
		this.children = children;
	}
}
