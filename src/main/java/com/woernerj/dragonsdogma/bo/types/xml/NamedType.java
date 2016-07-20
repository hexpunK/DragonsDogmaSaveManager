package com.woernerj.dragonsdogma.bo.types.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({ContainerType.class, NumberType.class, String.class, Bool.class, Time.class, Vector3.class})
public abstract class NamedType {

	private java.lang.String name;

	/**
	 * Get the <code>name</code> attribute of this XML node.
	 * 
	 * @return The value stored in the <code>name</code> attribute 
	 * of this XML node as a {@link java.lang.String}.
	 */
	@XmlAttribute
	public java.lang.String getName() {
		return this.name;
	}
	/**
	 * Set the value of the <code>name</code> attribute of this XML node.
	 * 
	 * @param name The new value for the <code>name</code> attribute as a 
	 * {@link java.lang.String}.
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	/**
	 * Gets the XML representation of this object.
	 * 
	 * @return A {@link java.lang.String} representation of the XML form 
	 * for this {@link NamedType}.
	 */
	public abstract java.lang.String getXmlString(boolean includeChildren);
	
	public java.lang.String getXmlString() {
		return getXmlString(false);
	}
	
	@Override
	public java.lang.String toString() {
		return getXmlString();
	}
}
