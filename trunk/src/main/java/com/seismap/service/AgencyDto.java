package com.seismap.service;

import org.codehaus.jackson.annotate.JsonProperty;

public class AgencyDto {

	@JsonProperty
	private long id;

	@JsonProperty
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
