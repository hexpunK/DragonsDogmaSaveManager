package com.woernerj.dragonsdogma.bo.types.xml;

import com.woernerj.dragonsdogma.util.TypeGrabber;

public class Array extends ContainerType {

	private java.lang.String type;
	private Integer count;
	
	public java.lang.String getType() {
		return type;
	}
	public Integer getCount() {
		return count;
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
		
		sb.append(java.lang.String.format("<array name=\"%s\" count=\"%s\">\n", getName(), count));
		for (int i = 0; i < count; i++) {
			if (i < getChildren().size()) {
				sb.append(getChildren().get(i).getXmlString());
			} else {
				sb.append(TypeGrabber.getInstanceForName(type).getXmlString());
			}
			sb.append("\n");
		}
		sb.append("</array>");
		
		return sb.toString();
	}
}
