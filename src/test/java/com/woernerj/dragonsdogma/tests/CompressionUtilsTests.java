package com.woernerj.dragonsdogma.tests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

import com.woernerj.dragonsdogma.exception.CompressionExpcetion;
import com.woernerj.dragonsdogma.util.CompressionUtils;

public class CompressionUtilsTests {

	@Test
	public void testCompressionUtils() throws UnsupportedEncodingException, CompressionExpcetion {
		String inputStr = "Hello World!";
		byte[] inputBytes = inputStr.getBytes("UTF-8");

		ByteArrayOutputStream compressedBytes = CompressionUtils.compress(inputBytes);
		assertNotEquals("No compressed bytes returned", 0, compressedBytes.size());

		ByteArrayOutputStream decompressedBytes = CompressionUtils.decompress(compressedBytes.toByteArray());
		assertArrayEquals("Decompressed byte count differs", inputBytes, decompressedBytes.toByteArray());
	}
}
