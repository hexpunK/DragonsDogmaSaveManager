package com.woernerj.dragonsdogma.bo.types.xml.steamid;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "error")
@XmlAccessorType(XmlAccessType.FIELD)
public class Error {

	@XmlElement(name = "errormsg")
	private String errorMessage;
	@XmlElement(name = "errorid")
	private Integer id;
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public Integer getId() {
		return id;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
