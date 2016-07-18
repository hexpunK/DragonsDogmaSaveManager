package com.woernerj.dragonsdogma.bo.types.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="f32")
@XmlAccessorType(XmlAccessType.FIELD)
public class F32 extends NumberType<Float, Float> {

	@Override
	public Float getMinValue() {
		return Float.MIN_VALUE;
	}
	@Override
	public Float getMaxValue() {
		return Float.MAX_VALUE;
	}
	
	@Override
	public java.lang.String getXmlString() {
		return java.lang.String.format("<f32 name=\"%s\" value=\"%.6f\"/>", name, getValue());
	}
}
