package com.woernerj.dragonsdogma.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Optional;

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
import com.woernerj.dragonsdogma.exception.SaveLoadException;

public class DDSaveLoader {
	
	private static final Integer[] CONSTANTS = new Integer[] {
			860693325, // What
			0, // Do
			860700740, // These
			1079398965 // Do??!?
	};	
	private static DocumentBuilder DocumentBuilder;
	
	private SaveDataCallback saveDataCallback = data -> { /* Do nothing */ };	
	private CompressionProgressCallback compressionCallback = perc -> {
		saveDataCallback.progressChanged(Level.DEBUG, String.format("Opening save %.2f", perc));
	};
	
	public void setSaveDataCallback(SaveDataCallback callback) {
		this.saveDataCallback = callback;
	}
	
	public void setCompressionCallback(CompressionProgressCallback callback) {
		this.compressionCallback = callback;
	}
	
	public Optional<DDSaveHeader> loadHeader(InputStream saveStream) {
		return parseHeader(new DataInputStream(saveStream));
	}
	
	public void loadSave(InputStream saveStream) {
		try (DataInputStream strm = new DataInputStream(saveStream)) {
			parseHeader(strm).ifPresent(header -> {
				try (ByteArrayInputStream bytes = new ByteArrayInputStream(parseSave(header, saveStream).toByteArray())) {
					Document document = getDocumentBuilder().parse(bytes);
					
					DDSave.build(header, document, saveDataCallback);
				} catch (SAXException e) {
					saveDataCallback.error(new SaveLoadException("Could not parse XML", e));
				} catch (IOException e) {
					saveDataCallback.error(new SaveLoadException("Could not read save data", e));
				} catch (SaveLoadException e) {
					saveDataCallback.error(new SaveLoadException(e.getMessage(), e.getCause()));
				}
			});
		} catch (IOException e) {
			saveDataCallback.error(new SaveLoadException("Could not close file"));
		};
	}
	
	public Optional<String> loadSaveAsXml(InputStream saveStream) 
			throws SaveLoadException {
		Optional<String> result = Optional.empty();
		try (DataInputStream strm = new DataInputStream(saveStream)) {
			result = parseHeader(strm).flatMap(header -> Optional.ofNullable(new String(parseSave(header, saveStream).toByteArray())) );
		} catch (IOException e) {
			throw new SaveLoadException("Could not close file", e);
		}
		return result;
	}
	
	private Optional<DDSaveHeader> parseHeader(DataInput saveStream) {
		ByteBuffer buffer = ByteBuffer.allocate(DDSaveHeader.HEADER_BYTES);
		byte[] data = new byte[DDSaveHeader.HEADER_BYTES];
		
		try {
			saveStream.readFully(data);
		} catch (IOException e) {
			saveDataCallback.error(new SaveLoadException("Could not read save data file", e));
			return Optional.empty();
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
			saveDataCallback.error(new SaveLoadException("Header constants did not match expected constants"));
			return Optional.empty();
		}
		
		DDSaveHeader header = new DDSaveHeader(platform, version, size, compressedSize, checksum);
		return Optional.ofNullable(header);
	}
	
	private ByteArrayOutputStream parseSave(DDSaveHeader header, InputStream saveStream) {
		if (header == null) {
			saveDataCallback.error(new SaveLoadException("A header must be provided to load save data"));
		}
		
		byte[] compressedData = new byte[header.getCompressedSize()];
		Integer length = header.getCompressedSize();
		Integer readBytes = -1;
		try {
			readBytes = saveStream.read(compressedData, 0, length);
		} catch (IOException e) {
			saveDataCallback.error(new SaveLoadException("Error reading save data", e));
		}
		
		if (!readBytes.equals(header.getCompressedSize())) {
			saveDataCallback.error(new SaveLoadException("Decompressed save data was an unexpected size"));
		}
		
		return CompressionUtils.decompress(compressedData, compressionCallback);
	}
	
	private DocumentBuilder getDocumentBuilder() throws SaveLoadException {
		if (DDSaveLoader.DocumentBuilder == null) {
			synchronized(DDSaveLoader.class) {
				if (DDSaveLoader.DocumentBuilder == null) {
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					try {
						DDSaveLoader.DocumentBuilder = factory.newDocumentBuilder();
					} catch (ParserConfigurationException e) {
						throw new SaveLoadException("Could not create XML parser", e);
					}
				}
			}
		}
		
		return DDSaveLoader.DocumentBuilder;
	}
}
