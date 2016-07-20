package com.woernerj.dragonsdogma.bo.types.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;

@XmlRootElement(name="string")
public class String extends NamedType {

	private java.lang.String value;
	
	@XmlAttribute
	public java.lang.String getValue() {
		return this.value;
	}
	public void setValue(java.lang.String value) {
		this.value = value;
	}
	
	@Override
	public java.lang.String getXmlString(boolean includeChildren) {
		if (StringUtils.isBlank(getName())) {
			return java.lang.String.format("<string value=\"\"/>", getValue());
		}
		return java.lang.String.format("<string name=\"\" value=\"\"/>", getName(), getValue());
	}
}