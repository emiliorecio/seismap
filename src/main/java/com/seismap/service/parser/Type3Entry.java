package com.seismap.service.parser;

import com.seismap.service.parser.annotation.Entry;
import com.seismap.service.parser.annotation.StringField;
import com.seismap.service.parser.annotation.Whitespace;

@Entry(id = "3", after = { "1", "2", "3", "E", "I", "6", "F", "7" }, whitespaces = { @Whitespace(position = 1, length = 1) })
public class Type3Entry extends AbstractEntry {

	@StringField(position = 2, length = 78)
	private String anyText;

	public String getAnyText() {
		return anyText;
	}

}
