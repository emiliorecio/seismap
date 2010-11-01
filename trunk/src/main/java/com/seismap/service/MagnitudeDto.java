package com.seismap.service;

import org.codehaus.jackson.annotate.JsonProperty;

import com.seismap.model.entity.MagnitudeType;

public class MagnitudeDto {

	@JsonProperty
	private long id;

	@JsonProperty
	private MagnitudeType type;

	@JsonProperty
	private float value;

	@JsonProperty
	private AgencyDto reportingAgency;

	public MagnitudeDto(long id, MagnitudeType type, float value,
			AgencyDto reportingAgency) {
		this.id = id;
		this.type = type;
		this.value = value;
		this.reportingAgency = reportingAgency;
	}

	public long getId() {
		return id;
	}

	public MagnitudeType getType() {
		return type;
	}

	public float getValue() {
		return value;
	}

	public AgencyDto getReportingAgency() {
		return reportingAgency;
	}
}
