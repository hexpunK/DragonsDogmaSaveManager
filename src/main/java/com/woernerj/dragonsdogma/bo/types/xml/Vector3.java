package com.woernerj.dragonsdogma.bo.types.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="vector3")
public class Vector3 extends NamedType {

	private Double x;
	private Double y;
	private Double z;
	
	@XmlAttribute
	public Double getX() {
		return this.x;
	}
	@XmlAttribute
	public Double getY() {
		return this.y;
	}
	@XmlAttribute
	public Double getZ() {
		return this.z;
	}
	public void setX(Double x) {
		this.x = x;
	}
	public void setY(Double y) {
		this.y = y;
	}
	public void setZ(Double z) {
		this.z = z;
	}
	
	@Override
	public java.lang.String getXmlString(boolean includeChildren) {
		return java.lang.String.format(
			"<vector3 name=\"%s\" x=\"%s\" y=\"%s\" z=\"%s\"/>",
			getName(), getX(), getY(), getZ());
	}
}
