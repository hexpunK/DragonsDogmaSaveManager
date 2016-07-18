package com.woernerj.dragonsdogma.bo.types.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="s16")
@XmlAccessorType(XmlAccessType.FIELD)
public class S16 extends NumberType<Short, Short> {

	@Override
	public Short getMinValue() {
		return Short.MIN_VALUE;
	}
	@Override
	public Short getMaxValue() {
		return Short.MAX_VALUE;
	}
}