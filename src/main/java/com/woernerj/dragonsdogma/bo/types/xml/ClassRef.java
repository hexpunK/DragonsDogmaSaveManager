package com.woernerj.dragonsdogma.bo.types.xml;

public class ClassRef extends ContainerType<NamedType> {

	private String type;
	
	public String getType() {
		return this.type;
	}	
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public java.lang.String getXmlString() {
		StringBuffer sb = new StringBuffer();
		sb.append(java.lang.String.format("<classref type=\"%s\">\n", this.type));
		for (NamedType child : getChildren()) {
			sb.append(child.getXmlString());
			sb.append("\n");
		}
		sb.append("</classref>");		
		return sb.toString();
	}
}
