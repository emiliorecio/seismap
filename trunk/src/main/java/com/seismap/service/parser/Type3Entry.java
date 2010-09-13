package com.seismap.service.parser;

import com.seismap.service.parser.annotation.Entry;
import com.seismap.service.parser.annotation.StringField;
import com.seismap.service.parser.annotation.Whitespace;

@Entry(values = '3', type = EntryType.THREE, whitespaces = {
		@Whitespace(position = 1, length = 1)
		})
public class Type3Entry extends AbstractEntry{
	
	@StringField(position = 2, length = 78)
	private String anyText;

	public String getAnyText() {
		return anyText;
	}
	
	

}
