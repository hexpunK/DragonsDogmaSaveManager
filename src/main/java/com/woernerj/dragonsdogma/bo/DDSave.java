package com.woernerj.dragonsdogma.bo;

public class DDSave {

	public static final Integer MAX_SIZE = 524288;
	
	private DDSaveHeader header;
	
	public DDSaveHeader getHeader() {
		return this.header;
	}
	public void setHeader(DDSaveHeader header) {
		this.header = header;
	}
	
	public static DDSave build(DDSaveHeader header, String inputXml) {
		DDSave obj = new DDSave();
		obj.setHeader(header);
		
		return obj;
	}
}
