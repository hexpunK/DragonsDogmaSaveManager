package com.woernerj.dragonsdogma.util;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.zip.DataFormatException;

import com.woernerj.dragonsdogma.bo.DDSave;
import com.woernerj.dragonsdogma.bo.DDSaveHeader;

public class DDSaveLoader {

	private static final Integer[] CONSTANTS = new Integer[] {
			860693325,
			0,
			860700740,
			1079398965
	};
	
	public DDSave load(InputStream saveStream) throws IOException, DataFormatException {
		DDSaveHeader header = parseHeader(new DataInputStream(saveStream));
		DDSave save = parseSave(header, saveStream);
		return save;
	}
	
	private DDSaveHeader parseHeader(DataInput saveStream) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(DDSaveHeader.HEADER_BYTES);
		byte[] data = new byte[DDSaveHeader.HEADER_BYTES];
		
		saveStream.readFully(data);
		
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
	
	private DDSave parseSave(DDSaveHeader header, InputStream saveStream) throws IOException, DataFormatException {
		byte[] compressedData = new byte[header.getCompressedSize()];
		Integer length = header.getCompressedSize() - DDSaveHeader.HEADER_BYTES;
		Integer readBytes = saveStream.read(compressedData, 0, length);
		
		if (readBytes < length) return null;
		
		for (int i = 0; i < 8; i++) {
			System.out.printf("0x%02X\n",compressedData[i]);
		}
		
		byte[] uncompressed = CompressionUtils.decompress(compressedData, header.getSize());
		for (int i = 0; i < 8; i++) {
			System.out.printf("0x%02X\n",uncompressed[i]);
		}
		return null;
	}
}
