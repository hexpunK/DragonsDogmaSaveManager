package com.woernerj.dragonsdogma.adapters;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.woernerj.dragonsdogma.bo.types.xml.Array;
import com.woernerj.dragonsdogma.bo.types.xml.U8;

public class ASCIIStringAdapter extends XmlAdapter<Array<U8>, String> {

	@Override
	public String unmarshal(Array<U8> v) throws Exception {
		if (!v.getName().equals("(u8*)mNameStr")) return null;
		ByteArrayOutputStream strm = new ByteArrayOutputStream();
		for (U8 child : v.getChildren()) {
			strm.write(child.getValue());
		}		
		return strm.toString("ASCII");
	}

	@Override
	public Array<U8> marshal(String v) throws Exception {
		byte[] bytes = v.getBytes("ASCII");
		Array<U8> arr = new Array<>(U8.class);
		arr.setName("(u8*)mNameStr");
		arr.setCount(25);
		
		List<U8> children = IntStream.range(0, bytes.length)
		.map(i -> bytes[i] )
		.mapToObj(b -> {
			U8 itm = new U8();
			itm.setValue((short)b);
			return itm;
		})
		.collect(Collectors.toList());
		arr.setChildren(children);
		
		return arr;
	}
}
