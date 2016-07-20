package com.woernerj.dragonsdogma.bo.types.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="bool")
public class Bool extends NamedType {
	
	private Boolean value;
	
	@XmlAttribute
	public Boolean getValue() {
		return this.value;
	}
	public void setValue(Boolean value) {
		this.value = value;
	}
	
	@Override
	public java.lang.String getXmlString(boolean includeChildren) {
		return java.lang.String.format("<bool name=\"%s\" value=\"%s\"/>", getName(), getValue());
	}
}
