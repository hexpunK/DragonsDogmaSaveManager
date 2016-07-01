package com.woernerj.dragonsdogma.tests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import com.woernerj.dragonsdogma.util.CompressionUtils;

public class CompressionUtilsTests {

	@Test
	public void testCompressionUtils() throws UnsupportedEncodingException {
		String inputStr = "Hello World!";
		byte[] inputBytes = inputStr.getBytes("UTF-8");

		byte[] compressedBytes = CompressionUtils.compress(inputBytes);
		assertNotEquals("No compressed bytes returned", 0, compressedBytes.length);

		byte[] decompressedBytes = CompressionUtils.decompress(compressedBytes);
		assertArrayEquals("Decompressed byte count differs", inputBytes, decompressedBytes);
	}
}
