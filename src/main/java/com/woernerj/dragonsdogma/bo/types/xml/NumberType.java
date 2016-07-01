package com.woernerj.dragonsdogma.bo.types.xml;

import net.jodah.typetools.TypeResolver;

public abstract class NumberType<T extends Number, R extends Number> extends NamedType {

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
		java.lang.Class<?>[] classes = TypeResolver.resolveRawArguments(NumberType.class, getClass());
		java.lang.Class<T> left = (java.lang.Class<T>)classes[0];
		java.lang.Class<R> right = (java.lang.Class<R>)classes[1];
		
		return left.equals(right);
	}
	
	public abstract R getMinValue();
	
	public abstract R getMaxValue();
	
	@SuppressWarnings("unchecked")
	@Override
	public java.lang.String getXmlString() {
		R val = getValue();
		if (val == null) {
			val = (R)Integer.valueOf(0);
		}
		return java.lang.String.format("<%s name=\"%s\" value=\"%s\" />", getClass().getSimpleName().toLowerCase(), getName(), val);
	}
}
