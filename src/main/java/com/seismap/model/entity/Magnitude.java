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
	private MagnitudeType type;

	@Column(nullable = false)
	private float value;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Agency reportingAgency;

	protected Magnitude() {

	}

	public Magnitude(Agency reportingAgency, MagnitudeType type, float value) {
		this.reportingAgency = reportingAgency;
		this.type = type;
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public MagnitudeType getType() {
		return type;
	}

	public float getValue() {
		return value;
	}

	public Agency getReportingAgency() {
		return reportingAgency;
	}

}
