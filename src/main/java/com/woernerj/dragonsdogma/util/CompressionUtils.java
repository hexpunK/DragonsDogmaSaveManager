package com.woernerj.dragonsdogma.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.apache.commons.lang3.ArrayUtils;

public class CompressionUtils {

	public static byte[] compress(final byte[] raw) {
		if (ArrayUtils.isEmpty(raw)) return new byte[0];
		
		Deflater deflater = new Deflater();
		deflater.setInput(raw);
		deflater.finish();
		
		try (ByteArrayOutputStream out = new ByteArrayOutputStream(raw.length)){
			byte[] buffer = new byte[1024];
			while (!deflater.finished()) {
				out.write(buffer, 0, deflater.deflate(buffer));
			}
			return out.toByteArray();
		} catch (IOException e) {
			System.err.println("Compression failed! Returning the raw data...");
			e.printStackTrace();
			return raw;
		}
	}
	
	public static byte[] decompress(final byte[] compressed) {
		return decompress(compressed, null);
	}
	
	public static byte[] decompress(final byte[] compressed, Integer expectedSize) {
		if (ArrayUtils.isEmpty(compressed)) return new byte[0];
		if (expectedSize == null) expectedSize = 0;
		
		Inflater inflater = new Inflater();
		inflater.setInput(compressed);
		
		try (ByteArrayOutputStream out = new ByteArrayOutputStream(compressed.length)){
			byte[] buffer = new byte[1024];
			Integer done = 0;
			System.out.println(expectedSize);
			while (!inflater.finished()) {
				Integer len = inflater.inflate(buffer);
				done += len;
				if (expectedSize > 0) {
					float perc = (float)done/(float)expectedSize;
					System.out.printf("%.2f    \r", perc);
				}
				out.write(buffer, 0, len);
			}
			return out.toByteArray();
		} catch (DataFormatException | IOException e) {
			System.err.println("Decompression failed! Returning the compressed data...");
			e.printStackTrace();
			return compressed;
		}
	}
}
