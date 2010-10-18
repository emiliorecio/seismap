package com.seismap.service;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "agency")
public class AgencyDto {

	@XmlAttribute(required = true)
	private long id;

	@XmlAttribute(required = true)
	private String code;

	public AgencyDto(long id, String code) {
		this.id = id;
		this.code = code;
	}

	public long getId() {
		return id;
	}

	public String getCode() {
		return code;
	}
}
