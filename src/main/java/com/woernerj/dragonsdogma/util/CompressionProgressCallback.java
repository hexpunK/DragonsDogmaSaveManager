package com.woernerj.dragonsdogma.util;

public interface CompressionProgressCallback {

	public default void update(double perc) { /* Do nothing. */ }
}
