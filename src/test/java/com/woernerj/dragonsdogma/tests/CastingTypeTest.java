package com.woernerj.dragonsdogma.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import javax.management.ReflectionException;

import org.junit.Test;

import com.woernerj.dragonsdogma.bo.types.xml.CastingType;
import com.woernerj.dragonsdogma.bo.types.xml.S8;
import com.woernerj.dragonsdogma.bo.types.xml.U16;
import com.woernerj.dragonsdogma.bo.types.xml.U32;
import com.woernerj.dragonsdogma.bo.types.xml.U64;
import com.woernerj.dragonsdogma.bo.types.xml.U8;

public class CastingTypeTest {

	@Test
	public void testCastingTypeSameTypes() throws ReflectionException {
		SameTestingType test = new SameTestingType();		
		assertTrue(test.getSameType());
		
		// Ensure that same typed values do not change value
		S8 test2 = new S8();
		test2.setValue(Byte.valueOf("1"));
		assertEquals("'getValue' did not return same value", Byte.valueOf("1"), test2.getValue());
	}
	
	@Test
	public void testCastingTypeDifferentTypes() throws ReflectionException {
		DifferentTestingType test = new DifferentTestingType();
		assertFalse(test.getSameType());
		
		// Check to see if a non-overridden getValue() fails
		test.setValue(Byte.valueOf("1"));
		assertNull("Improperly setup type did not return null", test.getValue());
	}
	
	@Test
	public void testUnsignedByte() {
		U8 test = new U8();
		
		test.setValue((short)0);
		assertEquals("Value did not return zero", test.getMinValue(), test.getValue());
		
		test.setValue((short)255);
		assertEquals("Properly setup type did not return unsigned value", test.getMaxValue(), test.getValue());
		
		test.setValue((short)-1);
		assertTrue("Value was less than zero", test.getValue() > 0);
		assertEquals("Properly setup type did not return unsigned value", test.getMaxValue(), test.getValue());
	}

	@Test
	public void testUnsignedShort() {
		U16 test = new U16();
		
		test.setValue(0);
		assertEquals("Value did not return zero", test.getMinValue(), test.getValue());
		
		test.setValue(65535);
		assertEquals("Properly setup type did not return unsigned value", test.getMaxValue(), test.getValue());
		
		test.setValue((int)-1);
		assertTrue("Value was less than zero", test.getValue() > 0);
		assertEquals("Properly setup type did not return unsigned value", test.getMaxValue(), test.getValue());
	}

	@Test
	public void testUnsignedInteger() {
		U32 test = new U32();
		
		test.setValue(0L);
		assertEquals("Value did not return zero", test.getMinValue(), test.getValue());
		
		test.setValue(4294967295L);
		assertEquals("Properly setup type did not return unsigned value", test.getMaxValue(), test.getValue());
		
		test.setValue(-1L);
		assertTrue("Value was less than zero", test.getValue() > 0);
		assertEquals("Properly setup type did not return unsigned value", test.getMaxValue(), test.getValue());
	}

	@Test
	public void testUnsignedLong() {
		U64 test = new U64();
		
		test.setValue(BigDecimal.ZERO);
		assertEquals("Value did not return zero", test.getMinValue(), test.getValue());
		
		test.setValue(new BigDecimal("18446744073709551615"));
		assertEquals("Properly setup type did not return unsigned value", test.getMaxValue(), test.getValue());
		
		test.setValue(BigDecimal.valueOf(-1L));
		assertTrue("Value was less than zero", test.getValue().compareTo(BigDecimal.ZERO) > 0);
		assertEquals("Properly setup type did not return unsigned value", test.getMaxValue(), test.getValue());
	}
	
	private static class TestingType<T, R> extends CastingType<T, R> {	
		
		public boolean getSameType() throws ReflectionException {
			try {
				Method m = CastingType.class.getDeclaredMethod("isSameType");
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
	
	private static class SameTestingType extends TestingType<Boolean, Boolean> {}
	
	private static class DifferentTestingType extends TestingType<Boolean, Byte> {}
}