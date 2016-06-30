package com.woernerj.dragonsdogma.bo.types;

public class U8 extends CastingType<Byte, Short> {
	
	@Override
	public Short getValue() {
		return (short)(this.value & 0x00FF); 
	}
	@Override
	public void setValue(Short value) {
		this.value = (byte)(value & 0xFF);
	}
}