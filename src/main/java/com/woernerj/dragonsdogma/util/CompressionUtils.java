package com.woernerj.dragonsdogma.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.apache.commons.lang3.ArrayUtils;

import com.woernerj.dragonsdogma.bo.CompressionProgressCallback;
import com.woernerj.dragonsdogma.exception.CompressionExpcetion;

/**
 * A simple wrapper for Java ZIP utilities. Provides a neater way to compress 
 * and decompress data.
 * 
 * @author Jordan Woerner
 * @version 1.3
 * @since 2016-08-21
 */
public class CompressionUtils {

	private static final int BUFFER_SIZE = 512;
	/**
	 * Compresses the specified data using the ZLib compression algorithm.
	 * 
	 * @param raw The data to be compressed as an array of bytes.
	 * @return An array of bytes containing the compressed data.
	 * @throws CompressionExpcetion Thrown if no input data was provided or 
	 * there was an IO error during compression.
	 * @since 1.1
	 */
	public static ByteArrayOutputStream compress(final byte[] raw) {
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
	 * @throws CompressionExpcetion Thrown if no input data was provided or 
	 * there was an IO error during compression.
	 * @since 1.0
	 */
	public static ByteArrayOutputStream compress(final byte[] raw, 
			final CompressionProgressCallback callback) {
		if (ArrayUtils.isEmpty(raw)) {
			callback.onCompressionError(new CompressionExpcetion("Input data was empty"));
			return null;
		}
		
		Deflater deflater = new Deflater();
		deflater.setInput(raw);
		deflater.finish();
		
		try (ByteArrayOutputStream out = new ByteArrayOutputStream(raw.length)){
			byte[] buffer = new byte[BUFFER_SIZE];
			while (!deflater.finished()) {
				double perc = (deflater.getBytesRead())/(double)raw.length;
				out.write(buffer, 0, deflater.deflate(buffer));
				callback.update(perc);
			}
			deflater.end();
			return out;
		} catch (IOException e) {
			callback.onCompressionError(new CompressionExpcetion("Could not close input stream", e));
		}
		
		return null;
	}

	/**
	 * Decompresses the specified data using the ZLib compression algorithm.
	 * 
	 * @param compressed The data to be compressed as an array of bytes.
	 * @return An array of bytes containing the decompresses data.
	 * @throws CompressionExpcetion Thrown if no input data was provided or 
	 * there was an IO error during compression.
	 * @since 1.1
	 */
	public static ByteArrayOutputStream decompress(final byte[] compressed) {
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
	 * @throws CompressionExpcetion Thrown if no input data was provided or 
	 * there was an IO error during compression.
	 * @since 1.0
	 */
	public static ByteArrayOutputStream decompress(final byte[] compressed, 
			final CompressionProgressCallback callback) {
		if (ArrayUtils.isEmpty(compressed)) {
			callback.onCompressionError(new CompressionExpcetion("Input data was empty"));
			return null;
		}
		
		Inflater inflater = new Inflater();
		inflater.setInput(compressed);
		
		try (ByteArrayOutputStream out = new ByteArrayOutputStream(compressed.length)){
			byte[] buffer = new byte[BUFFER_SIZE];
			while (inflater.getRemaining() > 0) {
				float perc = inflater.getBytesRead()/(float)compressed.length;
				out.write(buffer, 0, inflater.inflate(buffer));
				callback.update(perc);
			}
			inflater.end();
			return out;
		} catch (DataFormatException e) {
			callback.onCompressionError(new CompressionExpcetion("Data format is not supported", e));
		} catch (IOException e) {
			callback.onCompressionError(new CompressionExpcetion("Could not close input stream", e));
		}
		
		return null;
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
