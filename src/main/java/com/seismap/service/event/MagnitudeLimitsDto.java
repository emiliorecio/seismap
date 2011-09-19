package com.seismap.service.event;

import org.codehaus.jackson.annotate.JsonProperty;

public class MagnitudeLimitsDto {

	@JsonProperty
	private ExtendedMagnitudeType magnitudeType;

	@JsonProperty
	private Float min;

	@JsonProperty
	private Float max;

	protected MagnitudeLimitsDto() {
	}

	public MagnitudeLimitsDto(ExtendedMagnitudeType magnitudeType, Float min,
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
