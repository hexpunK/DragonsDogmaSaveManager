package com.woernerj.dragonsdogma.bo.types.xml;

import java.math.BigDecimal;

public class U64 extends CastingType<Long, BigDecimal> {
	
	@Override
	public BigDecimal getValue() {
		return new BigDecimal(Long.toUnsignedString(this.value));
	}
	@Override
	public void setValue(BigDecimal value) {
		java.lang.String s = Long.toUnsignedString(value.longValue() & 0xFFFFFFFFFFFFFFFFL);
		this.value = Long.parseUnsignedLong(s);
	}
	@Override
	public BigDecimal getMinValue() {
		return BigDecimal.ZERO;
	}
	@Override
	public BigDecimal getMaxValue() {
		return new BigDecimal("18446744073709551615");
	}
}