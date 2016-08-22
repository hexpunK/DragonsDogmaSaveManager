package com.woernerj.dragonsdogma.exception;

public class CompressionExpcetion extends Exception {
	
	private static final long serialVersionUID = -4869718469094183119L;

	public CompressionExpcetion(String message) {
		super(message);
	}
	
	public CompressionExpcetion(String message, Throwable cause) {
		super(message, cause);
	}
}
