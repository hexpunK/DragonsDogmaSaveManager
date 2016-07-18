package com.woernerj.dragonsdogma.bo.types.xml.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class NumberValueAdapter extends XmlAdapter<java.lang.String, Number> {

	@Override
	public Number unmarshal(java.lang.String v) throws Exception {
		return Double.valueOf(v);
	}

	@Override
	public java.lang.String marshal(Number v) throws Exception {
		return v.toString();
	}

}
