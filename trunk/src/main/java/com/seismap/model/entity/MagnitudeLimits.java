package com.seismap.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import com.seismap.service.event.ExtendedMagnitudeType;

@Entity
public class MagnitudeLimits {

	@Id
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ExtendedMagnitudeType magnitudeType;

	@Column(nullable = false)
	private float min;

	@Column(nullable = false)
	private float max;

	protected MagnitudeLimits() {

	}

	protected MagnitudeLimits(ExtendedMagnitudeType magnitudeType, float min,
			float max) {
		this.magnitudeType = magnitudeType;
		this.min = min;
		this.max = max;
	}

	public ExtendedMagnitudeType getMagnitudeType() {
		return magnitudeType;
	}

	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}
}
