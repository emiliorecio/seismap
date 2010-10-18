package com.seismap.service;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.seismap.model.entity.MagnitudeType;

@XmlRootElement
public class MagnitudeDto {

	@XmlAttribute(required = true)
	private long id;

	@XmlAttribute(required = true)
	private MagnitudeType type;

	@XmlAttribute(required = true)
	private float value;

	@XmlElement
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
