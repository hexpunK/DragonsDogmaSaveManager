package com.woernerj.dragonsdogma.bo.types.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="class")
public class Class extends ContainerType<NamedType> {

	private java.lang.String type;
	
	@XmlAttribute
	public java.lang.String getType() {
		return this.type;
	}
	public void setType(java.lang.String type) {
		this.type = type;
	}
	
	@Override
	public java.lang.String getXmlString(boolean includeChildren) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(java.lang.String.format("<class name=\"%s\" type=\"%s\">\n", getName(), getType()));
		if (includeChildren) {
			for (NamedType child : getChildren()) {
				sb.append(child.getXmlString()).append("\n");
			}
		}
		sb.append("</class>");
		
		return sb.toString();
	}
}
