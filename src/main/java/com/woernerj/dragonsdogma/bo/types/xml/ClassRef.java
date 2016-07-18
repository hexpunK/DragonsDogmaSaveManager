package com.woernerj.dragonsdogma.bo.types.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="classref")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClassRef extends ContainerType<NamedType> {

	@XmlAttribute
	public java.lang.String type;
	
	@Override
	public java.lang.String getXmlString() {
		StringBuffer sb = new StringBuffer();
		sb.append(java.lang.String.format("<classref type=\"%s\">\n", this.type));
		for (NamedType child : children) {
			sb.append(child.getXmlString());
			sb.append("\n");
		}
		sb.append("</classref>");
		return sb.toString();
	}
}
