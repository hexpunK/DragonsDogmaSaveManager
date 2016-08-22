package com.woernerj.dragonsdogma.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.Level;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.woernerj.dragonsdogma.bo.CompressionProgressCallback;
import com.woernerj.dragonsdogma.bo.DDSave;
import com.woernerj.dragonsdogma.bo.DDSaveHeader;
import com.woernerj.dragonsdogma.bo.SaveDataCallback;
import com.woernerj.dragonsdogma.bo.types.DDPlatform;
import com.woernerj.dragonsdogma.exception.CompressionExpcetion;
import com.woernerj.dragonsdogma.exception.SaveLoadException;

public class DDSaveLoader {
	
	private static final Integer[] CONSTANTS = new Integer[] {
			860693325, // What
			0, // Do
			860700740, // These
			1079398965 // Do??!?
	};
	
	private SaveDataCallback saveDataCallback = data -> { /* Do nothing */ };
	
	private CompressionProgressCallback compressionCallback = perc -> {
		saveDataCallback.progressChanged(Level.DEBUG, String.format("Opening save %.2f",	perc));
	};
	
	public void setSaveDataCallback(SaveDataCallback callback) {
		this.saveDataCallback = callback;
	}
	
	public void setCompressionCallback(CompressionProgressCallback callback) {
		this.compressionCallback = callback;
	}
	
	public DDSaveHeader loadHeader(InputStream saveStream) 
			throws SaveLoadException {
		return parseHeader(new DataInputStream(saveStream));
	}
	
	public void loadSave(InputStream saveStream) {
		saveDataCallback.loadStarted();
		DDSaveHeader header;
		try {
			header = parseHeader(new DataInputStream(saveStream));
		} catch (SaveLoadException e) {
			saveDataCallback.onError(e);
			return;
		}
		
		DDSave saveData = null;
		try (ByteArrayInputStream bytes = new ByteArrayInputStream(parseSave(header, saveStream).toByteArray())) {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(bytes);
			
			saveData = DDSave.build(header, document, saveDataCallback);
		} catch (ParserConfigurationException e) {
			saveDataCallback.onError(new SaveLoadException("Could not create XML parser", e));
			return;
		} catch (SAXException e) {
			saveDataCallback.onError(new SaveLoadException("Could not parse XML", e));
			return;
		} catch (IOException e) {
			saveDataCallback.onError(new SaveLoadException("Could not read save data", e));
			return;
		} catch (CompressionExpcetion e) {
			saveDataCallback.onError(new SaveLoadException("Failed to decompress save data", e));
			return;
		} catch (SaveLoadException e) {
			saveDataCallback.onError(e);
			return;
		}
		saveDataCallback.loadCompleted(saveData);
	}
	
	public String loadSaveAsXml(InputStream saveStream) 
			throws SaveLoadException {
		DDSaveHeader header = parseHeader(new DataInputStream(saveStream));
		try {
			return new String(parseSave(header, saveStream).toByteArray());
		} catch (CompressionExpcetion e) {
			throw new SaveLoadException("Failed to decompress save data", e);
		}
	}
	
	private DDSaveHeader parseHeader(DataInput saveStream) 
			throws SaveLoadException {
		ByteBuffer buffer = ByteBuffer.allocate(DDSaveHeader.HEADER_BYTES);
		byte[] data = new byte[DDSaveHeader.HEADER_BYTES];
		
		try {
			saveStream.readFully(data);
		} catch (IOException e) {
			throw new SaveLoadException("Could not read save data file", e);
		}
		
		byte[] testByte = new byte[] {
				data[15], data[14], data[13], data[12]
		};
		DDPlatform platform = DDPlatform.getPlatform(testByte);
		
		buffer.order(platform.endianness).put(data).flip();
		
		Integer version = buffer.getInt(0);
		Integer size = buffer.getInt(4);
		Integer compressedSize = buffer.getInt(8);
		
		final Integer[] headerConstants = new Integer[]{
			buffer.getInt(12),
			buffer.getInt(16),
			buffer.getInt(20),
			buffer.getInt(28)
		};
		
		Integer checksum = buffer.getInt(24);
		
		if (!Arrays.deepEquals(headerConstants, CONSTANTS)) {
			throw new SaveLoadException("Header constants did not match expected constants");
		}
		
		DDSaveHeader header = new DDSaveHeader(platform, version, size, compressedSize, checksum);
		return header;
	}
	
	private ByteArrayOutputStream parseSave(DDSaveHeader header, InputStream saveStream)
			throws CompressionExpcetion, SaveLoadException {
		if (header == null) {
			throw new SaveLoadException("A header must be provided to load save data");
		}
		
		byte[] compressedData = new byte[header.getCompressedSize()];
		Integer length = header.getCompressedSize();
		Integer readBytes = -1;
		try {
			readBytes = saveStream.read(compressedData, 0, length);
		} catch (IOException e) {
			throw new SaveLoadException("Error reading save data", e);
		}
		
		if (!readBytes.equals(header.getCompressedSize())) {
			throw new SaveLoadException("Decompressed save data was an unexpected size");
		}
		
		return CompressionUtils.decompress(compressedData, compressionCallback);
	}
}
