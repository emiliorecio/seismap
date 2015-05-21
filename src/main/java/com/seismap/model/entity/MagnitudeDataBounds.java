package com.seismap.model.entity;

import com.seismap.service.event.ExtendedMagnitudeType;

import javax.persistence.*;

@Entity
@Table(name = "magnitudedatabounds")
public class MagnitudeDataBounds {

	@Id
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ExtendedMagnitudeType magnitudeType;

	@Column(nullable = true)
	private Float min;

	@Column(nullable = true)
	private Float max;

	protected MagnitudeDataBounds() {

	}

	protected MagnitudeDataBounds(ExtendedMagnitudeType magnitudeType, Float min,
			Float max) {
		this.magnitudeType = magnitudeType;
		this.min = min;
		this.max = max;
	}

	public ExtendedMagnitudeType getMagnitudeType() {
		return magnitudeType;
	}

	public Float getMin() {
		return min;
	}

	public Float getMax() {
		return max;
	}
}
