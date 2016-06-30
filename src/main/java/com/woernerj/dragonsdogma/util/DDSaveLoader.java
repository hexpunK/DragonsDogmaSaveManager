package com.woernerj.dragonsdogma.util;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

import com.woernerj.dragonsdogma.bo.DDSave;
import com.woernerj.dragonsdogma.bo.DDSaveHeader;

public class DDSaveLoader {

	private static final Integer[] CONSTANTS = new Integer[] {
			860693325,
			0,
			860700740,
			1079398965
	};
	
	public DDSaveHeader loadHeader(InputStream saveStream) {
		return parseHeader(new DataInputStream(saveStream));
	}
	
	public DDSave loadSave(InputStream saveStream) {
		DDSaveHeader header = parseHeader(new DataInputStream(saveStream));
		parseSave(header, saveStream);
		return new DDSave();
	}
	
	public String loadSaveAsXml(InputStream saveStream) {
		DDSaveHeader header = parseHeader(new DataInputStream(saveStream));
		return new String(parseSave(header, saveStream));
	}
	
	private DDSaveHeader parseHeader(DataInput saveStream) {
		ByteBuffer buffer = ByteBuffer.allocate(DDSaveHeader.HEADER_BYTES);
		byte[] data = new byte[DDSaveHeader.HEADER_BYTES];
		
		try {
			saveStream.readFully(data);
		} catch (IOException e) {
			return null;
		}
		
		buffer.order(DDSaveHeader.ENDIANNESS).put(data).flip();
		
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
			return null;
		}
		
		DDSaveHeader header = new DDSaveHeader(version, size, compressedSize, checksum);
		return header;
	}
	
	private byte[] parseSave(DDSaveHeader header, InputStream saveStream) {
		byte[] compressedData = new byte[header.getCompressedSize()];
		Integer length = header.getCompressedSize();
		Integer readBytes = -1;
		try {
			readBytes = saveStream.read(compressedData, 0, length);
		} catch (IOException e) {
			return null;
		}
		
		if (readBytes < 0) return null;
		
		return CompressionUtils.decompress(compressedData);
	}
}
