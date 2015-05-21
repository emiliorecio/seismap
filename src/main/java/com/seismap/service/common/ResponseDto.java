package com.seismap.service.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.seismap.service.common.ExceptionCause.ExceptionParameter;

import java.io.Serializable;

public abstract class ResponseDto<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private ExceptionDto exception;

	@JsonProperty
	private T value;

	protected ResponseDto() {

	}

	public ResponseDto(T value) {
		this.value = value;
		this.exception = null;
	}

	public ResponseDto(ExceptionCause cause, String message) {
		this.value = null;
		this.exception = new ExceptionDto(cause, message);
	}

	@JsonIgnore
	public boolean isException() {
		return exception != null;
	}

	public ExceptionDto getException() {
		return exception;
	}

	public void addExceptionParameter(ExceptionParameter parameter, Object value) {
		exception.addParameter(parameter, value);
	}

	public T getValue() {
		return value;
	}

	@Override
	public String toString() {
		if (isException()) {
			return getClass().getSimpleName() + "[exception: "
					+ exception.toString() + "]";
		} else {
			return getClass().getSimpleName() + "[value: "
					+ value + "]";
		}
	}
}
