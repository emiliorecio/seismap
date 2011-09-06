package com.seismap.service.event;

import org.codehaus.jackson.annotate.JsonProperty;

public class MagnitudeDataBoundsDto {

	@JsonProperty
	private MagnitudeType magnitudeType;

	@JsonProperty
	private Float min;

	@JsonProperty
	private Float max;

	protected MagnitudeDataBoundsDto() {
	}

	public MagnitudeDataBoundsDto(MagnitudeType magnitudeType, Float min,
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
