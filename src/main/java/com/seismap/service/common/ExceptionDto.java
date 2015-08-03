package com.seismap.service.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seismap.service.common.ExceptionCause.ExceptionParameter;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ExceptionDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private ExceptionCause cause;

	@JsonProperty
	private String message;

	@JsonProperty
	private Map<ExceptionParameter, String> parameters = new HashMap<ExceptionParameter, String>();

	ExceptionDto() {

	}

	public ExceptionDto(ExceptionCause cause, String message) {
		this.cause = cause;
		this.message = message;
	}

	public ExceptionCause getCause() {
		return cause;
	}

	public String getMessage() {
		return message;
	}

	public Map<ExceptionParameter, String> getParameters() {
		return Collections.unmodifiableMap(parameters);
	}

	public void addParameter(ExceptionParameter parameter, Object value) {
		parameters.put(parameter, value == null ? null : value.toString());
	}

	@Override
	public String toString() {
		return "Exception " + cause + ": " + message + " " + parameters;
	}
}
