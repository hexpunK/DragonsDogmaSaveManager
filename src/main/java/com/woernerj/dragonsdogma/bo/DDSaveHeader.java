package com.woernerj.dragonsdogma.bo;

import com.woernerj.dragonsdogma.DDPlatform;

public class DDSaveHeader {

	public static final Integer HEADER_BYTES = 32;
	
	private DDPlatform platform;
	private Integer version;
	private Integer size;
	private Integer compressedSize;
	private Integer checksum;

	public DDSaveHeader() {
		this.version = 0;
		this.size = 0;
		this.compressedSize = 0;
		this.checksum = 0;
	}

	public DDSaveHeader(final DDPlatform platform, final Integer version, 
			final Integer size, final Integer compressedSize, 
			final Integer checksum) {
		this.platform = platform;
		this.version = version;
		this.size = size;
		this.compressedSize = compressedSize;
		this.checksum = checksum;
	}

	public DDPlatform getPlatform() {
		return this.platform;
	}
	public Integer getVersion() {
		return this.version;
	}	
	public DDVersion getDDVersion() {
		return DDVersion.parse(this.version);
	}
	public Integer getSize() {
		return this.size;
	}
	public Integer getCompressedSize() {
		return this.compressedSize;
	}
	public Integer getChecksum() {
		return this.checksum;
	}
	public void setPlatform(DDPlatform platform) {
		this.platform = platform;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public void setCompressedSize(Integer compressedSize) {
		this.compressedSize = compressedSize;
	}
	public void setChecksum(Integer checksum) {
		this.checksum = checksum;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!(obj instanceof DDSaveHeader)) return false;
		
		DDSaveHeader other = (DDSaveHeader)obj;
		return other.hashCode() == this.hashCode();
	}
	
	@Override
	public int hashCode() {
		if (this.checksum == null) {
			return -1;
		}
		return this.checksum;
	}
}
