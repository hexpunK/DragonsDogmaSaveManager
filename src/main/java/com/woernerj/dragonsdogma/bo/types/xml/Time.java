package com.woernerj.dragonsdogma.bo.types.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="time")
@XmlAccessorType(XmlAccessType.FIELD)
public class Time extends NamedType {
	
	@XmlAttribute
	public Short year;
	@XmlAttribute
	public Byte month;
	@XmlAttribute
	public Byte day;
	@XmlAttribute
	public Byte hour;
	@XmlAttribute
	public Byte minute;
	@XmlAttribute
	public Byte second;
	
	@Override
	public java.lang.String getXmlString() {
		return java.lang.String.format(
			"<time name=\"%s\" year=\"%s\" month=\"%s\" day=\"%s\" hour=\"%s\" minute=\"%s\" second=\"%s\"/>",
			name, year, month, day, hour, minute, second);
	}
}
