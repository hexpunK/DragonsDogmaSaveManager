package com.woernerj.dragonsdogma.bo.types;

import net.jodah.typetools.TypeResolver;

public abstract class CastingType<T, R> extends NamedType {

	protected T value;
	
	@SuppressWarnings("unchecked")
	public R getValue() {
		if (isSameType()) {
			return (R)this.value;
		}
		return null;
	}	
	@SuppressWarnings("unchecked")
	public void setValue(R value) {
		if (isSameType()) {
			this.value = (T)value;
		}
	}
	
	@SuppressWarnings("unchecked")
	private boolean isSameType() {
		java.lang.Class<?>[] classes = TypeResolver.resolveRawArguments(CastingType.class, getClass());
		java.lang.Class<T> left = (java.lang.Class<T>)classes[0];
		java.lang.Class<R> right = (java.lang.Class<R>)classes[1];
		
		return left.equals(right);
	}
}
