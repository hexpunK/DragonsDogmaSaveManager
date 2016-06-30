package com.woernerj.dragonsdogma.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.management.ReflectionException;

import org.junit.Test;

import com.woernerj.dragonsdogma.bo.types.CastingType;
import com.woernerj.dragonsdogma.bo.types.S8;
import com.woernerj.dragonsdogma.bo.types.U8;

public class CastingTypeTest {

	@Test
	public void testCastingTypeSameTypes() throws ReflectionException {
		SameTestingType test = new SameTestingType();		
		assertTrue(test.getSameType());
		
		// Ensure that same typed values do not change value
		S8 test2 = new S8();
		test2.setValue(Byte.valueOf("1"));
		assertEquals(Byte.valueOf("1"), test2.getValue());
	}
	
	@Test
	public void testCastingTypeDifferentTypes() throws ReflectionException {
		DifferentTestingType test = new DifferentTestingType();
		assertFalse(test.getSameType());
		
		// Check to see if a non-overriden getValue() fails
		test.setValue(Byte.valueOf("1"));
		assertNull(test.getValue());
		
		// Ensure we can cast "unsigned" values to and from their storage type
		U8 test2 = new U8();
		test2.setValue((short)(Byte.MAX_VALUE+1));
		assertEquals(Short.valueOf((short)(Byte.MAX_VALUE+1)), test2.getValue());
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
	}
	
	private static class SameTestingType extends TestingType<Boolean, Boolean> {}
	
	private static class DifferentTestingType extends TestingType<Boolean, Byte> {}
}