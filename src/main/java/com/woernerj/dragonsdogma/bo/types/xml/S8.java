package com.woernerj.dragonsdogma.bo.types.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="s8")
@XmlAccessorType(XmlAccessType.FIELD)
public class S8 extends NumberType<Byte, Byte> {

	@Override
	public Byte getMinValue() {
		return Byte.MIN_VALUE;
	}
	@Override
	public Byte getMaxValue() {
		return Byte.MAX_VALUE;
	}
}