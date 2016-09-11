package com.woernerj.dragonsdogma.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import com.woernerj.dragonsdogma.bo.DDSave;
import com.woernerj.dragonsdogma.bo.DDSaveHeader;
import com.woernerj.dragonsdogma.bo.DDVersion;
import com.woernerj.dragonsdogma.exception.SaveLoadException;
import com.woernerj.dragonsdogma.util.DDSaveLoader;

public class DDSaveLoaderTests {
	
	private DDSaveLoader saveLoader;
	private DDSave saveData;
	
	@Before
	public void setup() {
		saveLoader = new DDSaveLoader();
		saveLoader.setSaveDataCallback(data -> saveData = data );
	}
	
	@Test
	public void testLoadHeader() throws FileNotFoundException, IOException, SaveLoadException  {
		URL fileLoc = this.getClass().getClassLoader().getResource("ddda.sav");
		if (fileLoc == null) fail("Could not get file location");
		
		try (FileInputStream file = new FileInputStream(fileLoc.getFile())) {
			DDSaveHeader result = saveLoader.loadHeader(file).orElseGet(null);

			assertNotNull("No header returned", result);
			assertEquals("Header version was not DD:DA", DDVersion.DDDA, result.getDDVersion());
		}
	}
	
	@Test
	public void testLoadSave() throws FileNotFoundException, IOException, SaveLoadException {
		URL fileLoc = this.getClass().getClassLoader().getResource("ddda.sav");
		if (fileLoc == null) fail("Could not get file location");
		
		try (FileInputStream file = new FileInputStream(fileLoc.getFile())) {
			saveLoader.loadSave(file);

			assertNotNull("No save data returned", saveData);
			assertNotNull("No header returned", saveData.getHeader());
			assertEquals("Header version was not DD:DA", DDVersion.DDDA, saveData.getHeader().getDDVersion());
		}
	}
	
	@Test
	public void testLoadAsXml() throws SaveLoadException, IOException {
		URL fileLoc = this.getClass().getClassLoader().getResource("ddda.sav");
		if (fileLoc == null) fail("Could not get file location");
		File file = new File(fileLoc.getFile());
		
		String result = null;
		try (FileInputStream input = new FileInputStream(file)) {
			result = saveLoader.loadSaveAsXml(input).orElseGet(null);
		}
		
		assertNotNull(result);
		File output = new File("output.xml");
		try (PrintWriter printer = new PrintWriter(output)) {
			printer.write(result);
		}
	}
}
