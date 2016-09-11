package com.woernerj.dragonsdogma.bo;

import com.woernerj.dragonsdogma.util.CompressionUtils;

/**
 * Provides a callback to handle reporting the progress of compression
 * operations started by {@link CompressionUtils}.
 * 
 * @author Jordan Woerner
 * @version 1.0
 * @since 2016-06-30
 */
@FunctionalInterface
public interface CompressionProgressCallback {

	/**
	 * Called when the {@link CompressionUtils#decompress(byte[])} or 
	 * {@link CompressionUtils#compress(byte[])} methods process a block of 
	 * bytes.
	 * 
	 * @param perc The percentage of the data provided that has been processed 
	 * as a value between 0 and 1.
	 * @since 1.0
	 */
	public void update(double perc);
	
	default public void onCompressionError(Throwable cause) {
		// Do nothing
	}
}
