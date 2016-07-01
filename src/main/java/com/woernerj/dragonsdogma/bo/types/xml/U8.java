package com.woernerj.dragonsdogma.bo.types.xml;

public class U8 extends NumberType<Byte, Short> {
	
	@Override
	public Short getValue() {
		if (this.value == null) return null;
		return (short)(this.value & 0x00FF); 
	}
	@Override
	public void setValue(Short value) {
		this.value = (byte)(value & 0xFF);
	}
	@Override
	public Short getMinValue() {
		return 0;
	}
	@Override
	public Short getMaxValue() {
		return 255;
	}
}