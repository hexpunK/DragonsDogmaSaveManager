package com.woernerj.dragonsdogma.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.zip.DataFormatException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.woernerj.dragonsdogma.bo.DDSaveHeader;
import com.woernerj.dragonsdogma.bo.DDVersion;
import com.woernerj.dragonsdogma.util.DDSaveLoader;

public class DDSaveLoaderTests {

	@Test
	public void testLoadHeader() throws NoSuchMethodException, SecurityException, URISyntaxException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, DataFormatException, SAXException, ParserConfigurationException, XMLStreamException {
		Method method = DDSaveLoader.class.getDeclaredMethod("parseHeader", DataInput.class);
		method.setAccessible(true);
		
		File file = new File(this.getClass().getClassLoader().getResource("ddda.sav").getFile());
		FileInputStream input = new FileInputStream(file);
		
		DDSaveHeader result = (DDSaveHeader)method.invoke(new DDSaveLoader(), new DataInputStream(input));
		
		assertNotNull(result);
		assertEquals(DDVersion.DDDA, result.getDDVersion());
	}
}
