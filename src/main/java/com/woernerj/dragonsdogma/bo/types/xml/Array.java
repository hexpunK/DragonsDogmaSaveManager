package com.woernerj.dragonsdogma.bo.types.xml;

import com.woernerj.dragonsdogma.util.TypeGrabber;

public class Array<T extends NamedType> extends ContainerType<T> {

	private java.lang.String type;
	private Integer count;
	
	public Array() { }
	
	public Array(java.lang.Class<T> clazz) {	
		this.type = clazz.getSimpleName().toLowerCase();
	}
	
	public java.lang.String getType() {
		return this.type;
	}
	public Integer getCount() {
		return this.count;
	}
	public void setType(java.lang.String type) {
		this.type = type;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	@Override
	public java.lang.String getXmlString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(java.lang.String.format("<array name=\"%s\" count=\"%s\" type=\"%s\">\n", getName(), count, getType()));
		for (int i = 0; i < count; i++) {
			if (i < getChildren().size()) {
				sb.append(getChildren().get(i).getXmlString());
			} else {
				sb.append(TypeGrabber.getInstanceForName(getType()).getXmlString());
			}
			sb.append("\n");
		}
		sb.append("</array>");
		
		return sb.toString();
	}
}
