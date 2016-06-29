package com.woernerj.dragonsdogma.bo;

public enum DDVersion {
	DD(5),
	DDDA(21);
	
	public final Integer VERSION;
	
	private DDVersion(Integer version) {
		this.VERSION = version;
	}
	
	public static DDVersion parse(Integer version) {
		for (DDVersion ddVersion : DDVersion.values()) {
			if (ddVersion.VERSION == version) 
				return ddVersion;
		}
		return null;
	}
}
