package com.woernerj.dragonsdogma.bo.types;

public class Time extends NamedType {

	private Short year;
	private Byte month;
	private Byte day;
	private Byte hour;
	private Byte minute;
	private Byte second;
	
	public Short getYear() {
		return year;
	}
	public Byte getMonth() {
		return month;
	}
	public Byte getDay() {
		return day;
	}
	public Byte getHour() {
		return hour;
	}
	public Byte getMinute() {
		return minute;
	}
	public Byte getSecond() {
		return second;
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
}
