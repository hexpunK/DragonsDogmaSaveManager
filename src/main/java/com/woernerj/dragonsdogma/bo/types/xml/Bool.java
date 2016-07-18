package com.woernerj.dragonsdogma.bo.types.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="bool")
@XmlAccessorType(XmlAccessType.FIELD)
public class Bool extends NamedType {
	
	@XmlAttribute
	public Boolean value;
	
	@Override
	public java.lang.String getXmlString() {
		return java.lang.String.format("<bool name=\"%s\" value=\"%s\"/>", name, value);
	}
}
