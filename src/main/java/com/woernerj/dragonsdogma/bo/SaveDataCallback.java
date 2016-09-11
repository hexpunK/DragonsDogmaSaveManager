package com.woernerj.dragonsdogma.bo;

import org.apache.logging.log4j.Level;

/**
 * Callback to track the loading status of the save file.
 *  
 * @author Jordan Woerner
 * @version 1.0
 * @since 2016-08-22
 */
@FunctionalInterface
public interface SaveDataCallback {

	/**
	 * Called when the save data starts to load.
	 * 
	 * @since 1.0
	 */
	default public void loadStarted() { /* Do nothing by default */ }
	
	/**
	 * Called when the save data loader reports on a change in the progress of 
	 * loading the save file.
	 * 
	 * @param logLevel The {@link Level} to log this progress event to
	 * @param message The progress change message as a {@link String}
	 * @since 1.0
	 */
	default public void progressChanged(Level logLevel, String message) {
		/* Do nothing by default */
	}
	
	/**
	 * Called when the file has finished loading. If there is an error during 
	 * loading and {@link SaveDataCallback#onCompressionError(Throwable)} is called, this 
	 * will normally not be called as well.
	 * 
	 * @param data The {@link DDSave} containing the loaded save data.
	 * @since 1.0
	 */
	public void loadCompleted(DDSave data);
	
	/**
	 * Called when an error occurs during loading.
	 * 
	 * @param cause The cause of the error as a {@link Throwable} or a 
	 * sub-class of {@link Throwable}.
	 * @since 1.0
	 */
	default public void onCompressionError(Throwable cause) {
		/* Do nothing by default */
	}
}
