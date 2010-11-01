package com.seismap.service;

import org.codehaus.jackson.annotate.JsonProperty;

public class RangeDto<T> {

	@JsonProperty
	private T minimum;

	@JsonProperty
	private T maximum;

	public RangeDto() {

	}

	public RangeDto(T minimum, T maximum) {
		this.minimum = minimum;
		this.maximum = maximum;
	}

	public T getMinimum() {
		return minimum;
	}

	public T getMaximum() {
		return maximum;
	}
}
