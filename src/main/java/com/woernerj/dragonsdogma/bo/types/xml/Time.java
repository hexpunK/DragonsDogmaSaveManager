package com.woernerj.dragonsdogma.bo.types.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="time")
public class Time extends NamedType {
	
	private Short year;
	private Byte month;
	private Byte day;
	private Byte hour;
	private Byte minute;
	private Byte second;
	
	@XmlAttribute
	public Short getYear() {
		return this.year;
	}
	@XmlAttribute
	public Byte getMonth() {
		return this.month;
	}
	@XmlAttribute
	public Byte getDay() {
		return this.day;
	}
	@XmlAttribute
	public Byte getHour() {
		return this.hour;
	}
	@XmlAttribute
	public Byte getMinute() {
		return this.minute;
	}
	@XmlAttribute
	public Byte getSecond() {
		return this.second;
	}
	public void setYear(Short year) {
		this.year = year;
	}
	public void setMonth(Byte month) {
		this.month = month;
	}
	public void setDay(Byte day) {
		this.day = day;
	}
	public void setHour(Byte hour) {
		this.hour = hour;
	}
	public void setMinute(Byte minute) {
		this.minute = minute;
	}
	public void setSecond(Byte second) {
		this.second = second;
	}

	@Override
	public java.lang.String getXmlString(boolean includeChildren) {
		return java.lang.String.format(
			"<time name=\"%s\" year=\"%s\" month=\"%s\" day=\"%s\" hour=\"%s\" minute=\"%s\" second=\"%s\"/>",
			getName(), getYear(), getMonth(), getDay(), getHour(), getMinute(), getSecond());
	}
}
