package com.woernerj.dragonsdogma.bo.types.xml;

public class Vector3 extends NamedType {

	private Long x;
	private Long y;
	private Long z;
	
	public Long getX() {
		return x;
	}
	public Long getY() {
		return y;
	}
	public Long getZ() {
		return z;
	}
	public void setX(Long x) {
		this.x = x;
	}
	public void setY(Long y) {
		this.y = y;
	}
	public void setZ(Long z) {
		this.z = z;
	}
	
	@Override
	public java.lang.String getXmlString() {
		return java.lang.String.format(
			"<vector3 name=\"%s\" x=\"%s\" y=\"%s\" z=\"%s\"/>",
			getName(), this.x, this.y, this.z);
	}
}
