package com.seismap.service.parser;

import com.seismap.service.parser.annotation.Entry;
import com.seismap.service.parser.annotation.EnumeratedField;
import com.seismap.service.parser.annotation.EnumerationMapping;
import com.seismap.service.parser.annotation.StringField;
import com.seismap.service.parser.annotation.Whitespace;
import com.seismap.service.parser.enumeration.DiastrophismCode;

@Entry(values = '7', type = EntryType.SEVEN, whitespaces = {
		@Whitespace(position = 1, length = 5),
		@Whitespace(position = 21, length = 1),
		@Whitespace(position = 27, length = 1),
		@Whitespace(position = 33, length = 1),
		@Whitespace(position = 40, length = 1),
		@Whitespace(position = 48, length = 1),
		@Whitespace(position = 71, length = 1),
		@Whitespace(position = 76, length = 4) })
public class Type7Entry extends AbstractEntry {

	@StringField(position = 6, length = 15)
	private String descriptiveText;

	@EnumeratedField(position = 22, length = 1, mappings = {
			@EnumerationMapping(value = "F", mapsTo = "SURFACE"),
			@EnumerationMapping(value = "U", mapsTo = "UPLIFT"),
			@EnumerationMapping(value = "D", mapsTo = "FAULTING") })
	private DiastrophismCode diastrophismCode;
}
