package com.seismap.service.parser;

import com.seismap.service.parser.annotation.CharacterField;
import com.seismap.service.parser.annotation.Entry;
import com.seismap.service.parser.annotation.EnumeratedField;
import com.seismap.service.parser.annotation.EnumerationMapping;
import com.seismap.service.parser.annotation.StringField;
import com.seismap.service.parser.annotation.Whitespace;
import com.seismap.service.parser.enumeration.InstrumentType;
import com.seismap.service.parser.enumeration.Phase;

@Entry(value = '4', type = EntryType.FOUR, whitespaces = {
		@Whitespace(position = 1, length = 1)
		})
public class Type4Entry extends AbstractEntry{

	
	@StringField(position = 2, length = 5)
	private String stationName;
	
	@EnumeratedField(position = 7, length = 1, mappings = {
			@EnumerationMapping(value = "S", mapsTo = "SP"),
			@EnumerationMapping(value = "I", mapsTo = "IP"),
			@EnumerationMapping(value = "L", mapsTo = "LP") })
	private InstrumentType instrumentType;
	
	
	@CharacterField(position = 8)	
	private char component;
	
	@CharacterField(position = 9)	
	private char weight;
	
	@CharacterField(position = 10)	
	private char qualityIndicator;
	
	@EnumeratedField(position = 11, length = 2, mappings = {
			@EnumerationMapping(value = "PN", mapsTo = "PN"),
			@EnumerationMapping(value = "PG", mapsTo = "PG"),
			@EnumerationMapping(value = "LG", mapsTo = "LG"),
			@EnumerationMapping(value = "P", mapsTo = "P"),
			@EnumerationMapping(value = "S", mapsTo = "S")
			})
	private Phase phase;
	
	
	
	
	
	
	
}
