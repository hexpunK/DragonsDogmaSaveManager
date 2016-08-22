package com.woernerj.dragonsdogma.exception;

public class SaveLoadException extends Exception {
	
	private static final long serialVersionUID = -4586958957690146090L;

	public SaveLoadException(String message) {
		super(message);
	}
	
	public SaveLoadException(String message, Throwable cause) {
		super(message, cause);
	}
}
