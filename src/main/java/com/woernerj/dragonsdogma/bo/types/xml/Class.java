package com.woernerj.dragonsdogma.bo.types.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="class")
@XmlAccessorType(XmlAccessType.FIELD)
public class Class extends ContainerType<NamedType> {

	@XmlAttribute
	public java.lang.String type;
	
	@Override
	public java.lang.String getXmlString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(java.lang.String.format("<class name=\"%s\" type=\"%s\">", name, type));
		for (NamedType child : children) {
			sb.append(child.getXmlString());
		}
		sb.append("</class>");
		
		return sb.toString();
	}
}
