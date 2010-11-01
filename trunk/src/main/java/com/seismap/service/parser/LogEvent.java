package com.seismap.service.parser;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class LogEvent {

	private Map<String, List<AbstractEntry>> entries;

	public LogEvent(Map<String, List<AbstractEntry>> entries) {
		this.entries = entries;
	}

	public Map<String, List<AbstractEntry>> getLogEvent(){
		return this.entries;
	}
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
