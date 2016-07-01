package com.woernerj.dragonsdogma.bo.types.xml;

public class U16 extends NumberType<Short, Integer>{

	@Override
	public Integer getValue() {
		return (this.value & 0x0000FFFF); 
	}
	@Override
	public void setValue(Integer value) {
		this.value = (short)(value & 0xFFFF);
	}
	@Override
	public Integer getMinValue() {
		return 0;
	}
	@Override
	public Integer getMaxValue() {
		return 65535;
	}
}