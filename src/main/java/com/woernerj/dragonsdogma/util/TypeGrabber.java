package com.woernerj.dragonsdogma.util;

import java.util.HashMap;
import java.util.Map;

import com.woernerj.dragonsdogma.bo.types.xml.Array;
import com.woernerj.dragonsdogma.bo.types.xml.Bool;
import com.woernerj.dragonsdogma.bo.types.xml.ClassRef;
import com.woernerj.dragonsdogma.bo.types.xml.F32;
import com.woernerj.dragonsdogma.bo.types.xml.NamedType;
import com.woernerj.dragonsdogma.bo.types.xml.S16;
import com.woernerj.dragonsdogma.bo.types.xml.S32;
import com.woernerj.dragonsdogma.bo.types.xml.S8;
import com.woernerj.dragonsdogma.bo.types.xml.Time;
import com.woernerj.dragonsdogma.bo.types.xml.U16;
import com.woernerj.dragonsdogma.bo.types.xml.U32;
import com.woernerj.dragonsdogma.bo.types.xml.U64;
import com.woernerj.dragonsdogma.bo.types.xml.U8;
import com.woernerj.dragonsdogma.bo.types.xml.Vector3;

public class TypeGrabber {

	private static final Map<String, Class<? extends NamedType>> MAPPING;
	
	static {
		MAPPING = new HashMap<>();
		{
			MAPPING.put("s8", S8.class);
			MAPPING.put("s16", S16.class);
			MAPPING.put("s32", S32.class);
			MAPPING.put("u8", U8.class);
			MAPPING.put("u16", U16.class);
			MAPPING.put("u32", U32.class);
			MAPPING.put("u64", U64.class);
			MAPPING.put("f32", F32.class);
			MAPPING.put("vector3", Vector3.class);
			MAPPING.put("time", Time.class);
			MAPPING.put("string", com.woernerj.dragonsdogma.bo.types.xml.String.class);
			MAPPING.put("bool", Bool.class);
			MAPPING.put("array", Array.class);
			MAPPING.put("class", com.woernerj.dragonsdogma.bo.types.xml.Class.class);
			MAPPING.put("classref", ClassRef.class);
		}
	}
	
	public static Class<? extends NamedType> getClassForName(String name) {
		return MAPPING.get(name);
	}
	
	public static NamedType getInstanceForName(String name) {
		try {
			return MAPPING.get(name).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			return null;
		}
	}
}
