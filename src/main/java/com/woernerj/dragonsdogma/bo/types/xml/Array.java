package com.woernerj.dragonsdogma.bo.types.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.woernerj.dragonsdogma.util.TypeGrabber;

@XmlRootElement(name = "array")
@XmlAccessorType(XmlAccessType.FIELD)
public class Array<T extends NamedType> extends ContainerType<T> {

	@XmlAttribute
	public java.lang.String type;
	@XmlAttribute
	public Integer count;
	
	public Array() { }
	
	public Array(java.lang.Class<T> clazz) {	
		this.type = clazz.getSimpleName().toLowerCase();
	}
	
	@Override
	public java.lang.String getXmlString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(java.lang.String.format("<array name=\"%s\" count=\"%s\" type=\"%s\">\n", name, count, type));
		for (int i = 0; i < count; i++) {
			if (i < children.size()) {
				sb.append(children.get(i).getXmlString());
			} else {
				sb.append(TypeGrabber.getInstanceForName(type).getXmlString());
			}
			sb.append("\n");
		}
		sb.append("</array>");
		
		return sb.toString();
	}
}
