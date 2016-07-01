package com.woernerj.dragonsdogma.bo.types.xml;

import org.apache.commons.lang3.StringUtils;

public class String extends NamedType {

	private String value;
	
	public String getValue() {
		return this.value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public java.lang.String getXmlString() {
		if (StringUtils.isBlank(getName())) {
			return java.lang.String.format("<string value=\"\"/>", this.value);
		}
		return java.lang.String.format("<string name=\"\" value=\"\"/>", getName(), this.value);
	}
}