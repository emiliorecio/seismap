package com.seismap.service.parser;

import com.seismap.service.parser.annotation.Entry;
import com.seismap.service.parser.annotation.StringField;
import com.seismap.service.parser.annotation.Whitespace;

@Entry(id = "7", after = { "1", "2", "3", "4", "6", "E", "I" }, whitespaces = { @Whitespace(position = 1, length = 1) })
public class Type7Entry extends AbstractEntry {

	@StringField(position = 2, length = 78)
	private String helperLines;

	public String getHelperLines() {
		return helperLines;
	}
}
