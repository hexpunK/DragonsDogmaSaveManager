package com.woernerj.dragonsdogma.bo;

public interface CompressionProgressCallback {

	public default void update(double perc) { /* Do nothing. */ }
}
