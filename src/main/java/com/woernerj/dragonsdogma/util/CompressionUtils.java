package com.woernerj.dragonsdogma.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.apache.commons.lang3.ArrayUtils;

import com.woernerj.dragonsdogma.bo.CompressionProgressCallback;

/**
 * A simple wrapper for Java ZIP utilities. Provides a neater way to compress 
 * and decompress data.
 * 
 * @author Jordan Woerner
 * @version 1.2
 * @since 2016-07-04
 */
public class CompressionUtils {

	/**
	 * Compresses the specified data using the ZLib compression algorithm.
	 * 
	 * @param raw The data to be compressed as an array of bytes.
	 * @return An array of bytes containing the compressed data.
	 * @since 1.1
	 */
	public static byte[] compress(final byte[] raw) {
		return compress(raw, p -> { /* Do nothing */ });
	}
	/**
	 * Compresses the specified data using the ZLib compression algorithm. 
	 * Allows the specification of a callback to report the progress of the 
	 * compression operation. 
	 * 
	 * @param raw The data to be compressed as an array of bytes.
	 * @param callback A callback that will report the progress of the 
	 * compression operation as an implementation of {@link 
	 * CompressionProgressCallback}.
	 * @return An array of bytes containing the compressed data.
	 * @since 1.0
	 */
	public static byte[] compress(final byte[] raw, 
			final CompressionProgressCallback callback) {
		if (ArrayUtils.isEmpty(raw)) return new byte[0];
		
		Deflater deflater = new Deflater();
		deflater.setInput(raw);
		deflater.finish();
		
		try (ByteArrayOutputStream out 
				= new ByteArrayOutputStream(raw.length)){
			byte[] buffer = new byte[1024];
			while (!deflater.finished()) {
				double perc = (deflater.getBytesRead())/(double)raw.length;
				out.write(buffer, 0, deflater.deflate(buffer));
				callback.update(perc);
			}
			deflater.end();
			return out.toByteArray();
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Decompresses the specified data using the ZLib compression algorithm.
	 * 
	 * @param compressed The compressed data as an array of bytes.
	 * @return An array of bytes containing the raw data.
	 * @since 1.1
	 */
	public static byte[] decompress(final byte[] compressed) {
		return decompress(compressed, p -> { /* Do nothing */ });
	}
	/**
	 * Decompresses the specified data using the ZLib compression algorithm. 
	 * Allows the specification of a callback to report the progress of the 
	 * decompression operation. 
	 * 
	 * @param compressed The compressed data as an array of bytes.
	 * @param callback A callback that will report the progress of the 
	 * decompression operation as an implementation of {@link 
	 * CompressionProgressCallback}.
	 * @return An array of bytes containing the raw data.
	 * @since 1.0
	 */
	public static byte[] decompress(final byte[] compressed, 
			final CompressionProgressCallback callback) {
		if (ArrayUtils.isEmpty(compressed)) return new byte[0];
		
		Inflater inflater = new Inflater();
		inflater.setInput(compressed);
		
		try (ByteArrayOutputStream out 
				= new ByteArrayOutputStream(compressed.length)){
			byte[] buffer = new byte[1024];
			while (inflater.getRemaining() > 0) {
				float perc = inflater.getBytesRead()/(float)compressed.length;
				out.write(buffer, 0, inflater.inflate(buffer));
				callback.update(perc);
			}
			inflater.end();
			return out.toByteArray();
		} catch (DataFormatException | IOException e) {
			return null;
		}
	}
	
	/**
	 * Calculates the CRC error checking code for the specified data.
	 * 
	 * @param data The data to generate a CRC code for as an array of bytes.
	 * @return The CRC code as a long.
	 * @since 1.2
	 */
	public static long calculateCrc32(byte[] data) {
		CRC32 crc = new CRC32();
		crc.update(data);
		return crc.getValue();
	}
}
