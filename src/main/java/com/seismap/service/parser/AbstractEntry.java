package com.seismap.service.parser;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public abstract class AbstractEntry {

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
