package com.seismap.service;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RangeDto<T> {

	@XmlAttribute(required = false)
	private T minimum;

	@XmlAttribute(required = false)
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
