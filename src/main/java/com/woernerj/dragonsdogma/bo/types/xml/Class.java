package com.woernerj.dragonsdogma.bo.types.xml;

public class Class extends ContainerType<NamedType> {

	private String type;
	
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public java.lang.String getXmlString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(java.lang.String.format("<class name=\"%s\" type=\"%s\">", type));
		for (NamedType child : getChildren()) {
			sb.append(child.getXmlString());
		}
		sb.append("</class>");
		
		return sb.toString();
	}
}
