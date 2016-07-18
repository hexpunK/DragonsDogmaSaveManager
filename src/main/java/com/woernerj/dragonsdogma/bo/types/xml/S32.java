package com.woernerj.dragonsdogma.bo.types.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="s32")
@XmlAccessorType(XmlAccessType.FIELD)
public class S32 extends NumberType<Integer, Integer> {

	@Override
	public Integer getMinValue() {
		return Integer.MIN_VALUE;
	}
	@Override
	public Integer getMaxValue() {
		return Integer.MAX_VALUE;
	}
}