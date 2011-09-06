package com.seismap.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import com.seismap.service.event.MagnitudeType;

@Entity
public class MagnitudeDataBounds {

	@Id
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private MagnitudeType magnitudeType;

	@Column(nullable = true)
	private Float min;

	@Column(nullable = true)
	private Float max;

	protected MagnitudeDataBounds() {

	}

	protected MagnitudeDataBounds(MagnitudeType magnitudeType, Float min,
			Float max) {
		this.magnitudeType = magnitudeType;
		this.min = min;
		this.max = max;
	}

	public MagnitudeType getMagnitudeType() {
		return magnitudeType;
	}

	public Float getMin() {
		return min;
	}

	public Float getMax() {
		return max;
	}
}
