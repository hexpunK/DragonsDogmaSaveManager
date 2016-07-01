package com.woernerj.dragonsdogma.bo.types.xml;

public class Bool extends CastingType<Boolean, Boolean> {
	
	@Override
	public Boolean getMinValue() {
		return Boolean.FALSE;
	}	
	@Override
	public Boolean getMaxValue() {
		return Boolean.TRUE;
	}
}
