package com.woernerj.dragonsdogma.bo.types.xml;

public class Bool extends NamedType {
	
	private Boolean value;
	
	public Boolean getValue() {
		return this.value;
	}
	public void setValue(Boolean value) {
		this.value = value;
	}
	
	@Override
	public java.lang.String getXmlString() {
		return java.lang.String.format("<bool name=\"%s\" value=\"%s\" />", getName(), value);
	}
}
