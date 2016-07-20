package com.woernerj.dragonsdogma.bo.types.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang3.StringUtils;

import com.woernerj.dragonsdogma.bo.types.xml.adapters.NumberValueAdapter;

import net.jodah.typetools.TypeResolver;

@XmlSeeAlso({S8.class, S16.class, S32.class, U8.class, U16.class, U32.class, U64.class, F32.class})
public abstract class NumberType<T extends Number, R extends Number> extends NamedType {

	protected T value;
	
	@XmlAttribute
	@XmlJavaTypeAdapter(NumberValueAdapter.class)
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
	public java.lang.String getXmlString(boolean includeChildren) {
		R val = getValue();
		if (val == null) {
			val = (R)Integer.valueOf(0);
		}
		if (StringUtils.isBlank(getName())) {
			return java.lang.String.format("<%s value=\"%s\"/>", getClass().getSimpleName().toLowerCase(), getValue());
		}
		return java.lang.String.format("<%s name=\"%s\" value=\"%s\"/>", getClass().getSimpleName().toLowerCase(), getName(), getValue());
	}
}
