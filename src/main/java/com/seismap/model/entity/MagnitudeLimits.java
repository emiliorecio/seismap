package com.seismap.model.entity;

import com.seismap.service.event.ExtendedMagnitudeType;

import javax.persistence.*;

@Entity
@Table(name = "magnitudelimits")
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
