package com.seismap.service.parser;

import com.seismap.service.parser.annotation.Entry;
import com.seismap.service.parser.annotation.StringField;
import com.seismap.service.parser.annotation.Whitespace;

@Entry(id = "6", after = { "1", "2", "3", "4", "E", "I" }, whitespaces = { @Whitespace(position = 1, length = 1) })
public class Type6Entry extends AbstractEntry {

	@StringField(position = 2, length = 78)
	private String traceDataFileNames;

	public String getTraceDataFileNames() {
		return traceDataFileNames;
	}
}
