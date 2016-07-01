package com.woernerj.dragonsdogma;

import java.nio.ByteOrder;

public enum DDPlatform {

	PC(ByteOrder.LITTLE_ENDIAN),
	PS3(ByteOrder.LITTLE_ENDIAN),
	XBOX360(ByteOrder.BIG_ENDIAN)
	;
	
	public final ByteOrder endianness;
	private DDPlatform(ByteOrder endianness) {
		this.endianness = endianness;
	}
	
	public static DDPlatform getPlatform(byte[] testByte) {
		final int TEST_CONSTANT = 860693325;
		int value = testByte[3] & 0xFF |
	            (testByte[2] & 0xFF) << 8 |
	            (testByte[1] & 0xFF) << 16 |
	            (testByte[0] & 0xFF) << 24;
		
		if (value == TEST_CONSTANT) {
			return DDPlatform.PC;
		} else {
			return DDPlatform.XBOX360;
		}
	}
}
