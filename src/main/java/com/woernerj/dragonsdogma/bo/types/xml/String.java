package com.woernerj.dragonsdogma.bo.types.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;

@XmlRootElement(name="string")
@XmlAccessorType(XmlAccessType.FIELD)
public class String extends NamedType {

	@XmlAttribute
	private java.lang.String value;
	
	@Override
	public java.lang.String getXmlString() {
		if (StringUtils.isBlank(name)) {
			return java.lang.String.format("<string value=\"\"/>", this.value);
		}
		return java.lang.String.format("<string name=\"\" value=\"\"/>", name, this.value);
	}
}