package com.woernerj.dragonsdogma.bo.types;

public abstract class SimpleType<T> extends NamedType {

	private T value;
	
	public T getValue() {
		return this.value;
	}
	
	public void setValue(T value) {
		this.value = value;
	}
}
