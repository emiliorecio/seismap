package com.seismap.service.event;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

public class AgencyDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private Long id;

	@JsonProperty
	private String code;

	public AgencyDto(Long id, String code) {
		this.id = id;
		this.code = code;
	}

	public Long getId() {
		return id;
	}

	public String getCode() {
		return code;
	}
}
