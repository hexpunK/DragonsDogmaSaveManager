package com.woernerj.dragonsdogma.bo.types.xml;

import javax.xml.bind.annotation.XmlAttribute;

public abstract class NamedType {

	@XmlAttribute
	public java.lang.String name;
	
	public abstract java.lang.String getXmlString();
	
	@Override
	public java.lang.String toString() {
		return getXmlString();
	}
}
