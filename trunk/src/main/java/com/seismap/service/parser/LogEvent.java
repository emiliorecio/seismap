package com.seismap.service.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class LogEvent {

	private Map<String, List<AbstractEntry>> entries;

	public LogEvent(Map<String, List<AbstractEntry>> entries) {
		this.entries = entries;
	}

	@SuppressWarnings("unchecked")
	public <T extends AbstractEntry> List<T> getEntries(String type) {
		List<AbstractEntry> list = this.entries.get(type);
		if (list == null) {
			return new ArrayList<T>(0);
		} else {
			return new ArrayList<T>((List<T>)list);
		}
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
