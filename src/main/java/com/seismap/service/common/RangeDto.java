package com.seismap.service.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class RangeDto<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private T minimum;

	@JsonProperty
	private T maximum;

	protected RangeDto() {

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
