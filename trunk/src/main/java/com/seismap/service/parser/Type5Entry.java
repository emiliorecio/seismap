package com.seismap.service.parser;

import com.seismap.service.parser.annotation.Entry;
import com.seismap.service.parser.annotation.StringField;
import com.seismap.service.parser.annotation.Whitespace;

@Entry(value = '5', type = EntryType.FIVE, whitespaces = {
		@Whitespace(position = 1, length = 1)
		})
public class Type5Entry extends AbstractEntry{
	
	@StringField(position = 2, length = 77)
	private String textAnyThing;

	public String getTextAnyThing() {
		return textAnyThing;
	}
	
	

}
