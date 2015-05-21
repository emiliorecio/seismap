package com.seismap.model.entity;

import com.seismap.service.event.MagnitudeType;

import javax.persistence.*;

@Entity
@Table(name = "magnitude")
public class Magnitude implements Identifiable<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
