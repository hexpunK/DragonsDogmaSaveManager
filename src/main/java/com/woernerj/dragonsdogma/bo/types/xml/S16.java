package com.woernerj.dragonsdogma.bo.types.xml;

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