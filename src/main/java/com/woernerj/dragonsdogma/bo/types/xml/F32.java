package com.woernerj.dragonsdogma.bo.types.xml;

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
		return java.lang.String.format("<f32 name=\"%s\" value=\"%.6f\" />", getName(), getValue());
	}
}
