package com.woernerj.dragonsdogma.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

import org.junit.Test;

import com.woernerj.dragonsdogma.bo.DDSaveHeader;
import com.woernerj.dragonsdogma.bo.DDVersion;
import com.woernerj.dragonsdogma.util.DDSaveLoader;

public class DDSaveLoaderTests {

	@Test
	public void testLoadHeader()  {
		Method method;
		try {
			method = DDSaveLoader.class.getDeclaredMethod("parseHeader", DataInput.class);
		} catch (NoSuchMethodException | SecurityException e) {
			fail("Could not find method 'parseHeader'");
			return;
		}
		method.setAccessible(true);

		URL fileLoc = this.getClass().getClassLoader().getResource("ddda.sav");
		if (fileLoc == null) fail("Could not get file location");
		
		File file = new File(fileLoc.getFile());
		
		DDSaveHeader result = null;
		try (FileInputStream input = new FileInputStream(file)) {
			result = (DDSaveHeader) method.invoke(new DDSaveLoader(), new DataInputStream(input));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
			fail(String.format("Could not invoke 'parseHeader'\nReason: %s", e1.getMessage()));
			return;
		} catch (IOException e2) {
			fail(String.format("Could not read save data\nReason: %s", e2.getMessage()));
			return;
		}

		assertNotNull("No header returned", result);
		assertEquals("Header version was not DD:DA", DDVersion.DDDA, result.getDDVersion());
	}
}
