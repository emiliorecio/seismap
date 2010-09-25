package com.seismap.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Magnitude {

	@Id
	@GeneratedValue
	@Column(nullable = false)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MagnitudeType magnitudeType;

	@Column(nullable = false)
	private float value;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Agency reportingAgency;

	public Magnitude(Agency reportingAgency, MagnitudeType magnitudeType,
			float value) {
		this.reportingAgency = reportingAgency;
		this.magnitudeType = magnitudeType;
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MagnitudeType getMagnitudeType() {
		return magnitudeType;
	}

	public void setMagnitudeType(MagnitudeType magnitudeType) {
		this.magnitudeType = magnitudeType;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public Agency getReportingAgency() {
		return reportingAgency;
	}

	public void setReportingAgency(Agency reportingAgency) {
		this.reportingAgency = reportingAgency;
	}

}
