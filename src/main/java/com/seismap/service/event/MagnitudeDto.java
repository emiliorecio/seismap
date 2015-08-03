package com.seismap.service.event;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


public class MagnitudeDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private Long id;

	@JsonProperty
	private MagnitudeType type;

	@JsonProperty
	private Float value;

	@JsonProperty
	private AgencyDto reportingAgency;

	protected MagnitudeDto() {

	}

	public MagnitudeDto(Long id, MagnitudeType type, Float value,
			AgencyDto reportingAgency) {
		this.id = id;
		this.type = type;
		this.value = value;
		this.reportingAgency = reportingAgency;
	}

	public Long getId() {
		return id;
	}

	public MagnitudeType getType() {
		return type;
	}

	public Float getValue() {
		return value;
	}

	public AgencyDto getReportingAgency() {
		return reportingAgency;
	}
}
