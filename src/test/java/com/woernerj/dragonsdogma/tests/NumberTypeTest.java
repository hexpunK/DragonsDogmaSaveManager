package com.woernerj.dragonsdogma.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import javax.management.ReflectionException;

import org.junit.Test;

import com.woernerj.dragonsdogma.bo.types.xml.NumberType;
import com.woernerj.dragonsdogma.bo.types.xml.S8;
import com.woernerj.dragonsdogma.bo.types.xml.U16;
import com.woernerj.dragonsdogma.bo.types.xml.U32;
import com.woernerj.dragonsdogma.bo.types.xml.U64;
import com.woernerj.dragonsdogma.bo.types.xml.U8;

public class NumberTypeTest {

	@Test
	public void testCastingTypeSameTypes() {
		SameTestingType test = new SameTestingType();		
		try {
			assertTrue(test.getSameType());
		} catch (ReflectionException e) {
			fail(e.getMessage());
		}
		
		// Ensure that same typed values do not change value
		S8 test2 = new S8();
		test2.setValue((byte)1);
		assertEquals("'getValue' did not return same value", Byte.valueOf("1"), test2.getValue());
	}
	
	@Test
	public void testCastingTypeDifferentTypes() {
		DifferentTestingType test = new DifferentTestingType();
		try {
			assertFalse(test.getSameType());
		} catch (ReflectionException e) {
			fail(e.getMessage());
		}
		
		// Check to see if a non-overridden getValue() fails
		test.setValue((short)1);
		assertNull("Improperly setup type did not return null", test.getValue());
	}
	
	@Test
	public void testUnsignedByte() {
		testCastingTypeInstance(new U8(), (short)0, (short)255, (short)-1);
	}

	@Test
	public void testUnsignedShort() {
		testCastingTypeInstance(new U16(), 0, 65535, -1);
	}

	@Test
	public void testUnsignedInteger() throws Exception {
		testCastingTypeInstance(new U32(), 0L, 4294967295L, -1L);
	}

	@Test
	public void testUnsignedLong() {
		testCastingTypeInstance(new U64(), BigDecimal.ZERO, new BigDecimal("18446744073709551615"), BigDecimal.valueOf(-1L));
	}
	
	private <T extends Number, R extends Number> void testCastingTypeInstance(NumberType<T, R> instance, R min, R max, R negative) {
		instance.setValue(min);
		assertEquals("Value did not return zero", instance.getMinValue(), instance.getValue());
		
		instance.setValue(max);
		assertEquals("Properly setup type did not return unsigned value", instance.getMaxValue(), instance.getValue());
		
		instance.setValue(negative);
		assertTrue("Value was less than zero", instance.getValue().doubleValue() > 0);
		assertEquals("Properly setup type did not return unsigned value", instance.getMaxValue(), instance.getValue());
	}
	
	private static class TestingType<T extends Number, R extends Number> extends NumberType<T, R> {	
		
		public boolean getSameType() throws ReflectionException {
			try {
				Method m = NumberType.class.getDeclaredMethod("isSameType");
				m.setAccessible(true);
				return (boolean)m.invoke(this);
			} catch (SecurityException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
				throw new ReflectionException(e, "Could not find 'isSameType' field");
			}
		}
		
		@Override
		public R getMinValue() { return null; }
		
		@Override
		public R getMaxValue() { return null; }
	}
	
	private static class SameTestingType extends TestingType<Byte, Byte> {}
	
	private static class DifferentTestingType extends TestingType<Byte, Short> {}
}