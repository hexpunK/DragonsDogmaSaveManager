package com.woernerj.dragonsdogma.bo.types.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="vector3")
@XmlAccessorType(XmlAccessType.FIELD)
public class Vector3 extends NamedType {

	@XmlAttribute
	public Long x;
	@XmlAttribute
	public Long y;
	@XmlAttribute
	public Long z;
	
	@Override
	public java.lang.String getXmlString() {
		return java.lang.String.format(
			"<vector3 name=\"%s\" x=\"%s\" y=\"%s\" z=\"%s\"/>",
			name, this.x, this.y, this.z);
	}
}
