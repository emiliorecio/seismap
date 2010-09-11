package com.seismap.service.parser.annotation;

public @interface CharacterColumn {

	public int position();
	
	public EnumerationMapping[] enumerationMappings();
	
}
